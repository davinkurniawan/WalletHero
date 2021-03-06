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
	
	<title>${applicationScope['WEB_NAME']} - Sign Up</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
    <jsp:include page="navbar.jsp"></jsp:include>
	
	<div class="container marketing">

	  <h2>Sign Up Now to ${applicationScope['WEB_NAME']}</h2>

	  <hr class="featurette-divider">
	  
	  <div id="div-loading" name="div-loading">
	  	<div id="loader" name="loader"></div>
	  	<center>
	  		<h3 id="loadertext" name="loadertext">Signing You Up...</h3>
	  	</center>
	  </div>

	  <c:choose>
		  <c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('yes')}" >
			<h5 style="color:Green;text-align:center">
				Your Account is registered with us!
				<br/><br/>
				Please Activate Your Account. Before you can login, you must active your account with the code sent to your email address. If you did not receive this email, please check your junk/spam folder. If you entered an incorrect email address, you will need to re-register with the correct email address.
			</h5>
			 <div class="row featurette" name="div-content" id="div-content">
			 </div>
		  </c:when>
		  <c:otherwise>
			  <h5 style="color:Red" name="error_message" id="error_message">
		        <c:if test="${errorMessage != null}">
		          ${errorMessage}
		        </c:if>
		      </h5>

		      <div class="row featurette" name="div-content" id="div-content">
		      	<div class="col-md-6"> 

			      <form action="${applicationScope['ROUTER_SIGNUP']}" method="POST">

				  	<div class="form-group" id="div-username" name="div-username">
				  		<label>Username <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="username" name="username" placeholder="Username..." value="${param['username']}"/>
					</div>

					<div class="form-group" id="div-password" name="div-password">
				  		<label>Password <label style="color:red">*</label></label>
						<input type="password" class="form-control" id="password" name="password" placeholder="Password..." />
					</div>

					<div class="form-group" id="div-repassword" name="div-repassword">
				  		<label>Retype Password <label style="color:red">*</label></label>
						<input type="password" class="form-control" id="repassword" name="repassword" placeholder="Retype Password..." />
					</div>
					
					<div class="form-group" id="div-email" name="div-email">
				  		<label>Email <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="email" name="email" placeholder="Email..." value="${param['email']}"/>
					</div>
					
					<div class="form-group" id="div-firstname" name="div-firstname">
				  		<label>First Name <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name..." value="${param['firstname']}"/>
					</div>
					
					<div class="form-group" id="div-middlename" name="div-middlename">
				  		<label>Middle Name</label>
						<input type="text" class="form-control" id="middlename" name="middlename" placeholder="Middle Name..." value="${param['middlename']}"/>
					</div>
					
					<div class="form-group" id="div-lastname" name="div-lastname">
				  		<label>Last Name <label style="color:red">*</label></label>
						<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name..." value="${param['lastname']}"/>
					</div>

					<input type="hidden" name="action" value="signUp"/> 
		          	<button type="submit" class="btn btn-primary">Sign Up</button> 

			      </form>
			    </div>
			  </div>
		  </c:otherwise>
	  </c:choose>

	<%@ include file="footer.jsp" %>
    </div>

    <script type="text/javascript">
	    $(document).ready(function(){
	 		document.getElementById('div-loading').style.display = 'none';	
	 		document.getElementById('div-content').style.display = 'block';
	 		document.getElementById('div-footer').style.display = 'block';
	 	});
	    
		function validateEmail(email) {
		    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		    return re.test(email);
		}

		function validator_signup(form){									
			var username = form.username.value.trim();
			var password = form.password.value.trim();
			var repassword = form.repassword.value.trim();
			var email = form.email.value.trim();
			var firstname = form.firstname.value.trim();
			var lastname = form.lastname.value.trim();
			
			username = username.replace(" ", "");
			email = email.replace(" ", "");

			if(username.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Username!";
				document.getElementById("div-username").className = "form-group has-error";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";

				form.username.focus();
				return false;
			}
			else if (password.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Password!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group has-error";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.password.focus();
				return false;
			}
			else if (repassword.length == 0){
				document.getElementById("error_message").innerHTML = "Please retype your Password!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group has-error";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.repassword.focus();
				return false;
			}
			else if (email.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Email!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group has-error";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.email.focus();
				return false;
			}
			else if (firstname.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your First Name!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group has-error";
				document.getElementById("div-lastname").className = "form-group";
				
				form.firstname.focus();
				return false;
			}
			else if (lastname.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Last Name!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group has-error";
				
				form.lastname.focus();
				return false;
			}
			else if (username.length > 32 || username.length < 6){
				document.getElementById("error_message").innerHTML = "Username must be 6 to 32 characters!";
				document.getElementById("div-username").className = "form-group has-error";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";

				form.username.focus();
				return false;
			}
			else if (password.length < 6){
				document.getElementById("error_message").innerHTML = "Password must be at least 6 characters!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group has-error";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";

				form.password.focus();
				return false;
			}
			else if (repassword.length < 6){
				document.getElementById("error_message").innerHTML = "Retyped Password must be at least 6 characters!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group has-error";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";

				form.repassword.focus();
				return false;
			}
			else if (password != repassword){
				document.getElementById("error_message").innerHTML = "Passsword does not match with Retyped Password";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group has-error";
				document.getElementById("div-repassword").className = "form-group has-error";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.password.focus();
				return false;
			}
			else if (!validateEmail(email)){
				document.getElementById("error_message").innerHTML = "Please enter a valid email address!";
				document.getElementById("div-username").className = "form-group";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group";
				document.getElementById("div-email").className = "form-group has-error";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.email.focus();
				return false;
			}
				 		
			return true;
		};	
		
		$('form').submit( function(event) {
	       	var form = this;
			
			if (validator_signup(form)){
				document.getElementById('div-loading').style.display = 'block';	
		 		document.getElementById('div-content').style.display = 'none';
		 		document.getElementById('div-footer').style.display = 'none';
		 		document.getElementById("error_message").innerHTML = '';
	
			    event.preventDefault();
			    
			    setTimeout( function () { 
			        form.submit();
			    }, 1000);
			}
			else {
				event.preventDefault();
			}
		}); 
	</script>
</body>
</html>