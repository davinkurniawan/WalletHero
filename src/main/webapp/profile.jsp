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
                <li><a href="${applicationScope['ROUTER_HOME']}">Home</a></li>
                <li><a href="${applicationScope['ROUTER_VIEWTRANSACTIONS']}">Transactions</a></li>
                <li><a href="${applicationScope['ROUTER_PROFILE']}">Profile</a></li>
                <li><a href="${applicationScope['ROUTER_ABOUT']}">About</a></li>
                <li><a href="${applicationScope['ROUTER_SIGNOUT']}">Sign Out</a></li>

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
	   <h2>Update Your Profile</h2>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-6">
          <h2 class="featurette-heading">Your User Details</h2>
          
          <form action="${applicationScope['ROUTER_PROFILE']}" method="POST">
			  
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
				<input type="text" class="form-control" id="first_name" name="first_name" placeholder="First Name..." value="${user.getFirst_name()}"/>
			</div>
			
			<div class="form-group" id="div-middlename" name="div-middlename">
		  		<label>Middle Name</label>
				<input type="text" class="form-control" id="middle_name" name="middle_name" placeholder="Middle Name..." value="${user.getMiddle_name()}"/>
			</div>
			
			<div class="form-group" id="div-lastname" name="div-lastname">
		  		<label>Last Name <label style="color:red">*</label></label>
				<input type="text" class="form-control" id="last_name" name="last_name" placeholder="Last Name..." value="${user.getLast_name()}"/>
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
				<input type="text" class="form-control" id="password" name="password" placeholder="Password..." value=""/>
			</div>
			
			<div class="form-group" id="div-repassword" name="div-repassword">
		  		<label>Retype Password <label style="color:red">*</label></label>
				<input type="text" class="form-control" id="repassword" name="repassword" placeholder="Retype Password..." value=""/>
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
</body>
</html>