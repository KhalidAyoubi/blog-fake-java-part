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
    </style>
</head>
<body>
<h1>Registrar un nuevo Idioma</h1>

<form action="idioma" method="POST">
    <input type="text" name="nom" placeholder="català" required>
    <input type="text" name="codi" placeholder="ca" required>
    <input type="number" name="defecte" placeholder="0 = false, 1 = true" required>
    <input type="hidden" name="action" value="crear">
    <button class="btn" type="submit">Crear</button>
</form>


<h2>Lista de idiomas</h2>
<div id="editarForm"></div>
<table border="1">
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Código</th>
        <th>¿Por defecto?</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
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
                <form action="idioma" method="POST">
                    <input type="hidden" name="action" value="borrar">
                    <input type="hidden" name="ididioma" value="${idioma.ididioma}">
                    <button type="submit" class="btn btn-borrar">Borrar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
