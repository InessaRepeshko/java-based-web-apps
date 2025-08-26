package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.StudentService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Controller class for managing operations related to students.
 * This class provides methods for handling CRUD (Create, Read, Update, Delete) operations
 * for the `Student` entity, as well as filtering and sorting functionalities.
 * <p>
 * Key functionalities:
 * - Viewing a list of students with optional filtering and sorting.
 * - Adding new students, including associating them with entrants.
 * - Editing and updating existing students' information.
 * - Viewing details of a specific student.
 * - Deleting students and removing their association with entrants.
 * <p>
 * Exception handling ensures that validation errors and service-level exceptions are
 * properly managed and communicated to the user with meaningful messages.
 * <p>
 * Dependencies:
 * - `StudentService`: Handles business logic for managing students.
 * - `EntrantService`: Handles business logic for managing associated entrants.
 * <p>
 * View modes are managed using the `Mode` enum to dynamically adapt UI behavior
 * based on the current operation (e.g., VIEW_TABLE, ADD, EDIT, DELETE).
 * <p>
 * Relationships between `Student` and `Entrant` entities are maintained,
 * ensuring data consistency during operations such as creation and deletion.
 *
 * @author Inessa Repeshko CS-222a
 * @see Student
 * @see Entrant
 * @see StudentService
 * @see EntrantService
 * @see Mode
 * @see CustomServiceException
 */

