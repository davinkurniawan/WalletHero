<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	
	<title>${applicationScope['WEB_NAME']} - Sign In</title>
<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
    <div class="navbar-wrapper">
      <div class="container">
        <nav class="navbar navbar-custom navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${applicationScope['ROUTER_PUBLIC']}">WalletHero</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li><a href="./">Home</a></li>
                <li class="active"><a href="${applicationScope['ROUTER_SIGNIN']}">Sign In</a></li>
                <li><a href="${applicationScope['ROUTER_SIGNUP']}">Sign Up</a></li>
                <li><a href="${applicationScope['ROUTER_ABOUT']}">About</a></li>

                <!--<li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li role="separator" class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li>
                  </ul>
                </li>-->

              </ul>
            </div>
          </div>
        </nav>
      </div>
    </div>
	
	<div class="container marketing">
	
	  <h2>Sign In</h2>
		<form action="${PROFILE_COMMAND}" method="POST">
		  <table><tbody>
			<tr>
			  <td>Username / Email:</td>
			  <td><input type="text" name="username" /></td>
			</tr>
			<tr>
			  <td>Password:</td> 
			  <td><input type="password" name="password" /></td>
			</tr>
		  </tbody></table>
		  
		  <input type="hidden" name="action" value="login"/>	
		  <button type="submit" class="btn btn-default">Sign In</button>	 
		</form>
	  
	 <hr class="featurette-divider">
	
	<%@ include file="footer.jsp" %>
    </div><!-- /.container -->
</body>
</html>