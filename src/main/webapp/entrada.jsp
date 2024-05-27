<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>BlogKhalid - ${entrada.titol}</title>
    <style>
        /* Estilos generales */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 30px 10px 0;
            background-color: #f4f4f4;

            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Estilos para el título */
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        /* Estilos para los botones */
        .btn {
            display: block;
            /*width: 100px;*/
            color: white;
            padding: 5px 10px;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            /*margin-top: 10px;*/
        }

        .btn-borrar {
            background-color: #f44336;
            border: none;
        }

        .btn-borrar:hover {
            background-color: #a12f21;
        }

        .btn-editar {
            background-color: #4CAF50;
            border: none;
        }

        .btn-editar:hover {
            background-color: #2b7a2f;
        }

        .btn-back-entradas {
            width: 500px;
            background-color: #4f4caf;
            padding: 10px 20px;
            margin-bottom: 30px;
        }

        .btn-back-entradas:hover {
            background-color: #2b367a;
        }

        from {
            display: flex;
        }

        .entrada {
            padding: 10px 15px;
            background-color: #f2f2f2;
            border: 1px solid #d5d5d5;
            border-radius: 10px;
            margin-bottom: 10px;

            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .entrada-descripcio{
            font-size: 1.1rem;
            color: #333333;

            width: 60%;
        }

        .entrada-info {
            display: flex;
            flex-direction: row;
            gap: 25px;
        }

        .entrada-autor, .entrada-data, .entrada-idioma, .entrada-publica {
            font-size: .8rem;
        }

        .entrada-actions {
            margin-top: 30px;
            display: flex;
            flex-direction: row;
            justify-content: center;
            gap: 25px;
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
            <li><a href="idioma">Idiomas</a></li>
            <li><a href='logout'>Log out</a></li>
            <c:if test='${rol == "ADMINISTRADOR" }'>
                <li><a href="register">Registrar usuario</a></li>
            </c:if>
        </ul>
    </nav>
</header>
 <h1>${entrada.titol}</h1>
 <a class="btn btn-back-entradas" href="${pageContext.request.contextPath}/entrades"> << Todas las entradas</a>
    <div class="entrada">
        <div class="entrada-info">
            <span class="entrada-autor"><b>Autor:</b> ${entrada.autor.nom} ${entrada.autor.cognoms}</span>
            <span class="entrada-data"><b>Fecha:</b> ${entrada.data}</span>
            <span class="entrada-idioma"><b>Idioma:</b> ${entrada.idioma.nom}</span>
            <span class="entrada-publica"><b>Estat:</b> ${entrada.publica == 1 ? "Pública" : "Privada"}</span>
        </div>

        <c:if test='${rol == "ADMINISTRADOR" }'>
            <div class="entrada-actions">
                <form action="borrarentrada" method="POST">
                    <input type="hidden" name="id" value="${entrada.id}">
                    <button type="submit" class="btn btn-borrar">Borrar</button>
                </form>

                <form action="editarentrada" method="GET">
                    <input type="hidden" name="id" value="${entrada.id}">
                    <button type="submit" class="btn btn-editar">Editar</button>
                </form>
            </div>
        </c:if>
        <p class="entrada-descripcio">${entrada.descripcio}</p>
    </div>
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
