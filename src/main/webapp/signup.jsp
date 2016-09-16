<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<body>
<h3>Register as a new user:</h3>
<form action="${PROFILE_COMMAND}" method="POST">
  <table><tbody>
	<tr>
	  <td>Username:</td>
	  <td><input type="text" name="username" /></td>
	</tr>
	<tr>
	  <td>Password:</td> 
	  <td><input type="text" name="password" /></td>
	</tr>
	<tr>
	  <td>Email:</td> 
	  <td><input type="text" name="email" /></td>
	</tr>
  </tbody></table>
  <input type=submit />
</form>
</body>
</html>