<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - LOGIN</title>
</head>
<body>
<h1>Iniciar sesión</h1>
<form action="login" method="POST">
    <label for="username">Usuario:</label>
    <input id="username" type="text" name="username" required>
    <label for="password">Contraseña:</label>
    <input id="password" type="password" name="password" required>
    <button type="submit">Iniciar sesión</button>
</form>

</body>
</html>
