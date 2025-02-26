package ntukhpi.csit.semit.riv.webappsrivlab2.controller.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * PageNotFoundErrorServlet class handles requests to the "/page-not-found-error" URL.
 * It forwards the request to the PageNotFound.jsp view, which displays a 404 error page
 * informing the user that the requested page could not be found.
 *
 * This servlet handles GET requests, setting the response status to HTTP 200 (OK)
 * before forwarding the request to the error page view.
 *
 * This is typically used to handle "404 Not Found" errors in the application.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "PageNotFoundErrorServlet", value = "/page-not-found-error")
public class PageNotFoundErrorServlet extends HttpServlet {
    private static final String VIEW_PATH = "/view/error/PageNotFound.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_PATH);
        dispatcher.forward(request, response);
    }
}
