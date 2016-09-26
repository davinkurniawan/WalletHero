<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/navbar.css">
<link rel="stylesheet" href="css/sticky-footer.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.0/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.0/jquery-ui.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<title>${applicationScope['WEB_NAME']}-TransactionsOverview</title>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>


	<div class="container marketing">
		<h3>${requestScope.transactionRange}</h3>

		<p></p>

		<form action="router?operation=viewTransactions" method="POST">
			From date: <input type="text" class="datepicker" id="from_date" name="from_date">
			To date: <input type="text" class="datepicker" id="to_date" name="to_date">


			<p></p>

			<input type="checkbox" name="incomesChk" id="incomesButton" value="true" checked="checked" />
			Show incomes?

			<p></p>

			<input type="checkbox" name="expensesChk" id="expensesButton" value="true" checked="checked" />
			Show expenses?

			<p></p>

			Category: <select name="category">
				<option value="-1">All</option>
				<c:forEach items="${requestScope.categories}" var="c">
					<option value="${c.getID()}">${c.category}</option>

				</c:forEach>
			</select>

			<p></p>

			<input type=submit value="Confirm" class="btn btn-primary" />
		</form>

		<p></p>

		<table class="table table-bordered" style="table-layout: fixed">
			<tbody>
				<tr>
					<th>Transaction ID</th>
					<th>Details</th>
					<th>Amount</th>
					<th>Date</th>
					<th>Category</th>
					<th>Type</th>
				</tr>
				<c:forEach items="${requestScope.transactionList}" var="t">
					<tr class="${t.getTransactionType()}">
						<td><c:out value="${t.transactionID}"></c:out></td>
						<td><c:out value="${t.detail}"></c:out></td>
						<td>$<c:out value="${t.amount}"></c:out></td>
						<td><c:out value="${t.date}"></c:out></td>
						<td><c:out value="${t.getCategoryName()}"></c:out></td>
						<td><c:out value="${t.getTransactionType()}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<%@ include file="footer.jsp"%>
	</div>

	<script>
		$(function() {
			$(".datepicker").datepicker();
		});

		$(function() {
			$(".datepicker").datepicker("option", "dateFormat", "yy-mm-dd");
		});
		
		
		$("#from_date").datepicker();
		$("#to_date").datepicker();
		
		$("#from_date").datepicker("setDate", new Date('${requestScope.fromDate}'));
		$("#to_date").datepicker("setDate", new Date('${requestScope.toDate}'));
	</script>
</body>
</html>