<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Deals</title>
</head>
<body>
	<table style="width:100%" cellpadding="10">
		<tr>
			<th>Image</th>
			<th>Title</th>
			<th>Price</th>
			<th>Discount</th>
		</tr>
		<c:forEach var="i" items="${deals}">
			<tr>
				<td><img src="${i.image_url}" height="200" width="250"></td>
				<td>${i.title}</td>
				<td>${i.price}</td>
				<td>${i.discount_amount}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>