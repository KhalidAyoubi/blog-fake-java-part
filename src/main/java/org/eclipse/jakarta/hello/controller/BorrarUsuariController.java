package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.UsuariDao;
import org.eclipse.jakarta.hello.dao.UsuariDaoImpl;
import org.eclipse.jakarta.hello.model.Usuari;
import org.eclipse.jakarta.hello.service.UsuariService;
import org.eclipse.jakarta.hello.service.UsuariServiceImpl;

import java.io.IOException;

@WebServlet(name = "borrarusuari", value = "/borrarusuari")
public class BorrarUsuariController extends HttpServlet {
    UsuariService usuariService;

    public BorrarUsuariController() {
        UsuariDao usuariDao = new UsuariDaoImpl();
        this.usuariService = new UsuariServiceImpl(usuariDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*        if (request.getSession().getAttribute("username") == null || !request.getSession().getAttribute("rol").equals("ADMINISTRADOR")) {
            response.sendRedirect("login");
        }*/
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*        if (request.getSession().getAttribute("username") == null || !request.getSession().getAttribute("rol").equals("ADMINISTRADOR")) {
            response.sendRedirect("login");
        }*/
        String username = request.getParameter("username");

        try {
            //existeix usuari?
            Usuari usuari = this.usuariService.findByUsername(username);

            if (usuari.getUsername() != null) {
                boolean isUsuariBorrat = this.usuariService.deteleUsuari(usuari);
                if (isUsuariBorrat){
                    System.out.println("S'ha borrat l'usuari " + usuari.getUsername());
                } else {
                    System.out.println("No s'ha borrat l'usuari " + usuari.getUsername());
                }
            }

            response.sendRedirect("register");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
