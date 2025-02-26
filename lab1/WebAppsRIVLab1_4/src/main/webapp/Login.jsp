<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome to Web World!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<div class="container justify-content-center">
    <h1 class="text-info bg-info-subtle border border-3 rounded-pill border-info-subtle
               fs-1 fw-semibold font-monospace text-center
               shadow-lg p-3 my-5 bg-body-tertiary rounded">
        Welcome to Web World!
    </h1>

    <div class="container w-25 text-center justify-content-center">
        <h2 class="text-info bg-info-subtle border border-3 rounded-pill border-info-subtle
                   fs-3 fw-semibold font-monospace text-center
                   shadow p-2 mb-3 bg-body-tertiary rounded">
            Login
        </h2>
    </div>

    <div class="container w-50 text-center justify-content-center">
        <div class="card border border-2 border-info-subtle p-5
                    shadow mb-5 bg-body-tertiary rounded">
            <form id="login-form" action="process-login" method="POST">
                <div class="card-body bg-light font-monospace">
                    <div class="input-group mb-5 border border-1 border-info-subtle rounded">
                        <span class="input-group-text text-info">Login</span>
                        <input type="text" class="form-control text-dark" name="login" required>
                    </div>

                    <div class="input-group mb-5 border border-1 border-info-subtle rounded">
                        <span class="input-group-text text-info">Password</span>
                        <input type="password" class="form-control text-dark" name="password" required>
                    </div>

                    <div class="d-flex justify-content-center">
                        <input type="submit" class="btn btn-outline-info" name="submit" value="login">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<jsp:include page="Footer.jsp"/>
</body>
</html>
