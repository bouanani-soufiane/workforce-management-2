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
                    <h2>Add Employee</h2>

                    <form id="employeeForm">
                        <div>
                            <label for="name">Name:</label>
                            <input type="text" id="name" name="name" required><br><br>
                        </div>

                        <div>
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" required><br><br>
                        </div>

                        <div>
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" required><br><br>

                        </div>
                        <div>
                            <label for="phone">Phone:</label>
                            <input type="text" id="phone" name="phone" required><br><br>

                        </div>
                        <div>
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" required><br><br>

                        </div>
                        <div>
                            <label for="department">Department:</label>
                            <select id="department" name="department" required>
                                <option value="" disabled>Select a department</option>
                                <option value="HR">Human Resources</option>
                                <option value="IT">Information Technology</option>
                                <option value="Finance">Finance</option>
                                <option value="Sales">Sales</option>
                            </select><br><br>
                        </div>

                        <div>
                            <label for="position">Job Title:</label>
                            <input type="text" id="position" name="jobTitle" required><br><br>

                        </div>
                        <div>
                            <label for="birthDate">Birth Date:</label>
                            <input type="date" id="birthDate" name="birthDate" required><br><br>

                        </div>
                        <div>
                            <label for="securityNumber">Social Security Number:</label>
                            <input type="text" id="securityNumber" name="securityNumber" pattern="\d{3}-\d{2}-\d{4}"
                                   title="Format: AAA-GG-SSSS" required><br><br>

                        </div>
                        <div>
                            <label for="childrenCount">Children Count:</label>
                            <input type="number" id="childrenCount" name="childrenCount" min="0" required><br><br>

                        </div>
                        <div>
                            <label for="salary">Salary:</label>
                            <input type="number" id="salary" name="salary" min="0" step="0.01" required><br><br>

                        </div>
                        <input type="submit" value="Add Employee">
                    </form>

                </div>

                <script>
                    const form = document.getElementById('employeeForm');
                    const successAlert = document.getElementById('successAlert');
                    const errorAlert = document.getElementById('errorAlert');
                    form.addEventListener('submit', function (event) {
                        event.preventDefault();
                        const formData = new URLSearchParams(new FormData(this)).toString();

                        fetch("/workforce/employee/store", {
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


                </script>

            </div>
        </div>
    </main>
</div>
</body>
</html>
