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
	
	<title>${applicationScope['WEB_NAME']} - Transactions Overview</title>
</head>
<body>

	<h3>Viewing your past transactions</h3>

	<label class="checkbox-inline"><input type="checkbox"
		id="incomesButton" value="" checked="checked"> Show incomes? </label>

	<label class="checkbox-inline"><input type="checkbox"
		id="expensesButton" value="" checked="checked"> Show expenses?
	</label>

	<p></p>

	<table class="table table-bordered" style="table-layout: fixed">
		<tbody>
			<tr>
				<th>Transaction ID</th>
				<th>Details</th>
				<th>Amount</th>
				<th>Date</th>
				<th>Type</th>
			</tr>
			<c:forEach items="${requestScope.transactionList}" var="t">
				<tr class="${t.getTransactionType()}">
					<td><c:out value="${t.transactionID}"></c:out></td>
					<td><c:out value="${t.details}"></c:out></td>
					<td>$<c:out value="${t.amount}"></c:out></td>
					<td><c:out value="${t.date}"></c:out></td>
					<td><c:out value="${t.getTransactionType()}"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<script>
		$("#incomesButton").change(function() {
			$(".Income").toggle();
		});

		$("#expensesButton").change(function() {
			$(".Expense").toggle();
		});
	</script>

</body>
</html>