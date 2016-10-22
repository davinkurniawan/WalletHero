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
          		<li>
          			Bootstrap
          		</li>
          		<li>
          			PostgreSQL
          		</li>
          		<li>
          			Apache
          		</li>
          		<li>
          			JSTL
          		</li>
          		<li>
          			Javax Mail
          		</li>
          		<li>
          			Spring Framework
          		</li>
          		<li>
          			Jackson Framework
          		</li>
          		<li>
          			Quartz Framework
          		</li>
          		<li>
          			HighChart.js
          		</li>
          		<li>
          			Exporting.js
          		</li>
          		<li>
          			Progressbar.js
          		</li>
          		<li style="color:red">
          			List here...
          		</li>
          	</ul>
        </div>
      </div>
          	
      
  	   <%@ include file="footer.jsp" %>
    </div>
</body>
</html>