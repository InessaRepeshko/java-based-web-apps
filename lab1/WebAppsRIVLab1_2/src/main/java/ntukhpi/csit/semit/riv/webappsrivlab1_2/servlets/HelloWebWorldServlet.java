package ntukhpi.csit.semit.riv.webappsrivlab1_2.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The HelloWebWorldServlet class handles HTTP GET requests and includes the content of the
 * `HelloWebWorld.jsp` page in the response.
 *
 * <p>This servlet is mapped to two URL patterns: "/hello-web-world" and the root URL ("/").
 * When accessed, it includes the content from the `HelloWebWorld.jsp` page, allowing the
 * servlet to continue processing the response after the JSP content is rendered.
 *
 * <p>The servlet uses {@link RequestDispatcher#include} to include the content of the JSP page,
 * which allows the servlet to maintain control and perform further processing if needed.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "HelloWebWorldServlet", value = {"/hello-web-world", "/"})
public class HelloWebWorldServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request by including the content of the `HelloWebWorld.jsp` page.
     *
     * <p>This method retrieves a {@link RequestDispatcher} to include the content of the
     * `HelloWebWorld.jsp` page in the response. After including the JSP content, the servlet
     * can continue processing the request.
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made to the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws ServletException if there is an issue with the request dispatch or JSP processing
     * @throws IOException      if an input or output error occurs during the handling of the request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/HelloWebWorld.jsp");
        dispatcher.include(request, response);
    }
}
