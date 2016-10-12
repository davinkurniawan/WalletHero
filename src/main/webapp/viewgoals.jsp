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
	<%@ include file="bootstrapHeader.jsp"%>
</head>
<body>
	<%@ include file="signedinnavbar.jsp"%>

	<div class="container marketing">
		<h2>Your Goals</h2>
		
		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<table class="table table-bordered" style="table-layout: auto">
					<tbody>
						<tr>
							<th>Goal #</th>
							<th>Details</th>
							<th>Frequency</th>
							<th>Goal Amount</th>
							<th>Current Amount</th>
							<th>Category</th>
							<th>Status</th>
						</tr>
		
						<c:forEach items="${requestScope.goalList}" var="g">
							<tr>
								<td><c:out value="${g.goalID}"></c:out></td>
								<td><c:out value="${g.detail}"></c:out></td>
								<td><c:out value="${g.goalPeriod.toUpperCase()}"></c:out></td>
								<td>$<c:out value="${g.goalAmount}"></c:out></td>
								<td>$<c:out value="${g.currentAmount}"></c:out></td>
								<td><c:out value="${g.categoryString}"></c:out></td>
								<td><c:out value="${g.statusString}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>