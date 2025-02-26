package ntukhpi.csit.semit.riv.webappsrivlab2.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * ProcessLoginServlet class handles login requests for the "/process-login" URL.
 * It verifies user credentials by comparing the provided login and password with predefined values.
 * If the credentials are correct, it creates a session and redirects the user to the home page.
 * Otherwise, it redirects to an unauthorized error page.
 * This servlet supports both GET and POST requests, although GET requests are internally
 * forwarded to the POST handler.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "ProcessLoginServlet", value = "/process-login")
public class ProcessLoginServlet extends HttpServlet {
    private static final String LOGIN = "irepeshko";
    private static final String PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login.equals(LOGIN) && password.equals(PASSWORD)) {
            HttpSession session = request.getSession();
            session.setAttribute("userLogin", login);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(request.getContextPath() + "/unauthorized-error");
        }
    }
}
