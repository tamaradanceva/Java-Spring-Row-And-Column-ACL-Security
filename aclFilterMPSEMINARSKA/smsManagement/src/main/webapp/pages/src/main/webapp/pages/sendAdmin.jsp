<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>

<script>

      $(document).ready(function(){
    	  
    	  jQuery('#postForm').bind('submit', function(event) {
    		   event.preventDefault();

    		   var sender = document.getElementById("sender").value;
    		   
    		   var receiver = document.getElementById("receiver").value;
    		   
    		   var body = document.getElementById("body").value;	   

    		   var data = {
    		    "sender" : sender,
    		    "receiver" : receiver,
    		    "body" : body
    		   };

    		   $.ajax({
    		    url : "${pageContext.request.contextPath}/receive",
    		    type : "POST",
    		    contentType : 'application/json',
    		    dataType : 'json',
    		    data : JSON.stringify(data),
    		    success : alert("Испративте порака!")
    		   });

    		   return true;
    		  });
      });
    
      
</script>
</head>
<body>
SEND SMS AS ADMIN
<jsp:include page="NumberOfUsers.jsp" />
<form id="postForm">
 <p>Broj na prakac</p>
 <input type="text" id="sender" name="sender" >
 <p>Broj na primac</p>
 <input type="text" id="receiver" name="receiver" >
 <p>Sodrzina na porakata</p>
 <input type="text" id="body" name="body" >
 <br />
 <br />
 <input type="submit" value="Send my message">
 
 <a href="${pageContext.request.contextPath}/smsPregled.html">Врати се назад на преглед</a>
</form>
</body>
</html>