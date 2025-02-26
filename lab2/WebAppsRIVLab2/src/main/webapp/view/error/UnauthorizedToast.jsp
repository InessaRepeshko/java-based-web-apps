<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Unauthorized Toast</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/view/image/icon/favicon_square.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<div class="toast fade show z-1000 position-absolute top-0 start-50 translate-middle-x" id="warning-toast"
     role="alert" aria-live="assertive" aria-atomic="true">
    <div class="toast-header">
        <svg class="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg"
             aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
            <rect width="100%" height="100%" fill="#FF0000"></rect>
        </svg>
        <strong class="me-auto">Warning!</strong>
        <button type="button" class="btn-close" id="warning-toast-close-btn" onclick="closeToast()"
                data-bs-dismiss="toast" aria-label="Close"></button>
    </div>

    <div class="toast-body">
        Invalid credentials entered.<br>
        Please, try again!
    </div>
</div>

<script>
    document.getElementById("warning-toast-close-btn").addEventListener('click', closeToast);

    function closeToast() {
        const toastElement = document.getElementById("warning-toast");
        toastElement.classList.remove("show");
        toastElement.classList.add("hide");
    }
</script>
</body>
</html>
