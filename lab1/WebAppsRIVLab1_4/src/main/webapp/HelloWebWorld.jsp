<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Hello, Web World!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="NavigationBar.jsp"/>

<div class="container justify-content-center">
    <h1 class="text-info bg-info-subtle border border-3 rounded-pill border-info-subtle
               fs-1 fw-semibold font-monospace text-center
               shadow-lg p-3 my-5 bg-body-tertiary rounded">
        <%
            StringBuilder text = new StringBuilder();
            text.append("Welcome to Web World");

            if (request.getParameter("login") != null) {
                text.append(", ").append(request.getParameter("login"));
            }

            text.append("!");
        %>
        <%=text.toString()%>
    </h1>

    <div class="row align-items-start column-gap-5">
        <div class="col w-50 p-0 card border border-2 border-info-subtle
                    shadow mb-5 bg-body-tertiary rounded">
            <div class="card-header bg-body-secondary border-bottom border-2 border-info-subtle
                        font-monospace text-center text-secondary-emphasis fw-bolder">
                Student data
            </div>
            <div class="card-body bg-light font-monospace">
                <p class="card-text text-secondary-emphasis fw-light">
                    Name:
                    <span class="text-primary-emphasis fw-medium fst-italic ms-3">Repeshko Inessa</span>
                </p>
                <p class="card-text text-secondary-emphasis fw-light">
                    Group:
                    <span class="text-primary-emphasis fw-medium fst-italic ms-3">CS-222a</span>
                </p>
                <p class="card-text text-secondary-emphasis fw-light">
                    Department:
                    <span class="text-primary-emphasis fw-medium fst-italic ms-3">SEMIT</span>
                </p>
                <p class="card-text text-secondary-emphasis fw-light">
                    Year:
                    <span class="text-primary-emphasis fw-medium fst-italic ms-3">2024</span>
                </p>
                <p class="card-text text-secondary-emphasis fw-light">
                    Page access time:
                    <span class="text-primary-emphasis fw-medium fst-italic ms-3">
                        <%= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) %>
                    </span>
                </p>
            </div>
        </div>

        <div class="col w-50 p-0 card border border-2 border-info-subtle
                    shadow mb-5 bg-body-tertiary rounded">
            <div class="card-header bg-body-secondary border-bottom border-2 border-info-subtle
                        font-monospace text-center text-secondary-emphasis fw-bolder">
                Employee Table
                <span class="badge text-bg-info">include()</span>
            </div>

            <div class="card-body bg-light font-monospace">
                <div class="card-text text-secondary-emphasis fw-light">
                    <p>Table of employees of some IT company.</p>
                    <p>The page is displayed by including the content.</p>
                </div>

                <div class="border-start border-info-subtle  border-5 bg-body-secondary p-3 mb-3">
                    The <code class="text-info">include()</code> method of the
                    <code class="text-info">RequestDispatcher</code> class is used to include
                    the content of another resource (servlet, JSP page, HTML file) in the server-side response
                    while preserving the data of the original request and without changing the original URL.
                </div>

                <div class="d-flex justify-content-center">
                    <a href="employee-table-by-include-method" class="btn btn-outline-info" role="button">Show</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row align-items-start column-gap-5">
        <div class="col w-50 p-0 card border border-2 border-info-subtle
                    shadow mb-5 bg-body-tertiary rounded">
            <div class="card-header bg-body-secondary border-bottom border-2 border-info-subtle
                        font-monospace text-center text-secondary-emphasis fw-bolder">
                Employee Table
                <span class="badge text-bg-info">forward()</span>
            </div>

            <div class="card-body bg-light font-monospace">
                <div class="card-text text-secondary-emphasis fw-light">
                    <p>Table of employees of some IT company.</p>
                    <p>The page is displayed using a request forwarding.</p>
                </div>

                <div class="border-start border-info-subtle  border-5 bg-body-secondary p-3 mb-3">
                    The <code class="text-info">forward()</code> method of the
                    <code class="text-info">RequestDispatcher</code> class allows forwarding
                    a request from a servlet to another resource (servlet, html-page or jsp page) on the server side
                    while preserving the data of the original request and without changing the original URL.
                </div>

                <div class="d-flex justify-content-center">
                    <a href="employee-table-by-forward-method" class="btn btn-outline-info" role="button">Show</a>
                </div>
            </div>
        </div>

        <div class="col w-50 p-0 card border border-2 border-info-subtle
                    shadow mb-5 bg-body-tertiary rounded">
            <div class="card-header bg-body-secondary border-bottom border-2 border-info-subtle
                        font-monospace text-center text-secondary-emphasis fw-bolder">
                Employee Table
                <span class="badge text-bg-info">sendRedirect()</span>
            </div>

            <div class="card-body bg-light font-monospace">
                <div class="card-text text-secondary-emphasis fw-light">
                    <p>Table of employees of some IT company.</p>
                    <p>The page is displayed using a request redirecting.</p>
                </div>

                <div class="border-start border-info-subtle  border-5 bg-body-secondary p-3 mb-3">
                    The <code class="text-info">sendRedirect()</code> method of the
                    <code class="text-info">HttpServletResponse</code> class is used to redirect the client (browser)
                    to another resource (URL, which can be either an internal or external resource) on the client side
                    with the loss of original data (if not passed to the URL) and a change in the original URL.
                </div>

                <div class="d-flex justify-content-center">
                    <a href="employee-table-by-sendredirect-method" class="btn btn-outline-info" role="button">Show</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
