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
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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

        if (request.getSession().getAttribute("username") == null || request.getSession().getAttribute("rol") != "ADMINISTRADOR") {
            response.sendRedirect("login");
        }

        try {
            List<Usuari> usuaris = this.usuariService.findAll();
            usuaris.stream().map(usuari -> {
                Integer idRol = this.usuariHasRolService.usuarsRolByUsername(usuari);
                System.out.println("Register idRol: " + idRol);
                if (idRol != null) {
                    try {
                        usuari.setRol(this.rolService.getRolById(idRol));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return usuari;
            }).collect(Collectors.toList());
            request.setAttribute("usuaris", usuaris);
            request.getRequestDispatcher("register.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("username") == null || request.getSession().getAttribute("rol") != "ADMINISTRADOR") {
            response.sendRedirect("login");
        }

        request.setCharacterEncoding("UTF-8");
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

            if (rol != null && usuariExisteix.getUsername() == null) { //Si el rol exiteix, pero l'usuari no existeix el registram
                boolean isRegistrat = this.usuariService.register(usuari, password);

                if (isRegistrat) { //Si es registre l'usuari correctament, li assignam rol
                        this.usuariHasRolService.setRolToUsuari(usuari, rol);
                        //response.sendRedirect("login");
                }
            }

            response.sendRedirect("register");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

