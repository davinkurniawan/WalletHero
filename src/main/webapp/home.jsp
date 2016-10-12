<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <title>${applicationScope['WEB_NAME']} - Home</title>
    <%@ include file="bootstrapHeader.jsp" %>
    
    <script src="js/highcharts.js"></script>
	<script src="js/exporting.js"></script>
</head>
<body>
	<jsp:include page="signedinnavbar.jsp"></jsp:include>

    <!-- Carousel
    ================================================== 
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!-- Indicators 
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class=""></li>
        <li data-target="#myCarousel" data-slide-to="1" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="2" class=""></li>
      </ol>
      <div class="carousel-inner" role="listbox">
        <div class="item active left">
          <img class="first-slide" src="images/test.png" alt="First slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>Example headline.</h1>
              <p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
              <p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p>
            </div>
          </div>
        </div>
        <div class="item next left">
          <img class="second-slide" src="images/test.png" alt="Second slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>Another example headline.</h1>
              <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <p><a class="btn btn-lg btn-primary" href="#" role="button">Learn more</a></p>
            </div>
          </div>
        </div>
        <div class="item">
          <img class="third-slide" src="images/test.png" alt="Third slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>One more for good measure.</h1>
              <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <p><a class="btn btn-lg btn-primary" href="#" role="button">Browse gallery</a></p>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div> /.carousel -->


    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container marketing">

	  <h2>Welcome back, ${username}</h2>
	  <h4>Last Sign In: ${lastaccessed}</h4>
      
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
      		<h2 class="featurette-heading">Other Contents</h2>
      	</div>
      </div>

  	   <%@ include file="footer.jsp" %>
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