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


                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <h1>Welcome To Employees List</h1>
                <table>
                    <thead>
                    <tr>
                        <th>title</th>
                        <th>description</th>
                        <th>required Skills</th>
                        <th>Date publish</th>
                        <th>Date end</th>
                        <th>is active</th>
                    </tr>
                    </thead>
                    <tbody id="table-data">
                    <c:choose>
                        <c:when test="${not empty offers}">
                            <c:forEach var="offer" items="${offers}">
                                <tr>
                                    <td>${offer.title}</td>
                                    <td>${offer.description}</td>
                                    <td>${offer.requiredSkills}</td>
                                    <td>${offer.datePublish}</td>
                                    <td>${offer.dateEnd}</td>
<%--                                    <td>${offer.isActive}</td>--%>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/offer/edit?id=${offer.id}"
                                           class="edit-link">Edit</a>
                                    </td>
                                    <td>
                                        <form action="/workforce/offer/delete?id=${offer.id}" method="post">
                                            <button class="deletebtn">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8">No offers found.</td>
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