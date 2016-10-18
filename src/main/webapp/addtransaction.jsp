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
	
	<title>${applicationScope['WEB_NAME']} - Add a New Transaction</title>
	
	<%@ include file="bootstrapHeader.jsp"%>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h2>Add a new Transaction</h2>

		<c:choose>
			<c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('yes')}">
				<div class="alert alert-success">
					<strong>Transaction Successfully Added!</strong>
				</div>
			</c:when>
			<c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('no')}">
				<div class="alert alert-danger">
					<strong>Transaction Failed to be Added!</strong>
				</div>
			</c:when>
			<c:when test="${errorMessage != null && errorFlg == 1}">
				<div class="alert alert-danger">
					<strong>Error!</strong> ${errorMessage}.
				</div>
			</c:when>
		</c:choose>

		<hr class="featurette-divider">

		<h5 style="color: Red" name="error_message" id="error_message"></h5>

		<div class="row featurette">
			<div class="col-md-6">
				<h3 class="featurette-heading">Please enter your transaction details:</h3>

				<form action="${applicationScope['ROUTER_ADDTRANSACTION']}"
					method="POST" onSubmit="return validator_add_transaction(this)">

					<div class="form-group" id="div-details" name="div-details">
						<label>Details <label style="color: red">*</label></label> <input
							type="text" class="form-control" id="details" name="details"
							placeholder="Details..." value="${param['details']}" />
					</div>

					<div class="form-group" id="div-currency" name="div-currency">
				  		<label>Currency<label style="color:red">*</label></label>
				  		<br/>
						<select id="currency" name="currency" class="form-control">		
							<c:forEach var="cur" items="${currency}">			
								<c:choose>
									<c:when test="${preference.getShortName() == cur.getShortName()}">
										<option value="${cur.getShortName()}" selected>${cur.getLongName()} - ${cur.getShortName()}</option>
									</c:when>
									<c:otherwise>
										<option value="${cur.getShortName()}">${cur.getLongName()} - ${cur.getShortName()}</option>
									</c:otherwise>
								</c:choose>	
							</c:forEach>
						</select> 
					</div>

					<div class="form-group" id="div-amount" name="div-amount">
						<label>Amount <label style="color: red">*</label></label>
						<div class="input-group">
							<span class="input-group-addon">$</span> <input type="number"
								class="form-control" id="amount" name="amount"
								placeholder="Amount..." value="${param['amount']}" step="0.01"
								min="0.00" />
						</div>
					</div>

					<div class="form-group" id="div-from-date" name="div-from-date">
						<label>Transaction Date / Recurrence Starting Date<label style="color: red">*</label></label> <input
							type="text" class="form-control datepicker" id="transaction_date"
							name="transaction_date" placeholder="From Date..."
							value="${param['transaction_date']}" />
					</div>

					<div class="form-group" id="div-transaction-type"
						name="div-transaction-type">
						<label>Transaction Type <label style="color: red">*</label></label>
						<div class="radio">
							<label><input type="radio" name="transactionType"
								value="income" id="income">Income</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="transactionType"
								value="expense" id="expense" checked>Expense</label>
						</div>
					</div>

					<div class="form-group" id="div-category" name="div-category">
						<label>Category <label style="color: red">*</label></label> <br />
						<select id="categoryOption" name="categoryOption"
							class="form-control">
							<option value="" selected>Please Select</option>
							<c:forEach items="${category}" var="c">
								<c:if
									test="${ c.getCategoryID() != 1 && c.getCategoryID() != 2 }">
									<c:choose>
										<c:when test="${param['category'] == c.getCategoryID()}">
											<option value="${c.getCategoryID()}" selected>${c.getCategory()}</option>
										</c:when>
										<c:otherwise>
											<option value="${c.getCategoryID()}">${c.getCategory()}</option>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</div>

					<div class="form-group" id="div-payment-type"
						name="div-payment-type">
						<label>Payment Type <label style="color: red">*</label></label>
						<div class="radio">
							<label><input type="radio" name="oneOff" value="true"
								id="oneOffPayment" checked>One-off</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="oneOff" value="false"
								id="recurringPayment">Recurring</label>
						</div>
					</div>

					<div class="panel panel-primary" id="div-recurrence-option"
						name="div-recurrence-option" style="display: none">
						<div class="panel-heading">Recurrence Option</div>

						<div class="panel-body">
							<div class="form-group" id="div-frequencey" name="div-frequencey">
								<label>Recurrence Frequency <label style="color: red">*</label></label>
								<select id="recurrenceFreq" name="recurrenceFreq"
									class="form-control">
									<option value="weekly">Weekly</option>
									<option value="fortnightly">Fortnightly</option>
									<option value="monthly">Monthly</option>
									<option value="quarterly">Quarterly</option>
									<option value="half_yearly">Half yearly</option>
									<option value="yearly">Yearly</option>
								</select>
							</div>

							<div class="form-group" id="div-period" name="div-period">
								<label>Payment Period <label style="color: red">*</label></label>
								<div class="radio">
									<label><input type="radio" name="paymentPeriod"
										id="indefinite" value="indefinite" checked>Indefinite</label>
								</div>
								<div class="radio">
									<label><input type="radio" name="paymentPeriod"
										id="paymentNumber" value="amount">Number of payments:</label>
								</div>
								<div class="form-group" id="div-payment-number"
									name="div-payment-number" style="display: none">
									<input type="number" class="form-control" id="numberPayments"
										name="numberPayments" placeholder="Number of Payments..."
										value="${param['numberPayments']}" />
								</div>
							</div>
						</div>
					</div>

					<input type="hidden" name="action" value="addTransaction" /> <input
						type=submit value="Confirm" class="btn btn-primary" />
				</form>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
		    $("#oneOffPayment").click(function(){
				if (document.getElementById('oneOffPayment').checked) {
		            document.getElementById('div-recurrence-option').style.display = 'none';
		        }
		    });
		    
		    $("#recurringPayment").click(function(){
				if (document.getElementById('recurringPayment').checked) {
		            document.getElementById('div-recurrence-option').style.display = 'block';
		        }
		    });
		    
		    $("#indefinite").click(function(){
				if (document.getElementById('indefinite').checked) {
		            document.getElementById('div-payment-number').style.display = 'none';
		        }
		    });
		    
		    $("#paymentNumber").click(function(){
				if (document.getElementById('paymentNumber').checked) {
		            document.getElementById('div-payment-number').style.display = 'block';
		        }
		    });
		});
	</script>
	
	<script type="text/javascript">
		$("input[type='radio'][name='transactionType']").change(function(){
	
		    var selected = $("input[type='radio'][name='transactionType']:checked").val();
	
		    if(selected == "income") var opts = [
		        {name:"Please Select", val:""},
		        {name:"Business", val:"1"},
		        {name:"Interest", val:"2"},
		        {name:"Others", val:"0"}
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
		        {name:"Others", val:"0"},
		        {name:"Postage", val:"11"},
		        {name:"Properties", val:"12"},
		        {name:"Rent", val:"13"},
		        {name:"Taxes", val:"14"},
		        {name:"Telephone/Mobile", val:"15"},
		        {name:"Utilities", val:"16"}
		    ];
	
		    $("#categoryOption").empty();
	
		    $.each(opts, function(k,v){
		        $("#categoryOption").append("<option value='"+v.val+"'>"+v.name+"</option>");
		    });
		});
	</script>

	<script type="text/javascript">
		function isNumeric(n) {
			return !isNaN(parseFloat(n)) && isFinite(n);
		}

		function validator_add_transaction(form) {
			var details = form.details.value.trim();
			var amount = form.amount.value.trim();

			var e = document.getElementById("categoryOption");
			var categoryOption = e.options[e.selectedIndex].text;

			var oneOff = form.oneOff.value.trim();
			var paymentPeriod = form.paymentPeriod.value.trim();
			var numberPayments = form.numberPayments.value.trim();

			amount = amount.replace(" ", "");

			if (details.length == 0) {
				document.getElementById("error_message").innerHTML = "Please enter the Transaction Detail!";
				document.getElementById("div-details").className = "form-group has-error";
				document.getElementById("div-amount").className = "form-group";
				document.getElementById("div-category").className = "form-group";
				document.getElementById("div-payment-number").className = "form-group";

				form.details.focus();
				return false;
			} else if (amount.length == 0) {
				document.getElementById("error_message").innerHTML = "Please enter the Transaction Amount!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group has-error";
				document.getElementById("div-category").className = "form-group";
				document.getElementById("div-payment-number").className = "form-group";

				form.amount.focus();
				return false;
			} else if (!isNumeric(amount)) {
				document.getElementById("error_message").innerHTML = "Please enter a valid Amount!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group has-error";
				document.getElementById("div-category").className = "form-group";
				document.getElementById("div-payment-number").className = "form-group";

				form.amount.focus();
				return false;
			} else if (categoryOption == 'Please Select') {
				document.getElementById("error_message").innerHTML = "Please choose the Category!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group";
				document.getElementById("div-category").className = "form-group has-error";
				document.getElementById("div-payment-number").className = "form-group";

				form.categoryOption.focus();
				return false;
			}

			if (oneOff == 'false') {
				if (paymentPeriod == 'amount') {
					if (numberPayments.length == 0) {
						document.getElementById("error_message").innerHTML = "Please enter the Number of Payment!";
						document.getElementById("div-details").className = "form-group";
						document.getElementById("div-amount").className = "form-group";
						document.getElementById("div-category").className = "form-group";
						document.getElementById("div-payment-number").className = "form-group has-error";

						form.numberPayments.focus();
						return false;
					} else if (numberPayments <= 0) {
						document.getElementById("error_message").innerHTML = "Please enter a Number of Payment Amount!";
						document.getElementById("div-details").className = "form-group";
						document.getElementById("div-amount").className = "form-group";
						document.getElementById("div-category").className = "form-group";
						document.getElementById("div-payment-number").className = "form-group has-error";

						form.numberPayments.focus();
						return false;
					}
				}
			}

			return true;
		};
	</script>

	<script type="text/javascript">
		// Code from viewtransactions.jsp.
		function populateDefaultValues() {
			var today = new Date();

			$('#transaction_date').val(
					$.datepicker.formatDate('dd MM yy', today))
					.datepicker({
						maxDate : 0,
						dateFormat : 'dd MM yy',
						onClose : function() {
							var newDate = $('#transaction_date').val();

							if (newDate == '' || newDate == null)
								populateDefaultValues();
						}
					});
		}

		$(document).ready(function() {
			populateDefaultValues();
		});
	</script>
</body>
</html>