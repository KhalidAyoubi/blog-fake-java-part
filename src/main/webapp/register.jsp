<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlogKhalid - REGISTER</title>
    <style>
        /* Estilos generales */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 30px 10px 0;
            background-color: #f4f4f4;
            display: flex;
            flex-direction: column;
            gap: 30px;
            justify-content: center;
            align-items: center;
        }

        /* Estilos para los botones */
        .btn {
            display: block;
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }

        .createform {
            width: 500px;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .btn:hover {
            background-color: #45a049;
        }

        .btn-borrar {
            background-color: #f44336;
        }

        .btn-borrar:hover {
            background-color: #73170c;
        }

        /* Estilos para los campos de texto y selección */
        input[type="text"], input[type="password"], input[type="email"], select {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        /* Estilos para el título */
        h1, h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        /* Estilos para la tabla */
        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }

        header {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        nav {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        nav > ul {
            display: flex;
            flex-direction: row;
            gap: 20px;
            list-style: none;
            color: #333333;
        }

        nav > ul > li > a:link, nav > ul > li > a:visited, nav > ul > li > span {
            background-color: #f2f2f2;
            color: #333333;
            padding: 6px 12px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border: 1px solid #d5d5d5;
            border-radius: 6px;
        }

        nav > ul > li > span, nav > ul > li > a:hover, a:active {
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }
    </style>
</head>
<body>
<header>
    <span>¡Hola!  <%= (session.getAttribute("username")) != null ? (session.getAttribute("username")) : "No estàs registrat" %></span>

    <nav>
        <ul>
            <li><a href="entrades">Entradas</a></li>
            <li><a href="idioma">Idiomas</a></li>
            <li><a href='logout'>Log out</a></li>
            <c:if test='${rol == "ADMINISTRADOR" }'>
                <li><span>Registrar usuario</span></li>
            </c:if>
        </ul>
    </nav>
</header>
    <h1>Registrar un nuevo usuario</h1>
    <form class="createform" action="register" method="POST">
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
        <button type="submit" class="btn">Registrarse</button>
    </form>

    <h2>Lista de Usuarios</h2>
    <table border="1">
        <thead>
        <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Rol</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${usuaris}" var="usuari">
            <tr>
                <td>${usuari.username}</td>
                <td>${usuari.email}</td>
                <td>${usuari.nom}</td>
                <td>${usuari.cognoms}</td>
                <td>${usuari.rol.nom}</td>
                <td>
                    <form action="borrarusuari" method="POST">
                        <input type="hidden" name="username" value="${usuari.username}">
                        <button type="submit" class="btn btn-borrar">Borrar</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
