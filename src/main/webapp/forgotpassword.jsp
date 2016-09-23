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
	
	<title>${applicationScope['WEB_NAME']} - Forgot Password?</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<%@ include file="navbar.jsp" %>

    <div class="container marketing">

		<h2>Recover your Password</h2>

		<hr class="featurette-divider">

		<c:choose>
		  <c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('yes')}" >
			<h5 style="color:Green;text-align:center">
				We have sent you a Password Recovery Email!
				<br/><br/>
				Please Check Your Email. Before you can recover, you must click the link sent to your email address. If you did not receive this email, please check your junk/spam folder. If you entered an incorrect email address, you will need to re-enter your account details with the correct email address or username.
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

			      <form action="${applicationScope['ROUTER_FORGOTPASSWORD']}" method="POST" onSubmit="return validator_forgotpassword(this)">

				  	<div class="form-group" id="div-username" name="div-username">
				  		<label>Username or Email <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="username" name="username" placeholder="Username or Email..." value="${param['username']}"/>
					</div>
					
					<div class="form-group" id="div-firstname" name="div-firstname">
				  		<label>First Name <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name..." value="${param['firstname']}"/>
					</div>
					
					<div class="form-group" id="div-lastname" name="div-lastname">
				  		<label>Last Name <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name..." value="${param['lastname']}"/>
					</div>

					<input type="hidden" name="action" value="recovery"/> 
		          	<button type="submit" class="btn btn-default">Recover</button> 

			      </form>
			    </div>
			  </div>
		  </c:otherwise>
	  </c:choose>

	<%@ include file="footer.jsp" %>
    </div>

    <script type="text/javascript">
    	function validator_forgotpassword(form){									
			var username = form.username.value.trim();
			var firstname = form.firstname.value.trim();
			var lastname = form.lastname.value.trim();
			
			username = username.replace(" ", "");

			if(username.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Username!";
				document.getElementById("div-username").className = "form-group has-error";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";

				form.username.focus();
				return false;
			}
			else if (firstname.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your First Name!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-firstname").className = "form-group has-error";
				document.getElementById("div-lastname").className = "form-group";
				
				form.firstname.focus();
				return false;
			}
			else if (lastname.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Last Name!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group has-error";
				
				form.lastname.focus();
				return false;
			}
			else if (username.length > 32 || username.length < 6){
				document.getElementById("error_message").innerHTML = "Username must be 6 to 32 characters!";
				document.getElementById("div-username").className = "form-group has-error";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";

				form.username.focus();
				return false;
			}

			return true;
		};	
	</script>
</body>
</html>