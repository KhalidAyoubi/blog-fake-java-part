<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - INDEX</title>
</head>
<body>
<h1>Blog Khalid</h1>
<p>Usuari registrat: <%= (session.getAttribute("usuari")) != null ? (session.getAttribute("usuari")) : "No estàs registrat" %></p>

</body>
</html>
