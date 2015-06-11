<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sms serivce</title>
</head>
<body>
	<h1>LOGIN PAGE</h1>
	<hr />
	<%
		if (request.getParameter("error") != null) {
			out.println("ERROR LOGIN");
		}
	%>
	<form
		action="${pageContext.request.contextPath}/j_spring_security_check"
		method="post">
		<table>
			<tr>
				<td>Username<input type="text" name="j_username" />
				</td>
			</tr>
			<tr>
				<td>Password<input type="password" name="j_password" />
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>