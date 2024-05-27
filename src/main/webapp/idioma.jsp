<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - IDIOMES</title>
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

        /* Estilos para los botones */
        .btn {
            display: block;
            width: calc(100% - 22px);
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }


        .btn:hover {
            background-color: #45a049;
        }

        .btn-borrar {
            background-color: #f44336;
        }

        .btn-borrar:hover  {
            background-color: #73170c;
        }

        /* Estilos para los campos de texto y selección */
        input[type="text"], input[type="password"], input[type="email"], input[type="number"], select {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }


        .go-up {
            display: block;
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 24px;
            transition: background-color 0.3s;
        }

        .go-up:hover {
            background-color: #45a049;
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
            <li><span>Idiomas</span></li>
            <li><a href='logout'>Log out</a></li>
        </ul>
    </nav>
</header>
<c:if test='${rol == "ADMINISTRADOR" }'>
    <h1>Registrar un nuevo Idioma</h1>

    <form action="idioma" method="POST">
        <input type="text" name="nom" placeholder="català" required>
        <input type="text" name="codi" placeholder="ca" required>
        <input type="number" name="defecte" placeholder="0 = false, 1 = true" required>
        <input type="hidden" name="action" value="crear">
        <button class="btn" type="submit">Crear</button>
    </form>
</c:if>

<h2>Lista de idiomas</h2>
<div id="editarForm"></div>
<table border="1">
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Código</th>
        <th>¿Por defecto?</th>
    </tr>
    </thead>
    <tbody>
    <c:if test='${rol != "ADMINISTRADOR" }'>
        <c:forEach items="${idiomas}" var="idioma">
            <tr>
                    <td>${idioma.nom}</td>
                    <td>${idioma.codi}</td>
                    <td>${idioma.defecte}</td>
            </tr>
        </c:forEach>
    </c:if>

<c:if test='${rol == "ADMINISTRADOR" }'>
    <c:forEach items="${idiomas}" var="idioma">
        <tr>
            <form action="idioma" method="POST">
            <td><input type="text" name="nom" value="${idioma.nom}"></td>
            <td><input type="text" name="codi" value="${idioma.codi}"></td>
            <td><input type="number" name="defecte" value="${idioma.defecte}"></td>
            <td>
                <input type="hidden" name="ididioma" value="${idioma.ididioma}">
                <input type="hidden" name="action" value="editar">
                <button type="submit" class="btn">Guardar</button>
                </form>
                <form action="borraridioma" method="POST">
                    <input type="hidden" name="ididioma" value="${idioma.ididioma}">
                    <button type="submit" class="btn btn-borrar">Borrar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</c:if>
    </tbody>
</table>

<a class="go-up" href="#">↑</a>

<script>
    document.querySelector('.go-up').addEventListener('click', function(event) {
        event.preventDefault();
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });
</script>
</body>
</html>
