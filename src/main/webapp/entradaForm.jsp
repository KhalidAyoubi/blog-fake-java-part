<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlogKhalid - ${accio == 'editar' ? 'Editar' : 'Crear'} Entrada</title>
    <style>
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

        .btn:hover {
            background-color: #45a049;
        }

        .btn-danger {
            background-color: #f44336;
        }

        .btn-danger:hover {
            background-color: #73170c;
        }

        .createform {
            width: 500px;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        input[type="text"], input[type="password"], input[type="email"], select {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        h1, h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
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
    </style>
</head>
<body>
<h1>${accio == 'editar' ? 'Editar Entrada' : 'Crear Entrada'}</h1>
<a class="btn btn-back-entradas" href="${pageContext.request.contextPath}/entrades"> << Todas las entradas</a>

<form class="createform" action="${accio == 'editar' ? 'editarentrada' : 'crearentrada'}" method="POST">
    <%--<input type="hidden" name="id" value="${entrada.id}">--%>
        <c:if test="${entrada.autor != null}">
            <input type="hidden" name="autor" value="${entrada.autor.username}"  required>
        </c:if>
        <c:if test="${entrada.id != null}">
            <input type="hidden" name="id" value="${entrada.id}"  required>
        </c:if>

    <label for="titol">Título:</label>
    <input id="titol" type="text" name="titol" value="${entrada.titol == null ? '' : entrada.titol}" required>

    <label for="descripcio">Descripción:</label>
    <textarea id="descripcio" name="descripcio" rows="5" required>${entrada.descripcio == null ? '' : entrada.descripcio}</textarea>

    <label for="idioma">Idioma:</label>
    <select id="idioma" name="idioma" required>
        <c:forEach items="${idiomas}" var="idioma">
            <option value="${idioma.ididioma}" ${entrada.idioma.codi == idioma.codi ? 'selected' : ''}>${idioma.nom}</option>
        </c:forEach>
    </select>

    <label>Estado:</label>
    <label>
        <input type="radio" name="publica" value="1" ${entrada.publica == 1 ? 'checked' : ''}> Pública
    </label>
    <label>
        <input type="radio" name="publica" value="0" ${entrada.publica == 0 ? 'checked' : ''}> Privada
    </label>

    <button type="submit" class="btn">${accio == 'editar' ? 'Actualizar' : 'Crear'}</button>
</form>

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
