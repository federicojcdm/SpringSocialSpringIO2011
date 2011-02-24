<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/spring-social/facebook/tags" prefix="facebook" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Update Status</title>

<!-- jQuery required for Facebook connect form -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script>

</head>
<body>
<h1>Aplicación Demo Spring Social</h1>
<a href="<c:url value='/j_spring_security_logout'/>">Logout</a>

<h2>Services</h2>

<!--  LINKEDIN -->

<c:if test="${connectedToLinkedIn}">
	Conectado a LinkedIn como <a href="<c:out value='${linkedInProfile.publicProfileUrl }'/>"><c:out value="${linkedInProfile.firstName }"/> <c:out value="${linkedInProfile.lastName }"/></a><br/>
</c:if>
<c:if test="${!connectedToLinkedIn}">
	<a href="<c:url value='/connect/linkedin'/>">Conectar a LinkedIn</a><br/>
</c:if>
<c:if test="${connectedToLinkedIn}">
	<a href="<c:url value='/detail/linkedin'/>">Conexiones LinkedIn</a><br/>
</c:if>






<!--  FACEBOOK -->

<c:if test="${connectedToFacebook}">
	Conectado a Facebook como <c:out value="${facebookProfile.firstName }"/> <c:out value="${facebookProfile.lastName }"/>
	<a href="<c:url value='/detail/facebook'/>">Perfil Facebook</a><br/>
</c:if>
<c:if test="${!connectedToFacebook}">

<%/* Connect to Facebook */ %> 

<form id="fb_signin" action="<c:url value="/connect/facebook" />" method="post">
	<div class="formInfo">
	</div>
	<div id="fb-root"></div>	
	<p><fb:login-button perms="email,publish_stream,offline_access" onlogin="$('#fb_signin').submit();" v="2" length="long">Connect to Facebook</fb:login-button></p>
</form>

<facebook:init />
</c:if>

<br/>

<!--  TWITTER -->

<c:if test="${connectedToTwitter}">
	Conectado a Twitter como <c:out value="${twitterProfile}"/>
	<a href="<c:url value='/detail/twitter'/>">Tweets</a><br/>
</c:if>
<c:if test="${!connectedToTwitter}">
	<a href="<c:url value='/connect/twitter'/>">Conectar a Twitter</a>
</c:if>


<!-- UPDATE STATUS -->
<form:form action="status" method="post" modelAttribute="statusForm">
	<form:errors path="status" cssClass="error"/><br/>
	<form:textarea path="status" />
	<br/>
	<input type="submit" value="Send" />
</form:form>

</body>
</html>