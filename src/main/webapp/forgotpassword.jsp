<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>${applicationScope['WEB_NAME']}- Password Recovery</title>
</head>
<body>
	<c:choose>
		<c:when test="${errorMessage != null}">
			<font color="red"><c:out value="${errorMessage}" /></font>
		</c:when>
		<c:when test="${success != null}">
			<font color="green">Password Recovery Email Sent!</font>
		</c:when>
		<c:otherwise>
			<form action="${applicationScope['ROUTER_FORGOTPASSWORD']}"
				method=POST>
				<table>
					<tbody>
						<tr>
							<td>Username or Email:</td>
							<td><input type="text" name="username" /></td>
						</tr>
						<tr>
							<td>First Name:</td>
							<td><input type="text" name="first_name" /></td>
						</tr>
						<tr>
							<td>Last Name:</td>
							<td><input type="text" name="last_name" /></td>
						</tr>
					</tbody>
				</table>

				<input type="hidden" name="action" value="recovery" />
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>