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

@WebServlet(name = "updateusuari", value = "/updateusuari")
public class UpdateUsuariController extends HttpServlet {
    UsuariService usuariService;
    RolService rolService;
    UsuariHasRolService usuariHasRolService;

    public UpdateUsuariController() {
        UsuariDao usuariDao = new UsuariDaoImpl();
        this.usuariService = new UsuariServiceImpl(usuariDao);

        RolDao rolDao = new RolDaoImpl();
        this.rolService = new RolServiceImpl(rolDao);

        UsuariHasRolDao usuariHasRolDao = new UsuariHasRolDaoImpl();
        this.usuariHasRolService = new UsuariHasRolServiceImpl(usuariHasRolDao);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("       ACTUALIZAR USUARIO CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");
        response.sendRedirect("register");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username") != null ? request.getParameter("username") : "";
        String email = request.getParameter("email") != null ? request.getParameter("email") : "";
        String nom = request.getParameter("nom") != null ? request.getParameter("nom") : "";
        String cognoms = request.getParameter("cognoms") != null ? request.getParameter("cognoms") : "";
        int rolid = request.getParameter("rol") != null ? Integer.parseInt(request.getParameter("rol")) : 0;

        //Si algún camp es null, regirigeix a /register
        if (username.isEmpty() || email.isEmpty() || nom.isEmpty() || cognoms.isEmpty() || rolid == 0) {
            response.sendRedirect("register");
            return;
        }

        //Existeix l'usuari?
        Usuari existeixUsuari = null;
        try {
            existeixUsuari = this.usuariService.findByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Si no existeix, es retorna a /register
        if (existeixUsuari.getUsername() == null) {
            response.sendRedirect("register");
            return;
        }

        //Rol existeix?
        Rol rol = null;
        try {
            rol = this.rolService.getRolById(rolid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Si no existeix, es retorna a /register
        if (rol == null) {
            response.sendRedirect("register");
            return;
        }

        Usuari usuari = new Usuari();
        usuari.setUsername(username);
        usuari.setEmail(email);
        usuari.setNom(nom);
        usuari.setCognoms(cognoms);

        try {
            boolean actualitzarUsuari = this.usuariService.update(usuari);
            if (actualitzarUsuari) {
                this.usuariHasRolService.updateRolToUsuari(usuari, rol);
            }

            response.sendRedirect("register");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

