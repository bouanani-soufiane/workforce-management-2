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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="static/css/chat.css">
    <link rel="stylesheet" href="static/css/home.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        <%@include file="style/home.css" %>
    </style>

</head>
<body>
<section class="header">
    <nav>
        <div class="logo">
            <a href="#"><span>Z</span>🌑</a>
        </div>
        <div class="nav-links" id="navLinks">
            <i class="fa fa-times" onclick="hideMenu()"></i>
            <ul>
                <li><a href="#home">HOME</a></li>
                <li><a href="#Offer">OFFER</a></li>

            </ul>
        </div>
        <i class="fa fa-bars" onclick="showMenu()"></i>
    </nav>
    <div class="text-box">
        <h1>Best Job Opportunity Here</h1>
        <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Alias praesentium placeat mollitia, quod fugiat ad
            laudantiu, tempore nihil. <br>java css everything is possible here we are here . don't worry</p>
        <a href="project_1.html" class="hero-btn">Login Here</a>
    </div>

</section>

<!------------Offers------------>
<section class="price-table" id="Offer">
    <h1>List Offers</h1>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam, officiis sunt. Pariatur.</p>
    <div class="basic-price-col">
        <c:choose>
            <c:when test="${not empty jobOffers}">
                <c:forEach var="offer" items="${jobOffers}">
                    <div class="basic-price-info">
                        <h4>${offer.title}</h4>
                        <div class="price">
                            <p>${offer.description}</p>
                            <div class="price-list">
                                <h3> required skills are : </h3>
                                <ul>
                                    <c:forEach var="skill" items="${fn:split(offer.requiredSkills, ',')}">
                                        <li>${fn:trim(skill)}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <p>deadline date is: ${f:formatLocalDateTime(offer.dateEnd, 'dd.MM.yyyy')}</p>
                            <p>Days left: ${f:getDaysLeft(offer.dateEnd)}</p>

                            <form action="${pageContext.request.contextPath}/candidate/create" method="GET">
                                <input type="text" name="jobOfferId" value="${offer.id}">
                                <button type="submit" class="price-btn">Apply Now</button>
                            </form>

                        </div>
                    </div>

                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="8">No employees found.</td>
                </tr>
            </c:otherwise>
        </c:choose>

    </div>
</section>


<!----------------------Footer--------------------->
<section class="footer">
    <h4>About Us</h4>
    <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Minima animi et voluptate, laudantium itaque, vitae
        aspernatur repellendus optio, enim quam totam <br> ad Lorem ipsum dolor sit, amet consectetur adipisicing elit.
        Tempore suscipit cum non.</p>
    <div class="icons">
        <i class="fa-brands fa-facebook"></i>
        <i class="fa-brands fa-twitter"></i>
        <i class="fa-brands fa-instagram"></i>
        <i class="fa-brands fa-linkedin"></i>
    </div>
    <p>Made With love</p>

</section>

</body>
</html>