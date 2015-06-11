<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="NumberOfUsers.jsp" />
	<p>${username}</p>
	
	<p> Моментално имате: ${saldo} денари на вашата сметка</p>
	Листа на испратени пораки:
	
	
	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
			    <th width="40%" height="40">Имате испратено пораки до</th>
				<th width="40%" height="40">текст</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sms" items="${sentSMS}">
				<!--listasms definirana vo kontrolerot kaj post baranje na {broj} -->
				<tr>
					<td width="40%" height="40">
					<a href="${pageContext.request.contextPath}/list/${sms.receiver}.html">${sms.receiver.username}</a></td>
					<td>${sms.body}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<br> </br>
	Листа на примени пораки
	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="50%" height="40">Имате примено пораки од</th>
			<!-- 	<th width="10%">body</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sms" items="${receivedSMS}">
				<!--listasms definirana vo kontrolerot kaj post baranje na {broj} -->
				<tr>
					<td width="50%" height="40"><a
						href="${pageContext.request.contextPath}/list/${sms.sender}.html">${sms.sender.username}</a></td>
				<!--  	<td>${sms.body}</td> -->
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
<br> </br>
	<a href="${pageContext.request.contextPath}/sendSms.html">Испрати нова порака!</a>
		<br />
		<br />
	<a href="${pageContext.request.contextPath}/sendAdmin.html">Испрати нова порака како admin!</a>
		<br> </br>
		<a href="${pageContext.request.contextPath}/forget">Избриши податоци!</a>
</body>
</html>