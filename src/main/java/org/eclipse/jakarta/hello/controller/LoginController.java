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
            System.out.println("------------------------------------------------------");
            System.out.println("                        LOGIN");
            System.out.println("------------------------------------------------------");
            //Recuepram usuari si existeix
            Usuari usuari = this.usuariService.login(username, password);

            //Si no existeix, tornam a login directament
            if (usuari == null){
                System.out.println("L'usuari "+ username +" no existeix o la contrassenya està malament");
                response.sendRedirect("login");
                return;
            }

            //Obtenim l'id del seu rol si en té
            Integer idRol = this.usuariHasRolService.usuarsRolByUsername(usuari);
            System.out.println("idRol val: " + idRol);

            //Assumint que l'usuari requereix tenir un rol si o sí, si no el té es redirigeix a login de nou
            if (idRol == null) {
                System.out.println("L'usuari "+ username +" no té el rol requerit");
                response.sendRedirect("login");
                return;
            }

            //Asignam rol en cas de que si en tengui
            usuari.setRol(this.rolService.getRolById(idRol));

            System.out.println("Usuari després d'asignar-li el rol" + usuari);

            boolean usuariHasRolNecessari = usuari.getRol().getNom().toUpperCase().equals("ADMINISTRADOR") || usuari.getRol().getNom().toUpperCase().equals("USUARI REGISTRAT");
            System.out.println("usuari te rol? " + usuariHasRolNecessari);
            if (usuariHasRolNecessari) {
                request.getSession().setAttribute("usuari", username);
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("login");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            response.sendRedirect("login");
        }
    }
}
