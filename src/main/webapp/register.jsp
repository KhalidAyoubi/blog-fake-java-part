<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - REGISTER</title>
</head>
<body>
<h1>Registro</h1>
<form action="register" method="POST">
    <label for="username">Usuario:</label>
    <input id="username" type="text" name="username" required>

    <label for="password">Contraseña:</label>
    <input id="password" type="password" name="password" required>

    <label for="email">Correo:</label>
    <input id="email" type="email" name="email" required>

    <label for="nom">Nombre:</label>
    <input id="nom" type="text" name="nom" required>

    <label for="cognoms">Apellidos:</label>
    <input id="cognoms" type="text" name="cognoms" required>

    <label for="rol">Rol: </label>
    <select name="rol" id="rol">
        <option value="USUARI REGISTRAT">Usuari registrat</option>
        <option value="ADMINISTRADOR">Administrador</option>
    </select>

    <button type="submit">Registrarse</button>
</form>
</body>
</html>
