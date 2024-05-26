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
            border: none;
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
            padding: 10px 15px;
            background-color: #f2f2f2;
            border: 1px solid #d5d5d5;
            border-radius: 10px;
        }

        .entrada-titol {
            font-size: 1.3rem;
            text-decoration: none;
            color: #333333;
        }

        .entrada-descripcio{
            font-size: 1.1rem;
            color: #333333;
        }

        .entrada-autor, .entrada-data, .entrada-idioma, .entrada-publica, .leer-mas {
            font-size: .8rem;
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
            <a href="${pageContext.request.contextPath}/entrada?id=${entrada.id}" class="entrada-titol"><h3>${entrada.titol}</h3></a>
            <p>${entrada.descripcio} <a href="${pageContext.request.contextPath}/entrada?id=${entrada.id}" class="leer-mas"><b>Leer más...</b></a></p>
            <span class="entrada-autor"><b>Autor:</b> ${entrada.autor.nom} ${entrada.autor.cognoms} | </span>
            <span class="entrada-data"><b>Fecha:</b> ${entrada.data} | </span>
            <span class="entrada-idioma"><b>Idioma:</b> ${entrada.idioma.nom} | </span>
            <span class="entrada-publica"><b>Estat:</b> ${entrada.publica == 1 ? "Pública" : "Privada"}</span>

            <form action="borrarentrada" method="POST">
                <input type="hidden" name="id" value="${entrada.id}">
<%--
                <input type="hidden" name="action" value="borrar">
--%>
                <button type="submit" class="btn btn-borrar">Borrar</button>
            </form>

            <a href="#" class=" btn editar">Editar</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
