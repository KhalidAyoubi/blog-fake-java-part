package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.*;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.service.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "editarentrada", value = "/editarentrada")
public class EditarEntradaController extends HttpServlet {
    EntradaService entradaService;
    UsuariService usuariService;
    UsuariHasRolService usuariHasRolService;
    RolService rolService;
    IdiomaService idiomaService;

    public EditarEntradaController() {
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
        System.out.println("------------------------------------------------------");
        System.out.println("               ENTRADA CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");

        String id = request.getParameter("id");
        System.out.println("entrada_id: " + id);

        try {
            if (id == null) {
                response.sendRedirect("entradas");
            }
            assert id != null;
            int entrada_id = Integer.parseInt(id);
            System.out.println("entrada_id: " + entrada_id);
            Entrada entrada = this.entradaService.getEntradaById(entrada_id);
            System.out.println("entrada: " + entrada);
            try {
                entrada.setAutor(this.usuariService.findByUsername(entrada.getAutor().getUsername()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("entrada amb autor: " + entrada);

            Integer idRol = this.usuariHasRolService.usuarsRolByUsername(entrada.getAutor());
            System.out.println("Autor idRol: " + idRol);
            if (idRol != null) {
                try {
                    entrada.getAutor().setRol(this.rolService.getRolById(idRol));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("entrada amb rol: " + entrada);

            entrada.setIdioma(this.idiomaService.getIdioma(entrada.getIdioma().getIdidioma()));

            System.out.println("Entrda amb usuari i idioma| " + entrada);

            request.setAttribute("entrada", entrada);
            request.getRequestDispatcher("entrada.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
