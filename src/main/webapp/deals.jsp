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
		
	<title>${applicationScope['WEB_NAME']} - Deals</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<%@ include file="signedinnavbar.jsp" %>
	
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
		<c:forEach var="i" items="${deals_list}">
			<tr>
				<td align="center">
					<div class="container_deal">
						<a href="${i.url}"><img src="${i.image_url}" onerror="this.src='images/not_available.png'"/></a>
					</div>
				</td>
				<td align="center"><a href="${i.url}">${i.title}</a></td>
				<td align="center">${i.value}</td>
				<td align="center">${i.discount_amount}</td>
				<td align="center">${i.price}</td>
				<td>${i.image_url}</td>
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