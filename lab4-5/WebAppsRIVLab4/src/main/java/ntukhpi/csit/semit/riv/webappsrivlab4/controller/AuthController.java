package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.*;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.user.UserList;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.PasswordResetTokenService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller class for managing user authentication and authorization.
 * This class handles HTTP requests related to user login, registration, password reset, and password change processes.
 * It serves as a bridge between the service layer and the view layer, managing the flow of user-related data.
 * <p>
 * Key functionalities:
 * - Login: Displays the login page and handles authentication errors.
 * - Registration: Handles new user registration and ensures the validity of input data.
 * - Password Reset: Allows users to request and handle password reset operations via email.
 * - Password Change: Facilitates password update based on valid tokens.
 * <p>
 * Exception handling is incorporated to provide user-friendly error messages for various scenarios,
 * such as invalid credentials, expired sessions, and incorrect token usage.
 * <p>
 * Dependencies:
 * - UserService: For user-related operations such as registration, login, and updates.
 * - PasswordResetTokenService: For managing password reset tokens and their validation.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserService
 * @see PasswordResetTokenService
 * @see PasswordResetToken
 * @see UserEntity
 * @see UserTestData
 * @see UserLogin
 * @see UserRegister
 * @see UserResetPassword
 * @see UserChangePassword
 * @see CustomServiceException
 */

