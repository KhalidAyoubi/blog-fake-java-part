<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - INDEX</title>
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

        /* Estilos para los botones */
        .btn {
            display: block;
            width: 200px;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }

        .btn-borrar {
            background-color: #f44336;
        }

        .btn-borrar:hover {
            background-color: #73170c;
        }

        .entradas {
            display: flex;
            flex-direction: column;
            gap: 15px;
            padding: 15px;
        }

        .entrada {
            background-color: #f2f2f2;
            border: 1px solid #d5d5d5;
        }

        .entrada >

    </style>
</head>
<body>
<h1>Blog Khalid</h1>
<p>Usuari registrat: <%= (session.getAttribute("usuari")) != null ? (session.getAttribute("usuari")) : "No estàs registrat" %></p>

<h2>Entradas</h2>
<div class="entradas">
    <c:forEach items="${entradas}" var="entrada">
        <div class="entrada">
            <h3 class="entrada-titol">${entrada.titol}</h3>
            <p class="entrada-descripcio">${entrada.descripcio}</p>
            <p class="entrada-autor">Autor: ${entrada.autor.username}</p>
            <p class="entrada-data">Fecha: ${entrada.data}</p>
            <p class="entrada-idioma">Idioma: ${entrada.idioma.nom}</p>
            <form action="entrada" method="POST">
                <input type="hidden" name="id" value="${entrada.id}">
                <input type="hidden" name="action" value="borrar">
                <button type="submit" class="btn btn-borrar">Borrar</button>
            </form>
            <a href="#" class=" btn editar">Editar</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
