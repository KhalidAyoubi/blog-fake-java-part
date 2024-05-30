package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.*;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.model.Usuari;
import org.eclipse.jakarta.hello.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "entrades", value = "/entrades")
public class EntradesController extends HttpServlet {
    EntradaService entradaService;
    UsuariService usuariService;
    UsuariHasRolService usuariHasRolService;
    RolService rolService;
    IdiomaService idiomaService;

    public EntradesController() {
        EntradaDao entradaDao = new EntradaDaoImpl();
        this.entradaService = new EntradaServiceImpl(entradaDao);

        UsuariDao usuariDao = new UsuariDaoImpl();
        this.usuariService = new UsuariServiceImpl(usuariDao);

        UsuariHasRolDao usuariHasRolDao = new UsuariHasRolDaoImpl();
        this.usuariHasRolService = new UsuariHasRolServiceImpl(usuariHasRolDao);

        RolDao rolDao = new RolDaoImpl();
        this.rolService = new RolServiceImpl(rolDao);

        IdiomaDao idiomaDao = new IdiomaDaoImpl();
        this.idiomaService = new IdiomaServiceImpl(idiomaDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
/*        if (request.getSession().getAttribute("username") == null || request.getSession().getAttribute("username").equals("")) {
            response.sendRedirect("login");
        }*/

        System.out.println("------------------------------------------------------");
        System.out.println("               ENTRADES CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");
        try {
            List<Entrada> entradas = this.entradaService.getEntradas();

            entradas.stream().map(entrada -> {
                try {
                    entrada.setAutor(this.usuariService.findByUsername(entrada.getAutor().getUsername()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                Integer idRol = this.usuariHasRolService.usuarsRolByUsername(entrada.getAutor());
                System.out.println("Autor idRol: " + idRol);
                if (idRol != null) {
                    try {
                        entrada.getAutor().setRol(this.rolService.getRolById(idRol));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                entrada.setIdioma(this.idiomaService.getIdioma(entrada.getIdioma().getIdidioma()));

                System.out.println("Entrda amb usuari i idioma| " + entrada);

                //Limitam el contingut a 100 caràcters per a la previsualització
                entrada.setDescripcio(entrada.getDescripcio().length() > 100 ? entrada.getDescripcio().substring(0, 100) + "..." : entrada.getDescripcio());

                return entrada;
            }).collect(Collectors.toList());

            request.setAttribute("entradas", entradas);

            //Retornam el rol que hi ha a la session de l'usuari
            request.setAttribute("rol", request.getSession().getAttribute("rol"));
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
