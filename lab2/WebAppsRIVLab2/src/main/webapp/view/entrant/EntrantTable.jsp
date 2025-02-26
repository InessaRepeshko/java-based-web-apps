<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="buttonPath" value="${contextPath}/view/image/button/"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Entrant Table</title>
    <link rel="icon" type="image/x-icon" href="${contextPath}/view/image/icon/favicon_square.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>

<jsp:include page="../component/NavigationBar.jsp"/>

<div class="mx-3 mt-3 mb-5 font-monospace">
    <div class="row">
        <h1 class="text-info fs-1 my-3 fw-semibold text-center">
            Entrant Table
        </h1>
    </div>

    <div class="row align-items-start">
        <div class="col-2 mx-2">
            <div class="card row p-2 mb-4 border border-3 border-info-subtle bg-light shadow">
                <div class="card-body col">
                    <div class="card-title row">
                        <p class="text-info fs-5 fw-bold p-0 m-0">Search</p>
                        <p class="text-secondary fw-lighter p-0 m-0" style="font-size: 0.8rem;">Search by case number,
                            surname</p>
                    </div>
                    <form class="card-text col mt-3" id="formSearch" method="GET">
                        <div class="row mb-3">
                            <label for="inputSearch" class="visually-hidden">Search</label>
                            <input type="search" class="form-control border border-2 border-info-subtle"
                                   id="inputSearch" name="search" placeholder="Type to search...">
                        </div>
                        <div class="col d-flex justify-content-evenly align-content-center text-center p-0 m-0">
                            <a type="button" id="btn-search" onclick="applySearch(this)"
                               class="btn btn-outline-info border-2" title="Apply search">
                                <img src="${buttonPath}search.png" width="30" height="30" alt="Apply Search"/>
                            </a>
                            <a type="button" id="btn-search-reset" onclick="resetSearch(this)"
                               class="btn btn-outline-info border-2 button-reset" title="Reset search">
                                <img src="${buttonPath}reset.png" width="30" height="30" alt="Search Reset"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card row p-2 mb-4 border border-3 border-info-subtle bg-light shadow">
                <div class="card-body col">
                    <div class="card-title row">
                        <p class="text-info fs-5 fw-bold p-0 m-0">Filters</p>
                    </div>
                    <div class="card-subtitle row mt-2" id="filterBirthday">
                        <p class="text-info fs-6 fw-bold p-0 m-0">Birthday</p>
                        <p class="text-secondary fw-lighter p-0 m-0" style="font-size: 0.8rem;">
                            Filtering by birthdate interval</p>
                    </div>
                    <form class="card-text col mt-2" id="formFilterBirthday" method="GET">
                        <div class="row mb-2">
                            <label for="inputBirthdayStart" class="form-label text-secondary fw-lighter py-0 px-1 m-0"
                                   style="font-size: 0.8rem;">Start</label>
                            <input type="date" class="form-control border border-2 border-info-subtle"
                                   id="inputBirthdayStart" name="birthdayStart">
                        </div>
                        <div class="row mb-3">
                            <label for="inputBirthdayEnd" class="form-label text-secondary fw-lighter py-0 px-1 m-0"
                                   style="font-size: 0.8rem;">End</label>
                            <input type="date" class="form-control border border-2 border-info-subtle"
                                   id="inputBirthdayEnd" name="birthdayEnd">
                        </div>
                        <div class="col d-flex justify-content-evenly align-content-center text-center p-0 m-0">
                            <a type="button" id="btn-filter-birthday" onclick="applyFilterBirthday(this)"
                               class="btn btn-outline-info border-2" title="Apply filter">
                                <img src="${buttonPath}filter.png" width="30" height="30" alt="Apply Filter"/>
                            </a>
                            <a type="button" id="btn-filter-birthday-reset" onclick="resetFilterBirthday(this)"
                               class="btn btn-outline-info border-2 button-reset" title="Reset filter">
                                <img src="${buttonPath}reset.png" width="30" height="30" alt="Filter Reset"/>
                            </a>
                        </div>
                    </form>

                    <div class="card-subtitle row mt-4" id="filterRatingScore">
                        <p class="text-info fs-6 fw-bold p-0 m-0">RatingScore</p>
                        <p class="text-secondary fw-lighter p-0 m-0" style="font-size: 0.8rem;">
                            Filtering by rating score interval</p>
                    </div>
                    <form class="card-text col mt-2" id="formFilterRatingScore" method="GET">
                        <div class="row mb-2">
                            <label for="inputRatingScoreMin" class="form-label text-secondary fw-lighter py-0 px-1 m-0"
                                   style="font-size: 0.8rem;">Start</label>
                            <input type="number" class="form-control border border-2 border-info-subtle"
                                   id="inputRatingScoreMin" name="ratingScoreMin" min="120.001" max="200.000"
                                   step="0.001">
                        </div>
                        <div class="row mb-3">
                            <label for="inputRatingScoreMax" class="form-label text-secondary fw-lighter py-0 px-1 m-0"
                                   style="font-size: 0.8rem;">End</label>
                            <input type="number" class="form-control border border-2 border-info-subtle"
                                   id="inputRatingScoreMax" name="ratingScoreMax" min="120.001" max="200.000"
                                   step="0.001">
                        </div>
                        <div class="col d-flex justify-content-evenly align-content-center text-center p-0 m-0">
                            <a type="button" id="btn-filter-rating-score" onclick="applyFilterRatingScore(this)"
                               class="btn btn-outline-info border-2" title="Apply filter">
                                <img src="${buttonPath}filter.png" width="30" height="30" alt="Apply Filter"/>
                            </a>
                            <a type="button" id="btn-filter-rating-score-reset" onclick="resetFilterRatingScore(this)"
                               class="btn btn-outline-info border-2 button-reset" title="Reset filter">
                                <img src="${buttonPath}reset.png" width="30" height="30" alt="Filter Reset"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card row p-2 mb-4 border border-3 border-info-subtle bg-light shadow">
                <div class="card-body col">
                    <div class="card-title row">
                        <p class="text-info fs-5 fw-bold p-0 m-0">Add row</p>
                        <p class="text-secondary fw-lighter p-0 m-0" style="font-size: 0.8rem;">Add new row to the end
                            of the table</p>
                    </div>
                    <form class="card-text col mt-3" id="formInsert" method="GET">
                        <div class="col d-flex justify-content-evenly align-content-center text-center p-0 m-0">
                            <a href="${contextPath}/entrants/create" id="btn-add-entrant"
                               class="btn btn-outline-info border-2" title="Add record" role="button">
                                <img src="${buttonPath}insert.png" width="30" height="30" alt="Insert"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="col">
            <table id="entrantTable"
                   class="table table-sm table-hover font-monospace table-bordered border border-3 border-info-subtle shadow-lg">
                <thead class="table-info text-center fw-medium text-secondary-emphasis">
                <tr>
                    <th class="text-center align-content-center">#</th>
                    <c:forEach var="fieldName" items="${fieldNames}">
                        <th class="text-center align-content-center" data-column="${fieldName[0]}">
                            <div class="d-flex p-0 m-0">
                                <div class="flex-grow-1 align-content-center justify-content-center p-0 m-0">
                                    <span class="">
                                         <a class="sortLink link-info link-offset-2 link-offset-3-hover link-underline
                                            link-underline-opacity-0 link-underline-opacity-75-hover"
                                            onclick="applySort('${fieldName[0]}')" id="${fieldName[0]}"
                                         >${fieldName[1]}</a>
                                    </span>
                                </div>
                                <div class="d-none flex-shrink-1 align-content-center justify-content-end ms-2 p-0 m-0"
                                     style="width: 20px;">
                                    <button type="button" class="btn bg-transparent p-0 m-0" title="Sort records">
                                        <img class="sortingAsc" src="${buttonPath}sorting_asc.png"
                                             width="20" height="20" alt="Sorting Ascending">
                                        <img class="sortingDesc" src="${buttonPath}sorting_desc.png"
                                             width="20" height="20" alt="Sorting Descending">
                                    </button>
                                </div>
                            </div>
                        </th>
                    </c:forEach>
                    <th class="text-center align-content-center">Operations</th>
                </tr>
                </thead>
                <tbody class="table-light fw-light text-primary-emphasis">
                <c:forEach var="entrant" items="${entrantList}" varStatus="status">
                    <tr data-id="${entrant.id}">
                        <td class="text-center align-content-center">${status.index + 1}</td>
                        <td class="text-start align-content-center" data-column="caseNumber">${entrant.caseNumber}</td>
                        <td class="text-start align-content-center" data-column="surname">${entrant.surname}</td>
                        <td class="text-start align-content-center" data-column="name">${entrant.name}</td>
                        <td class="text-start align-content-center" data-column="patronymic">${entrant.patronymic}</td>
                        <td class="text-center align-content-center"
                            data-column="birthday">${entrant.getBirthdayAsUADate()}</td>
                        <td class="text-center align-content-center" data-column="gender"
                            style="color: ${entrant.gender ? "#0d6efd" : "#d63384"};">${entrant.gender ? "male" : "female"}</td>
                        <td class="text-end align-content-center" data-column="ratingScore">
                            <fmt:formatNumber value="${entrant.ratingScore}" pattern="#0.000"/></td>
                        <td class="text-center align-content-center">
                            <div class="d-flex justify-content-evenly p-0 m-0">
                                <a type="button" href="${contextPath}/entrants/${entrant.id}/update"
                                   class="p-0 m-0 border border-0 bg-transparent button-update"
                                   data-id="${entrant.id}" title="Edit record">
                                    <img src="${buttonPath}update.png" width="30" height="30" alt="Update"/>
                                </a>
                                <a type="button" href="${contextPath}/entrants/${entrant.id}/delete"
                                   class="p-0 m-0 border border-0 bg-transparent button-delete"
                                   data-id="${entrant.id}" title="Delete record">
                                    <img src="${buttonPath}delete_red.png" width="30" height="30" alt="Delete"/>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
        </div>
    </div>

    <div class="d-flex justify-content-center">
        <a href="${contextPath}/home" class="btn btn-outline-info me-5" type="button">
            Go Home
        </a>
    </div>
