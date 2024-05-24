<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - IDIOMES</title>
    <style>
        /* Estilos para la tabla */
        table {
            border-collapse: collapse;
            width: 100%;
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
        button {
            background-color: #4CAF50;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }

        button:hover {
            background-color: #45a049;
        }

        /* Estilos para los campos de texto y ocultos */
        input[type="text"], input[type="number"], input[type="hidden"] {
            padding: 8px;
            margin: 4px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        /* Estilos para el título */
        h1 {
            color: #333;
            font-family: Arial, sans-serif;
        }

        /* Estilos para el formulario de edición */
        #editarForm {
            margin-bottom: 16px;
        }

        /* Estilos para los botones */
        .editar-btn {
            background-color: #4CAF50;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            margin-right: 8px; /* Añade espacio a la derecha del botón */
        }

        .editar-btn:hover {
            background-color: #45a049;
        }

        .borrar-btn {
            background-color: #f44336; /* Cambia el color del botón */
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }

        .borrar-btn:hover {
            background-color: #d32f2f; /* Cambia el color del botón al pasar el ratón */
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
    <button type="submit">Crear</button>
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
                <button type="submit" class="editar-btn">Guardar</button>
                </form>
                <form action="idioma" method="POST">
                    <input type="hidden" name="action" value="borrar">
                    <input type="hidden" name="ididioma" value="${idioma.ididioma}">
                    <button type="submit" class="borrar-btn">Borrar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
