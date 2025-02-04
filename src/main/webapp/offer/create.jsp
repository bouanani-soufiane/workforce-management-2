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
                    <h2>Add Offer</h2>

                    <form id="offerForm">
                        <div>
                            <label for="title">title:</label>
                            <input type="text" id="title" name="title" required><br><br>
                        </div>

                        <div>
                            <label for="description">description:</label>
                            <input type="text" id="description" name="description" required><br><br>
                        </div>

                        <div>
                            <label for="requiredSkills">requiredSkills:</label>
                            <input type="text" id="requiredSkills" name="requiredSkills" required><br><br>

                        </div>
                        <div>
                            <label for="dateEnd">dateEnd:</label>
                            <input type="datetime-local" id="dateEnd" name="dateEnd"  required><br><br>
                        </div>
                        <input type="submit" value="Add offer">
                    </form>

                </div>

                <script>
                    const form = document.getElementById('offerForm');
                    const successAlert = document.getElementById('successAlert');
                    const errorAlert = document.getElementById('errorAlert');
                    form.addEventListener('submit', function (event) {
                        event.preventDefault();
                        const formData = new URLSearchParams(new FormData(this)).toString();

                        fetch("/workforce/offer/store", {
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
                        const dateInput = document.getElementById('dateEnd');
                        const today = new Date();
                        const minDate = today.toISOString().slice(0, 16);
                        dateInput.setAttribute('min', minDate);
                    }
                    window.onload = setMinDate;
                </script>

            </div>
        </div>
    </main>
</div>
</body>
</html>
