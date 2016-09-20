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

<title>${applicationScope['WEB_NAME']}-AddTransaction</title>
</head>
<body>
	<h3>Please enter transaction details:</h3>

	<form action="router?operation=addTransaction" method="POST">
		<table>
			<tbody>
				<tr>
					<td>Details:</td>
					<td>&nbsp&nbsp<input type="text" name="details" /></td>
				</tr>
				<tr>
					<td>Amount:</td>
					<td>$<input type="number" name="amount" step="0.01" min="0.00" /></td>
				</tr>
				<tr>
					<td><input type="radio" name="transactionType" value="income"
						checked /> Income&nbsp&nbsp</td>
					<td><input type="radio" name="transactionType" value="expense" />
						Expense</td>
				</tr><tr>
			
				<td><input type="radio" name="oneOff" value="true" checked>One-off&nbsp&nbsp</td>
				<td><input type="radio" name="oneOff" value="false">
					Recurring</td>
				</tr></tbody>
		</table>
		<p></p>
		<input type=submit value="Confirm" class="btn btn-primary" />
	</form>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>