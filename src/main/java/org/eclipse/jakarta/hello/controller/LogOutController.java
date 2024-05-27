package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jakarta.hello.dao.*;
import org.eclipse.jakarta.hello.model.Usuari;
import org.eclipse.jakarta.hello.service.*;

import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogOutController extends HttpServlet {


    public LogOutController() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("_________________________________________--");
        System.out.println("            LOG OUT doGet");
        System.out.println("_________________________________________--");
        try {
            // Obté la sesió actual
            HttpSession session = request.getSession(false);
            if (session != null) {
                System.out.println("LOG OUT");
                System.out.println(session);
                // Invalida la sesió si existeix
                session.invalidate();
            }

            response.sendRedirect("entrades");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
