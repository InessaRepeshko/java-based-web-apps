<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Internal Server Error</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/view/image/icon/favicon_square.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<div class="container-fluid p-0 m-0 vh-100 align-content-end justify-content-center">
    <div class="row align-content-center justify-content-center my-5">
        <div class="card border border-2 border-danger-subtle
                    shadow bg-danger-subtle rounded mt-5 w-50 p-0">
            <div class="card-header bg-danger-subtle border-bottom border-2 border-danger-subtle
                            font-monospace text-center text-secondary-emphasis fw-bolder">
                <span class="badge text-bg-danger">505</span>
                Internal Server Error
            </div>

            <div class="card-body bg-light font-monospace">
                <div class="card-text text-secondary-emphasis fw-light text-center">
                    <p>Something went wrong.</p>
                </div>
            </div>
        </div>

        <div class="d-flex justify-content-center mt-5">
            <button class="btn btn-outline-info me-5" type="button" onclick="window.history.back()">Go Back</button>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-info" role="button">Go Home</a>
        </div>
    </div>

    <div class="row align-content-end p-0 m-0">
        <jsp:include page="../component/Footer.jsp"/>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
