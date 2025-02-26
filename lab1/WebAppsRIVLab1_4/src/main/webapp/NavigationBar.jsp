<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-info-subtle text-info-emphasis font-monospace">
    <div class="container-fluid">
        <div class="mx-3">
            <a class="navbar-brand text-info " href=".">
                <svg class="bd-placeholder-img rounded me-2" width="30" height="30" xmlns="http://www.w3.org/2000/svg"
                     aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
                    <rect width="100%" height="100%" fill="#0dcaf0"></rect>
                </svg>
                Hello, Web World!
            </a>
        </div>

        <div class="mx-3">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                    aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse mx-3" id="navbarNavAltMarkup">
            <div class="navbar-nav font-monospace text-secondary-emphasis">
                <div class="nav-item me-5">
                    <a class="nav-link active" aria-current="page" href="hello-web-world">Home</a>
                </div>

                <div class="nav-item dropdown me-5 align-content-center">
                    <div class="nav-item dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        Employee Table
                    </div>

                    <ul class="dropdown-menu font-monospace text-start text-secondary-emphasis">
                        <li>
                            <a class="dropdown-item" href="employee-table-by-include-method">
                                Employee Table
                                <span class="badge text-bg-info">include()</span>
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="employee-table-by-forward-method">
                                Employee Table
                                <span class="badge text-bg-info">forward()</span>
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="employee-table-by-sendredirect-method">
                                Employee Table
                                <span class="badge text-bg-info">sendRedirect()</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="nav-item">
                    <a class="nav-link disabled" aria-disabled="true">Further more</a>
                </div>
            </div>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
