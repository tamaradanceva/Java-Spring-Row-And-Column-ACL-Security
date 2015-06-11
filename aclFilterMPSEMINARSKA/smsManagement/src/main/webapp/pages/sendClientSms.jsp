<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>send client sms</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script>
	$(document).ready(function() {
		$('#myform').validate({ // initialize the plugin
			rules : {
				receiver : {
					required : true
				}
			}
		});
		
		$.validator.addMethod(
		        "regex",
		        function(value, element, regexp) {
		            var re = new RegExp(regexp);
		            return this.optional(element) || re.test(value);
		        },
		        "Please check your input."
		);
		
		$("#receiver").rules("add", { regex: "^(?:\s*\d{1}\s*(?:,|$))+$" })

	});
</script>
</head>
<body>
	<jsp:include page="NumberOfUsers.jsp" />
	<h1>SEND CLIENT SMS</h1>
	<hr />
	<table>
		<tr>
			<td><jsp:include page="mainMenu.jsp" />
				<p>${message}</p>
				<p>Saldo: ${saldo}</p>
				<p>Ime i prezime: ${ime}</p></td>
			<td>
				<form id="myform" method="post"
					action="${pageContext.request.contextPath}/client/sendClientSms.html">
					<p>Enter the numbers, separated by a comma</p>
					<input type="text" id="receiver" name="receiver">
					<p>Sodrzina na porakata</p>
					<textarea id="body" name="body" cols="40" rows="4">Enter text</textarea>
					<br /> <br /> <input type="submit" value="Send my message" />
				</form>
			</td>>
		</tr>
	</table>
</body>
</html>