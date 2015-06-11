<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>filter date</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#datepicker").datepicker();

		$("#tabs").tabs();

	});
</script>
</head>
<body>
	<h1>Search by date</h1>
	<hr />
	<jsp:include page="NumberOfUsers.jsp" />
	<br />
	<table>
		<tr>
			<td><jsp:include page="mainMenu.jsp" />
				<p>${message}</p></td>
			<td>
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Find by date</a></li>
						<li><a href="#tabs-2">Overview</a></li>
					</ul>
					<div id="tabs-1">
						<form id="formPost" method="post"
							action="${pageContext.request.contextPath}/client/filterDate">
							<p>
								Date: <input type="text" id="datepicker" name="datepicker">
							</p>
							<p>
								<input type="submit" value="Get Messages" />
							</p>
						</form>
						<table id="tableSMS" border="1px" cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th width="10%">To</th>
									<th width="10%">From</th>
									<th width="15%">Contents</th>
									<th width="10%">Date and time</th>
									<th width="10%">Seen</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="sms" items="${lista}">
									<tr>
										<td><c:forEach var="receiver" items="${sms.receivers}">
								${receiver} <br />
											</c:forEach></td>
										<td>${sms.sender}</td>
										<td>${sms.body}</td>
										<td>${sms.date}</td>
										<td>${sms.seen}</td>
									</tr>
								</c:forEach>
								<c:forEach var="sms" items="${lista1}">
									<tr>
										<td><c:forEach var="receiver" items="${sms.receivers}">
								${receiver} <br />
											</c:forEach></td>
										<td>${sms.sender}</td>
										<td>${sms.body}</td>
										<td>${sms.date}</td>
										<td>${sms.seen}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="tabs-2">
						<p>General info</p>
						<p>Ime: ${ime}</p>
						<p>Saldo: ${saldo}</p>
						<p>${msg}</p>
					</div>

				</div>
			</td>
		</tr>
	</table>
</body>
</html>