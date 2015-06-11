<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to SmsService</title>
</head>
<body>
<jsp:include page="NumberOfUsers.jsp" />
	<h1>Welcome to SmsService</h1>
	<hr />
	<br />
	<a href="${pageContext.request.contextPath}/client/login">Login as client </a>
	<br />
	<br />
	<a href="${pageContext.request.contextPath}/admin/login">Login as admin </a>
</body>
</html>