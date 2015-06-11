<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	MESSAGE SENT ${msg}
	<hr />
	<p>Saldo:${saldo}</p>
	<p>Ime:${ime}</p>
	<jsp:include page="NumberOfUsers.jsp" />
	<br />
	<table>
		<tr>
			<td><jsp:include page="mainMenu.jsp" /></td>
			<td>
				<h2>Message is sent</h2>
			</td>
		</tr>
	</table>
</body>
</html>