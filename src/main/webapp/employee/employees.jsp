<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container">
    <h1>Welcome To Employees List</h1>


    <div class="search_filter">
        <form id="filterForm" method="post" action="${pageContext.request.contextPath}/filterByDepartment">
            <h4>Filter by Department:</h4>
            <div class="filter-checkboxes">
                <label><input type="checkbox" name="department[]" value="HR"> Human Resources</label>
                <label><input type="checkbox" name="department[]" value="IT"> Information Technology</label>
                <label><input type="checkbox" name="department[]" value="Finance"> Finance</label>
                <label><input type="checkbox" name="department[]" value="Sales"> Sales</label>
            </div>
            <button type="submit">Filter</button>
        </form>

        <input type="search" id="search-input" placeholder="Search...">

        <a href="${pageContext.request.contextPath}/employee/create" class="addem">Add New</a>

    </div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Birth Date</th>
            <th>Department</th>
            <th>hire Date</th>
            <th>Job Title</th>
            <th>Security Number</th>
            <th>Sold Vacation</th>
            <th>Salary</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody id="table-data">
        <c:choose>
            <c:when test="${not empty employeeList}">
                <c:forEach var="emp" items="${employeeList}">
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.name}</td>
                        <td>${emp.email}</td>
                        <td>${emp.phone}</td>
                        <td>${emp.address}</td>
                        <td>${emp.birthDate}</td>
                        <td>${emp.departement}</td>
                        <td>${emp.hireDate}</td>
                        <td>${emp.jobTitle}</td>
                        <td>${emp.securityNumber}</td>
                        <td>${emp.soldVacation}</td>
                        <td>${emp.famillyAllowance.salary}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/edit?id=${emp.id}" class="edit-link">Edit</a>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/delete?id=${emp.id}" method="post">
                                <button class="deletebtn">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="8">No employees found.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <div id="result"></div>

</div>
