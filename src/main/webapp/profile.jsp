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
		
	<title>${applicationScope['WEB_NAME']} - Profile</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<jsp:include page="signedinnavbar.jsp"/>

    <div class="container marketing">
	  <h2>Update Your Profile</h2>

      <hr class="featurette-divider">
      
      <h5 style="color:Red" name="error_message" id="error_message">
        <c:if test="${errorMessage != null && errorFlg == 1}">
          ${errorMessage}
        </c:if>
      </h5>
      
      <h5 style="color:Green" name="correct_message" id="correct_message">
        <c:if test="${errorMessage != null && errorFlg == 0}">
          ${errorMessage}
        </c:if>
      </h5>

      <div class="row featurette">
        <div class="col-md-6">
          <h2 class="featurette-heading">Your User Details</h2>
          
          <form action="${applicationScope['ROUTER_PROFILE']}" method="POST" onSubmit="return validator_update_profile(this)">
			  
		  	<div class="form-group" id="div-username" name="div-username">
		  		<label>Username <label style="color:red">*</label></label>
  				<input type="text" class="form-control" id="username" name="username" placeholder="Username..." value="${user.getUsername()}" readonly/>
			</div>
			
			<div class="form-group" id="div-email" name="div-email">
		  		<label>Email <label style="color:red">*</label></label>
  				<input type="text" class="form-control" id="email" name="email" placeholder="Email..." value="${user.getEmail()}"/>
			</div>
			
			<div class="form-group" id="div-firstname" name="div-firstname">
		  		<label>First Name <label style="color:red">*</label></label>
  				<input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name..." value="${user.getFirstName()}"/>
			</div>
			
			<div class="form-group" id="div-middlename" name="div-middlename">
		  		<label>Middle Name</label>
  				<input type="text" class="form-control" id="middlename" name="middlename" placeholder="Middle Name..." value="${user.getMiddleName()}"/>
			</div>
			
			<div class="form-group" id="div-lastname" name="div-lastname">
		  		<label>Last Name <label style="color:red">*</label></label>
  				<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name..." value="${user.getLastName()}"/>
			</div>
			 			  
			<input type="hidden" name="action" value="update_profile"/>	
			<button type="submit" class="btn btn-default">Update</button>	
		  </form>
        </div>
      </div>

      <hr class="featurette-divider">
      
       <div class="row featurette">
        <div class="col-md-6">
          <h2 class="featurette-heading">Update Your Password</h2>
          
          <form action="${applicationScope['ROUTER_PROFILE']}" method="POST">
			  
		  	<div class="form-group" id="div-password" name="div-password">
		  		<label>Password <label style="color:red">*</label></label>
				<input type="password" class="form-control" id="password" name="password" placeholder="Password..." value=""/>
			</div>
			
			<div class="form-group" id="div-repassword" name="div-repassword">
		  		<label>Retype Password <label style="color:red">*</label></label>
				<input type="password" class="form-control" id="repassword" name="repassword" placeholder="Retype Password..." value=""/>
			</div>
			
			<input type="hidden" name="action" value="update_password"/>	
			<button type="submit" class="btn btn-default">Update</button>	
		  </form>
        </div>
      </div>

      <hr class="featurette-divider">
      
       <div class="row featurette">
        <div class="col-md-6">
          <h2 class="featurette-heading">Update Your Preferences</h2>
          
          <form action="${applicationScope['ROUTER_PROFILE']}" method="POST">
          
          	<input type="hidden" name="action" value="update_preferences"/>	
			<button type="submit" class="btn btn-default">Update</button>	
		  </form>
        </div>
      </div>

  	   <%@ include file="footer.jsp" %>
    </div>
    
    <script type="text/javascript">
		function validateEmail(email) {
		    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		    return re.test(email);
		}

		function validator_update_profile(form){									
			var email = form.email.value.trim();
			var firstname = form.firstname.value.trim();
			var lastname = form.lastname.value.trim();
			
			email = email.replace(" ", "");

			if (email.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Email!";
				document.getElementById("div-email").className = "form-group has-error";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.email.focus();
				return false;
			}
			else if (firstname.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your First Name!";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group has-error";
				document.getElementById("div-lastname").className = "form-group";
				
				form.firstname.focus();
				return false;
			}
			else if (lastname.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter your Last Name!";
				document.getElementById("div-email").className = "form-group";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group has-error";
				
				form.lastname.focus();
				return false;
			}
			else if (!validateEmail(email)){
				document.getElementById("error_message").innerHTML = "Please enter a valid email address!";
				document.getElementById("div-email").className = "form-group has-error";
				document.getElementById("div-firstname").className = "form-group";
				document.getElementById("div-lastname").className = "form-group";
				
				form.email.focus();
				return false;
			}

			return true;
		};	
	</script>
</body>
</html>