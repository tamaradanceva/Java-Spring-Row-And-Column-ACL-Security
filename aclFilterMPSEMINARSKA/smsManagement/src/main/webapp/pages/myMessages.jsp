<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tablesorter.js")" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tablesorter.pager.js")" type="text/javascript"></script>

<script>
	$(function() {
		$("#tabs").tabs();
	

		$(document).on(
				"click",
				".edit",
				function(e) {

					var curRowId = $(this).attr("id");
					var el = $("#" + curRowId + ".seen");

					var row = el.attr("id");

					$('#tableReceived').find(el).text("true");

					$.getJSON(
							"${pageContext.request.contextPath}/client/markAsSeen/"
									+ curRowId, function(msg) {

							});
				});
		
		
	});
</script>
</head>
<body>
	<h1>
		<b>MY MESSAGES</b></span>
	</h1>
	<hr />
	<jsp:include page="NumberOfUsers.jsp" />
	
	<table>
		<tr>
			<td><jsp:include page="mainMenu.jsp" /> <br />
				<p>${message}</p></td>
				<p><a href="${pageContext.request.contextPath}/client/aclTest"><b>GO TO TEST</b></a></p>
			<td>
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Overview</a></li>
						<li><a href="#tabs-2">Sent messages</a></li>
						<li><a href="#tabs-3">Received messages</a></li>
					</ul>
					<div id="tabs-1">
						<p>General info</p>
						<p>Ime: ${ime}</p>
						<p>Saldo: ${saldo}</p>
						<p>${msg}</p>
					</div>
					<div id="tabs-2">
						<table class="paginated" border="1px" cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th width="5%">To</th>
									<th width="5%">Contents</th>
									<th width="5%">Date and time</th>
									<th width="5%">Seen</th>
								</tr>
							</thead>
							<tbody>
										<c:forEach var="sms" items="${listaSent}">
											<tr>
												<td><c:forEach var="receiver" items="${sms.receivers}">
														<a
															href="${pageContext.request.contextPath}/client/show/${receiver.broj}">${receiver}</a>
														<br />
													</c:forEach></td>
												<td>${sms.body}</td>
												<td>${sms.date}</td>
												<td>${sms.seen}</td>
											</tr>
										</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="tabs-3">
						${msg1}
						<table class="paginated" border="1px" cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th width="10%">From</th>
									<th width="15%">Contents</th>
									<th width="10%">Date and time</th>
									<th width="10%">Seen</th>
									<th width="10%">Actions</th>
								</tr>
							</thead>
							<tbody id="tableReceived">
								<c:forEach var="sms" items="${listaReceived}">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/client/show/${sms.sender.broj}">${sms.sender}</a></td>
										<td>${sms.body}</td>
										<td>${sms.date}</td>
										<td class="seen" id="${sms.sms_id}">${sms.seen}</td>
										<td><a href="#" id="${sms.sms_id}" class="edit">Mark
												as seen</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

				</div>
			</td>
		</tr>
	</table>

</body>
</html>