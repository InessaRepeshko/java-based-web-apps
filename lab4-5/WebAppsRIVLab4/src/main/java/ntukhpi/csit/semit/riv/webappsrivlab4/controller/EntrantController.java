package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for managing operations on Entrants.
 * This class provides methods to handle CRUD (Create, Read, Update, Delete) operations
 * for Entrant entities, along with features like filtering and sorting.
 * <p>
 * Key functionalities:
 * - Viewing the list of entrants with filtering and sorting options.
 * - Adding new entrants to the database.
 * - Editing existing entrants and saving updates.
 * - Viewing entrant details in a read-only mode.
 * - Deleting entrants from the database with confirmation.
 * <p>
 * Each operation includes exception handling to manage potential validation errors
 * or service layer exceptions, ensuring user-friendly error messages and smooth redirection.
 * <p>
 * Dependencies:
 * - EntrantService: Handles business logic and interactions with the data layer.
 * <p>
 * Model attributes such as `mode` are used to determine the current operation
 * (e.g., VIEW_TABLE, ADD, EDIT, DELETE) and adapt the view dynamically.
 *
 * @author Inessa Repeshko CS-222a
 * @see Entrant
 * @see EntrantService
 * @see CustomServiceException
 * @see BindingResult
 * @see ConstraintViolationException
 * @see Mode
 * @see UriComponentsBuilder
 * @see RedirectAttributes
 */

@Controller
@Validated
public class EntrantController {
    private static final Logger logger = LoggerFactory.getLogger(EntrantController.class);
    private final EntrantService entrantService;

    @Autowired
    public EntrantController(EntrantService entrantService) {
        this.entrantService = entrantService;
    }

    @GetMapping("/entrants")
    public String showEntrantTable(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "birthdayStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdayStart,
                                   @RequestParam(value = "birthdayEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdayEnd,
                                   @RequestParam(value = "ratingScoreMin", required = false) Double ratingScoreMin,
                                   @RequestParam(value = "ratingScoreMax", required = false) Double ratingScoreMax,
                                   @RequestParam(value = "sort", required = false, defaultValue = "id-asc") String sort,
                                   Model model) {
        model.addAttribute("fields", Entrant.getFieldNamesAsFormattedStrings());
        model.addAttribute("mode", Mode.VIEW_TABLE);

        try {
            List<Entrant> entrants = entrantService.getFilteredAndSortedEntrants(
                    search,
                    birthdayStart,
                    birthdayEnd,
                    ratingScoreMin,
                    ratingScoreMax,
                    sort);

            model.addAttribute("entrants", entrants);
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Failed to load table");
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "entity/entrant/EntrantTable";
    }

    @GetMapping("/entrants/add")
    public String showCreateEntrantForm(Model model) {
        Entrant entrant = new Entrant();
        
        model.addAttribute("entrant", entrant);
        model.addAttribute("mode", Mode.ADD);

        return "entity/entrant/EntrantForm";
    }

    @PostMapping("/entrants/add")
    public String saveEntrant(@Valid @ModelAttribute("entrant") Entrant entrantToSave,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        model.addAttribute("mode", Mode.ADD);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            Entrant savedEntrant = entrantService.saveEntrant(entrantToSave);

            redirectAttributes.addFlashAttribute("entrant", savedEntrant);
            redirectAttributes.addFlashAttribute("successTitle", "Successfully saved");
            redirectAttributes.addFlashAttribute("successMessage",
                    "The record successfully saved to the database.");
            redirectAttributes.addFlashAttribute("isSaved", true);

            String redirectUrl = UriComponentsBuilder.fromPath("/entrants/{id}/edit")
                    .buildAndExpand(savedEntrant.getId())
                    .toUriString();

            return "redirect:" + redirectUrl;
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("entrant", entrantToSave);
            model.addAttribute("errorTitle", "Failed to save");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/entrant/EntrantForm";
        }
    }

    @GetMapping("/entrants/{id}/view")
    public String showViewEntrantForm(@PathVariable Long id,
                                      Model model) {
        model.addAttribute("mode", Mode.VIEW_FORM);

        try {
            Entrant entrantToView = entrantService.findEntrantById(id);

            model.addAttribute("entrant", entrantToView);

            return "entity/entrant/EntrantForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @GetMapping("/entrants/{id}/edit")
    public String showUpdateEntrantForm(@PathVariable Long id,
                                        Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            Entrant entrantToUpdate = entrantService.findEntrantById(id);

            model.addAttribute("entrant", entrantToUpdate);

            return "entity/entrant/EntrantForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/entrants/{id}/edit")
    public String updateEntrant(@Valid @ModelAttribute("entrant") Entrant entrantToUpdate,
                                @PathVariable Long id,
                                BindingResult result,
                                Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new CustomServiceException(result.getAllErrors().toString());
            }

            Entrant updatedEntrant = null;

            if (id.equals(entrantToUpdate.getId())) {
                updatedEntrant = entrantService.updateEntrant(entrantToUpdate);
            } else {
                throw new CustomServiceException("The ID of the entrant to update does not match the ID in the route." +
                        " Check the data and try again.");
            }

            model.addAttribute("entrant", updatedEntrant);
            model.addAttribute("successTitle", "Successfully updated");
            model.addAttribute("successMessage",
                    "The record successfully updated in the database.");

            return "entity/entrant/EntrantForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("entrant", entrantToUpdate);
            model.addAttribute("errorTitle", "Failed to update");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/entrant/EntrantForm";
        }
    }

    @GetMapping("/entrants/{id}/delete")
    public String showDeleteEntrantForm(@PathVariable Long id,
                                        Model model) {
        model.addAttribute("mode", Mode.DELETE);

        try {
            Entrant entrantToDelete = entrantService.findEntrantById(id);

            model.addAttribute("entrant", entrantToDelete);

            return "entity/entrant/EntrantForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/entrants/{id}/delete")
    public String deleteEntrant(@Valid @ModelAttribute("entrant") Entrant entrantToDelete,
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

            if (id.equals(entrantToDelete.getId())) {
                entrantService.deleteEntrantById(entrantToDelete.getId());
            } else {
                throw new CustomServiceException("The ID of the entrant to delete does not match the ID in the route." +
                        " Check the data and try again.");
            }

            redirectAttributes.addFlashAttribute("successTitle", "Successfully deleted");
            redirectAttributes.addFlashAttribute("successMessage",
                    "The record successfully deleted from the database.");

            return "redirect:/entrants";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("entrant", entrantToDelete);
            model.addAttribute("errorTitle", "Failed to delete");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/entrant/EntrantForm";
        }
    }
}
