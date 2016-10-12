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
		
	<title>${applicationScope['WEB_NAME']} - About</title>
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

	  <h2>About ${applicationScope['WEB_NAME']}</h2>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-12">
          <h3 class="featurette-heading">About</h3>
          Once upon a time there was an international student studying in Australia. The student has their parents send them money 
          monthly. That fixed sum would have to be enough to sustain them for the full month including rent transport and food.
          Usually they were able to manage their money carefully in order to have enough money to last the month. However they 
          were careless one day and ate at a restaurant for dinner. When they were about to pay the bill, it turns out they didn't 
          have enough money in their wallets. They had to call up a friend to quickly save them.
          <br/><br/>
          Our aim is to be a hero to prevent such scenarios and save your wallet.
          <br/><br/>
          - <b>WalletHero Team</b>
        </div>
      </div>

      <hr class="featurette-divider">
      
       <div class="row featurette">
        <div class="col-md-12">
          <h3 class="featurette-heading">Team Members</h3>
			<ul>
				<li>
					Gabriela Febriana
				</li>
				<li>
					Natalia Djohari
				</li>
				<li>
					Davin Kurniawan
				</li>
				<li>
					Samuel Turner
				</li>
				<li>
					Timothy Putra Pringgondhani
				</li>
			</ul>
        </div>
      </div>

      <hr class="featurette-divider">
      
       <div class="row featurette">
        <div class="col-md-12">
          <h3 class="featurette-heading">Credits and Acknowledgement</h3>
          	<ul>
          		<li>
          			Yahoo! Finance
          		</li>
          		<li>
          			Sqoot Deals
          		</li>
          		<li style="color:red">
          			List here...
          		</li>
          	</ul>
        </div>
      </div>

      <hr class="featurette-divider">
      
       <div class="row featurette">
        <div class="col-md-12">
          <h3 class="featurette-heading">Troubleshooting and help</h3>
          	Find relevant help or step by step guides here
          	<br/>
          	
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			  <div class="panel panel-info">
			    <div class="panel-heading" role="tab" id="headingOne">
			      <h4 class="panel-title">
			        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
			          How to add transactions
			        </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
			      <div class="panel-body">
			      		This feature is only available when you are logged in.
						<br/><br/>
						Navigate to the add transactions page by clicking on Transaction on the navigation bar and selecting 'Add Transaction' in the drop down menu.
						<br/><br/>
						The Add Transaction page has the form that you need to complete in order to add a transaction. Here is a breakdown of the information.
						<br/><br/>
						<strong>Details:</strong> Information that you supply regarding that transaction. It can be as simple as Grocery Shopping or Drinks with Friends. This will be used to describe what the transaction was in the view transaction page.
						<br/><br/>
						<strong>Amount:</strong> The amount of money that was involved in this transaction.
						<br/><br/>
						<strong>Transaction Date/Recurrence Starting Date:</strong> The date of when this transaction occured. By default it would be the current date of when you created the transaction. However, it could easily be changed. This is also the date in which the recurrence will start if you choose to mark this transaction as recurring.
						<br/><br/>
						<strong>Transaction Type:</strong> You can select either Income or Expense for the type. Your budget will be subtracted by the amount if Expense is selected and added if Income was selected
						<br/><br/>
						<strong>Category:</strong> This classifies your transaction so that it could be filtered in the view transaction page. Think of it as a tag for your transaction.
						<br/><br/>
						<strong>Payment Type:</strong> One-off is the default option and you should select it if this transaction will not occur on a regular basis. Otherwise select on recurring to let wallethero automatically add the same transaction in the frequency you choose for the period of time you specify.
			      </div>
			    </div>
			  </div>
			  <div class="panel panel-info">
			    <div class="panel-heading" role="tab" id="headingTwo">
			      <h4 class="panel-title">
			        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
			          How to view transactions
			        </a>
			      </h4>
			    </div>
			    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
			      <div class="panel-body">
			      
						This feature is only available when you are logged in.
						<br/><br/>
						Navigate to the view transactions page by clicking on Transaction on the navigation bar and selecting 'View Transactions' in the drop down menu.
						<br/><br/>
						The View Transactions page is the accumulation of all the transactions that were added to your account. By default it would show the transaction for the last week.
						<br/><br/>
						This page can be separated into three main parts:
						<br/><br/>
						<strong>Filter</strong>
						<br/>
						<u>From and To Date:</u>
						Specify the period of time to view your transactions. Note that it will not work if the from date is after the to date. 
						<br/>
						<u>Show Income:</u>
						If checked this will let you view transactions that were typed as income
						<br/>
						<u>Show Expense:</u>
						If checked this will let you view transactions that were typed as expense
						<br/>
						<u>Category:</u>
						Choose the categories of transaction you want to view. By default all categories are included.
						<br/><br/>
						<strong>Graph</strong>
						<br/>
						The graph shows the visual graph of all your expenses and income in the time period you have selected.
						<br/><br/>
						<strong>Table</strong>
						<br/>
						The table is a breakdown of each transaction, giving the full information of every transaction in the selected time period. It is possible to edit the details or delete the transaction from this table.
			      </div>
			    </div>
			  </div>
			  <div class="panel panel-info">
			    <div class="panel-heading" role="tab" id="headingThree">
			      <h4 class="panel-title">
			        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
			          Everything you need to know about goals
			        </a>
			      </h4>
			    </div>
			    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
			      <div class="panel-body">
			        Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
			      </div>
			    </div>
			  </div>
			  
			  <div class="panel panel-info">
			    <div class="panel-heading" role="tab" id="headingFour">
			      <h4 class="panel-title">
			        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
			          Currency conversion and how it works
			        </a>
			      </h4>
			    </div>
			    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
			      <div class="panel-body">
						Wallet hero supports currency conversion from a select number of supported currencies. This is a tool you can access from the 'Tool' header in navigation bar and selecting "Currency Conversion" in the drop down menu.
						<br/><br/>
						Select the currency from which you want to convert and the currency you want to convert to and fill in the from amount. Any value filled in the to amount will be ignored. By clicking the convert button, the conversion will occur from real time exchange rate and the results will be given in the to amount.
									      
			      </div>
			    </div>
			  </div>
			  <div class="panel panel-info">
			    <div class="panel-heading" role="tab" id="headingFive">
			      <h4 class="panel-title">
			        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
			          Finding deals relevant to you
			        </a>
			      </h4>
			    </div>
			    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
			      <div class="panel-body">
			        Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
			      </div>
			    </div>
			  </div>
			</div>
			
		    <hr class="featurette-divider">
		    Didn't find what you were looking for? Contact us here
	        <br/><br/>
	        
	        <form action="${applicationScope['ROUTER_SIGNIN']}" method="POST" onSubmit="return validator_signin(this)">
	
		        <div class="form-group" id="div-email" name="div-email">
		          <label>Email</label>
		          <input type="text" class="form-control" id="email" name="email" placeholder="Email..." value="${param['email']}"/>
		        </div>
		
		        <div class="form-group" id="div-message" name="div-message">
		          <label>Message</label>
		          <textarea class="form-control" id="message" name="message" placeholder="Type your message here..." value=""></textarea>
		        </div>
	
	          <input type="hidden" name="action" value="login"/> 
	          <button type="submit" class="btn btn-primary">Submit message</button> 
	        </form>
          	
        </div>
      </div>
      
  	   <%@ include file="footer.jsp" %>
    </div>
</body>
</html>

<script>
$('textarea').each(function () {
	  this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
	}).on('input', function () {
	  this.style.height = 'auto';
	  this.style.height = (this.scrollHeight) + 'px';
	});
</script>