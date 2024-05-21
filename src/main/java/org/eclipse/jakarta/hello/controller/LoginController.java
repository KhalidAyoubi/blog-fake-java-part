package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jakarta.hello.dao.*;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;
import org.eclipse.jakarta.hello.service.*;

import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class LoginController extends HttpServlet {
    UsuariService usuariService;
    RolService rolService;
    UsuariHasRolService usuariHasRolService;

    public LoginController() {
        UsuariDao usuariDao = new UsuariDaoImpl();
        this.usuariService = new UsuariServiceImpl(usuariDao);

        RolDao rolDao = new RolDaoImpl();
        this.rolService = new RolServiceImpl(rolDao);

        UsuariHasRolDao usuariHasRolDao = new UsuariHasRolDaoImpl();
        this.usuariHasRolService = new UsuariHasRolServiceImpl(usuariHasRolDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            //Recuepram usuari si existeix
            Usuari usuari = this.usuariService.login(username, password);
            System.out.println("Usuari abans de cercar rol" + usuari);

            //Obtenim l'id del seu rol si en té
            Integer idRol = this.usuariHasRolService.usuarsRolByUsername(usuari);

            //Asignam rol en cas de que tengui
            if (idRol != null) {
            usuari.setRol(this.rolService.getRolById(idRol));
            }
            System.out.println("Usuari després de cercar rol" + usuari);

            boolean usuariHasRol = usuari.getRol().getNom().equals("ADMINISTRADOR") || usuari.getRol().getNom().equals("USUARIO REGISTRADO");
            System.out.println("usuari te rol? " + usuariHasRol);
            if (usuari != null && usuariHasRol) {
                request.getSession().setAttribute("usuari", username);
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
