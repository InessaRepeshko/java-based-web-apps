package ntukhpi.csit.semit.riv.webappsrivlab1_4.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ntukhpi.csit.semit.riv.webappsrivlab1_4.model.Employee;
import ntukhpi.csit.semit.riv.webappsrivlab1_4.model.EmployeeList;

import java.io.IOException;
import java.util.List;

/**
 * The EmployeeTableSendRedirectServlet class handles HTTP GET requests by redirecting the client
 * to the `EmployeeTable.jsp` page with a list of employee IDs as URL parameters.
 *
 * <p>This servlet is mapped to the URL pattern "/employee-table-by-sendredirect-method" and retrieves
 * the employee list from the {@link EmployeeList} singleton. It builds a query string containing
 * the employee IDs and uses the {@link HttpServletResponse#sendRedirect} method to redirect
 * the client to the JSP page.
 *
 * <p>Unlike forwarding, the sendRedirect method sends a new request to the server, meaning the client
 * is aware of the redirection, and the URL in the browser changes to the new target.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "EmployeeTableSendRedirectServlet", value = "/employee-table-by-sendredirect-method")
public class EmployeeTableSendRedirectServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request by retrieving the list of employees and redirecting the client
     * to the `EmployeeTable.jsp` page with employee IDs passed as URL parameters.
     *
     * <p>This method builds a comma-separated list of employee IDs and appends it to the URL
     * as a query string. It then redirects the client to the JSP page for further processing.
     *
     * @param request  the {@link HttpServletRequest} object containing the client's request
     * @param response the {@link HttpServletResponse} object containing the servlet's response
     * @throws IOException if an input or output error occurs while handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EmployeeList employeeList = EmployeeList.getInstance();
        List<Employee> employees = employeeList.getEmployees();

        StringBuilder employeeIds = new StringBuilder();

        for (Employee employee : employees) {
            employeeIds.append(employee.getId()).append(",");
        }

        if (employeeIds.length() > 0) {
            employeeIds.setLength(employeeIds.length() - 1);
        }

        response.setStatus(HttpServletResponse.SC_FOUND);
        response.sendRedirect(request.getContextPath() + "/EmployeeTable.jsp?employeeIds=" + employeeIds.toString());
    }
}
