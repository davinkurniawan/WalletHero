<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>${applicationScope['WEB_NAME']} - Home</title>
	<%@ include file="bootstrapHeader.jsp"%>
	
	<script src="js/progressbar.min.js"></script>
	<script src="js/highcharts.js"></script>
	<script src="js/exporting.js"></script>
</head>
<body>
	<jsp:include page="signedinnavbar.jsp"></jsp:include>

    <div class="container marketing">

	  <h2>Welcome back, ${username}</h2>
	  <h4>Last Sign In: ${lastAccessed}</h4>
      
      <hr class="featurette-divider">
      
      <div class="row featurette">
        <div class="col-md-12">
        	<h3 class="featurette-heading">Summary</h3>
        	<c:choose>
        		<c:when test="${ totalBudget < 0 }">
        			<h4><b>Total Profit:</b> ${preferredCurrency} <label style="color:red"><fmt:formatNumber value="${totalBudget}" minFractionDigits="2" maxFractionDigits="2"/></label></h4>       	
        		</c:when>
        		<c:when test="${ totalBudget > 0 }">
        			<h4><b>Total Profit:</b> ${preferredCurrency} <label style="color:green"><fmt:formatNumber value="${totalBudget}" minFractionDigits="2" maxFractionDigits="2"/></label></h4>       	
        		</c:when>
        		<c:otherwise>
        			<h4><b>Total Profit:</b> ${preferredCurrency} <fmt:formatNumber value="${totalBudget}" minFractionDigits="2" maxFractionDigits="2"/></h4>       	
        		</c:otherwise>
        	</c:choose>
        	<h4><b>Total Expenses:</b> ${preferredCurrency} <fmt:formatNumber value="${totalExpense}" minFractionDigits="2" maxFractionDigits="2"/></h4>
        	<h4><b>Total Incomes:</b> ${preferredCurrency} <fmt:formatNumber value="${totalIncome}" minFractionDigits="2" maxFractionDigits="2"/></h4>
	  	</div>
	  </div>
      
      <hr class="featurette-divider">
      
      <div class="row featurette">
        <div class="col-md-12">
			   <c:choose>
					<c:when test="${requestScope.graphDataExpense.size() > 0}">
					
						<h3 class="featurette-heading">Last 7 Days of Expenses</h3>
						<div id="graphExpense"></div>
						
						<br/><br/>
						
						<div class="row featurette">
							<div class="col-md-12">			
								<table class="table table-bordered" style="table-layout: fixed">
									<tbody>
										<tr>
											<th>Transaction #</th>
											<th>Details</th>
											<th>Amount</th>
											<th>Date</th>
											<th>Category</th>
											<th>Recurring</th>
										</tr>
										<c:forEach items="${requestScope.incomesList}" var="t" varStatus="myIndex">
											<fmt:parseDate pattern="yyyy-MM-dd" value="${t.date}" var="parsedDate" />
											
											<tr class="${t.getTransactionType()}">
												<td style="display:none;" name="t_id_${t.transactionID}" id="t_id_${t.transactionID}"><c:out value="${t.transactionID}"></c:out></td>
												<td name="t_pos_${t.transactionID}" id="t_pos_${t.transactionID}"><c:out value="${requestScope.incomesList.size() - myIndex.index}"></c:out></td>
												<td name="t_detail_${t.transactionID}" id="t_detail_${t.transactionID}"><c:out value="${t.detail}"></c:out></td>
												<td name="t_cur_amt_${t.transactionID}" id="t_cur_amt_${t.transactionID}"><c:out value="${t.currency} "></c:out><fmt:formatNumber value="${t.amount}" minFractionDigits="2" maxFractionDigits="2"/></td>
												<td name="t_date_${t.transactionID}" id="t_date_${t.transactionID}"><fmt:formatDate value="${parsedDate}" pattern="dd MMMM yyyy" /></td>
												<td name="t_cat_${t.transactionID}" id="t_cat_${t.transactionID}"><c:out value="${t.getCategoryName()}"></c:out></td>
												<td><c:out value="${t.getRecurrenceType()}"></c:out></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<h2 class="featurette-heading">No Expenses Found.</h2>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<c:choose>
					<c:when test="${requestScope.graphDataIncome.size() > 0}">
						<h3 class="featurette-heading">Last 7 Days of Incomes</h3>
						<div id="graphIncome"></div>
						
						<div class="row featurette">
						
						<br/><br/>
		
						<div class="col-md-12">			
							<table class="table table-bordered" style="table-layout: fixed">
								<tbody>
									<tr>
										<th>Transaction #</th>
										<th>Details</th>
										<th>Amount</th>
										<th>Date</th>
										<th>Category</th>
										<th>Recurring</th>
									</tr>
									<c:forEach items="${requestScope.expensesList}" var="t" varStatus="myIndex">
										<fmt:parseDate pattern="yyyy-MM-dd" value="${t.date}" var="parsedDate" />
										
										<tr class="${t.getTransactionType()}">
											<td style="display:none;" name="t_id_${t.transactionID}" id="t_id_${t.transactionID}"><c:out value="${t.transactionID}"></c:out></td>
											<td name="t_pos_${t.transactionID}" id="t_pos_${t.transactionID}"><c:out value="${requestScope.expensesList.size() - myIndex.index}"></c:out></td>
											<td name="t_detail_${t.transactionID}" id="t_detail_${t.transactionID}"><c:out value="${t.detail}"></c:out></td>
											<td name="t_cur_amt_${t.transactionID}" id="t_cur_amt_${t.transactionID}"><c:out value="${t.currency} "></c:out><fmt:formatNumber value="${t.amount}" minFractionDigits="2" maxFractionDigits="2"/></td>
											<td name="t_date_${t.transactionID}" id="t_date_${t.transactionID}"><fmt:formatDate value="${parsedDate}" pattern="dd MMMM yyyy" /></td>
											<td name="t_cat_${t.transactionID}" id="t_cat_${t.transactionID}"><c:out value="${t.getCategoryName()}"></c:out></td>
											<td><c:out value="${t.getRecurrenceType()}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
						
					</c:when>
					<c:otherwise>
						<h2 class="featurette-heading">No Incomes Found.</h2>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<h3 class="featurette-heading">Your Goals</h3>
				<c:choose>
					<c:when test="${requestScope.goalList.size() == 0 }">
						<h2 class="featurette-heading">No Goals Found.</h2>
					</c:when>
					<c:otherwise>
						<table class="table table-bordered" style="table-layout: auto">
							<tbody>
								<tr>
									<th>Goal #</th>
									<th>Goal Type</th>
									<th>Goal Currency</th>
									<th>Details</th>
									<th>Frequency</th>
									<th>Period</th>
									<th>Category</th>
									<th>Status</th>
								</tr>
				
								<c:forEach items="${requestScope.goalList}" var="g" varStatus="myIndex">
									<tr>
										<td><c:out value="${myIndex.index + 1}"></c:out></td>
										<td><c:out value="${g.getGoalTypeString()}"></c:out></td>
										<td><c:out value="${g.getCurrency()}"></c:out></td>
										<td><c:out value="${g.detail}"></c:out></td>
										<td><c:out value="${g.getFrequencyString()}"></c:out></td>
										<td><c:out value="${g.getDatePeriodString()}"></c:out></td>
										<td><c:out value="${g.categoryString}"></c:out></td>
										<td><div id="bar${g.goalID}"></div></td>
										
										<script>
										<c:if test="${g.getType() == 1}">
											var bar = new ProgressBar.Line(bar${g.goalID}, {
											  strokeWidth: 3,
											  easing: 'easeInOut',
											  duration: 1400,
											  color: '#FFEA82',
											  trailColor: '#eee',
											  trailWidth: 3,
											  svgStyle: {width: '100%', height: '100%'},
											  text: {
											    style: {
											      color: '#999',
											      position: 'relative',
											      right: '0',
											      top: '0',
											      padding: 0,
											      margin: 0,
											      transform: null
											    },
											    autoStyleContainer: false
											  },
											  from: {color: '#e75757'},
											  to: {color: '#79ea86'},
											  
											  step: (state, bar) => {

											    bar.setText('You have saved <b>${g.getCurrency()} $${g.getCurrentAmount()}</b> of your <b>${g.getCurrency()} $${g.getGoalAmount()}</b> ${g.getFrequencyString().toLowerCase()} savings goal.');
											    bar.path.setAttribute('stroke', state.color);
											  }
											});
										
											var amount = ${g.getCurrentAmount()} / ${g.getGoalAmount()};
											
											if (amount > 1) {
												amount = 1;
											} else if (amount < 0) {
												amount = 0;
											}
											
											bar.animate(amount);

										</c:if>
										
										<c:if test="${g.getType() == 2}">
										var bar = new ProgressBar.Line(bar${g.goalID}, {
										  strokeWidth: 3,
										  easing: 'easeInOut',
										  duration: 1400,
										  color: '#FFEA82',
										  trailColor: '#eee',
										  trailWidth: 3,
										  svgStyle: {width: '100%', height: '100%'},
										  text: {
										    style: {
										      color: '#999',
										      position: 'relative',
										      right: '0',
										      top: '0',
										      padding: 0,
										      margin: 0,
										      transform: null
										    },
										    autoStyleContainer: false
										  },
										  from: {color: '#79ea86'},
										  to: {color: '#e75757'},
										  
										  step: (state, bar) => {
											bar.setText('You have spent <b>${g.getCurrency()} $${g.getCurrentAmount()}</b> of your <b>${g.getCurrency()} $${g.getGoalAmount()}</b> ${g.getFrequencyString().toLowerCase()} limit on <b>${g.categoryString}</b>.');
										    bar.path.setAttribute('stroke', state.color);
										  }
										});
									
										bar.animate(Math.min(1, ${g.getCurrentAmount()} / ${g.getGoalAmount()}));
										
									</c:if>
										</script>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<div class="pull-left">
					<h2 class="featurette-heading">Recommended For You</h2>
				</div>
				<div class="col-md-12">
					<div class="pull-right">
						<p><a href="${applicationScope['ROUTER_DEALS']}<c:forEach var="i" items="${categories}">&category=${i}</c:forEach>">See More...</a></p>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="row">
				<c:choose>
					<c:when test="${not empty deals_list}">
						<c:forEach var="i" items="${deals_list}" begin="0" end="2">
							<c:if test="${not empty i}">
								<div class="col-sm-6 col-md-4">
									<div class="thumbnail" style="height: 330px;">
										<a target="_blank" href="${i.url}"><img
											src="${i.image_url}"
											onerror="this.src='images/not_available.png'" height="200"
											width="300" /></a>
										<div class="caption">
											<h3>
												<a target="_blank" href="${i.url}">${i.short_title}</a>
											</h3>
											<p>
												From ${requestScope.userPreferredCurrency} $<fmt:formatNumber value="${i.value}" minFractionDigits="2"
													maxFractionDigits="2" />
												down to <b>${requestScope.userPreferredCurrency} $<fmt:formatNumber value="${i.price}"
														minFractionDigits="2" maxFractionDigits="2" /></b>!
											</p>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<h3 align=center>No deals to show!</h3>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>

	<script type="text/javascript">
    	var EXPENSES_ONLY = 1;
    	var INCOMES_ONLY = 2;
	    //1 is expense
	    // 2 is income
	    
	    <c:if test="${graphTypeExpense == 1}">
	    $(function () {
	        $('#graphExpense').highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: true,
	                type: 'pie'
	            },
	            title: {
	                text: 'Expense percentage per category from ${fromDate} to ${toDate}:'
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
	                       
	                <c:forEach var="hash" items="${graphDataExpense}">
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
	    
	    <c:if test="${graphTypeIncome == 2}">
	    $(function () {
	        $('#graphIncome').highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: true,
	                type: 'pie'
	            },
	            title: {
	                text: 'Income percentage per category from ${fromDate} to ${toDate}:'
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
	                       
	                <c:forEach var="hash" items="${graphDataIncome}">
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