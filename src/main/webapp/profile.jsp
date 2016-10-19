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
      
      <div id="div-loading" name="div-loading">
	  	<div id="loader" name="loader"></div>
	  	<center>
	  		<h3 id="loadertext" name="loadertext">Signing You Up...</h3>
	  	</center>
	  </div>
      
      <div name="div-content" id="div-content">
	      <c:if test="${errorMessage != null && errorFlg == 1}">
		  	<div class="alert alert-danger">
				<strong>Error!</strong> ${errorMessage}
			</div>
		  </c:if>
		  
		  <c:if test="${errorMessage != null && errorFlg == 3}">
		  	<div class="alert alert-info">
				<strong> ${errorMessage}
			</div>
		  </c:if>

		  <c:if test="${errorMessage != null && errorFlg == 0}">
			<div class="alert alert-success">
				<strong>${errorMessage}</strong>
			</div>
		  </c:if>
		
	      <h5 style="color:Red" name="error_message" id="error_message"></h5>
	
	      <div class="row featurette" name="div-content" id="div-content">
	        <div class="col-md-6">
	          <h3 class="featurette-heading">Your User Details</h3>
	          
	          <form name="update_profile" action="${applicationScope['ROUTER_PROFILE']}" method="POST">
				  
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
				<button type="submit" class="btn btn-primary">Update</button>	
			  </form>
	        </div>
	      </div>
	
	      <hr class="featurette-divider">
	      
	      <h5 style="color:Red" name="error_message_password" id="error_message_password">
	        <c:if test="${errorMessage != null && errorFlg == 2}">
	          ${errorMessage}
	        </c:if>
	      </h5>
	      
	       <div class="row featurette" name="div-content-2" id="div-content-2">
	        <div class="col-md-6">
	          <h3 class="featurette-heading">Update Your Password</h3>
	          
	          <form name="update_password" action="${applicationScope['ROUTER_PROFILE']}" method="POST">
				  
			  	<div class="form-group" id="div-password" name="div-password">
			  		<label>Password <label style="color:red">*</label></label>
					<input type="password" class="form-control" id="password" name="password" placeholder="Password..." value=""/>
				</div>
				
				<div class="form-group" id="div-repassword" name="div-repassword">
			  		<label>Retype Password <label style="color:red">*</label></label>
					<input type="password" class="form-control" id="repassword" name="repassword" placeholder="Retype Password..." value=""/>
				</div>
				
				<input type="hidden" name="action" value="update_password"/>	
				<button type="submit" class="btn btn-primary">Update</button>	
			  </form>
	        </div>
	      </div>
	
	      <hr class="featurette-divider">
	      
	       <div class="row featurette">
	        <div class="col-md-6">
	          <h3 class="featurette-heading">Update Your Preferences</h3>
	          
	          <form name="update_preferences" action="${applicationScope['ROUTER_PROFILE']}" method="POST">
	          
	          	<div class="form-group" id="div-currency" name="div-currency">
			  		<label>Your Currency <label style="color:red">*</label></label>
			  		<br/>
					<select id="currency" name="currency" class="form-control">				
						<c:forEach var="cur" items="${currency}">			
							<c:choose>
								<c:when test="${preference.getCurrencyID() == cur.getCurrencyID()}">
									<option value="${cur.getCurrencyID()}" selected>${cur.getLongName()} - ${cur.getShortName()}</option>
								</c:when>
								<c:otherwise>
									<option value="${cur.getCurrencyID()}">${cur.getLongName()} - ${cur.getShortName()}</option>
								</c:otherwise>
							</c:choose>					
						</c:forEach>
					</select> 
				</div>
				
				<div class="form-group" id="div-occupation" name="div-occupation">
			  		<label>Your Occupation <label style="color:red">*</label></label>
			  		<br/>
					<select id="occupation" name="occupation" class="form-control">				
						<c:forEach var="occ" items="${occupation}">
							<c:choose>
								<c:when test="${preference.getOccupationID() == occ.getOccupationID()}">
									<option value="${occ.getOccupationID()}" selected>${occ.getName()}</option>
								</c:when>
								<c:otherwise>
									<option value="${occ.getOccupationID()}">${occ.getName()}</option>
								</c:otherwise>
							</c:choose>						
						</c:forEach>
					</select> 
				</div>
				
				<div class="form-group" id="div-gender" name="div-gender">
			  		<label>Gender <label style="color:red">*</label></label>
			  		<br/>
					<select id="gender" name="gender" class="form-control">	
						<c:choose>
							<c:when test="${preference.getGender().equalsIgnoreCase('M')}">
								<option value="M" selected>Male</option>
								<option value="F">Female</option>
								<option value="O">Others</option>
							</c:when>
							<c:when test="${preference.getGender().equalsIgnoreCase('F')}">
								<option value="M">Male</option>
								<option value="F" selected>Female</option>
								<option value="O">Others</option>
							</c:when>
							<c:otherwise>
								<option value="M">Male</option>
								<option value="F">Female</option>
								<option value="O" selected>Others</option>
							</c:otherwise>
						</c:choose>		
					</select> 
				</div>
				
				<div class="form-group" id="div-age" name="div-age">
			  		<label>Age <label style="color:red">*</label></label>
					<input type="number" class="form-control" id="age" name="age" placeholder="Age..." value="${preference.getAge()}" step="1" min="1"/>
				</div>
	          
	          	<input type="hidden" name="action" value="update_preferences"/>	
				<button type="submit" class="btn btn-primary">Update</button>	
			  </form>
	         </div>
	        </div>
	        
	        <hr class="featurette-divider">
	        
	       	<div class="row featurette">
	        	<div class="col-md-12">
	        		 <h3 class="featurette-heading">Update Your Preferred Deals</h3>
	        	
					<form name="update_deals" action="${applicationScope['ROUTER_PROFILE']}" method="POST">
						<div class="form-group checkbox" id="div-category">
							<table class="table table-striped">
						        <colgroup>
						            <col class="col-md-2">
						            <col class="col-md-2">
						            <col class="col-md-2">
						            <col class="col-md-2">
						            <col class="col-md-2">
						            <col class="col-md-2">
						        </colgroup>
						        <tbody>
									<% int count = 0; %>
									<c:forEach items="${categories}" var="c">
										<% if (count % 5 == 0) out.println ("<tr>"); %>
										<!-- <% out.println(count); %> -->
										<c:set var="checked" scope="request" value=""></c:set>
										<td style="width:20%">
										<c:forEach items="${deals_preference}" var="i">
											<c:choose>
												<c:when test="${i == c.slug}">
													<c:set var="checked" scope="request" value="checked"></c:set>
												</c:when>
											</c:choose>
										</c:forEach>
										<label><input type="checkbox" name="category"
											id="categoryBox" value="${c.slug}" ${checked}> ${c.name}</label>
										</td>
										<%  count++;%>
									</c:forEach>
								</tbody>
							</table>
				          	<input type="hidden" name="action" value="update_deals"/>	
							<button type="submit" class="btn btn-primary">Update</button>	
						</div>
					</form>
	      		</div>
	      	</div>
	        
	        <hr class="featurette-divider">
	      
	       	<div class="row featurette">
	        	<div class="col-md-6">
	          		<h3 class="featurette-heading">Others</h3>
	          
	          		<div class="form-group" id="div-delete-data" name="div-delete-data">
		          		<form name="delete_user_data" action="${applicationScope['ROUTER_PROFILE']}" method="POST" onSubmit="return confirm('Are you sure you delete all of your data (Transactions and Goals)?');">
		          			<input type="hidden" name="action" value="delete_user_data"/>	
							<button style="min-width:150px" type="submit" class="btn btn-danger">Delete All User Data</button>	
		      			</form>
	      			</div>
	
	          		<div class="form-group" id="div-delete-account" name="div-delete-account">
		      			<form name="delete_account" action="${applicationScope['ROUTER_PROFILE']}" method="POST" onSubmit="return confirm('Are you sure you delete your WalletHero Account? (All of your data would be unrecoverable.)');">
		          			<input type="hidden" name="action" value="delete_account"/>	
							<button style="min-width:150px" type="submit" class="btn btn-danger">Delete Account</button>	
		      			</form>
	      			</div>
	      		</div>
	      	</div>
      	</div>

  	   <%@ include file="footer.jsp" %>
    </div>
    
    <script type="text/javascript">
	    $(document).ready(function(){
	 		document.getElementById('div-loading').style.display = 'none';	
	 		document.getElementById('div-content').style.display = 'block';
	 		document.getElementById('div-footer').style.display = 'block';
	 	});
    	
	    function isNumeric(n) {
		  return !isNaN(parseFloat(n)) && isFinite(n);
		}
	    	
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
		
		function validator_update_password(form){									
			var password = form.password.value.trim();
			var repassword = form.repassword.value.trim();

			if (password.length == 0){
				document.getElementById("error_message_password").innerHTML = "Please enter your Password!";
				document.getElementById("div-password").className = "form-group has-error";
				document.getElementById("div-repassword").className = "form-group";
				
				form.password.focus();
				return false;
			}
			else if (repassword.length == 0){
				document.getElementById("error_message_password").innerHTML = "Please retype your Password!";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group has-error";
				
				form.repassword.focus();
				return false;
			}
			else if (password.length < 6){
				document.getElementById("error_message_password").innerHTML = "Password must be at least 6 characters!";
				document.getElementById("div-password").className = "form-group has-error";
				document.getElementById("div-repassword").className = "form-group";

				form.password.focus();
				return false;
			}
			else if (repassword.length < 6){
				document.getElementById("error_message_password").innerHTML = "Retyped Password must be at least 6 characters!";
				document.getElementById("div-password").className = "form-group";
				document.getElementById("div-repassword").className = "form-group has-error";

				form.repassword.focus();
				return false;
			}
			else if (password != repassword){
				document.getElementById("error_message_password").innerHTML = "Passsword does not match with Retyped Password";
				document.getElementById("div-password").className = "form-group has-error";
				document.getElementById("div-repassword").className = "form-group has-error";
				
				form.password.focus();
				return false;
			}

			return true;
		};	
		
		function validator_update_preference(form){									
			var age = form.age.value.trim();
			age = age.replace(" ", "");
			
			var re = /^[0-9]+$/;
		    return re.test(age);
		};
		
		$('form').submit( function(event) {
	       	var form = this;
	       				
	       	if (form == document.forms["update_profile"]) {
	       		
	       		if (validator_update_profile(this)) {
					document.getElementById("loadertext").innerHTML = 'Updating Your Profile...';
	       			
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
	       	}
	       	else if (form == document.forms["update_password"]) {
	       		
	       		if (validator_update_password(this)) {
					document.getElementById("loadertext").innerHTML = 'Updating Your Password...';
	       			
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
	       	}
	       	else if (form == document.forms["update_preferences"]) {
	       		
	       		if (validator_update_preference(this)) {
	       			document.getElementById("loadertext").innerHTML = 'Updating Your Preferences...';
	       			
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
	       	}
	       	else if (form == document.forms["update_deals"]) {
	       		
       			document.getElementById("loadertext").innerHTML = 'Updating Your Deals...';
       			
       			document.getElementById('div-loading').style.display = 'block';	
		 		document.getElementById('div-content').style.display = 'none';
		 		document.getElementById('div-footer').style.display = 'none';
		 		document.getElementById("error_message").innerHTML = '';
		 		
	       		event.preventDefault();
	       		
	       		setTimeout( function () { 
			        form.submit();
			    }, 1000);
	       	}
	       	else if (form == document.forms["delete_user_data"]) {
				document.getElementById("loadertext").innerHTML = 'Deleting All of Your Data...';
       			
       			document.getElementById('div-loading').style.display = 'block';	
		 		document.getElementById('div-content').style.display = 'none';
		 		document.getElementById('div-footer').style.display = 'none';
		 		document.getElementById("error_message").innerHTML = '';
		 		
	       		event.preventDefault();
	       		
	       		setTimeout( function () { 
			        form.submit();
			    }, 1000);
	       	}
			else if (form == document.forms["delete_account"]) {  		
				document.getElementById("loadertext").innerHTML = 'Deleting Your WalletHero Account...';
       			
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