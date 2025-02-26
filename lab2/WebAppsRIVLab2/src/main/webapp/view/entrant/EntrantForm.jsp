<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="buttonPath" value="${contextPath}/view/image/button/"/>
<c:set var="requestTypeIsUpdate" value="${requestType == 'UPDATE'}"/>
<c:set var="requestTypeIsDelete" value="${requestType == 'DELETE'}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestTypeIsUpdate ? 'Update Entrant' : (requestTypeIsDelete ? 'Delete Entrant' : 'Create Entrant')}</title>
    <link rel="icon" type="image/x-icon" href="${contextPath}/view/image/icon/favicon_square.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>

<jsp:include page="../component/NavigationBar.jsp"/>

<div class="container w-50 text-center justify-content-center">
    <div class="card border border-3 border-info-subtle shadow bg-body-tertiary rounded m-0 mt-5 p-0">
        <div class="card-header bg-info-subtle border-bottom border-3 border-info-subtle
                    font-monospace text-center text-secondary-emphasis fw-bolder">
            ${requestTypeIsUpdate ? 'Update Entrant' : (requestTypeIsDelete ? 'Delete Entrant' : 'Create Entrant')}
        </div>

        <form method="POST" id="entrantForm"
              action="${contextPath}/entrants/${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getId() : ''}${requestTypeIsUpdate ? '/update' : (requestTypeIsDelete ? '/delete' : 'create')}"
              class="card-body bg-light font-monospace p-5 m-0">
            <div class="card-text mb-4 d-none">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputId" class=" col-3 input-group-text text-info">Id</label>
                    <input class="col-auto form-control text-dark" type="text" id="inputId" name="id"
                           minlength="1" maxlength="100" required ${requestTypeIsDelete ? 'readonly' : ''}
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getId() : 'null'}">
                </div>
            </div>
            <div class="card-text mb-4">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputCaseNumber" class=" col-3 input-group-text text-info">Case Number</label>
                    <input class="col-auto form-control text-dark" type="text" id="inputCaseNumber" name="caseNumber"
                           minlength="5" maxlength="20" required ${requestTypeIsDelete ? 'readonly' : ''}
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getCaseNumber(): ''}">
                </div>
            </div>

            <div class="card-text mb-4">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputSurname" class=" col-3 input-group-text text-info">Surname</label>
                    <input class="col-auto form-control text-dark" type="text" id="inputSurname" name="surname"
                           minlength="1" maxlength="50" required ${requestTypeIsDelete ? 'readonly' : ''}
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getSurname(): ''}">
                </div>
            </div>

            <div class="card-text mb-4">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputName" class=" col-3 input-group-text text-info">Name</label>
                    <input class="col-auto form-control text-dark" type="text" id="inputName" name="name"
                           minlength="1" maxlength="50" required ${requestTypeIsDelete ? 'readonly' : ''}
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getName(): ''}">
                </div>
            </div>

            <div class="card-text mb-4">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputPatronymic" class=" col-3 input-group-text text-info">Patronymic</label>
                    <input class="col-auto form-control text-dark" type="text" id="inputPatronymic" name="patronymic"
                           minlength="1" maxlength="50" required ${requestTypeIsDelete ? 'readonly' : ''}
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getPatronymic(): ''}">
                </div>
            </div>

            <div class="card-text mb-4">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputBirthday" class=" col-3 input-group-text text-info">Birthday</label>
                    <input class="col-auto form-control text-dark" type="date" id="inputBirthday" name="birthday"
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getBirthdayAsDefaultDate(): ''}"
                           required ${requestTypeIsDelete ? 'readonly' : ''}>
                </div>
            </div>

            <div class="card-text d-flex align-content-center justify-content-start mb-4">
                <div class="col-3 border border-1 border-info-subtle rounded d-inline-flex p-0 m-0 me-3">
                    <span class="d-flex flex-fill input-group-text text-info">Gender</span>
                </div>
                <div class="col-auto btn-group" role="group" aria-label="Gender radio toggle button group">
                    ${requestTypeIsDelete ? (entrant.getGender() ? '<input type="hidden" name="gender" value="true">' : '<input type="hidden" name="gender" value="false">') : ''}
                    <input type="radio" class="btn-check" name="gender" autocomplete="off"
                           id="inputGenderMale" value="true"
                    ${requestTypeIsUpdate ? (entrant.getGender() ? 'checked' : '') : 'checked'}
                    ${requestTypeIsDelete ? (entrant.getGender() ? 'checked' : '') : 'checked'}
                    ${requestTypeIsDelete ? 'disabled' : ''}>
                    <label class="btn btn-outline-info" for="inputGenderMale">Male</label>

                    <input type="radio" class="btn-check" name="gender" autocomplete="off"
                           id="inputGenderFemale" value="false"
                    ${requestTypeIsUpdate ? (entrant.getGender() ? '' : 'checked') : ''}
                    ${requestTypeIsDelete ? (entrant.getGender() ? '' : 'checked') : ''}
                    ${requestTypeIsDelete ? 'disabled' : ''}>
                    <label class="btn btn-outline-info" for="inputGenderFemale">Female</label>
                </div>
            </div>

            <div class="card-text mb-4">
                <div class="input-group border border-1 border-info-subtle rounded ">
                    <label for="inputRatingScore" class="col-3 input-group-text text-info">Rating score</label>
                    <input class="col-auto form-control text-dark" type="number" id="inputRatingScore"
                           name="ratingScore" min="120.001" max="200.000" step="0.001" required
                           value="${requestTypeIsUpdate or requestTypeIsDelete ? entrant.getRatingScore() : 120.001}"
                    ${requestTypeIsDelete ? 'readonly' : ''}>
                </div>
            </div>

            <div class="d-flex justify-content-evenly">
                <button type="submit" class="btn btn-outline-info border-2 button-apply
                        ${requestTypeIsDelete ? 'd-none' : ''}" title="Save record" id="btn-form-save"
                        onclick="alert('The record successfully updated in the database!')">
                    <img src="${buttonPath}save.png" width="30" height="30" alt="Save"/>
                </button>
                <button type="reset" class="btn btn-outline-info border-2 button-reset
                        ${requestTypeIsDelete ? 'd-none' : ''}" title="Reset fields" id="btn-form-reset"
                        onclick="form.reset()">
                    <img src="${buttonPath}reset.png" width="30" height="30" alt="Reset"/>
                </button>
                <button type="button" class="btn btn-outline-info border-2 button-delete
                        ${requestTypeIsDelete ? '' : 'd-none'}" title="Delete record" id="btn-form-delete"
                        onclick="submitConfirmation('Are you sure you want to delete that record?')">
                    <img src="${buttonPath}delete_black.png" width="30" height="30" alt="Delete"/>
                </button>
            </div>
        </form>
    </div>
    <div class="d-flex justify-content-center mt-5">
        <a href="${contextPath}/entrants" class="btn btn-outline-info" type="button">
            Go to Table
        </a>
    </div>