@Controller
@Validated
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final PasswordResetTokenService tokenService;

    private final List<UserTestData> userTestDataList;

    @Autowired
    public AuthController(UserService userService,
                          PasswordResetTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userTestDataList = UserList.INSTANCE.getUsers();
    }

    @GetMapping("/")
    public String showStartPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "sessionExpired", required = false) String sessionExpired,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorTitle", "Failed authorization");
            model.addAttribute("errorMessage", "Invalid user identifier or password entered. " +
                    "Please, check your credentials and try again!");
        }

        if (Boolean.parseBoolean(sessionExpired)) {
            model.addAttribute("warningTitle", "Session expired");
            model.addAttribute("warningMessage", "Your session expired. " +
                    "Please, sign in again!");
        }

        UserTestData userTestData = userTestDataList.get(0);
        UserLogin userLogin = UserLogin.getUserLoginFromUserTestData(userTestData);

        model.addAttribute("user", userLogin);

        return "auth/Login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        UserTestData userTestData = userTestDataList.get(0);
        UserRegister userRegister = UserRegister.getUserRegisterFromUserTestData(userTestData);

        model.addAttribute("user", userRegister);

        return "auth/Register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") UserRegister userRegister,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            if (userRegister.getPassword() != null
                    && !userRegister.getPassword().isBlank()
                    && userRegister.getConfirmPassword() != null
                    && !userRegister.getConfirmPassword().isBlank()) {
                if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
                    throw new CustomServiceException("The password and password confirmation are not identical. " +
                            "Please check the data and try again!");
                }
            } else {
                throw new CustomServiceException("Invalid password data entered. " +
                        "Please check the data and try again!");
            }

            UserEntity savedUserEntity = userService.saveUser(
                    UserEntity.getUserEntityFromUserRegister(userRegister));
            UserLogin userLogin = UserLogin.getUserLoginFromUserEntity(savedUserEntity);

            redirectAttributes.addFlashAttribute("user", userLogin);
            redirectAttributes.addFlashAttribute("successTitle", "Successfully registered.");
            redirectAttributes.addFlashAttribute("successMessage",
                    "You have successfully registered!");
            redirectAttributes.addFlashAttribute("isSaved", true);

            return "redirect:/login";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("user", userRegister);
            model.addAttribute("action", "save");
            model.addAttribute("errorTitle", "Failed to save");
            model.addAttribute("errorMessage", e.getMessage());

            return "auth/Register";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(Model model) {
        UserTestData userTestData = userTestDataList.get(1);
        UserResetPassword userResetPassword = UserResetPassword.getUserResetPasswordFromUserTestData(userTestData);

        model.addAttribute("user", userResetPassword);

        return "auth/ResetPassword";
    }

    @PostMapping("/reset-password")
    public String sendResetPasswordMail(@Valid @ModelAttribute("user") UserResetPassword userResetPassword,
                                        BindingResult result,
                                        Model model,
                                        HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            UserEntity userEntity = null;

            try {
                userEntity = userService.findUserByCorporateEmail(userResetPassword.getCorporateEmail());

                if (tokenService.findTokenByUserId(userEntity.getId()) != null) {
                    tokenService.deleteTokenByUserId(userEntity.getId());
                }
            } catch (CustomServiceException e) {
                logger.error("Unable to find a user to send a password recovery email. " + e.getMessage());
            }

            MailController.sendResetPasswordMail(userEntity, request);

            model.addAttribute("successTitle", "Successfully sent an email");
            model.addAttribute("successMessage",
                    "If the email address you entered is associated with an account, " +
                            "a password reset link has been sent to your email.\n" +
                            "Please check your inbox and follow the instructions to reset your password.");

            return "auth/ResetPassword";
        } catch (Exception e) {
            model.addAttribute("user", userResetPassword);
            model.addAttribute("errorTitle", "Failed to send an email");
            model.addAttribute("errorMessage", e.getMessage());

            return "auth/ResetPassword";
        }
    }

    @GetMapping("/change-password")
    public String changePassword(@RequestParam(value = "token", required = true) String token,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        try {
            PasswordResetToken foundToken = tokenService.findTokenByToken(token);

            if (tokenService.isValidToken(foundToken)) {

                UserEntity userEntity = userService.findUserByExample(foundToken.getUser());

                if (userEntity == null || userEntity.getId() == null) {
                    String message = "The user data in the token is invalid.";
                    logger.error(message);
                    throw new CustomServiceException(message);
                }

                UserChangePassword userChangePassword = new UserChangePassword();

                model.addAttribute("token", token);
                model.addAttribute("user", userChangePassword);

                return "auth/ChangePasswordForm";
            } else {
                String message = "The specified token is invalid or expired.";
                logger.error(message);
                throw new CustomServiceException(message);
            }
        } catch (CustomServiceException | ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("errorTitle", "Access failed");
            redirectAttributes.addFlashAttribute("errorMessage",
                    "The specified token is invalid or expired. " +
                            "Please, try to restore access to your account again!");

            return "redirect:/login";
        }
    }

    @PostMapping("/change-password")
    public String showChangePasswordPage(@NotNull @ModelAttribute("token") String token,
                                         @Valid @ModelAttribute("user") UserChangePassword userChangePassword,
                                         BindingResult result,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            PasswordResetToken foundToken = tokenService.findTokenByToken(token);

            if (tokenService.isValidToken(foundToken)) {
                UserEntity userEntityToUpdate = userService.findUserById(foundToken.getUser().getId());

                if (userEntityToUpdate == null || userEntityToUpdate.getId() == null) {
                    String message = "The user data in the token is invalid.";
                    logger.error(message);
                    throw new CustomServiceException(message);
                }

                if (userChangePassword.getPassword() != null
                        && !userChangePassword.getPassword().isBlank()
                        && userChangePassword.getConfirmPassword() != null
                        && !userChangePassword.getConfirmPassword().isBlank()) {
                    if (!userChangePassword.getPassword().equals(userChangePassword.getConfirmPassword())) {
                        throw new CustomServiceException("The password and password confirmation are not identical. " +
                                "Please check the data and try again!");
                    }
                } else {
                    throw new CustomServiceException("Invalid password data entered. " +
                            "Please check the data and try again!");
                }

                userEntityToUpdate.setPassword(userChangePassword.getPassword());
                userService.updateUser(userEntityToUpdate);

                redirectAttributes.addFlashAttribute("token", token);
                redirectAttributes.addFlashAttribute("successTitle", "Successfully updated password");
                redirectAttributes.addFlashAttribute("successMessage",
                        "Your password successfully updated. Please, sign in!");

                return "redirect:/login";
            } else {
                String message = "The passed token is invalid or expired.";
                logger.error(message);
                throw new CustomServiceException(message);
            }
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Access failed");
            model.addAttribute("errorMessage",
                    "The passed token is invalid or expired. " +
                            "Please, try to restore access to your account again!");

            return "auth/ChangePasswordForm";
        }
    }
}
