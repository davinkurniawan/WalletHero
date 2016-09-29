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
		
	<title>${applicationScope['WEB_NAME']} - Transactions Overview</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>


	<div class="container marketing">
		<h2>${requestScope.transactionRange}</h2>

		<hr class="featurette-divider">

		<div class="row featurette">
        	<div class="col-md-6">

				<form action="${applicationScope['ROUTER_VIEWTRANSACTIONS']}" method="POST" onSubmit="">

					<div class="form-group" id="div-from-date" name="div-from-date">
				  		<label>From Date <label style="color:red">*</label></label>
		  				<input type="text" class="form-control datepicker" id="from_date" name="from_date" placeholder="From Date..." value="${param['from_date']}"/>
					</div>

					<div class="form-group" id="div-to-date" name="div-to-date">
				  		<label>To Date <label style="color:red">*</label></label>
		  				<input type="text" class="form-control datepicker" id="to_date" name="to_date" placeholder="To Date..." value="${param['to_date']}"/>
					</div>
					
					<div class="form-group checkbox" id="div-income" name="div-income">
					  <label><input type="checkbox" name="incomesChk" id="incomesButton" value="true" checked="checked"/>Show Incomes</label>
					</div>
					
					<div class="form-group checkbox" id="div-expenses" name="div-expenses">
					  <label><input type="checkbox" name="expensesChk" id="expensesButton" value="true" checked="checked"/>Show Expenses</label>
					</div>
					
					<div class="form-group" id="div-category" name="div-category">
				  		<label>Category <label style="color:red">*</label></label>
				  		<br/>
						<select id="category" name="category" class="form-control">				
							<c:forEach items="${category}" var="c">
								<c:choose>
									<c:when test="${param['category'] == c.getCategoryID()}">
										<option value="${c.getCategoryID()}" selected>${c.getCategory()}</option>
									</c:when>
									<c:otherwise>
										<option value="${c.getCategoryID()}">${c.getCategory()}</option>
									</c:otherwise>
								</c:choose>							
							</c:forEach>
						</select> 
					</div>

					<input type=submit value="Confirm" class="btn btn-primary" />
				</form>

				<p></p>
				
				<c:if test="${requestScope.transactionList.size() > 0}">
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
				</c:if>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>

    <script type="text/javascript">
	    function populateDefaultValues() {
		    var today = new Date();
		    var month = today.getMonth();
		    var year = today.getFullYear();
		    var date = today.getDate() - 6;
		    
		    if (month > 12) {
		        month = 1;
		        year += 1;
		    }
		    
		    var oneWeekBefore = new Date(year, month, date);
	
		    $('#from_date').val($.datepicker.formatDate('dd MM yy', oneWeekBefore)).datepicker(
	    		{minDate: '-6d', dateFormat: 'dd MM yy',
	    			onSelect: function(selected) {
	    				$("#to_date").datepicker("option", "minDate", selected)
					 },
					 onClose: function(){
						 var newDate = $('#from_date').val();
						 
						 if (newDate == '' || newDate == null)
							 populateDefaultValues();
					 } 
	    		});
		    
		    $('#to_date').val($.datepicker.formatDate('dd MM yy', today)).datepicker(
	    		{minDate: '0d', dateFormat: 'dd MM yy',
	    			onSelect: function(selected) {
	    				$("#from_date").datepicker("option","maxDate", selected)
					 },
					 onClose: function(){
						 var newDate = $('#to_date').val();
						 
						 if (newDate == '' || newDate == null)
							 populateDefaultValues();
					 } 
				});
		}
	    
	    function populateGivenValues() {
	    	var toDate = $.datepicker.parseDate('dd MM yy', $('#to_date').val());
			var fromDate = $.datepicker.parseDate('dd MM yy', $('#from_date').val());
			
			$('#from_date').val($.datepicker.formatDate('dd MM yy', fromDate)).datepicker(
	    		{minDate: '-6d', dateFormat: 'dd MM yy',
	    			onSelect: function(selected) {
	    				$("#to_date").datepicker("option", "minDate", selected)
					 },
					 onClose: function(){
						 var newDate = $('#from_date').val();
						 
						 if (newDate == '' || newDate == null)
							 populateDefaultValues();
					 } 
	    		});
			
			$('#to_date').val($.datepicker.formatDate('dd MM yy', toDate)).datepicker(
	    		{minDate: '0d', dateFormat: 'dd MM yy',
	    			onSelect: function(selected) {
	    				$("#from_date").datepicker("option","maxDate", selected)
					 },
					 onClose: function(){
						 var newDate = $('#to_date').val();
						 
						 if (newDate == '' || newDate == null)
							 populateDefaultValues();
					 } 
				});
	    }
	
		$(document).ready(function() {
			if ($('#from_date').val() == "" || $('#to_date').val() == ""){
				populateDefaultValues();
			}
			else{
				populateGivenValues();
			}
		});
	</script>
</body>
</html>