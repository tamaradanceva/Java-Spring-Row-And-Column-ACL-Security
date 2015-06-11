<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script>
$(document).ready(function () {

    $('#myform').validate({ // initialize the plugin
        rules: {
            number: {
                required: true,
                digits: true
            }
        }
    });

});
</script>
</head>



<body>
	<jsp:include page="NumberOfUsers.jsp" />
	LOGIN CLIENT
	<hr />
	<p>${message}</p>
	<form id="myform" method="POST" action="${pageContext.request.contextPath}/client/loginClient.html">
	<p>Vnesi go tvojot broj: </p>
	<input type="text" id="number" name="number">
	<br />
	<br />
	<input type="checkbox" id="cookie" name="cookie">Zapamti me vo naredniot mesec<br>
	<br />
	<input type="submit" value="Vnesi" />
	</form>
</body>
</html>