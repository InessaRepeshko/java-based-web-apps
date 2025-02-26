package ntukhpi.csit.semit.riv.webappsrivlab2.controller.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * UnauthorizedErrorServlet class handles requests to the "/unauthorized-error" URL.
 * It forwards the request to the Unauthorized.jsp view, which displays an error page
 * informing the user that they are not authorized to access a specific resource.
 *
 * This servlet only handles GET requests, and it sets the response status to HTTP 200 (OK)
 * before forwarding the request to the error page.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "UnauthorizedErrorServlet", value = "/unauthorized-error")
public class UnauthorizedErrorServlet extends HttpServlet {
    private static final String VIEW_PATH = "/view/error/Unauthorized.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_PATH);
        dispatcher.forward(request, response);
    }
}
