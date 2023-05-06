<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error page</title>
</head>
<body>
<h1>Mensagem de erro!!!</h1>
<%out.print(request.getAttribute("msg")); %>
<textarea rows="50" cols="50"></textarea>
</body>
</html>