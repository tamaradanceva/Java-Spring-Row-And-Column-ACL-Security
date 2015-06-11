<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>Внесете број:</p>
	<form method="POST" action="${pageContext.request.contextPath}/broj.html">
	<input type="text" id="broj" name="broj">
	<br />
	<input type="submit" value="Приказ на пораки!">
	</form>
</body>
</html>