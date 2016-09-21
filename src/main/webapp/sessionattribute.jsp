<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	Enumeration<String> keys = session.getAttributeNames();
	while (keys.hasMoreElements())
	{
	  String key = (String)keys.nextElement();
	  out.println(key + ": " + session.getAttribute(key).toString() + "<br>");
	}
%>

</body>
</html>