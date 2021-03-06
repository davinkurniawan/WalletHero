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

    <title>Welcome to ${applicationScope['WEB_NAME']}</title>
    <%@ include file="bootstrapHeader.jsp" %>
    
    <style>
		body {
			padding-top: 50px;
			color: #959595;
		
			height: 100%;
		    width: 100%;
		    margin: 0;
		    position: relative;
		    
		    -webkit-background-size: cover;
		    -moz-background-size: cover;
		    -o-background-size: cover;
		    background-size: cover;
		
		    background-repeat: no-repeat;
		    background-position: center;
		    background-attachment: fixed;
		}
		.container.navbar-border {	
		    border-bottom: 0px solid #87CEFA;
		}
		
    </style>
    
</head>
<body background="images/background.jpg">
	<%@ include file="navbar.jsp" %>

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

      <!-- START THE FEATURETTES -->
	  <h2 style="color:white">Welcome to ${applicationScope['WEB_NAME']}</h2>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading" style="color:white">What is WalletHero?  <span class="text-muted" style="color:white">It's the one budgeting app you need.</span></h2>
          <p class="lead" style="color:white">WalletHero is your one stop destination to keeping track of your budget. By signing up an account with us, we'll take care of the counting and remembering so that you have more time to do other stuff.</p>
        </div>
        <div class="col-md-5">
          <img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="500x500" src="images/Budget-icon.jpg" data-holder-rendered="true">
        </div>
      </div>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7 col-md-push-5">
          <h2 class="featurette-heading" style="color:white">Budget and Tracking  <span class="text-muted" style="color:white">It's for everyone</span></h2>
          <p class="lead" style="color:white">Expenses and incomes can be added by entering a few quick information. We'll then do the maths and update your budget. We support multiple currencies and convert them according to the latest conversion rates to the one you choose. You can even mark an expense or income as recurring so that you won't have to enter the data next time.</p>
        </div>
        <div class="col-md-5 col-md-pull-7">
          <img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="500x500" src="images/save.jpg" data-holder-rendered="true">
        </div>
      </div>

      <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading" style="color:white">Coupons, voucher and discounts. <span class="text-muted" style="color:white">Who doesn't want that?</span></h2>
          <p class="lead" style="color:white">By filling out your profile with preferences and opting in to receive discounts. We'll recommend deals that you might find appealing. We'll even provide you with the links to the websites so you can check for yourself whether or not you're interested.</p>
        </div>
        <div class="col-md-5">
          <img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="500x500" src="images/deals.jpg" data-holder-rendered="true">
        </div>
      </div>

      <!-- /END THE FEATURETTES -->

  	   <%@ include file="footer_index.jsp" %>
    </div>
</body>
</html>