<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>${applicationScope['WEB_NAME']}-Password Reset</title>
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${errorMessage != null}">
			<font color="red"><c:out value="${errorMessage}" /></font>
		</c:when>
		<c:when test="${param.success != null}">
			<font color="green">Password Reset Successful!</font>
		</c:when>
		<c:otherwise>
			<form action="${applicationScope['ROUTER_RESETPASSWORD']}"
				method=POST>
				Password Reset for ${param.username}
				<table>
					<tbody>
						<tr>
							<td>New Password:</td>
							<td><input type="text" name="password" /></td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="action" value="reset" />
				<input type="hidden" name="token" value="${param.token}" />
				<input type="hidden" name="username" value="${param.username}" />
				<button type="submit" class="btn btn-default">Reset</button>
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>