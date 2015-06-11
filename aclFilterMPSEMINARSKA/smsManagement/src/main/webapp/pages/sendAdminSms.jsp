<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script type="text/javascript">
	//Wait for jQuery to be loaded
	$(document).ready(function() {
		jQuery('#postForm').bind('submit', function(event) {
			event.preventDefault();

			var sender = document.getElementById("sender").value;
			
			var receivers = document.getElementById("receivers").value;
			
			var body = document.getElementById("body").value;
			

			var data = {
				"sender" : sender,
				"receivers" : receivers,
				"body" : body
			};

			$.ajax({
				url : "${pageContext.request.contextPath}/admin/receive",
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				data : JSON.stringify(data),
				success : function(data) {
					if(data==true) {
					alert("Your message was successfully sent!");
					$("#status").html("message sent");
					}
					else {
					alert("Your message was not sent!Check if the numebrs are valid");
					$("#status").html("message not sent");
					}
					$("#sender").val("");
					$("#receivers").val("");
					$("#body").html("Enter text");
				}
			});

			return true;
		});
		
		 $('#myform').validate({ 
		        rules: {
		        	sender:{
		        		required: true,
		        		 digits: true
		        	},
		            receivers: {
		                required: true
		            },
		            body: {
		                required: true
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
			
			$("#receivers").rules("add", { regex: "^(?:\s*\d{1}\s*(?:,|$))+$" })
	});
</script>
</head>
<body>
	<jsp:include page="NumberOfUsers.jsp" />
	<h1>SEND SMS AS ADMIN</h1>
	<p>Status: <span id="status"></span></p>
	<hr />
	<form id="postForm">
		<table>

			<tbody>
				<tr>
					<td>Enter the number of the sender:</td>
					<br />
					<td><input type="text" name="sender" id="sender" /></td>
				</tr>
				<tr>
					<td>Enter the numbers of the receivers, separated by a comma:</td>
					<br />
					<td><input type="text" name="receivers" id="receivers" /></td>
				</tr>
				<tr>
					<td>Enter the body of the message:</td>
					<br />
					<td><textarea name="body" id="body" cols="40" rows="4">Enter text</textarea></td>
				</tr>
				<tr>
					<td><input type="submit" value="Send message" /></td>
				</tr>
			</tbody>
		</table>
		</form>
</body>
</html>