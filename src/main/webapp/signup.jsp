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
	
	<title>${applicationScope['WEB_NAME']} - Sign Up</title>
</head>
<body>
<h3>Register as a new user:</h3>
	<form action="${applicationScope['ROUTER_SIGNUP']}" method="POST">
	  <input type="hidden" name="action" value="signUp">
	  <table><tbody>
		<tr>
		  <td>Username:</td>
		  <td><input type="text" name="username" /></td>
		</tr>
		<tr>
		  <td>Password:</td> 
		  <td><input type="password" name="password" /></td>
		</tr>
		<tr>
		  <td>Re-Password:</td> 
		  <td><input type="password" name="repassword" /></td>
		</tr>
		<tr>
		  <td>Email:</td> 
		  <td><input type="text" name="email" /></td>
		</tr>
		<tr>
		  <td>First Name:</td> 
		  <td><input type="text" name="first_name" /></td>
		</tr>
		<tr>
		  <td>Middle Name:</td> 
		  <td><input type="text" name="middle_name" /></td>
		</tr>
		<tr>
		  <td>Last Name:</td> 
		  <td><input type="text" name="last_name" /></td>
		</tr>
	  </tbody></table>
	  
	  <h2>CAPTCHA HERE</h2>
	  <input type="hidden" name="action" value="create"/>	
	  <button type="submit">Sign Up</button>
	</form>
</body>
</html>