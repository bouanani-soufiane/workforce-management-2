<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="style.css">

</head>
<body>
<h1><%= "Hello World!" %> <c:out value="cout value" />
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>


</body>
</html>