<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <style>
        <%@include file="../style/dashboard.css" %>
    </style>
</head>
<body>
<div class="grid-container">
    <jsp:include page="../component/dashboard/header.jsp"/>
    <jsp:include page="../component/dashboard/sidebar.jsp"/>
    <main class="main-container">
        <div class="charts">
            <div class="charts-card">
                <div class="container">
                    <h2>Add Vacation</h2>

                    <form id="vacationForm">
                        <div class="date-picker">
                            <label for="dateStart">date start:</label>
                            <input type="date" id="dateStart" class="date" name="dateStart"  required><br><br>
                        </div>

                        <div class="date-picker">
                            <label for="dateEnd">date end:</label>
                            <input type="date" id="dateEnd" name="dateEnd" class="date" required><br><br>
                        </div>

                        <div>
                            <label for="reason">reason:</label>
                            <input type="text" id="reason" name="reason" required><br><br>

                        </div>
                        <div>
<%--                            <label for="certificate">certificate:</label>--%>
<%--                            <input type="file" id="certificate" name="certificate" accept=".pdf">--%>

                        </div>
                        <input type="submit" value="Add vacation">
                    </form>

                </div>

                <script>
                    const form = document.getElementById('vacationForm');
                    const successAlert = document.getElementById('successAlert');
                    const errorAlert = document.getElementById('errorAlert');
                    form.addEventListener('submit', function (event) {
                        event.preventDefault();
                        const formData = new URLSearchParams(new FormData(this)).toString();

                        fetch("/workforce/vacation/store", {
                            method: "POST",
                            body: formData,
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded"
                            }
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
                    function setMinDate() {
                        const dateInputs = document.querySelectorAll(".date");
                        const today = new Date();
                        const minDate = today.toISOString().split("T")[0];
                        dateInputs.forEach(input => {
                            input.setAttribute('min', minDate);
                        });
                    }
                    window.onload = setMinDate;
                </script>

            </div>
        </div>
    </main>
</div>
</body>
</html>
