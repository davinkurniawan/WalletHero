<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	
	<title>${applicationScope['WEB_NAME']} - Latest Deals</title>
<%@ include file="bootstrapHeader.jsp"%>
</head>
<body onload="myFunction(${param.order})">
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h2>Latest Deals</h2>
		<hr class="featurette-divider">

		<form action="${applicationScope['ROUTER_DEALS']}" method="GET">
			<label>Search</label>
			<input type="hidden" name="operation" value="deals" />
			<c:if test="${not empty param.query}">
				<c:set var="query" scope="request" value="${param.query}" />
			</c:if>
			<input type="text" name='query' class="form-control" placeholder="Search for..." value=${query}> 
			<div class="form-group" id="div-order" name="div-order">
				  		<br><label>Order by</label>
				  		<br/>
						<select id="order" name="order" class="form-control">		
							<option id="" value = "" selected>Select order...</option>
							<option id="number_sold_desc" value="number_sold_desc">Number sold (High to Low)</option>
							<option id="number_sold" value="number_sold">Number sold (Low to High)</option>
							<option id="value_desc" value="value_desc">Original price (High to Low)</option>
							<option id="value" value="value">Original price (Low to High)</option>
							<option id="price_desc" value="price_desc">Final price (High to Low)</option>
							<option id="price" value="price">Final price (Low to High)</option>
							<option id="expires_at_desc" value="expires_at_desc">Expiration date (Descending)</option>
							<option id="expires_at" value="expires_at">Expiration date (Ascending)</option>
							<option id="updated_at_desc" value="updated_at_desc">Newest</option>
							<option id="updated_at" value="updated_at">Oldest</option>
						</select> 
					</div>
			<br> <label>Category</label>
			<div class="form-group checkbox" id="div-category">
				<c:forEach items="${categories}" var="c">
					<c:set var="checked" scope="request" value=""></c:set>
					<c:forEach items="${paramValues.category}" var="i">
						<c:choose>
							<c:when test="${i == c.slug}">
								<c:set var="checked" scope="request" value="checked"></c:set>
							</c:when>
						</c:choose>
					</c:forEach>
					<label><input type="checkbox" name="category"
						id="categoryBox" value="${c.slug}" ${checked}> ${c.name}</label>
				</c:forEach>
				<input type="hidden" name="page" value="1" /> <input type="submit"
					value="Search" class="btn btn-primary" />
			</div>
		</form>
		<div class="row featurette">
			<div class="col-md-12">
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
								<tr height="230">
									<td align="center">
										<div class="container_deal">
											<center>
												<a target="_blank" href="${i.url}"><img src="${i.image_url}"
													onerror="this.src='images/not_available.png'" height="200"
													width="300" /></a>
											</center>
										</div>
									</td>
									<td align="center"><a target="_blank" href="${i.url}">${i.title}</a></td>
									<td align="center">${i.value}</td>
									<td align="center">${i.discount_amount}</td>
									<td align="center">${i.price}</td>
								</tr>
							</c:forEach>
						</table>
						<c:forEach items="${paramValues.category}" var="i">
							<c:choose>
								<c:when test="${i} == ${c.slug}">
									<c:set var="checked" scope="request" value="checked"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="checked" scope="request" value=""></c:set>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<ul class="pagination">
							<li><a href="${applicationScope['ROUTER_DEALS']}&page=1&order=${param.order}&query=${param.query}<c:forEach items="${paramValues.category}" var="i">&category=${i}</c:forEach>">&lt;&lt;</a>
								<c:forEach var="p" begin="${starting_page}" end="${ending_page}">
									<c:set var="highlight" scope="request" value=""/>
									<c:if test="${param.page == p}">
										<c:set var="highlight" scope="request" value="class = \"active\"" />
									</c:if>
									<c:if test="${empty param.page}">
										<c:if test="${p == 1}">
											<c:set var="highlight" scope="request" value="class = \"active\"" />
										</c:if>
									</c:if>
									<li ${highlight}><a
										href="${applicationScope['ROUTER_DEALS']}&page=${p}&order=${param.order}&query=${param.query}<c:forEach items="${paramValues.category}" var="i">&category=${i}</c:forEach>">${p}</a>
								</c:forEach>
							<li><a
								href="${applicationScope['ROUTER_DEALS']}&page=${max_page}&order=${param.order}&query=${param.query}<c:forEach items="${paramValues.category}" var="i">&category=${i}</c:forEach>">&gt;&gt;</a>
						</ul>
					</c:when>
					<c:otherwise>
						<center>
							<h2 align="center">${errormsg}</h2>
						</center>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>
	<script>
	function myFunction(qs) {
		//window.alert(qs.value);
		qs.selected = "true";
	}
	</script>
</body>
</html>
