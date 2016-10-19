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
	
	<title>${applicationScope['WEB_NAME']} - Sign In</title>
<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
    <jsp:include page="navbar.jsp"></jsp:include>
	
	<div class="container marketing">
	  <h2>Sign In</h2>

    <hr class="featurette-divider">
    
    <div id="div-loading" name="div-loading">
	  	<div id="loader" name="loader"></div>
	  	<center>
	  		<h3 id="loadertext" name="loadertext">Signing You In...</h3>
	  	</center>
	</div>

    <h5 style="color:Red" name="error_message" id="error_message">
      <c:if test="${errorMessage != null && errorFlg == 1}">
        ${errorMessage}
      </c:if>
    </h5>
    
    <h5 style="color:Green"  name="error_message_2" id="error_message_2">
      <c:if test="${errorMessage != null && errorFlg == 2}">
        ${errorMessage}
      </c:if>
    </h5>

    <div class="row featurette" name="div-content" id="div-content">
      <div class="col-md-6">          
        <form action="${applicationScope['ROUTER_SIGNIN']}" method="POST">

        <div class="form-group" id="div-username" name="div-username">
          <label>Username or Email</label>
          <input type="text" class="form-control" id="username" name="username" placeholder="Username or Email..." value="${param['username']}"/>
        </div>

        <div class="form-group" id="div-password" name="div-password">
          <label>Password</label>
          <input type="password" class="form-control" id="password" name="password" placeholder="Password..." value=""/>
        </div>

          <input type="hidden" name="action" value="login"/> 
          <button type="submit" class="btn btn-primary">Sign In</button> 
        </form>

        <hr class="featurette-divider">

        <h5 style="color:Black">
            New to ${applicationScope['WEB_NAME']}? <a style="color:#0099FF" href="${applicationScope['ROUTER_SIGNUP']}">Sign up now »</a>
       		<br/><br/>
       		 <a style="color:#0099FF" href="${applicationScope['ROUTER_FORGOTPASSWORD']}">Forgot Your Password?</a>
        </h5>
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
	    
	    function validator_signin(form){                  
	      var username = form.username.value.trim();
	      var password = form.password.value.trim();
	
	      username = username.replace(" ", "");
	
	      if(username.length == 0){
	        document.getElementById("error_message").innerHTML = "Please enter your Username or Email!";
	        document.getElementById("div-username").className = "form-group has-error";
	        document.getElementById("div-password").className = "form-group";
	
	        form.username.focus();
	        return false;
	      }
	      else if (password.length == 0){
	        document.getElementById("error_message").innerHTML = "Please enter your Password!";
	        document.getElementById("div-username").className = "form-group";
	        document.getElementById("div-password").className = "form-group has-error";
	
	        form.password.focus();
	        return false;
	      }
	      else if (username.length > 32 || username.length < 6){
	        document.getElementById("error_message").innerHTML = "Username must be 6 to 32 characters!";
	        document.getElementById("div-username").className = "form-group has-error";
	        document.getElementById("div-password").className = "form-group";
	
	        form.username.focus();
	        return false;
	      }
	
	      return true;
	    };  
	    
	    $('form').submit( function(event) {
	       	var form = this;
			
			if (validator_signin(form)){
				document.getElementById('div-loading').style.display = 'block';	
		 		document.getElementById('div-content').style.display = 'none';
		 		document.getElementById('div-footer').style.display = 'none';
		 		document.getElementById("error_message").innerHTML = '';
		 		document.getElementById("error_message_2").innerHTML = '';
	
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