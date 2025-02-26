<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home</title>
    <link rel="icon" type="image/x-icon" href="${contextPath}/view/image/icon/favicon_square.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="../component/NavigationBar.jsp"/>

<div class="container justify-content-center">
    <h1 class="text-info bg-info-subtle border border-3 rounded-pill border-info-subtle
               fs-1 fw-semibold font-monospace text-center
               shadow-lg p-3 my-5 bg-body-tertiary rounded">
        <c:choose>
            <c:when test="${login != null && not empty login}">Welcome, ${login}!</c:when>
            <c:otherwise>Welcome to Student Database Viewer!</c:otherwise>
        </c:choose>
    </h1>

    <div class="row align-items-start column-gap-5">
        <div class="col w-50 p-0 card border border-2 border-info-subtle
                    shadow mb-5 bg-body-tertiary rounded">
            <div class="card-header bg-body-secondary border-bottom border-2 border-info-subtle
                        font-monospace text-center text-secondary-emphasis fw-bolder">
                Software developer info
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
                        <jsp:useBean id="now" class="java.util.Date" scope="page"/>
                        <fmt:formatDate value="${now}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </span>
                </p>
            </div>
        </div>

        <div class="col w-50 p-0 card border border-2 border-info-subtle
                    shadow mb-5 bg-body-tertiary rounded">
            <div class="card-header bg-body-secondary border-bottom border-2 border-info-subtle
                        font-monospace text-center text-secondary-emphasis fw-bolder">
                Entrant Table
            </div>

            <div class="card-body bg-light font-monospace">
                <div class="card-text text-secondary-emphasis fw-light">
                    <p>Table with data on entrants of a certain higher education institution in Ukraine.</p>
                </div>

                <div class="border-start border-info-subtle  border-5 bg-body-secondary p-3 mb-3">
                    <p class="p-0 m-0">The table contains the following information about entrants:</p>
                    <ul>
                        <li><code class="text-info">Long</code> unique identifier;</li>
                        <li><code class="text-info">String</code> case number of the personal file;</li>
                        <li><code class="text-info">String</code> surname;</li>
                        <li><code class="text-info">String</code> name;</li>
                        <li><code class="text-info">String</code> patronymic;</li>
                        <li><code class="text-info">LocalDate</code> date of birth;</li>
                        <li><code class="text-info">Boolean</code> gender;</li>
                        <li><code class="text-info">Double</code> rating score.</li>
                    </ul>
                </div>

                <div class="d-flex justify-content-center">
                    <a href="${contextPath}/entrants"
                       class="btn btn-outline-info" role="button">Show</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../component/Footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
