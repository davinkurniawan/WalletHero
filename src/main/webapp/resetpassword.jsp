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
	
	<title>${applicationScope['WEB_NAME']} - Reset Password</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<%@ include file="navbar.jsp" %>

    <div class="container marketing">

	  <hr class="featurette-divider">

	  <c:choose>
	  	<c:when test="${errorFlg == 2}">
			<font color="red"><c:out value="${errorMessage}" /></font>
		</c:when>
		<c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('yes')}" >
			<h5 style="color:Green;text-align:center">
				Password Reset Successful!
			</h5>
		</c:when>
		<c:otherwise>
			<h5 style="color:Red" name="error_message" id="error_message">
		        <c:if test="${errorMessage != null}">
		          ${errorMessage}
		        </c:if>
		    </h5>

		    <div class="row featurette">
		      	<div class="col-md-6"> 

		      		<h2>Password Reset for: ${param.username}</h2>

		      		<form action="${applicationScope['ROUTER_RESETPASSWORD']}"
						method="POST" onSubmit="return validator_resetpassword(this)">

						<div class="form-group" id="div-password" name="div-password">
					  		<label>New Password <label style="color:red">*</label></label>
							<input type="password" class="form-control" id="password" name="password" placeholder="New Password..." />
						</div>

						<input type="hidden" name="action" value="reset" />
						<input type="hidden" name="token" value="${param['token']}" />
						<input type="hidden" name="username" value="${param['username']}" />
						<button type="submit" class="btn btn-primary">Reset Password</button>
					</form>

		      	</div>
		    </div>
		</c:otherwise>
	  </c:choose>

	<%@ include file="footer.jsp" %>
    </div>

    <script type="text/javascript">
		function validator_resetpassword(form){									
			var password = form.password.value.trim();

			if (password.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Password!";
				document.getElementById("div-password").className = "form-group has-error";
				
				form.password.focus();
				return false;
			}
			else if (password.length < 6){
				document.getElementById("error_message").innerHTML = "Password must be at least 6 characters!";
				document.getElementById("div-password").className = "form-group has-error";

				form.password.focus();
				return false;
			}

			return true;
		};	
	</script>
</body>
</html>