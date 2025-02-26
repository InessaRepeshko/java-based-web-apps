package ntukhpi.csit.semit.riv.webappsrivlab3.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("userLogin")
public class MainController {
    @GetMapping("/")
    public String showStartPage() {
        return "login/Login";
    }

    private static final String LOGIN = "irepeshko";
    private static final String PASSWORD = "password";

    @PostMapping("/process-login")
    public String processLogin(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        if (LOGIN.equals(login) && PASSWORD.equals(password)) {
            session.setAttribute("userLogin", login);
            model.addAttribute("userLogin", login);

            return "redirect:/home";
        } else {
            return "redirect:/unauthorized-error";
        }
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "home/Home";
    }

    @GetMapping("/unauthorized-error")
    public String showUnauthorizedErrorPage(Model model) {
        model.addAttribute("errorTitle", "Unauthorized Error");
        model.addAttribute("errorCode", 401);
        model.addAttribute("errorMessage", "Unauthorized access. Please check your credentials.");

        return "error/ErrorPage";
    }

}
