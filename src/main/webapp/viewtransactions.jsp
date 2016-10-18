<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>${applicationScope['WEB_NAME']} - Transactions Overview</title>
	<%@ include file="bootstrapHeader.jsp"%>
	
	<script src="js/highcharts.js"></script>
	<script src="js/exporting.js"></script>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>
	
	<div class="container marketing">
		<h2>${requestScope.transactionRange}</h2>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-6">

				<form action="${applicationScope['ROUTER_VIEWTRANSACTIONS']}" method="POST" onSubmit="return true">

					<div class="form-group" id="div-from-date" name="div-from-date">
						<label>From Date <label style="color: red">*</label></label> <input
							type="text" class="form-control datepicker" id="from_date"
							name="from_date" placeholder="From Date..."
							value="${param['from_date']}" />
					</div>

					<div class="form-group" id="div-to-date" name="div-to-date">
						<label>To Date <label style="color: red">*</label></label> <input
							type="text" class="form-control datepicker" id="to_date"
							name="to_date" placeholder="To Date..."
							value="${param['to_date']}" />
					</div>

					<div class="form-group checkbox" id="div-income" name="div-income">
						<label><input type="checkbox" name="incomesChk"
							id="incomesButton" value="true" 
						
							<c:if test="${viewIncomes == true}">
								checked="checked" 
							</c:if>
							
							/>Show Incomes</label>
					</div>

					<div class="form-group checkbox" id="div-expenses"
						name="div-expenses">
						<label><input type="checkbox" name="expensesChk"
							id="expensesButton" value="true" 
							
							<c:if test="${viewExpenses == true}">
								checked="checked" 
							</c:if>
							
							/>Show Expenses</label>
					</div>

					<div class="form-group" id="div-category" name="div-category">
						<label>Category <label style="color: red">*</label></label> <br />
						<select id="category" name="category" class="form-control">
							<option value="-1" selected>All</option>
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
			</div>
		</div>
		
		<c:choose>
			<c:when test="${requestScope.transactionList.size() > 0}">
				<div class="row featurette">
					<hr class="featurette-divider">
					<div id="graph"></div>
				</div>
				
				<div class="row featurette">
					<hr class="featurette-divider">
	
					<div class="col-md-12">
						<h2 class="featurette-heading">Your Transactions</h2>
	
						<table class="table table-bordered" style="table-layout: fixed">
							<tbody>
								<tr>
									<th>Transaction #</th>
									<th>Details</th>
									<th>Amount</th>
									<th>Date</th>
									<th>Category</th>
									<th>Type</th>
									<th>Actions</th>
								</tr>
								<c:forEach items="${requestScope.transactionList}" var="t" varStatus="myIndex">
									<fmt:parseDate pattern="yyyy-MM-dd" value="${t.date}" var="parsedDate" />
									
									<tr class="${t.getTransactionType()}">
										<td style="display:none;" name="t_id_${t.transactionID}" id="t_id_${t.transactionID}"><c:out value="${t.transactionID}"></c:out></td>
										<td name="t_pos_${t.transactionID}" id="t_pos_${t.transactionID}"><c:out value="${requestScope.transactionList.size() - myIndex.index}"></c:out></td>
										<td name="t_detail_${t.transactionID}" id="t_detail_${t.transactionID}"><c:out value="${t.detail}"></c:out></td>
										<td name="t_cur_amt_${t.transactionID}" id="t_cur_amt_${t.transactionID}"><c:out value="${t.currency} "></c:out><c:out value="${t.amount}"></c:out></td>
										<td name="t_date_${t.transactionID}" id="t_date_${t.transactionID}"><fmt:formatDate value="${parsedDate}" pattern="dd MMMM yyyy" /></td>
										<td name="t_cat_${t.transactionID}" id="t_cat_${t.transactionID}"><c:out value="${t.getCategoryName()}"></c:out></td>
										<td name="t_type_${t.transactionID}" id="t_type_${t.transactionID}"><c:out value="${t.getTransactionType()}"></c:out></td>
										<td>
											<div class="form-group">
												<form action="#" method="POST">
													<input type="hidden" name="transactionID" value="${t.transactionID }"/>
													<input type="hidden" name="action" value="editTransaction"/>
													<button style="min-width:100px" type="submit" class="btn btn-warning" data-toggle="modal" data-target="#myModalIncomeExpense">Edit</button>
												</form>
											</div>
											<div class="form-group">
												<form action="${applicationScope['ROUTER_VIEWTRANSACTIONS']}" method="POST" onSubmit="return confirm('Are you sure you want to delete Transaction #' + ${requestScope.transactionList.size() - myIndex.index} + '?');">
													<input type="hidden" name="transactionID" value="${t.transactionID }"/>
													<input type="hidden" name="action" value="deleteTransaction"/> 
													<input style="min-width:100px" type=submit value="Delete" class="btn btn-danger" />
												</form>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row featurette">
					<hr class="featurette-divider">
	
					<div class="col-md-12">
						<h2 class="featurette-heading">No Transactions Found.</h2>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		
		<!-- Modal -->
	    <div class="modal fade" id="myModalIncomeExpense" role="dialog">
	      <div class="modal-dialog">
	    
	        <!-- Modal content-->
	        <div class="modal-content">
	        
			<form action="${applicationScope['ROUTER_VIEWTRANSACTIONS']}" method="POST" onSubmit="return confirm('Are you sure you want to edit this Transaction?');">
	        
	       		<input type="hidden" id="transactionID" name="transactionID"/>
				<input type="hidden" name="action" value="editTransactionReal"/> 

		          <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal">&times;</button>
		            <h4 class="modal-title" name="title" id="title">Edit Transaction #...</h4>
		            <h5 style="color: Red" name="error_message" id="error_message"></h5>
		          </div>
		          <div class="modal-body">
	
		            <div class="form-group" id="div-details" name="div-details">
						<label>Details <label style="color: red">*</label></label> <input
							type="text" class="form-control" id="details"
							name="details" placeholder="Details..."
							value="" />
					</div>
					
					<div class="form-group" id="div-currency" name="div-currency">
				  		<label>Currency<label style="color:red">*</label></label>
				  		<br/>
						<select id="currency" name="currency" class="form-control">		
							<c:forEach var="cur" items="${currency}">			
								<option value="${cur.getShortName()}">${cur.getLongName()} - ${cur.getShortName()}</option>
							</c:forEach>
						</select> 
					</div>
					
					<div class="form-group" id="div-amount" name="div-amount">
						<label>Amount <label style="color: red">*</label></label> 
						<div class="input-group">
							<span class="input-group-addon">$</span>
							<input type="number" class="form-control" id="amount"
								name="amount" placeholder="Amount..."
								value="" 
								step="0.01" min="0.00"/>
						</div>
					</div>
					
					<div class="form-group" id="div-from-date" name="div-from-date">
						<label>Transaction Date / Recurrence Starting Date<label style="color: red">*</label></label> <input
							type="text" class="form-control datepicker" id="transaction_date"
							name="transaction_date" placeholder="Transaction Date..."
							value="${param['transaction_date']}" />
					</div>
					
					<div class="form-group" id="div-category" name="div-category">
							<label>Category <label style="color: red">*</label></label> <br />
							<select id="categoryOption" name="categoryOption" class="form-control">
								<option value="" selected>Please Select</option>
								<c:forEach items="${category}" var="c">
									<c:if test="${ c.getCategoryID() != 1 && c.getCategoryID() != 2 }">
										<option value="${c.getCategoryID()}">${c.getCategory()}</option>
									</c:if>
								</c:forEach>
							</select>
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
			       </div>
		          
		          <div class="modal-footer">
		          	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		            <button type="submit" class="btn btn-default">Update</button>
		          </div>
		       </form>
		  	</div>
	      </div>
	    </div>
		
		<%@ include file="footer.jsp"%>
	</div>
	
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
		
			amount = amount.replace(" ", "");
	
			if (details.length == 0) {
				document.getElementById("error_message").innerHTML = "Please enter the Transaction Detail!";
				document.getElementById("div-details").className = "form-group has-error";
				document.getElementById("div-amount").className = "form-group";
				document.getElementById("div-category").className = "form-group";
	
				form.details.focus();
				return false;
			} else if (amount.length == 0) {
				document.getElementById("error_message").innerHTML = "Please enter the Transaction Amount!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group has-error";
				document.getElementById("div-category").className = "form-group";
	
				form.amount.focus();
				return false;
			} else if (!isNumeric(amount)) {
				document.getElementById("error_message").innerHTML = "Please enter a valid Amount!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group has-error";
				document.getElementById("div-category").className = "form-group";
	
				form.amount.focus();
				return false;
			} else if (categoryOption == 'Please Select') {
				document.getElementById("error_message").innerHTML = "Please choose the Category!";
				document.getElementById("div-details").className = "form-group";
				document.getElementById("div-amount").className = "form-group";
				document.getElementById("div-category").className = "form-group has-error";
	
				form.categoryOption.focus();
				return false;
			}
	
			return true;
		};
	
		$('form').submit( function(event) {
	       	var form = this;
	       		       	
			if (form.action.value == 'editTransaction'){
				var opts = [
					{name:"Business", val:"1"},
					{name:"Interest", val:"2"},
					{name:"Others", val:"0"},
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
				        
				var transactionID = form.transactionID.value;
				var transactionPos = document.getElementById('t_pos_' + transactionID).innerHTML;
				var transactionDetail = document.getElementById('t_detail_' + transactionID).innerHTML;
				var transactionCurrencyAmount = document.getElementById('t_cur_amt_' + transactionID).innerHTML;
				var transactionDate = document.getElementById('t_date_' + transactionID).innerHTML;
				var transactionCategory = document.getElementById('t_cat_' + transactionID).innerHTML;
				var transactionType = document.getElementById('t_type_' + transactionID).innerHTML;
				
				var array = transactionCurrencyAmount.split(" ");
				var currency = array[0];
				var amount = array[1];
				var category = '';
				
				$.each(opts, function(k,v){
			        if (v.name === transactionCategory){
			        	category = v.val;
			        	return;
			        }
			    });
				
				document.getElementById('transactionID').value = transactionID;
				document.getElementById('title').innerHTML = 'Edit Transaction #' + transactionPos;
				document.getElementById('details').value = transactionDetail;
				document.getElementById('currency').value = currency;
				document.getElementById('amount').value = amount;
				document.getElementById('transaction_date').value = transactionDate;
				
				if (transactionType == 'Income') {
					var opts = [
						{name:"Please Select", val:""},
					    {name:"Business", val:"1"},
					    {name:"Interest", val:"2"},
					    {name:"Others", val:"0"}
					];
           	
					$("#categoryOption").empty();
					                     	
            		$.each(opts, function(k,v){
           		        $("#categoryOption").append("<option value='"+v.val+"'>"+v.name+"</option>");
           		    });
            		
					document.getElementById('income').checked = true;
					document.getElementById('expense').checked = false;
				}
				else {
					var opts = [
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
					
					document.getElementById('income').checked = false;
					document.getElementById('expense').checked = true;
				}
				
				document.getElementById('categoryOption').value = category;
		       	
			    event.preventDefault();
			}
			else if (form.action.value == 'editTransactionReal') {
				event.preventDefault();
				
				if (validator_add_transaction(this)){
					setTimeout( function () { 
				        form.submit();
				    }, 100);
				}
			}
			else {
				event.preventDefault();
				
				setTimeout( function () { 
			        form.submit();
			    }, 100);
			}
		});
	</script>

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
	    		{dateFormat: 'dd MM yy',
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
	    		{dateFormat: 'dd MM yy',
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
	    		{dateFormat: 'dd MM yy',
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
	    		{dateFormat: 'dd MM yy',
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
	    
	    function populateDefaultValuesTransaction(){
	    	var today = new Date();
	    	
	    	$('#transaction_date').val($.datepicker.formatDate('dd MM yy', today)).datepicker(
	    		{dateFormat: 'dd MM yy', maxDate : 0,
					 onClose: function(){
						 var newDate = $('#transaction_date').val();
						 
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
			
			populateDefaultValuesTransaction();
		});
	</script>

	<script type="text/javascript">
    var EXPENSES_ONLY = 1;
    var INCOMES_ONLY = 2;
    var BOTH = 3;   
    
    <c:if test="${graphType == 3}">
	    $(function () {
	             $('#graph').highcharts({
	               title: {
	                   text: 'Daily incomes and expenses from ${fromDate} to ${toDate}.'
	               },
	               xAxis: {
	                   categories: [
	                   <c:forEach var="parentHash" items="${graphData}">
	                                '${parentHash.key}',
	                   </c:forEach>
	                   ]
	               },
	               
	                   tooltip: {
	               pointFormat: "<b>Value</b>: $ {point.y:.2f}"
	           },
	               
	               series: [{
	                   type: 'column',
	                   color: '#e75757',
	                   name: 'Expenses',
	                   data: [
	                           <c:forEach var="parentHash" items="${graphData}">
	                                   ${parentHash.value.expense},
	                           </c:forEach>
	                          ],
	               }, {
	                   type: 'column',
	                   color: '#79ea86',
	                   name: 'Incomes',
	                   data: [
	                       <c:forEach var="parentHash" items="${graphData}">
	                               ${parentHash.value.income},
	                           </c:forEach>
	                      ],
	               }, {
	                   type: 'spline',
	                   name: 'Profit',
	                   color: '#649ff0',
	                   data: [
	                       <c:forEach var="parentHash" items="${graphData}">
	                               ${parentHash.value.profit},
	                           </c:forEach>
	                      ],
	               }]
	              });
	          });
	    </c:if>
	    
	    <c:if test="${graphType == 1 || graphType == 2}">
	    $(function () {
	        $('#graph').highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: true,
	                type: 'pie'
	            },
	            title: {
	                text: '${transactionType} percentage per category from ${fromDate} to ${toDate}:'
	            },
	            tooltip: {
	                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	            },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                        style: {
	                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                        }
	                    }
	                }
	            },
	            series: [{
	                name: 'Percentage',
	                colorByPoint: true,
	                data: [
	                       
	                <c:forEach var="hash" items="${graphData}">
	                {
	                    name: '${hash.key}',
	                    y: ${hash.value}
	                },
	                </c:forEach>
	                ]
	            }]
	        })
	    });
	    </c:if>
    </script>
</body>
</html>