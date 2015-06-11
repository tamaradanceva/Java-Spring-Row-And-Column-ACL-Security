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
<p>Листа на примени и пратени пораки:</p>
	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="10%">Испраќач</th>
				<th width="15%">Примач</th>
				<th width="10%">Текст</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sms" items="${listaSMS}"> <!--listasms definirana vo kontrolerot kaj GET baranje na list/id,ta e ta konechnata spoena lista -->
				<tr>
					<td>${sms.sender.username}</td>
					<td>${sms.receiver.username}</td>
					<td>${sms.body}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 
	 
<a href="${pageContext.request.contextPath}/smsPregled.html">Врати се на преглед на пораки</a>

</body>
</html>
	
<!--  	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="15%">receiver</th>
				<th width="10%">body</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sms" items="${listaSMS}"> <!--listasms definirana vo kontrolerot kaj post baranje na {broj} -->
 	<!--  		<tr>
					<td>${sms.receiver.username}</td>
					<td>${sms.body}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	 Примено пораки од:
	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="10%">sender</th>
				<th width="10%">body</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sms" items="${listaSMS}"> <!--listasms definirana vo kontrolerot kaj post baranje na {broj} -->
				<tr>
	<!--				<td>${sms.sender.username}</td>
					<td>${sms.body}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>



 -->

