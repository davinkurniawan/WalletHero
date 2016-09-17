<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Router Test</title>
</head>
<body>
	Router Test! which is run by the RouterTest.jsp Page!
	<br />
	<br />
	<br />
	<br />

	<div id="links">
		<ul>
			<li><a href="router?operation=signIn">Sign In Page</a></li>
			<li><a href="router?operation=signOut">Sign Out Page</a></li>
			<li><a href="router?operation=signUp">Sign Up Page</a></li>
			<li><a href="router?operation=public">Home Page (Public	Page)</a></li>
			<li><a href="router?operation=home">Home Page</a></li>
			<li><a href="router?operation=viewEditProfile">Profile	Page</a></li>
			<li><a href="router?operation=search">Search Page</a></li>
			<li><a href="router?operation=results">Results Page</a></li>
			<li><a href="router?operation=PAGE_NOT_FOUND">Error Page</a></li>
			<li><a href="router?operation=about">About Page</a></li>
		</ul>
	</div>
	 
</body>
</html>