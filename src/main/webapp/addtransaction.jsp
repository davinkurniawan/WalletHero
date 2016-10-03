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
	
	<title>${applicationScope['WEB_NAME']} - Add a Transaction</title>
	
	<%@ include file="bootstrapHeader.jsp"%>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h2>Add a Transaction</h2>

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

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-6">
				<h3 class="featurette-heading">Please enter your transaction details:</h3>

				<form action="${applicationScope['ROUTER_ADDTRANSACTION']}" method="POST" onSubmit="return true">
					
					<div class="form-group" id="div-details" name="div-details">
						<label>Details <label style="color: red">*</label></label> <input
							type="text" class="form-control" id="details"
							name="details" placeholder="Details..."
							value="${param['details']}" />
					</div>
					
					<div class="form-group" id="div-amount" name="div-amount">
						<label>Amount <label style="color: red">*</label></label> 
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="number" class="form-control" id="amount"
									name="amount" placeholder="Amount..."
									value="${param['amount']}" 
									step="0.01" min="0.00"/>
							</div>
					</div>
					
					<div class="form-group" id="div-transaction-type" name="div-transaction-type">
						<label>Transaction Type <label style="color: red">*</label></label> 
						<div class="radio">
							<label><input type="radio" name="transactionType" value="income" id="income">Income</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="transactionType" value="expense" id="expense" checked>Expense</label>
						</div>
					</div>
					
					<div class="form-group" id="div-category" name="div-category">
						<label>Category <label style="color: red">*</label></label> <br />
						<select id="categoryOption" name="categoryOption" class="form-control">
							<option value="" selected>Please Select</option>
							<c:forEach items="${category}" var="c">
								<c:if test="${ c.getCategoryID() != 1 && c.getCategoryID() != 2 }">
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
					
					<div class="form-group" id="div-payment-type" name="div-payment-type">
						<label>Payment Type <label style="color: red">*</label></label> 
						<div class="radio">
							<label><input type="radio" name="oneOff" value="true" id="oneOffPayment" checked>One-off</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="oneOff" value="false" id="recurringPayment">Recurring</label>
						</div>
					</div>
					
					<div class="panel panel-primary" id="div-recurrence-option" name="div-recurrence-option" style="display:none">
						<div class="panel-heading">Recurrence Option</div>		
						
						<div class="panel-body">
							<div class="form-group" id="div-frequencey" name="div-frequencey">
								<label>Recurrence Frequency <label style="color: red">*</label></label>
								<select id="recurrenceFreq" name="recurrenceFreq" class="form-control">						 
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
									<label><input type="radio" name="paymentPeriod" id="indefinite" value="indefinite" checked>Indefinite</label>
								</div>
								<div class="radio">
									<label><input type="radio" name="paymentPeriod" id="paymentNumber" value="amount">Number of payments:</label>
								</div>
								<div class="form-group" id="div-payment-number" name="div-payment-number" style="display:none">
									<input type="number" class="form-control" id="numberPayments" name="numberPayments" placeholder="Number of Payments..." value="${param['numberPayments']}" />
								</div>
							</div>
						</div>
					</div>

					<input type=submit value="Confirm" class="btn btn-primary" />
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
</body>
</html>