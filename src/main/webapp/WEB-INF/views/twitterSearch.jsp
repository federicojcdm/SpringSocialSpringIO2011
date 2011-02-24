<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/spring-social/facebook/tags" prefix="facebook" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Tweets</title>

<!-- jQuery required for Facebook connect form -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script>

</head>
<body>
<h1>Open Social tweets</h1>
<a href="<c:url value='/j_spring_security_logout'/>">Logout</a>

<h2>#springsocial Tweets</h2>
<table border="1">
<c:forEach var="tweet" items="${tweets}">
	<tr>
		<td>${tweet.fromUser}</td>
		<td>${tweet.createdAt}</td>
		<td>${tweet.text}</td>
	</tr>
	
</c:forEach>
</table>
</body>
</html>