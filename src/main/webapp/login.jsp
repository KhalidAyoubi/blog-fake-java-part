<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlogKhalid - LOGIN</title>
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

        form {
            width: 500px;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .btn:hover {
            background-color: #45a049;
        }

        /* Estilos para los campos de texto y contraseña */
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        /* Estilos para el título */
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <h1>Iniciar sesión</h1>
    <form action="login" method="POST">
        <label for="username">Usuario:</label>
        <input id="username" type="text" name="username" required>
        <label for="password">Contraseña:</label>
        <input id="password" type="password" name="password" required>
        <button type="submit" class="btn">Iniciar sesión</button>
    </form>
</body>
</html>
