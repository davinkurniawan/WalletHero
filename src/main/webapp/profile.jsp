<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	
	<title>${applicationScope['WEB_NAME']} - Profile</title>
</head>
<body>
	<h2>Update Your Profile</h2>
	
	<form action="${applicationScope['ROUTER_PROFILE']}" method="POST">
	  <table><tbody>
		<tr>
		  <td>Username <label style="color:red">*</label>:</td>
		  <td><input type="text" name="username" /></td>
		</tr>
		<tr>
		  <td>Email <label style="color:red">*</label>:</td> 
		  <td><input type="text" name="email" /></td>
		</tr>
		<tr>
		  <td>First Name <label style="color:red">*</label>:</td> 
		  <td><input type="text" name="first_name" /></td>
		</tr>
		<tr>
		  <td>Middle Name:</td> 
		  <td><input type="text" name="middle_name" /></td>
		</tr>
		<tr>
		  <td>Last Name <label style="color:red">*</label>:</td> 
		  <td><input type="text" name="last_name" /></td>
		</tr>
	  </tbody></table>
	  
	  <h2>CAPTCHA HERE</h2>
	  
	  <input type="hidden" name="action" value="update_profile"/>	
	  <input type="submit" value="Update"></input>
	</form>
	
	<h2>Update Your Password</h2>
	
	<form action="${applicationScope['ROUTER_PROFILE']}" method="POST">
	  <table><tbody>
		<tr>
		  <td>Password <label style="color:red">*</label>:</td> 
		  <td><input type="password" name="password" /></td>
		</tr>
		<tr>
		  <td>Re-Enter Password <label style="color:red">*</label>:</td> 
		  <td><input type="password" name="repassword" /></td>
		</tr>
	  </tbody></table>
	  
	  <input type="hidden" name="action" value="update_password"/>	
	  <input type="submit" value="Update"></input>
	</form>
	
	<h2>Update Your Preferences</h2>
	
	<form action="${applicationScope['ROUTER_PROFILE']}" method="POST">
		 <input type="hidden" name="action" value="update_preferences"/>	
		 <input type="submit" value="Update"></input>
	</form>
	
	<%@ include file="footer.jsp" %>
</body>
</html>