@Controller
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final EntrantService entrantService;

    @Autowired
    public StudentController(StudentService studentService, EntrantService entrantService) {
        this.studentService = studentService;
        this.entrantService = entrantService;
    }

    @GetMapping("/students")
    public String showStudentTable(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "fundingType", required = false) String fundingType,
                                   @RequestParam(value = "scholarshipStatus", required = false) String scholarshipStatus,
                                   @RequestParam(value = "sort", required = false, defaultValue = "id-asc") String sort,
                                   Model model) {
        model.addAttribute("fields", Student.getFieldNamesAsFormattedStrings());
        model.addAttribute("mode", Mode.VIEW_TABLE);

        try {
            List<Student> students = studentService.getFilteredAndSortedStudents(
                    search,
                    fundingType,
                    scholarshipStatus,
                    sort);

            model.addAttribute("students", students);
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Failed to load table");
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "entity/student/StudentTable";
    }

    @GetMapping("/students/add")
    public String showCreateStudentForm(Model model) {
        Student student = new Student();
        
        Entrant emptyEntrant = new Entrant();
        student.setEntrant(emptyEntrant);

        List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

        model.addAttribute("student", student);
        model.addAttribute("entrantList", entrantList);
        model.addAttribute("mode", Mode.ADD);

        return "entity/student/StudentForm";
    }

    @PostMapping("/students/add")
    public String saveStudent(@Valid @ModelAttribute("student") Student studentToSave,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        model.addAttribute("mode", Mode.ADD);

        try {
            if (studentToSave.getEntrant() == null || studentToSave.getEntrant().getId() == null) {
                result.rejectValue("entrant.id", "NotNull", "Entrant not selected for student creation");
                logger.error("Entrant not selected for student creation");
                throw new CustomServiceException("Entrant not selected for student creation");
            }

            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                StringBuilder errorMessage = new StringBuilder("Student creation failed:\n");
                result.getAllErrors().forEach(e -> errorMessage.append(e.getDefaultMessage()).append(".\n"));
                throw new CustomServiceException(errorMessage.toString());
            }

            studentToSave.setEntrant(entrantService.findEntrantById(studentToSave.getEntrant().getId()));
            Student savedStudent = studentService.saveStudent(studentToSave);

            Entrant entrantToUpdate = entrantService.findEntrantById(studentToSave.getEntrant().getId());
            entrantToUpdate.setStudentId(studentToSave.getId());
            entrantService.updateEntrant(entrantToUpdate);

            redirectAttributes.addFlashAttribute("student", savedStudent);
            redirectAttributes.addFlashAttribute("successTitle", "Successfully saved");
            redirectAttributes.addFlashAttribute("successMessage",
                    "The record successfully saved to the database.");
            redirectAttributes.addFlashAttribute("isSaved", true);

            String redirectUrl = UriComponentsBuilder.fromPath("/students/{id}/edit")
                    .buildAndExpand(savedStudent.getId())
                    .toUriString();

            return "redirect:" + redirectUrl;
        } catch (CustomServiceException | ConstraintViolationException e) {
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToSave);
            model.addAttribute("errorTitle", "Failed to save");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/student/StudentForm";
        }
    }

    @GetMapping("/students/{id}/view")
    public String showViewStudentForm(@PathVariable Long id,
                                      Model model) {
        model.addAttribute("mode", Mode.VIEW_FORM);

        try {
            Student studentToUpdate = studentService.findStudentById(id);
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("student", studentToUpdate);
            model.addAttribute("entrantList", entrantList);

            return "entity/student/StudentForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @GetMapping("/students/{id}/edit")
    public String showUpdateStudentForm(@PathVariable Long id,
                                        Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            Student studentToUpdate = studentService.findStudentById(id);
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("student", studentToUpdate);
            model.addAttribute("entrantList", entrantList);

            return "entity/student/StudentForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/students/{id}/edit")
    public String updateStudent(@Valid @ModelAttribute("student") Student studentToUpdate,
                                @PathVariable Long id,
                                BindingResult result,
                                Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            // Перевіряємо чи обрано entrant
            if (studentToUpdate.getEntrant() == null || studentToUpdate.getEntrant().getId() == null) {
                result.rejectValue("entrant.id", "NotNull", "Entrant not selected for student update");
                logger.error("Entrant not selected for student update");
                throw new CustomServiceException("Entrant not selected for student update");
            }

            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                StringBuilder errorMessage = new StringBuilder("Student update failed:\n");
                result.getAllErrors().forEach(e -> errorMessage.append(e.getDefaultMessage()).append(".\n"));
                throw new CustomServiceException(errorMessage.toString());
            }

            Student updatedStudent;

            if (id.equals(studentToUpdate.getId())) {
                studentToUpdate.setEntrant(entrantService.findEntrantById(studentToUpdate.getEntrant().getId()));
                updatedStudent = studentService.updateStudent(studentToUpdate);
            } else {
                throw new CustomServiceException("The ID of the student to update does not match the ID in the route." +
                        " Check the data and try again.");
            }

            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("student", updatedStudent);
            model.addAttribute("entrantList", entrantList);
            model.addAttribute("successTitle", "Successfully updated");
            model.addAttribute("successMessage",
                    "The record successfully updated in the database.");

            return "entity/student/StudentForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToUpdate);
            model.addAttribute("errorTitle", "Failed to update");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/student/StudentForm";
        }
    }

    @GetMapping("/students/{id}/delete")
    public String showDeleteStudentForm(@PathVariable Long id,
                                        Model model) {
        model.addAttribute("mode", Mode.DELETE);

        try {
            Student studentToDelete = studentService.findStudentById(id);
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToDelete);

            return "entity/student/StudentForm";
        } catch (CustomServiceException | ConstraintViolationException e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@Valid @ModelAttribute("student") Student studentToDelete,
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

            if (id.equals(studentToDelete.getId())) {
                studentService.deleteStudent(studentToDelete.getId());
            } else {
                throw new CustomServiceException("The ID of the student to delete does not match the ID in the route." +
                        " Check the data and try again.");
            }

            redirectAttributes.addFlashAttribute("successTitle", "Successfully deleted");
            redirectAttributes.addFlashAttribute("successMessage",
                    "The record successfully deleted from the database.");

            return "redirect:/students";
        } catch (CustomServiceException | ConstraintViolationException e) {
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToDelete);
            model.addAttribute("errorTitle", "Failed to delete");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/student/StudentForm";
        }
    }
}