</div>

<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
        setAllInputsValidity();
    });

    function setAllInputsValidity() {
        setPatternAttr();
        setBirthdayLimit();

        setStringInputValidity();
        setBirthdayValidity();
        setRatingScoreValidity();

        const form = document.getElementById('entrantForm');

        document.getElementById('entrantForm').addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
        });

        function setPatternAttr() {
            const regexCaseNumber = new RegExp("^(?=.{5,20}$)[А-ЩЮЯҐЄІЇ]{1,10}\\d{2}[\\-]\\d{1,4}$");
            const regexNames = new RegExp("^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ \\-][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$");

            const inputCaseNumber = document.getElementById('inputCaseNumber');
            inputCaseNumber.setAttribute('pattern', regexCaseNumber.source);

            const inputSurname = document.getElementById('inputSurname');
            inputSurname.setAttribute('pattern', regexNames.source);

            const inputName = document.getElementById('inputName');
            inputName.setAttribute('pattern', regexNames.source);

            const inputPatronymic = document.getElementById('inputPatronymic');
            inputPatronymic.setAttribute('pattern', regexNames.source);
        }

        function setBirthdayLimit() {
            document.getElementById('inputBirthday').setAttribute('min', getBirthdayMinValue());
            document.getElementById('inputBirthday').setAttribute('max', getBirthdayMaxValue());

            function getBirthdayMinValue() {
                const today = new Date();
                return new Date(today.getFullYear() - 110, today.getMonth(), today.getDate()).toISOString().split('T')[0];
            }

            function getBirthdayMaxValue() {
                const today = new Date();
                return new Date(today.getFullYear() - 16, today.getMonth(), today.getDate()).toISOString().split('T')[0];
            }
        }

        function setBirthdayValidity() {
            const regexpDate = new RegExp("^(?=.{10}$)\\d{4}\-\\d{2}\-\\d{2}");
            const messages = [
                "Birthday cannot be empty.",
                "Value must be date from 110 to 16 years ago today."
            ];
            const inputBirthday = document.getElementById('inputBirthday');

            inputBirthday.addEventListener('submit', function () {
                const minDate = new Date(this.min);
                const maxDate = new Date(this.max);
                const selectedDate = new Date(this.value);

                if (!regexpDate.test(selectedDate.toString())) {
                    console.log("Invalid input for " + this.name + ": \"" + this.value + "\"");
                    console.log(messages[0]);
                    this.setCustomValidity(messages[0]);
                } else if (selectedDate < minDate || selectedDate > maxDate) {
                    console.log("Invalid input for " + this.name + ": \"" + this.value + "\"");
                    console.log(messages[1]);
                    this.setCustomValidity(messages[1]);
                } else {
                    console.log("Valid input for " + this.name + ": \"" + this.value + "\"");
                    this.setCustomValidity("");
                }

                this.reportValidity();
            });
        }

        function setRatingScoreValidity() {
            const message = "Value must be decimal number in range from 120.001 to 200.000 inclusive.";
            const inputRatingScore = document.getElementById('inputRatingScore');

            inputRatingScore.addEventListener('input', function () {
                const minVal = parseFloat(this.min);
                const maxVal = parseFloat(this.max);
                const selectedVal = parseFloat(this.value);

                if (selectedVal < minVal || selectedVal > maxVal) {
                    console.log("Invalid input for " + this.name + ": \"" + this.value + "\"");
                    console.log(message);
                    this.setCustomValidity(message);
                } else {
                    console.log("Valid input for " + this.name + ": \"" + this.value + "\"");
                    this.setCustomValidity("");
                }

                this.reportValidity();
            });
        }

        function setStringInputValidity() {
            const messages = [
                "Value must be Ukrainian char string between 5 and 20 chars long in format 'LLLLLLLLLLNN-NNNN'.",
                "Value must be Ukrainian char string between 1 and 50 chars long, including space and apostrophe.",
                "Value must be Ukrainian char string between 1 and 50 chars long, including space and apostrophe.",
                "Value must be Ukrainian char string between 1 and 50 chars long, including space and apostrophe."
            ];

            const form = document.getElementById('entrantForm');
            const inputs = form.querySelectorAll('input[pattern]');

            inputs.forEach((input, i) => {
                input.addEventListener('input', function () {
                    if (this.validity.patternMismatch) {
                        console.log("Invalid input for " + this.name + ": \"" + this.value + "\"");
                        console.log(messages[i]);
                        this.setCustomValidity(messages[i]);
                    } else {
                        console.log("Valid input for " + this.name + ": \"" + this.value + "\"");
                        this.setCustomValidity('');
                    }

                    this.reportValidity();
                });
            });
        }

    }

    function submitConfirmation(message) {
        const form = document.getElementById('entrantForm');

        if (confirm(message != null ? message : 'Are you sure you want to do this action?') === true) {
            form.submit();
        }
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<jsp:include page="../component/Footer.jsp"/>

</body>
</html>
