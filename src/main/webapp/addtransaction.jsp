<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<title>Add a new transaction.</title>
</head>
<body>
	<h3>Please enter transaction details:</h3>
	<!--ADDTRANSACTION_COMMAND-->
	<form action="${ADDTRANSACTION_COMMAND}" method="POST">
		<table>
			<tbody>
				<tr>
					<td>Details:</td>
					<td>&nbsp&nbsp<input type="text" name="details" /></td>
				</tr>
				<tr>
					<td>Amount:</td>
					<td>$<input type="number" name="value" step="0.01" min="0.00" /></td>
				</tr>
				<tr>
					<td>Transaction type:&nbsp&nbsp</td>
					<td><input type="radio" name="transactionType" value="income" />
						Income</td>
					<td><input type="radio" name="transactionType" value="expense" />
						Expense</td>
				</tr>
			</tbody>
		</table>
		<input type=submit value="Confirm" class="btn btn-primary" />
	</form>
</body>
</html>