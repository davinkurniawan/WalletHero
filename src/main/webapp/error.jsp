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
		
	<title>${applicationScope['WEB_NAME']} - Page Not Found</title>
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
		<center>
		
		  <h2>Page Not Found</h2>
	
	      <hr class="featurette-divider">
	
	      <div class="row featurette">
	        <div class="col-md-12">
	          <h3 class="featurette-heading" style="color:Red;">The Page that you are looking for does not exists!</h3>
	          	<br/>
	          	<h3 style="color:Green;text-align:center">
					<a href="./" style="color:Green;text-align:center">Go Back to Home Page</a>
				</h3>
	        </div>
	      </div>
	   </center>

  	   <%@ include file="footer.jsp" %>
    </div>
</body>
</html>