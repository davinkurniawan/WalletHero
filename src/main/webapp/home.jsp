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

<title>${applicationScope['WEB_NAME']}- Home</title>
<%@ include file="bootstrapHeader.jsp"%>

<script src="js/highcharts.js"></script>
<script src="js/exporting.js"></script>
</head>
<body>
	<jsp:include page="signedinnavbar.jsp"></jsp:include>

	<div class="container marketing">

		<h2>Welcome back, ${username}</h2>
		<h4>Last Sign In: ${lastaccessed}</h4>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<h3 class="featurette-heading">Summary</h3>


				<!-- <c:choose>
					<c:when test="${requestScope.graphDataExpense.size() > 0}">
						<h3 class="featurette-heading">Summary</h3>
						
					</c:when>
					<c:otherwise>
						<h2 class="featurette-heading">No Summary Details Found.</h2>
					</c:otherwise>
			  </c:choose>-->
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<c:choose>
					<c:when test="${requestScope.graphDataExpense.size() > 0}">
						<h3 class="featurette-heading">Last 7 Days of Expenses</h3>
						<div id="graphExpense"></div>
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
				<div class="pull-left">
					<h2 class="featurette-heading">Recommended For You</h2>
				</div>
				<div class="col-md-12">
					<div class="pull-right">
						<p><a href="${applicationScope['ROUTER_DEALS']}">See More...</a></p>
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
												From $
												<fmt:formatNumber value="${i.value}" minFractionDigits="2"
													maxFractionDigits="2" />
												down to <b>$<fmt:formatNumber value="${i.price}"
														minFractionDigits="2" maxFractionDigits="2" /></b>
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