<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>${applicationScope['WEB_NAME']} - Goals Overview</title>
	<script src="js/progressbar.min.js"></script>
	<%@ include file="bootstrapHeader.jsp"%>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h2>Your Goals</h2>
		
		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
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
									<th>Details</th>
									<th>Frequency</th>
									<th>Period</th>
									<th>Category</th>
									<th>Status</th>
									<th>Actions</th>
								</tr>
				
								<c:forEach items="${requestScope.goalList}" var="g" varStatus="myIndex">
									<tr>
										<td><c:out value="${myIndex.index + 1}"></c:out></td>
										<td><c:out value="${g.getGoalTypeString()}"></c:out></td>
										<td><c:out value="${g.detail}"></c:out></td>
										<td><c:out value="${g.getFrequencyString()}"></c:out></td>
										<td><c:out value="${g.getDatePeriodString()}"></c:out></td>
										<td><c:out value="${g.categoryString}"></c:out></td>
										<td><div id="bar${g.goalID}"></div></td>
										<td>
											<div class="form-group">
												<form action="${applicationScope['ROUTER_VIEWGOALS']}" method="POST" onSubmit="return confirm('Are you sure you want to delete Goal #' + ${g.goalID} + '?');">
													<input type="hidden" name="goalID" value="${g.goalID}"/>
													<input type="hidden" name="action" value="deleteGoal"/> 
													<input style="min-width:100px" type=submit value="Delete" class="btn btn-danger" />
												</form>
											</div>
										</td>
										
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

											    bar.setText('You have saved <b>$${g.getCurrentAmount()}</b> of your <b>$${g.getGoalAmount()}</b> ${g.getGoalPeriod()} savings goal.');
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
											bar.setText('You have spent <b>$${g.getCurrentAmount()}</b> of your <b>$${g.getGoalAmount()}</b> ${g.getGoalPeriod()} limit on <b>${g.categoryString}</b>.');
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

		<%@ include file="footer.jsp"%>
	</div>
	
	
</body>
</html>