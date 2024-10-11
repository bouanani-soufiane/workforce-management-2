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
        <div class="main-title">
            <p class="font-weight-bold">DASHBOARD</p>
        </div>
        <div class="charts">
            <div class="charts-card">
                <jsp:include page="../employee/employees.jsp"/>
            </div>
        </div>
    </main>
</div>
</body>
</html>
