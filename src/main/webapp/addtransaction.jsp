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
<script src="js/bootstrap.min.js"></script>

<title>${applicationScope['WEB_NAME']}-AddTransaction</title>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h3>Please enter transaction details:</h3>

		<c:if test="${requestScope.error}">
			<div class="alert alert-danger">
				<strong>Error!</strong> Please fill in all fields.
			</div>
		</c:if>

		<c:if test="${requestScope.success}">
			<div class="alert alert-success">
				<strong>Transaction successfully inserted!</strong>
			</div>
		</c:if>

		<form action="router?operation=addTransaction" method="POST">
			<table>
				<tbody>
					<tr>
						<td>Details:</td>
						<td>&nbsp&nbsp<input type="text" name="details" /></td>
					</tr>
					<tr>
						<td>Amount:</td>
						<td>$<input type="number" name="amount" step="0.01"
							min="0.00" /></td>
					</tr>
					<tr>
						<td><input type="radio" name="transactionType" value="income" id="income"
							checked /> Income&nbsp&nbsp</td>
						<td><input type="radio" name="transactionType" id="expense"
							value="expense" /> Expense</td>
					</tr>
					<tr>
					<td>Category:</td>
					<td>
						<select class="dropdown" name="categoryOption" id="categoryOption" 
							style="width: 150px !important; min-width: 150px; max-width: 150px;">
							<option value="">Please Select</option>
							<option value="1">Business</option>
							<option value="2">Interest</option>
							<option value="0">Other</option>
						</select>
					</td>
					</tr>
					<tr>
						<td><input id="oneOffPayment" type="radio" name="oneOff"
							value="true" checked> One-off&nbsp&nbsp</td>
						<td><input id="recurringPayment" type="radio" name="oneOff"
							value="false"> Recurring</td>
					</tr>
					
					<tr class="reccurenceOption">
						<td>Recurrence frequency:&nbsp&nbsp</td>
						<td><select name="recurrenceFreq">
								<option value="weekly">Weekly</option>
								<option value="fortnightly">Fortnightly</option>
								<option value="monthly">Monthly</option>
								<option value="quarterly">Quarterly</option>
								<option value="half_yearly">Half yearly</option>
								<option value="yearly">Yearly</option>
						</select></td>
					<tr class="reccurenceOption">
						<td><input type="radio" name="paymentPeriod"
							value="indefinite" checked>Indefinite&nbsp&nbsp</td>
						<td><input type="radio" name="paymentPeriod" value="amount">Number
							of payments: <input type="number" name="numberPayments" /></td>
					</tr>

				</tbody>
			</table>
			<p></p>
			<input type=submit value="Confirm" class="btn btn-primary" />
		</form>
		<%@ include file="footer.jsp"%>
	</div>

	<script>
		$(".reccurenceOption").hide();

		$("#recurringPayment").change(function() {
			$(".reccurenceOption").toggle();
		});

		$("#oneOffPayment").change(function() {
			$(".reccurenceOption").toggle();
		});
	</script>
	<script>
	$("input[type='radio'][name='transactionType']").change(function(){

	    var selected = $("input[type='radio'][name='transactionType']:checked").val();

	    if(selected == "income") var opts = [
			{name:"Please Select", val:""},
	        {name:"Business", val:"1"},
	        {name:"Interest", val:"2"},
	        {name:"Other", val:"0"}
	    ];

	    else var opts = [
	        {name:"Please Select", val:""},
	        {name:"Accounting/Legal", val:"3"},
	        {name:"Auto", val:"4"},
	        {name:"Capital Expenditure", val:"5"},
	        {name:"Education", val:"6"},
	        {name:"Food/Drink", val:"7"},
	        {name:"Health", val:"8"},
	        {name:"Maintenance", val:"9"},
	        {name:"Office", val:"10"},
	        {name:"Postage", val:"11"},
	        {name:"Properties", val:"12"},
	        {name:"Rent", val:"13"},
	        {name:"Taxes", val:"14"},
	        {name:"Telephone/Mobile", val:"15"},
	        {name:"Utilities", val:"16"},
	        {name:"Other", val:"0"}
	    ];

	    $("#categoryOption").empty();

	    $.each(opts, function(k,v){

	        $("#categoryOption").append("<option value='"+v.val+"'>"+v.name+"</option>");

	    });
	});
	</script>
</body>
</html>