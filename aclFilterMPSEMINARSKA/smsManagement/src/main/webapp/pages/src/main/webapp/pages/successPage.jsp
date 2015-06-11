<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="NumberOfUsers.jsp" />
Вашата порака е успешно испратена!

<p> Моментално имате: ${saldo} денари на вашата сметка</p>

<a href="${pageContext.request.contextPath}/smsPregled.html">Врати се на преглед на пораки</a>
		
<p>${message}</p>


</body>
</html>