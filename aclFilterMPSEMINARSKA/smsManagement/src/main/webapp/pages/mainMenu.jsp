<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
	$(function() {
		$("#menu").menu();
	});
</script>
<style>
.ui-menu {
	width: 150px;
}
</style>
</head>
<body>
	<ul id="menu">
		<li><a href="${pageContext.request.contextPath}/client/myMessages">MyMessages</a></li>
  		<li><a href="${pageContext.request.contextPath}/client/sendClientSms">Send a message</a></li>
  		<li><a href="${pageContext.request.contextPath}/client/forgetMyNumber">Forget my number</a></li>
  		<li><a href="${pageContext.request.contextPath}/client/filterDate">Filter by date</a></li>
	</ul>
</body>
</html>