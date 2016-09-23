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
	
	<title>${applicationScope['WEB_NAME']} - Email Validation</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
    <div class="navbar-wrapper">
      <div class="container">
        <nav class="navbar navbar-custom navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${applicationScope['ROUTER_PUBLIC']}">WalletHero</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li><a href="./">Home</a></li>
                <li><a href="${applicationScope['ROUTER_SIGNIN']}">Sign In</a></li>
                <li><a href="${applicationScope['ROUTER_SIGNUP']}">Sign Up</a></li>
                <li><a href="${applicationScope['ROUTER_ABOUT']}">About</a></li>

                <!--<li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li role="separator" class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li>
                  </ul>
                </li>-->

              </ul>
            </div>
          </div>
        </nav>
      </div>
    </div>
	
	<div class="container marketing">

	  <h2>${applicationScope['WEB_NAME']} Email Validation</h2>

	  <hr class="featurette-divider">
	
	  <center>
		  <c:choose>
			  <c:when test="${errorFlg == 0}" >
				<h3 style="color:Green;text-align:center">
					<c:if test="${errorMessage != null}">
			          ${errorMessage}
			        </c:if>
			        <br/><br/>
					<a href="${applicationScope['ROUTER_SIGNIN']}">Sign In Now</a>
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