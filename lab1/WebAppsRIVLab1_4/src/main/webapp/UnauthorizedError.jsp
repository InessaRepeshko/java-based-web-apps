<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Unauthorized Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<div class="container justify-content-center">
    <div class="container justify-content-center w-50 position-absolute top-50 start-50 translate-middle">
        <div class="card border border-2 border-danger-subtle
                    shadow mb-5 bg-danger-subtle rounded">
            <div class="card-header bg-danger-subtle border-bottom border-2 border-danger-subtle
                            font-monospace text-center text-secondary-emphasis fw-bolder">
                <span class="badge text-bg-danger">401</span>
                Unauthorized Error
            </div>

            <div class="card-body bg-light font-monospace">
                <div class="card-text text-secondary-emphasis fw-light text-center">
                    <p>Authentication <code class="text-danger">failed</code> due to entering invalid credentials.</p>
                    <p>Please, try again!</p>
                </div>
            </div>
        </div>

        <div class="d-flex justify-content-center mt-5">
            <a href="/WebAppsRIVLab1_4" class="btn btn-outline-info" role="button">Go Back</a>
        </div>
    </div>
</div>
</div>
</body>
</html>