</div>

<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
        setBirthdayLimit();
        setInputValueFromAttr();
    });

    function setBirthdayLimit() {
        document.getElementById('inputBirthdayStart').setAttribute('min', getBirthdayMinValue());
        document.getElementById('inputBirthdayStart').setAttribute('max', getBirthdayMaxValue());
        document.getElementById('inputBirthdayEnd').setAttribute('min', getBirthdayMinValue());
        document.getElementById('inputBirthdayEnd').setAttribute('max', getBirthdayMaxValue());

        function getBirthdayMinValue() {
            const today = new Date();
            return new Date(today.getFullYear() - 110, today.getMonth(), today.getDate()).toISOString().split('T')[0];
        }

        function getBirthdayMaxValue() {
            const today = new Date();
            return new Date(today.getFullYear() - 16, today.getMonth(), today.getDate()).toISOString().split('T')[0];
        }
    }

    function setInputValueFromAttr() {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);
        const inputFields = document.getElementsByTagName("input");

        for (const input of inputFields) {
            if (params.has(input.name)) {
                input.value = params.get(input.name);
            }
        }
    }

    function applySearch(linkButton) {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const searchValue = document.getElementById("inputSearch");
        setFilteringParameter(params, searchValue);

        const newURL = currentURL.pathname + '?' + params.toString();
        linkButton.setAttribute('href', newURL);
    }

    function resetSearch(linkButton) {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const searchValue = document.getElementById("inputSearch");
        resetFilteringParameter(params, searchValue);

        const newURL = currentURL.pathname + '?' + params.toString();
        const form = document.getElementById("formSearch");
        form.reset();
        linkButton.setAttribute('href', newURL);
    }

    function resetFilterBirthday(linkButton) {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const dateStart = document.getElementById("inputBirthdayStart");
        const dateEnd = document.getElementById("inputBirthdayEnd");
        resetFilteringParameter(params, dateStart);
        resetFilteringParameter(params, dateEnd);

        const newURL = currentURL.pathname + '?' + params.toString();
        const form = document.getElementById("formFilterBirthday");
        form.reset();
        linkButton.setAttribute('href', newURL);
    }

    function resetFilterRatingScore(linkButton) {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const scoreMin = document.getElementById("inputRatingScoreMin");
        const scoreMax = document.getElementById("inputRatingScoreMax");
        resetFilteringParameter(params, scoreMin);
        resetFilteringParameter(params, scoreMax);

        const newURL = currentURL.pathname + '?' + params.toString();
        const form = document.getElementById("formFilterRatingScore");
        form.reset();
        linkButton.setAttribute('href', newURL);
    }

    function applyFilterBirthday(linkButton) {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const dayStart = document.getElementById("inputBirthdayStart");
        const dayEnd = document.getElementById("inputBirthdayEnd");
        setFilteringParameter(params, dayStart);
        setFilteringParameter(params, dayEnd);

        const newURL = currentURL.pathname + '?' + params.toString();
        linkButton.setAttribute('href', newURL);
    }

    function applyFilterRatingScore(linkButton) {
        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const scoreMin = document.getElementById("inputRatingScoreMin");
        const scoreMax = document.getElementById("inputRatingScoreMax");
        setFilteringParameter(params, scoreMin);
        setFilteringParameter(params, scoreMax);

        const newURL = currentURL.pathname + '?' + params.toString();
        linkButton.setAttribute('href', newURL);
    }

    function setFilteringParameter(params, inputElement) {
        const paramName = inputElement.name;
        const paramValue = inputElement.value.toString().trim();

        if (paramValue.length > 0) {
            params.set(paramName, paramValue);
        } else {
            params.delete(paramName);
        }
    }

    function resetFilteringParameter(params, inputElement) {
        const paramName = inputElement.name;
        params.delete(paramName);
    }

    function applySort(fieldName) {
        const linkElement = document.getElementById(fieldName);

        const currentURL = new URL(window.location.href);
        const params = new URLSearchParams(currentURL.searchParams);

        const paramName = "sort";
        const paramFieldName = linkElement.id;
        let paramSortOrder = 'asc';
        const currentSortValue = params.get(paramName);

        if (currentSortValue) {
            const currentSortFieldName = currentSortValue.split("-")[0];
            const currentSortOrder = currentSortFieldName === paramFieldName ? currentSortValue.split("-")[1] : '';
            paramSortOrder = currentSortOrder === 'asc' ? 'desc' : 'asc';
        }

        params.set(paramName, paramFieldName + '-' + paramSortOrder);

        const newURL = currentURL.pathname + '?' + params.toString();
        linkElement.setAttribute('href', newURL);
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<jsp:include page="../component/Footer.jsp"/>

</body>
</html>
