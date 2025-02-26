<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav class="navbar navbar-expand-lg bg-info-subtle text-info-emphasis font-monospace">
        <div class="container-fluid">
            <div class="mx-3">
                <div class="navbar-brand text-info">
                    <img class="rounded-circle"
                         src="${pageContext.request.contextPath}/view/image/icon/favicon_square.png" width="50"
                         height="50" alt="Favicon">
                    <span class="p-0 m-0 ms-2">Student Database Viewer</span>
                </div>
            </div>

            <div class="mx-3">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup"
                        aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>

            <div class="collapse navbar-collapse mx-3" id="navbarNavAltMarkup">
                <div class="navbar-nav font-monospace text-secondary-emphasis">
                    <div class="nav-item me-5">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
                    </div>

                    <div class="nav-item dropdown me-5 align-content-center">
                        <div class="nav-item dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                            Tables
                        </div>

                        <ul class="dropdown-menu font-monospace text-start text-secondary-emphasis">
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/entrants">
                                    Entrant Table
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item disabled"
                                   href="${pageContext.request.contextPath}/student-table" aria-disabled="true">
                                    Student Table
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
</header>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
