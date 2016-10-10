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
		
	<title>${applicationScope['WEB_NAME']} - Currency Converter</title>
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	<c:choose>
		<c:when test="${userID != null}">
			<%@ include file="signedinnavbar.jsp" %>
		</c:when>
		<c:otherwise>
			<%@ include file="navbar.jsp" %>
		</c:otherwise>
	</c:choose>
	
	<div class="container marketing">
	  
	  <h2>Currency Converter</h2>
	  
	  <hr class="featurette-divider">
	  
	  <h5 style="color:Red" name="error_message" id="error_message">
        <c:if test="${errorMessage != null}">
          ${errorMessage}
        </c:if>
      </h5>
	  
	  <div id="div-loading" name="div-loading">
	  	<div id="loader" name="loader"></div>
	  	<center>
	  		<h3 id="loadertext" name="loadertext">Converting...</h3>
	  	</center>
	  </div>
	  
	  <div class="row featurette" name="div-content" id="div-content">
        <div class="col-md-4">
        
        	<form action="#">
	          	<div class="form-group" id="div-from-currency" name="div-from-currency">
			  		<label>From: <label style="color:red">*</label></label>
			  		<br/>
					<select id="from_currency" name="from_currency" class="form-control">				
						<c:forEach var="cur" items="${currency}">			
							<c:choose>
								<c:when test="${preference.getCurrencyID() == cur.getCurrencyID()}">
									<option value="${cur.getShortName()}" selected>${cur.getLongName()} - ${cur.getShortName()}</option>
								</c:when>
								<c:otherwise>
									<option value="${cur.getShortName()}">${cur.getLongName()} - ${cur.getShortName()}</option>
								</c:otherwise>
							</c:choose>					
						</c:forEach>
					</select> 
					
					<br/>
					<div class="input-group">
						<span class="input-group-addon">$</span>
						<input type="number" class="form-control" id="from_amount" name="from_amount" placeholder="From..." value="0.00" step="0.01" min="0.00"/>
					</div>
				</div>
				
				<div class="form-group" id="div-to-currency" name="div-to-currency">
			  		<label>To: <label style="color:red">*</label></label>
			  		<br/>
					<select id="to_currency" name="to_currency" class="form-control">				
						<c:forEach var="cur" items="${currency}">			
							<option value="${cur.getShortName()}">${cur.getLongName()} - ${cur.getShortName()}</option>
						</c:forEach>
					</select> 
					
					<br/>
					<div class="input-group">
						<span class="input-group-addon">$</span>
						<input type="number" class="form-control" id="to_amount" name="to_amount" placeholder="To..." value="0.00" step="0.01" min="0.00"/>
					</div>
				</div>
				
				<button type="submit" class="btn btn-primary">Convert</button>
			</form>
		</div>
	   </div>
	   
	   <%@ include file="footer.jsp" %>
	 </div>
	 <script type="text/javascript">
	 	function isNumeric(n) {
		  return !isNaN(parseFloat(n)) && isFinite(n);
		}
	 
	 	function validator_currency(form){									
			var from_amount = form.from_amount.value.trim();
			var to_amount = form.to_amount.value.trim();
			
			from_amount = from_amount.replace(" ", "");
			to_amount = to_amount.replace(" ", "");
			
			if(from_amount.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter the from Currency amount!";
				document.getElementById("div-from-currency").className = "form-group has-error";
				document.getElementById("div-to-currency").className = "form-group";

				form.from_amount.focus();
				return false;
			}
			else if(to_amount.length == 0){
				document.getElementById("error_message").innerHTML = "Please enter the to Currency amount!";
				document.getElementById("div-from-currency").className = "form-group";
				document.getElementById("div-to-currency").className = "form-group has-error";

				form.to_amount.focus();
				return false;
			}
			else if (!isNumeric(from_amount)) {
				document.getElementById("error_message").innerHTML = "Please enter a valid from Currency amount!";
				document.getElementById("div-from-currency").className = "form-group has-error";
				document.getElementById("div-to-currency").className = "form-group";

				form.from_amount.focus();
				return false;
			}
			else if (!isNumeric(to_amount)) {
				document.getElementById("error_message").innerHTML = "Please enter a valid to Currency amount!";
				document.getElementById("div-from-currency").className = "form-group";
				document.getElementById("div-to-currency").className = "form-group has-error";

				form.to_amount.focus();
				return false;
			}
			
			return true;
	 	};
	 
	 	$(document).ready(function(){
	 		document.getElementById('div-loading').style.display = 'none';	
	 		document.getElementById('div-content').style.display = 'block';
	 		document.getElementById('div-footer').style.display = 'block';
	 		document.getElementById("error_message").innerHTML = '';
	 		document.getElementById("div-from-currency").className = "form-group";
			document.getElementById("div-to-currency").className = "form-group";
	 	});
	 	
	 	$('form').submit( function(event) {
	       	var form = this;
	       	
	       	if (validator_currency(form)){
	       		document.getElementById('div-loading').style.display = 'block';
		 		document.getElementById('div-content').style.display = 'none';
		 		document.getElementById('div-footer').style.display = 'none';
		 		document.getElementById("error_message").innerHTML = '';
		 		document.getElementById("div-from-currency").className = "form-group";
				document.getElementById("div-to-currency").className = "form-group";
		 		
		 		var from = document.getElementById("from_currency");
		 		var selected_from = from.options[from.selectedIndex].value;
		 		
		 		var to = document.getElementById("to_currency");
		 		var selected_to = to.options[to.selectedIndex].value;
		 		
		 		getRate(selected_from, selected_to);
	
			    event.preventDefault();
			}
			else {
				event.preventDefault();
			}
	 	});
	 
		function getRate(from, to) {
			var script = document.createElement('script');
			script.setAttribute('src', "http://query.yahooapis.com/v1/public/yql?q=select%20rate%2Cname%20from%20csv%20where%20url%3D'http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes%3Fs%3D"+ from + to +"%253DX%26f%3Dl1n'%20and%20columns%3D'rate%2Cname'&format=json&callback=parseExchangeRate");
			document.body.appendChild(script);
		}
		
		function parseExchangeRate(data) {
			var name = data.query.results.row.name;
			var rate = parseFloat(data.query.results.row.rate, 10);
			
			var amount_from = document.getElementById("from_amount").value.trim();
			var converted_temp = rate * amount_from;
			var converted_from = 1.00 * amount_from;
			document.getElementById("from_amount").value = converted_from.toFixed(2);
			document.getElementById("to_amount").value = converted_temp.toFixed(2);
			
			document.getElementById('div-loading').style.display = 'none';	
	 		document.getElementById('div-content').style.display = 'block';	
	 		document.getElementById('div-footer').style.display = 'block';
	 		document.getElementById("error_message").innerHTML = '';
	 		document.getElementById("div-from-currency").className = "form-group";
			document.getElementById("div-to-currency").className = "form-group";
		}

		//getRate("SEK", "USD");
		//getRate("USD", "SEK");
	</script>
</body>
</html>