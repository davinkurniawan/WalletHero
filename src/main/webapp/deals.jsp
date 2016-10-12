<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Deals</title>
</head>
<body>
	<div class="container" align="center">
	<c:choose>
	<c:when test="${empty errormsg}">
	<table style="width: 100%" cellpadding="10" align="center">
		<tr>
			<th align="center">Image</th>
			<th align="center">Title</th>
			<th align="center">From</th>
			<th align="center">Discount</th>
			<th align="center">Price</th>
		</tr>
		<c:forEach var="i" items="${deals}">
			<tr>
				<td align="center"><img src="${i.image_url}" height="200"
					width="250"></td>
				<td>${i.title}</td>
				<td align="center">${i.value}</td>
				<td align="center">${i.discount_amount}</td>
				<td align="center">${i.price}</td>
			</tr>
		</c:forEach>
	</table>
		<ul class="pagination">
			<li><a href="${applicationScope['ROUTER_DEALS']}&page=1">&lt;&lt;</a>
			<c:forEach var="p" begin="${starting_page}" end="${ending_page}">
				<li><a href="${applicationScope['ROUTER_DEALS']}&page=${p}">${p}</a>
			</c:forEach>
			<li><a href="${applicationScope['ROUTER_DEALS']}&page=${max_page}">&gt;&gt;</a>
		</ul>
	</c:when>
	<c:otherwise>
		<h2 align="center">${errormsg}</h2>
	</c:otherwise>
	</c:choose>
	</div>
</body>
</html>