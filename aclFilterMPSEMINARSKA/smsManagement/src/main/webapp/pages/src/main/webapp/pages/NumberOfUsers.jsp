<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@page import="java.util.Date"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<div style="float: right">
	<c:if test="${pageContext.request.userPrincipal.authenticated}">
			Најавени сте како: <b>${pageContext.request.userPrincipal.principal.username}</b> 
			<a href="${pageContext.request.contextPath}/j_spring_security_logout">одјава</a>
	</c:if>
</div>
<div>
	Моментално има ${numUsers} активни корисници<br />


</div>