<%-- 
    Document   : Konfirmacija
    Created on : 6.12.2010, 01:53:
    Author     : ristes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="NumberOfUsers.jsp"/>

        Трансакцијата е успешна <br/>
        <%=request.getParameter("name")%> <%=request.getParameter("lastName")%>,
        од вашата сметка се одземени ${payed} денари.

        <a href="${pageContext.request.contextPath}/forget">Избриши податоци</a> 
       
    </body>
</html>
