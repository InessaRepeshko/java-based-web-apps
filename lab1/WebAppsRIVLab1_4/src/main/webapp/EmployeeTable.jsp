<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ntukhpi.csit.semit.riv.webappsrivlab1_4.model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Employee Table</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="NavigationBar.jsp"/>

<div class="mx-3 my-3 font-monospace">
    <h1 class="text-info fs-1 my-3 fw-semibold text-center">
        Employee Table
    </h1>

    <table class="table table-sm table-hover font-monospace
              table-bordered border border-3 border-info-subtle shadow-lg">
        <thead class="table-info text-center fw-medium text-secondary-emphasis">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Gender</th>
            <th>Birthday</th>
            <th>Salary, UAH</th>
            <th>Program Language</th>
            <th>Importance</th>
        </tr>
        </thead>
        <tbody class="table-light fw-light text-primary-emphasis">
        <%
            String employeeIdsParam = request.getParameter("employeeIds");

            List<Long> employeeIds = new ArrayList<>();
            List<Employee> employeesToDisplay = new ArrayList<>();

            if (employeeIdsParam != null && !employeeIdsParam.isEmpty()) {
                String[] ids = employeeIdsParam.split(",");

                for (String id : ids) {
                    employeeIds.add(Long.parseLong(id));
                }

                EmployeeList employeeList = EmployeeList.getInstance();

                for (Employee employee : employeeList.getEmployees()) {
                    if (employeeIds.contains(employee.getId())) {
                        employeesToDisplay.add(employee);
                    }
                }
            } else {
                employeesToDisplay = (List<Employee>) request.getAttribute("employees");
            }

            for (Employee employee : employeesToDisplay) {
                String textColor;

                switch (employee.getImportance()) {
                    case 1:
                        textColor = "text-warning";
                        break;
                    case 2:
                        textColor = "text-danger";
                        break;
                    default:
                        textColor = "text-primary";
                }
                ;

                out.println("<tr>"); // out is an implicit object JspWriter
                out.println("<td class=\"text-start\">" + employee.getId() + "</td>");
                out.println("<td class=\"text-start\">" + employee.getName() + "</td>");
                out.println("<td class=\"text-start\">" + employee.getGenderAsString() + "</td>");
                out.println("<td class=\"text-end\">" + employee.getBirthdayAsString() + "</td>");
                out.println("<td class=\"text-end\">" + employee.getSalary() + "</td>");
                out.println("<td class=\"text-start\">" + employee.getProgramLanguage() + "</td>");
                out.println("<td class=\"text-start " + textColor + "\">" + employee.getImportanceAsString() + "</td>");
                out.println("</tr>");
            }
        %>
        </tbody>
    </table>
    <br>

    <div class="d-flex justify-content-center">
        <a href="/WebAppsRIVLab1_4/hello-web-world" class="btn btn-outline-info" role="button">Go Back</a>
    </div>
</div>

<jsp:include page="Footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
