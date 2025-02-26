package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserForm;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Controller class for managing user-related operations.
 * This class provides methods for handling CRUD (Create, Read, Update, Delete) operations
 * for the `User` entity, as well as filtering and sorting functionalities.
 * <p>
 * Key functionalities:
 * - Viewing a list of users with optional filtering and sorting.
 * - Adding new users and sending welcome emails with password setup instructions.
 * - Editing and updating user details, including roles and personal information.
 * - Deleting users while ensuring proper validation and error handling.
 * <p>
 * Exception handling ensures that validation errors and service-level exceptions are
 * properly managed and communicated to the user with meaningful messages.
 * <p>
 * Dependencies:
 * - `UserService`: Handles business logic for managing users.
 * - `MailController`: Handles email notifications, such as welcome emails.
 * <p>
 * View modes are managed using the `Mode` enum to dynamically adapt UI behavior
 * based on the current operation (e.g., VIEW_TABLE, ADD, EDIT, DELETE).
 * <p>
 * Relationships between users and other entities are maintained to ensure
 * data consistency during operations such as creation and deletion.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserForm
 * @see UserEntity
 * @see UserService
 * @see MailController
 * @see Mode
 * @see CustomServiceException
 */

@Controller
@Validated
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUserTable(@RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "role", required = false) String role,
                                @RequestParam(value = "sort", required = false, defaultValue = "id-asc") String sort,
                                Model model) {
        model.addAttribute("mode", Mode.VIEW_TABLE);
        model.addAttribute("fields", UserForm.getFieldNamesAsFormattedStrings());

        try {
            List<UserEntity> userEntities = userService.getFilteredAndSortedUsers(search, role, sort);
            List<UserForm> users = userEntities.stream()
                    .map(UserForm::getUserFormFromUserEntity)
                    .toList();

            model.addAttribute("users", users);
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Failed to load table");
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "entity/user/UserTable";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("mode", Mode.ADD);

        UserForm user = new UserForm(
                null,
                "Марчук",
                "Регіна",
                "Віталіївна",
                "regina.marchuk@cs.khpi.edu.ua",
                "regina.marchuk",
                Role.STUDENT_VIEWER);

        model.addAttribute("user", user);
        model.addAttribute("roleList", Role.getAllRoles());

        return "entity/user/UserForm";
    }

    @PostMapping("/users/add")
    public String saveUser(@Valid @ModelAttribute("user") UserForm userToSave,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model,
                           HttpServletRequest request) {
        model.addAttribute("mode", Mode.ADD);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            UserEntity savedUserEntity = userService.saveUser(
                    UserEntity.getUserEntityFromUserForm(
                            userToSave,
                            null));

            MailController.sendWelcomeMail(savedUserEntity, request);

            redirectAttributes.addFlashAttribute("user", savedUserEntity);
            redirectAttributes.addFlashAttribute("successTitle", "Successfully saved");
            redirectAttributes.addFlashAttribute("successMessage",
                    "The record successfully saved to the database.\n"
                            + "A password setup email was sent to '" + savedUserEntity.getCorporateEmail() + "'.");
            redirectAttributes.addFlashAttribute("isSaved", true);

            String redirectUrl = UriComponentsBuilder.fromPath("/users/{id}/edit")
                    .buildAndExpand(savedUserEntity.getId())
                    .toUriString();

            return "redirect:" + redirectUrl;
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("user", userToSave);
            model.addAttribute("roleList", Role.getAllRoles());
            model.addAttribute("errorTitle", "Failed to save");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/user/UserForm";
        }
    }

    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable Long id,
                                   Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            UserForm userToUpdate = UserForm.getUserFormFromUserEntity(
                    userService.findUserById(id));

            model.addAttribute("user", userToUpdate);
            model.addAttribute("roleList", Role.getAllRoles());

            return "entity/user/UserForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@Valid @ModelAttribute("user") UserForm userToUpdate,
                             @PathVariable Long id,
                             BindingResult result,
                             Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            UserForm updatedUser;
            UserEntity userEntity = userService.findUserById(userToUpdate.getId());

            if (id.equals(userToUpdate.getId())
                    && userEntity != null
                    && id.equals(userEntity.getId())) {
                UserEntity userEntityToSave = UserEntity.getUserEntityFromUserForm(
                        userToUpdate,
                        userEntity.getPassword());
                UserEntity updatedUserEntity = userService.updateUser(userEntityToSave);
                updatedUser = UserForm.getUserFormFromUserEntity(updatedUserEntity);
            } else {
                throw new CustomServiceException("The ID of the user to update does not match the ID in the route " +
                        "or the user with the specified ID does not exist. Check the data and try again.");
            }

            model.addAttribute("user", updatedUser);
            model.addAttribute("roleList", Role.getAllRoles());
            model.addAttribute("successTitle", "Successfully updated");
            model.addAttribute("successMessage",
                    "The record successfully updated in the database.");

            return "entity/user/UserForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("user", userToUpdate);
            model.addAttribute("roleList", Role.getAllRoles());
            model.addAttribute("errorTitle", "Failed to update");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/user/UserForm";
        }
    }

    @GetMapping("/users/{id}/delete")
    public String showDeleteUserForm(@PathVariable Long id,
                                     Model model) {
        model.addAttribute("mode", Mode.DELETE);

        try {
            UserForm userToDelete = UserForm.getUserFormFromUserEntity(
                    userService.findUserById(id));

            model.addAttribute("roleList", Role.getAllRoles());
            model.addAttribute("user", userToDelete);

            return "entity/user/UserForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@ModelAttribute("user") UserForm userToDelete,
                             @PathVariable Long id,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        model.addAttribute("mode", Mode.DELETE);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            UserEntity userEntity = userService.findUserById(userToDelete.getId());

            if (id.equals(userToDelete.getId())
                    && userEntity != null
                    && id.equals(userEntity.getId())) {
                userService.deleteUserById(userEntity.getId());
            } else {
                throw new CustomServiceException("The ID of the user to delete does not match the ID in the route " +
                        "or the user with the specified ID does not exist. Check the data and try again.");
            }

            redirectAttributes.addFlashAttribute("successTitle", "Successfully deleted");
            redirectAttributes.addFlashAttribute("successMessage",
                    "The record successfully deleted from the database.");

            return "redirect:/users";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("user", userToDelete);
            model.addAttribute("roleList", Role.getAllRoles());
            model.addAttribute("errorTitle", "Failed to delete");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/user/UserForm";
        }
    }
}
