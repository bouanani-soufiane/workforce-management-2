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
            <p class="font-weight-bold">vacations</p>
        </div>
        <div class="charts">
            <div class="charts-card">
                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <h1>Welcome To vacations List</h1>
                <a href="${pageContext.request.contextPath}/vacation/create">Add vacation</a>
                <table>
                    <thead>
                    <tr>
                        <th>start date</th>
                        <th>end date</th>
                        <th>reason</th>
                        <th>status</th>
                        <th>certificate</th>
                        <th>employee</th>
                    </tr>
                    </thead>
                    <tbody id="table-data">
                    <c:choose>
                        <c:when test="${not empty vacations}">
                            <c:forEach var="vacation" items="${vacations}">
                                <tr>
                                    <td>${vacation.startDate}</td>
                                    <td>${vacation.endDate}</td>
                                    <td>${vacation.reason}</td>
                                    <td>${vacation.vacationStatus}</td>
                                    <td>${vacation.certificate}</td>
                                    <td>${vacation.employee.name}</td>
                                    <td>
                                        <form action="/workforce/vacation/reject?id=${vacation.id}" method="post">
                                            <button class="setStatus">reject</button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/workforce/vacation/accept?id=${vacation.id}" method="post">
                                            <button class="setStatus">accept</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8">No vacations found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>

            </div>
        </div>
    </main>
</div>
</body>
</html>