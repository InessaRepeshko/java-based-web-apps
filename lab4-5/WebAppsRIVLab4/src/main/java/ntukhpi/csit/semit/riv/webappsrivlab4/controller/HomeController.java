package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import ntukhpi.csit.semit.riv.webappsrivlab4.config.EncoderConfig;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserChangeCredentials;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserForm;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing the user's home and profile-related operations.
 * This class provides endpoints for viewing the home page, managing the user's profile,
 * and updating credentials such as username and password.
 * <p>
 * Key functionalities:
 * - Displaying the home page.
 * - Viewing and updating the user's profile information.
 * - Changing user credentials (username and password) securely.
 * - Handling unauthorized access errors with a user-friendly error page.
 * <p>
 * Exception handling ensures that validation errors and service-level exceptions
 * are properly managed, providing meaningful error messages to the user.
 * <p>
 * Dependencies:
 * - UserService: Provides user-related business logic and database interactions.
 * - EncoderConfig: Handles password encryption and verification.
 * <p>
 * SecurityContextHolder is used to manage the authenticated user's session,
 * ensuring that user-specific data is retrieved dynamically.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserService
 * @see EncoderConfig
 * @see UserEntity
 * @see UserForm
 * @see UserChangeCredentials
 * @see SecurityContextHolder
 * @see CustomServiceException
 * @see ConstraintViolationException
 * @see Cookie
 * @see HttpServletRequest
 * @see HttpServletResponse
 */

@Controller
@Validated
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final UserService userService;
    private final EncoderConfig encoderConfig;

    @Autowired
    public HomeController(UserService userService, EncoderConfig encoderConfig) {
        this.userService = userService;
        this.encoderConfig = encoderConfig;
    }

    public UserEntity getCurrentUser() {
        return userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication()
                        .getName());
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "home/Home";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        UserForm userForm = UserForm.getUserFormFromUserEntity(getCurrentUser());

        model.addAttribute("user", userForm);

        return "home/ProfileForm";
    }

    @GetMapping("/profile/change-credentials")
    public String showProfileChangePasswordPage(Model model) {
        UserChangeCredentials userChangeCredentials = new UserChangeCredentials();

        model.addAttribute("userChangeCredentials", userChangeCredentials);

        return "home/ChangeCredentialsForm";
    }

    @PostMapping("/profile/change-credentials")
    public String updateCredentials(@Valid @ModelAttribute("userChangeCredentials") UserChangeCredentials userChangeCredentials,
                                    BindingResult result,
                                    Model model,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            UserEntity userEntityToUpdate = getCurrentUser();
            UserChangeCredentials updatedUserCredentials = null;

            if (userChangeCredentials.getPassword() != null
                    && !userChangeCredentials.getPassword().isBlank()
                    && userChangeCredentials.getConfirmPassword() != null
                    && !userChangeCredentials.getConfirmPassword().isBlank()) {
                if (!userChangeCredentials.getPassword().equals(userChangeCredentials.getConfirmPassword())) {
                    throw new CustomServiceException("The new password and password confirmation are not identical." +
                            " Please, check the data and try again!");
                }

                if (encoderConfig.passwordEncoder().matches(
                        userChangeCredentials.getPassword(),
                        userEntityToUpdate.getPassword())) {
                    throw new CustomServiceException("The new password cannot be identical to the current password." +
                            " Please, check the data and try again!");
                }

                userEntityToUpdate.setPassword(encoderConfig.passwordEncoder().encode(userChangeCredentials.getPassword()));
            } else {
                throw new CustomServiceException("Invalid password data entered. " +
                        "Please, check the data and try again!");
            }

            if (userChangeCredentials.getUsername() != null
                    && !userChangeCredentials.getUsername().isBlank()) {
                if (!userChangeCredentials.getUsername().equals(userEntityToUpdate.getUsername())) {
                    UserEntity userWithUsername = null;

                    try  {
                        userWithUsername = userService.findUserByUsername(userChangeCredentials.getUsername());
                    } catch (CustomServiceException ignored) {
                    }

                    if (userWithUsername == null) {
                        userEntityToUpdate.setUsername(userChangeCredentials.getUsername());
                    } else  {
                        throw new CustomServiceException("The provided name already belongs to another user. " +
                                "Please, check the data and try again!");
                    }
                }
            } else {
                throw new CustomServiceException("Invalid username entered. " +
                        "Please, check the data and try again!");
            }

            updatedUserCredentials = UserChangeCredentials.getUserChangeCredentialsFromUserEntity(
                    userService.updateUser(userEntityToUpdate));

            model.addAttribute("userChangeCredentials", updatedUserCredentials);
            model.addAttribute("successTitle", "Successfully updated");
            model.addAttribute("successMessage",
                    "User credentials successfully updated in the database.");

            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return "home/ChangeCredentialsForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("roleList", Role.getAllRoles());
            model.addAttribute("userChangeCredentials", userChangeCredentials);
            model.addAttribute("errorTitle", "Failed to update");
            model.addAttribute("errorMessage", e.getMessage());

            return "home/ChangeCredentialsForm";
        }
    }

    @GetMapping("/errors/unauthorized")
    public String showUnauthorizedErrorPage(Model model) {
        model.addAttribute("errorTitle", "Unauthorized Error");
        model.addAttribute("errorCode", 401);
        model.addAttribute("errorMessage", "Unauthorized access. Please check your credentials.");

        return "error/ErrorPage";
    }
}
