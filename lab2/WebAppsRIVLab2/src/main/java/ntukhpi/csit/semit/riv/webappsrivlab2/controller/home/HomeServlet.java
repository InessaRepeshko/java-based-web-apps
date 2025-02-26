package ntukhpi.csit.semit.riv.webappsrivlab2.controller.home;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * HomeServlet class handles GET requests for the "/home" URL.
 * It checks if a user is logged in by retrieving the "userLogin" attribute from the session.
 * If the session exists and the user is logged in, the login information is passed as a request attribute.
 * The servlet then forwards the request to the home page view.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private static final String VIEW_PATH = "/view/home/Home.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String login;

        if (session != null) {
            login = (String) session.getAttribute("userLogin");

            if (login != null && !login.isEmpty()) {
                request.setAttribute("login", login);
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_PATH);
        dispatcher.forward(request, response);
    }
}
