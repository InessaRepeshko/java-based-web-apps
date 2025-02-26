package ntukhpi.csit.semit.riv.webappsrivlab1_4.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ntukhpi.csit.semit.riv.webappsrivlab1_4.model.Employee;
import ntukhpi.csit.semit.riv.webappsrivlab1_4.model.EmployeeList;

import java.io.IOException;
import java.util.List;

/**
 * The EmployeeTableForwardServlet handles HTTP GET requests and forwards the request to the
 * `EmployeeTable.jsp` page, passing the list of employees as a request attribute.
 *
 * <p>This servlet is mapped to the URL pattern "/employee-table-by-forward-method" and retrieves the
 * employee list from the {@link EmployeeList} singleton. It forwards the list to the JSP page
 * for rendering in a table format.
 *
 * <p>The servlet uses {@link RequestDispatcher#forward} to forward the request and response
 * to the JSP page, which will handle the presentation of employee data.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "EmployeeTableForwardServlet", value = "/employee-table-by-forward-method")
public class EmployeeTableForwardServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request by retrieving the list of employees and forwarding it
     * to the `EmployeeTable.jsp` page for rendering.
     *
     * <p>This method obtains the employee list from the {@link EmployeeList} singleton, sets
     * it as a request attribute, and forwards the request to the JSP page for display.
     * The content type of the response is set to "text/html" with UTF-8 encoding to support
     * proper rendering of the employee data.
     *
     * @param request  the {@link HttpServletRequest} object containing the client's request
     * @param response the {@link HttpServletResponse} object containing the servlet's response
     * @throws ServletException if there is an issue with the request dispatch or JSP processing
     * @throws IOException      if an input or output error occurs while handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeList employeeList = EmployeeList.getInstance();
        List<Employee> employees = employeeList.getEmployees();

        request.setAttribute("employees", employees);
        response.setStatus(HttpServletResponse.SC_OK);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/EmployeeTable.jsp");
        dispatcher.forward(request, response);
    }
}
