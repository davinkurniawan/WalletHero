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
	
	<title>${applicationScope['WEB_NAME']} - Add a New Goal</title>
	
	<%@ include file="bootstrapHeader.jsp"%>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h2>Add a New Goal</h2>
		
		 <c:choose>
		 	<c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('yes')}" >
		 		<div class="alert alert-success">
					<strong>Goal Successfully Added!</strong>
				</div>
		 	</c:when>
		 	<c:when test="${param['success'] != null && param['success'].equalsIgnoreCase('no')}" >
		 		<div class="alert alert-danger">
					<strong>Goal Failed to be Added!</strong>
				</div>
		 	</c:when>
			<c:when test="${errorMessage != null && errorFlg == 1}">
				<div class="alert alert-danger">
					<strong>Error!</strong> ${errorMessage}.
				</div>
			</c:when>
		</c:choose>

		<hr class="featurette-divider">

		<h5 style="color:Red" name="error_message" id="error_message">

      	</h5>

		<div class="row featurette">
			<div class="col-md-6">
				<h3 class="featurette-heading">Please enter your goal details:</h3>

				<form action="${applicationScope['ROUTER_ADDGOAL']}" method="POST" onSubmit="return validator_add_transaction(this)">
					
					<div class="form-group" id="div-details" name="div-details">
						<label>Details <label style="color: red">*</label></label> <input
							type="text" class="form-control" id="details"
							name="details" placeholder="Details..."
							value="${param['details']}" />
					</div>
										
					<div class="form-group" id="div-transaction-type" name="div-transaction-type">
						<label>Goal Type <label style="color: red">*</label></label> 
						<div class="radio">
							<label><input type="radio" name="goalType" value="save" id="save">Saving</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="goalType" value="limitExpenses" id="limitExpenses" checked>Limiting expenses in a specific category</label>
						</div>
					</div>
					
					<div class="form-group" id="div-amount" name="div-amount">
						<label>Target amount <label style="color: red">*</label></label> 
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="number" class="form-control" id="amount"
									name="amount" placeholder="Amount..."
									value="${param['amount']}" 
									step="0.01" min="0.00"/>
							</div>
					</div>
					
					<div class="form-group" id="div-frequencey" name="div-frequencey">
						<label>Goal time frame <label style="color: red">*</label></label>
						<select id="recurrenceFreq" name="goalFreq" class="form-control">						 
							<option value="weekly">Weekly</option>
							<option value="fortnightly">Fortnightly</option>
							<option value="monthly">Monthly</option>
							<option value="quarterly">Quarterly</option>
							<option value="half_yearly">Half yearly</option>
							<option value="yearly">Yearly</option>
						</select>
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
					
					<input type=submit value="Confirm" class="btn btn-primary" />
				</form>
			</div>
		</div>
		
		<%@ include file="footer.jsp"%>
	</div>

	<script type="text/javascript">	
		$(document).ready(function(){
		    $("#div-transaction-type").click(function(){
				if (document.getElementById('save').checked) {
		            document.getElementById('div-category').style.display = 'none';
		        }
		    });
		    
		    $("#div-transaction-type").click(function(){
				if (document.getElementById('limitExpenses').checked) {
		            document.getElementById('div-category').style.display = 'block';
		        }
		    });
		    
		});
	</script>
	
	<script type="text/javascript">
	$(document).ready(function(){
		    opts = [
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
	
		function validator_add_transaction(form){									
			var details = form.details.value.trim();
			var amount = form.amount.value.trim();
			
			var e = document.getElementById("categoryOption");
			var categoryOption = e.options[e.selectedIndex].text;
			
			amount = amount.replace(" ", "");
			
			if(details.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter the Goal Detail!";
				document.getElementById("div-details").className = "form-group has-error";
				document.getElementById("div-amount").className = "form-group";
 				document.getElementById("div-category").className = "form-group";

				form.details.focus();
				return false;
			}
			else if(amount.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter the Goal Amount!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group has-error";
 				document.getElementById("div-category").className = "form-group";

				form.amount.focus();
				return false;
			}
			else if(!isNumeric(amount)){
				document.getElementById("error_message").innerHTML = "Please enter a valid Amount!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group has-error";
 				document.getElementById("div-category").className = "form-group";

				form.amount.focus();
				return false;
			}
			else if (categoryOption == 'Please Select'){
				document.getElementById("error_message").innerHTML = "Please choose the Category!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group";
				document.getElementById("div-category").className = "form-group has-error";

				form.categoryOption.focus();
				return false;
			}
			
			return true;
		};
	</script>
</body>
</html>