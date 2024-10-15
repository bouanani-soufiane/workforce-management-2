<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://ma.yc/functions" prefix="f" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Start Up website design</title>
    <style>
        <%@include file="../style/home.css" %>
    </style>
</head>
<body>
<section class="header">
    <jsp:include page="../component/navbar.jsp"/>
    <div class="text-box">
        <h1>create candidate</h1>
        <div class="candidate">
            <form id="candidate" enctype="multipart/form-data">
                <div>
                    <input type="hidden" name="jobOfferId" value="${jobOfferId}">
                </div>
                <div>
                    <label>your name</label>
                    <input type="text" name="name" required>
                </div>
                <div>
                    <label>your email</label>
                    <input type="email" name="email" required>
                </div>
                <div>
                    <label>your skills</label>
                    <input type="text" name="skills" required>
                </div>
                <div>
                    <label>your resume</label>
                    <input type="file" name="resume" accept=".pdf"  required>
                </div>
                <button>apply</button>

            </form>
        </div>
    </div>

</section>


<script>
    const form = document.getElementById('candidate');
    const successAlert = document.getElementById('successAlert');
    const errorAlert = document.getElementById('errorAlert');
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(this);

        fetch("/workforce/candidate/store", {
            method: "POST",
            body: formData,

        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                console.log(response)
                return response.json();
            })
            .then(json => {
                console.log(json);
                successAlert.classList.remove('hide');
                successAlert.classList.add('showAlert');
                setTimeout(() => {
                    successAlert.classList.add('hide');
                }, 3000);
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);

                errorAlert.classList.remove('hide');
                errorAlert.classList.add('showAlert');

                setTimeout(() => {
                    errorAlert.classList.add('hide');
                }, 3000);
            });
    });
</script>

<jsp:include page="../component/footer.jsp"/>

</body>
</html>