package ntukhpi.csit.semit.riv.webappsrivlab1_4.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The UnauthorizedErrorServlet handles HTTP GET requests and displays an error page
 * for unauthorized access attempts.
 *
 * <p>This servlet is mapped to the URL pattern "/unauthorized-error" and is triggered when
 * a user fails to provide valid credentials or attempts to access a restricted resource
 * without authorization. The servlet sets the response status to 401 (Unauthorized) and
 * includes the content of the `UnauthorizedError.jsp` page to display a meaningful error message.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "UnauthorizedErrorServlet", value = "/unauthorized-error")
public class UnauthorizedErrorServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request by setting the response status to 401 (Unauthorized) and
     * including the content of the `UnauthorizedError.jsp` page to inform the user of the
     * unauthorized access attempt.
     *
     * @param request  the {@link HttpServletRequest} object that contains the client's request
     * @param response the {@link HttpServletResponse} object that contains the servlet's response
     * @throws ServletException if there is an issue with the request dispatch or JSP processing
     * @throws IOException      if an input or output error occurs during the handling of the request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/UnauthorizedError.jsp");
        dispatcher.include(request, response);
    }
}
