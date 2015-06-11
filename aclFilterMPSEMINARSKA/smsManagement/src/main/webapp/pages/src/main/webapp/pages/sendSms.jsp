<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Ипратете нова порака
<br> </br>
<jsp:include page="NumberOfUsers.jsp" />

<p>${username}</p>

<p> Моментално имате: ${saldo} денари на вашата сметка</p>
	
<form method="post" action="${pageContext.request.contextPath}/sendSms.html">
	<p>Изберете примач</p>
	<select multiple id="select" name="select">
		<c:forEach var="contact" items="${Contacts}">
				<option value="${contact.broj}">${contact.username}</option>
			</c:forEach>
	<select>
	<p>Содржина на пораката</p>
	<input type="text" id="body" name="body" style="height: 80px; font-size: 10pt;">
	<br />
	<br />
	<input type="submit" value="Испрати порака" >
</form>
<br> </br>
<a href="${pageContext.request.contextPath}/smsPregled.html">Врати се назад на преглед</a>
</body>
</html>