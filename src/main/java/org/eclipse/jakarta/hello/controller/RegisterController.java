package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.*;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;
import org.eclipse.jakarta.hello.service.*;

import java.io.IOException;

@WebServlet(name = "register", value = "/register")

public class RegisterController extends HttpServlet {
    UsuariService usuariService;
    RolService rolService;
    UsuariHasRolService usuariHasRolService;

    public RegisterController() {
        UsuariDao usuariDao = new UsuariDaoImpl();
        this.usuariService = new UsuariServiceImpl(usuariDao);

        RolDao rolDao = new RolDaoImpl();
        this.rolService = new RolServiceImpl(rolDao);

        UsuariHasRolDao usuariHasRolDao = new UsuariHasRolDaoImpl();
        this.usuariHasRolService = new UsuariHasRolServiceImpl(usuariHasRolDao);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.getRequestDispatcher("register.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String nom = request.getParameter("nom");
        String cognoms = request.getParameter("cognoms");

        Usuari usuari = new Usuari();
        usuari.setUsername(username);
        usuari.setEmail(email);
        usuari.setNom(nom);
        usuari.setCognoms(cognoms);

        String rolName = request.getParameter("rol");

        try {
            //Si rol existeix, es crea l'usuari
            Rol rol = this.rolService.getRolByName(rolName);
            Usuari usuariExisteix = this.usuariService.findByUsername(username);

            if (usuariExisteix.getUsername() == null){
                System.out.println("Usuari no exiteix");
            }

            if (rol != null && usuariExisteix.getUsername() == null) {
                boolean isRegistrat = this.usuariService.register(usuari, password);

                if (isRegistrat) { //Si es registre l'usuari correctament, li assignam rol
                        this.usuariHasRolService.setRolToUsuari(usuari, rol);
                        response.sendRedirect("login.jsp");

                } else {
                    response.sendRedirect("register.jsp");
                }
            } else {
                response.sendRedirect("register.jsp");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

