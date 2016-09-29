<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	
	<title>${applicationScope['WEB_NAME']} - Email Update</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<c:choose>
		<c:when test="${userID != null}">
			<%@ include file="signedinnavbar.jsp" %>
		</c:when>
		<c:otherwise>
			<%@ include file="navbar.jsp" %>
		</c:otherwise>
	</c:choose>
	
	<div class="container marketing">

	  <h2>${applicationScope['WEB_NAME']} Email Update</h2>

	  <hr class="featurette-divider">
	
	  <center>
		  <c:choose>
			  <c:when test="${errorFlg == 0}" >
				<h3 style="color:Green;text-align:center">
					<c:if test="${errorMessage != null}">
			          ${errorMessage}
			        </c:if>
			        <br/><br/>
			        <c:choose>
						<c:when test="${userID == null}">
							<a href="${applicationScope['ROUTER_SIGNIN']}">Sign In Now</a>
						</c:when>
						<c:otherwise>
							<a href="./">Go Back Home</a>
						</c:otherwise>
					</c:choose>
				</h3>
			  </c:when>
			  <c:otherwise>
				  <h3 style="color:Red" name="error_message" id="error_message">
			        <c:if test="${errorMessage != null}">
			          ${errorMessage}
			        </c:if>
			        <br/><br/>
					<a href="./">Go Back Home</a>
			      </h3>
			  </c:otherwise>
		  </c:choose>
	   </center>

	<%@ include file="footer.jsp" %>
    </div>
</body>
</html>