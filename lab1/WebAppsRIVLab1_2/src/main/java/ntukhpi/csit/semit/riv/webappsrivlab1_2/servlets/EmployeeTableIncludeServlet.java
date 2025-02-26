package ntukhpi.csit.semit.riv.webappsrivlab1_2.servlets;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ntukhpi.csit.semit.riv.webappsrivlab1_2.model.*;
import java.util.List;

/**
 * The EmployeeTableServlet is responsible for processing HTTP GET requests and including the
 * content of the `EmployeeTable.jsp` page in the response.
 *
 * <p>This servlet is mapped to the URL pattern "/employee-table" and serves the purpose of rendering
 * an HTML table containing employee data. The servlet includes the content of the JSP page,
 * allowing for further response processing after the JSP content is rendered.
 *
 * <p>The `EmployeeTable.jsp` page displays the employee data in a table format, while this servlet
 * ensures that the correct data is passed to the page for display.
 *
 * <p>Inclusion of the JSP page's content is done via a {@link RequestDispatcher#include}, ensuring that
 * control is returned to the servlet after the page is processed, allowing for additional logic
 * or response handling.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "EmployeeTableIncludeServlet", value = "/employee-table-by-include-method")
public class EmployeeTableIncludeServlet extends HttpServlet {

    /**
     * Processes the HTTP GET request by including the content of the `EmployeeTable.jsp` page.
     *
     * <p>This method uses a {@link RequestDispatcher} to include the content of the JSP page
     * in the response, which displays an HTML table of employees. The inclusion allows the
     * servlet to continue processing after rendering the JSP content.
     *
     * <p>The JSP page is responsible for rendering the employee table using the data provided
     * in the request, while the servlet can continue to process or modify the response after
     * the inclusion.
     *
     * @param request  the {@link HttpServletRequest} object that contains the client's request
     * @param response the {@link HttpServletResponse} object that contains the servlet's response to the client
     * @throws ServletException if the request for the inclusion cannot be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeList employeeList = EmployeeList.getInstance();
        List<Employee> employees = employeeList.getEmployees();

        request.setAttribute("employees", employees);
        response.setStatus(HttpServletResponse.SC_OK);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/EmployeeTable.jsp");
        dispatcher.include(request, response);
    }
}
