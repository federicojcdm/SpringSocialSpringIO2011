<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Aplicación Demo Spring Social</title>
</head>
<body>

<c:if test="${not empty param.login_error}">
	<font color="red"> Error de autenticación. Inténtalo
	de nuevo.<br />
	<br />
	Razón: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </font>
</c:if>

<h1> Aplicación Demo Spring Social</h1>

<h2>Login:</h2>
<form action="<c:url value='/j_spring_security_check'/>" method="post">
	<table>
		<tr>
			<td>Nombre de usuario:</td>
			<td><input type="text" name='j_username'
				value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' /></td>
		</tr>
		<tr>
			<td>Contraseña:</td>
			<td><input type='password' name='j_password' /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Login" /></td>
		</tr>
	</table>
</form>


</body>
</html>