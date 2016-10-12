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
										<td style="display:none;"><c:out value="${t.transactionID}"></c:out></td>
										<td><c:out value="${requestScope.transactionList.size() - myIndex.index}"></c:out></td>
										<td><c:out value="${t.detail}"></c:out></td>
										<td>$<c:out value="${t.amount}"></c:out></td>
										<td><fmt:formatDate value="${parsedDate}" pattern="dd MMMM yyyy" /></td>
										<td><c:out value="${t.getCategoryName()}"></c:out></td>
										<td><c:out value="${t.getTransactionType()}"></c:out></td>
										<td>
											<div class="form-group">
												<form action="${applicationScope['ROUTER_VIEWTRANSACTIONS']}" method="POST" onSubmit="return false">
													<input type="hidden" name="action" value="editTransaction"/>
													<c:choose>
														<c:when test="${t.isIncome() }">
															<button style="min-width:100px" type="button" class="btn btn-warning" data-toggle="modal" data-target="#myModalIncome">Edit</button>
														</c:when>
														<c:otherwise>
															<button style="min-width:100px" type="button" class="btn btn-warning" data-toggle="modal" data-target="#myModalExpense">Edit</button>
														</c:otherwise>
													</c:choose>
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
						<h2 class="featurette-heading">No Transaction Found.</h2>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		
		<!-- Modal -->
	    <div class="modal fade" id="myModalExpense" role="dialog">
	      <div class="modal-dialog">
	    
	        <!-- Modal content-->
	        <div class="modal-content">
	          <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal">&times;</button>
	            <h4 class="modal-title">Edit Transaction #...</h4>
	          </div>
	          <div class="modal-body">

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
				
				<div class="form-group" id="div-category" name="div-category">
						<label>Category <label style="color: red">*</label></label> <br />
						<select id="categoryOption" name="categoryOption" class="form-control">
							<option value="" selected>Please Select</option>
							<c:forEach items="${category}" var="c">
								<c:if test="${ c.getCategoryID() != 1 && c.getCategoryID() != 2 }">
									<!--<c:choose>
										<c:when test="${param['category'] == c.getCategoryID()}">
											<option value="${c.getCategoryID()}" selected>${c.getCategory()}</option>
										</c:when>
										<c:otherwise>-->
											<option value="${c.getCategoryID()}">${c.getCategory()}</option>
										<!--</c:otherwise>
									</c:choose>-->
								</c:if>
							</c:forEach>
						</select>
					</div>
				
	          </div>
	          <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	          </div>
	        </div>
	      </div>
	    </div>
	    
	    <div class="modal fade" id="myModalIncome" role="dialog">
	      <div class="modal-dialog">
	    
	        <!-- Modal content-->
	        <div class="modal-content">
	          <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal">&times;</button>
	            <h4 class="modal-title">Edit Transaction #...</h4>
	          </div>
	          <div class="modal-body">
	          	
	          	
	          
	          </div>
	        </div>
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
	
		$(document).ready(function() {
			if ($('#from_date').val() == "" || $('#to_date').val() == ""){
				populateDefaultValues();
			}
			else{
				populateGivenValues();
			}
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