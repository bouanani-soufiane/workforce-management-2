<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="style.css">

</head>
<body>
<h1>hello : <c:out value="${message}" /></h1>

<c:set var="now" value="<%= new Date()%>" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"  />

</body>
</html>
