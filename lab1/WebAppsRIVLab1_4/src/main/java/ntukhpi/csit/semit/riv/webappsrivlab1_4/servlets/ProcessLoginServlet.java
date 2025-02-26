package ntukhpi.csit.semit.riv.webappsrivlab1_4.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The ProcessLoginServlet class handles login requests by validating the user's credentials
 * and redirecting to different pages based on the authentication result.
 *
 * <p>This servlet is mapped to the URL pattern "/process-login" and handles both GET and POST requests.
 * Upon receiving a login request, it compares the provided login and password with predefined values.
 * If the credentials are valid, the user is redirected to the welcome page; otherwise, they are
 * redirected to an error page.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "ProcessLoginServlet", value = "/process-login")
public class ProcessLoginServlet extends HttpServlet {
    private final String LOGIN = "irepeshko";
    private final String PASSWORD = "password";

    /**
     * Handles HTTP GET requests by delegating them to the {@link #doPost} method.
     *
     * @param request  the {@link HttpServletRequest} object containing the client's request
     * @param response the {@link HttpServletResponse} object containing the servlet's response
     * @throws IOException      if an input or output error occurs while handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    /**
     * Handles HTTP POST requests by validating the user's credentials.
     *
     * <p>This method retrieves the login and password parameters from the request and compares them to predefined values.
     * If the credentials match, the user is logged in, and the login is stored in the servlet context.
     * The response is redirected to the welcome page.
     * If the credentials do not match, the user is redirected to an unauthorized error page.
     *
     * @param request  the {@link HttpServletRequest} object containing the client's request
     * @param response the {@link HttpServletResponse} object containing the servlet's response
     * @throws IOException      if an input or output error occurs while handling the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login.equals(LOGIN) && password.equals(PASSWORD)) {
            getServletContext().setAttribute("login", login);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(request.getContextPath() + "/hello-web-world?login=" + login);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(request.getContextPath() + "/unauthorized-error");
        }
    }
}
