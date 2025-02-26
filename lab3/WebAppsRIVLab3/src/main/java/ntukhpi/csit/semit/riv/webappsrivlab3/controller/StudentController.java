package ntukhpi.csit.semit.riv.webappsrivlab3.controller;

import jakarta.validation.Valid;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private StudentService studentService;
    private EntrantService entrantService;

    public StudentController(StudentService studentService, EntrantService entrantService) {
        super();
        this.studentService = studentService;
        this.entrantService = entrantService;
    }

    @GetMapping("/students")
    public String showStudentTable(
            @RequestParam(value = "search", required = false) String search,
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
        } catch (Exception e) {
            model.addAttribute("action", "load table");
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "entity/student/StudentTable";
    }
    
    @GetMapping("/students/add")
    public String showCreateStudentForm(Model model) {
        Student student = new Student();
        /*student.setEntrant(entrantService.findEntrantById(35L));
        student.setFundingType(FundingType.BUDGET);
        student.setScholarshipStatus(ScholarshipStatus.ORDINARY);
        student.setCorporateEmail("regina.marchyk@cs.khpi.edu.ua");*/

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
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new IllegalArgumentException(result.getAllErrors().toString());
            }

            Student savedStudent = studentService.saveStudent(studentToSave);

            redirectAttributes.addFlashAttribute("student", savedStudent);
            redirectAttributes.addFlashAttribute("successMessage", "The record successfully saved to the database.");
            redirectAttributes.addFlashAttribute("isSaved", true);

            String redirectUrl = UriComponentsBuilder.fromPath("/students/{id}/edit")
                    .buildAndExpand(savedStudent.getId())
                    .toUriString();

            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToSave);
            model.addAttribute("action", "save");
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/students/{id}/edit")
    public String updateStudent(@Valid @ModelAttribute("student") Student studentToUpdate,
                                BindingResult result,
                                Model model) {
        model.addAttribute("mode", Mode.EDIT);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new IllegalArgumentException(result.getAllErrors().toString());
            }

            Student updatedStudent = studentService.updateStudent(studentToUpdate);
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("student", updatedStudent);
            model.addAttribute("entrantList", entrantList);
            model.addAttribute("successMessage", "The record successfully updated in the database.");

            return "entity/student/StudentForm";
        } catch (Exception e) {
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToUpdate);
            model.addAttribute("action", "update");
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
        } catch (Exception e) {
            model.addAttribute("errorTitle", "Page Not Found Error");
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Oops! Looks like the page doesn't exist.");
            model.addAttribute("errorException", e.getMessage());

            return "error/ErrorPage";
        }
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@Valid @ModelAttribute("student") Student studentToDelete,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        model.addAttribute("mode", Mode.DELETE);

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(e -> logger.error(e.toString()));
                throw new IllegalArgumentException(result.getAllErrors().toString());
            }

            studentService.deleteStudent(studentToDelete);

            redirectAttributes.addFlashAttribute("successMessage", "The record successfully deleted from the database.");

            return "redirect:/students";
        } catch (Exception e) {
            List<Entrant> entrantList = entrantService.findEntrantsWithoutStudents();

            model.addAttribute("entrantList", entrantList);
            model.addAttribute("student", studentToDelete);
            model.addAttribute("action", "delete");
            model.addAttribute("errorMessage", e.getMessage());

            return "entity/student/StudentForm";
        }
    }
}
