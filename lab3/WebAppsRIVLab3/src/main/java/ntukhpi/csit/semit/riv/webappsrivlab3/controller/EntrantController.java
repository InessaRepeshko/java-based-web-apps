package ntukhpi.csit.semit.riv.webappsrivlab3.controller;

import jakarta.validation.Valid;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EntrantController {
    private static final Logger logger = LoggerFactory.getLogger(EntrantController.class);
    private EntrantService entrantService;

    public EntrantController(EntrantService entrantService) {
        super();
        this.entrantService = entrantService;
    }

    @GetMapping("/entrants")
    public String showEntrantTable(
            @RequestParam(value = "search", required = false) String search,
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
        } catch (Exception e) {
            model.addAttribute("action", "load table");
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "entity/entrant/EntrantTable";
    }

    @GetMapping("/entrants/add")
    public String showCreateEntrantForm(Model model) {
        /*Entrant entrant = new Entrant("КН22-0", "Репешко", "Інесса", "Віталіївна", "01.01.2000", "FALSE", "120.001");*/
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
                throw new IllegalArgumentException(result.getAllErrors().toString());
            }

            Entrant savedEntrant = entrantService.saveEntrant(entrantToSave);

            redirectAttributes.addFlashAttribute("entrant", savedEntrant);
            redirectAttributes.addFlashAttribute("successMessage", "The record successfully saved to the database.");
            redirectAttributes.addFlashAttribute("isSaved", true);

            String redirectUrl = UriComponentsBuilder.fromPath("/entrants/{id}/edit")
                    .buildAndExpand(savedEntrant.getId())
                    .toUriString();

            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            model.addAttribute("entrant", entrantToSave);
            model.addAttribute("action", "save");
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/entrants/{id}/edit")
    public String updateEntrant(@Valid @ModelAttribute("entrant") Entrant entrantToUpdate,
                                BindingResult result,
                                Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new IllegalArgumentException(result.getAllErrors().toString());
            }

            Entrant updatedEntrant = entrantService.updateEntrant(entrantToUpdate);

            model.addAttribute("entrant", updatedEntrant);
            model.addAttribute("successMessage", "The record successfully updated in the database.");

            return "entity/entrant/EntrantForm";
        } catch (Exception e) {
            model.addAttribute("entrant", entrantToUpdate);
            model.addAttribute("action", "update");
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
        } catch (Exception e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/entrants/{id}/delete")
    public String deleteEntrant(@Valid @ModelAttribute("entrant") Entrant entrantToDelete,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        model.addAttribute("mode", Mode.DELETE);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new IllegalArgumentException(result.getAllErrors().toString());
            }

            entrantService.deleteEntrant(entrantToDelete);

            redirectAttributes.addFlashAttribute("successMessage", "The record successfully deleted from the database.");

            return "redirect:/entrants";
        } catch (Exception e) {
            model.addAttribute("entrant", entrantToDelete);
            model.addAttribute("action", "delete");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/entrant/EntrantForm";
        }
    }
}
