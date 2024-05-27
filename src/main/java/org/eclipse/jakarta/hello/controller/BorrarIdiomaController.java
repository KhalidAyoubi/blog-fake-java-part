package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.EntradaDao;
import org.eclipse.jakarta.hello.dao.EntradaDaoImpl;
import org.eclipse.jakarta.hello.dao.IdiomaDao;
import org.eclipse.jakarta.hello.dao.IdiomaDaoImpl;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.service.EntradaService;
import org.eclipse.jakarta.hello.service.EntradaServiceImpl;
import org.eclipse.jakarta.hello.service.IdiomaService;
import org.eclipse.jakarta.hello.service.IdiomaServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "borraridioma", value = "/borraridioma")
public class BorrarIdiomaController extends HttpServlet {

    IdiomaService idiomaService;
    EntradaService entradaService;

    public BorrarIdiomaController() {
        IdiomaDao idiomaDao = new IdiomaDaoImpl();
        this.idiomaService = new IdiomaServiceImpl(idiomaDao);

        EntradaDao entradaDao = new EntradaDaoImpl();
        this.entradaService = new EntradaServiceImpl(entradaDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("username") == null || !request.getSession().getAttribute("rol").equals("ADMINISTRADOR")) {
            response.sendRedirect("login");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("username") == null || !request.getSession().getAttribute("rol").equals("ADMINISTRADOR")) {
            response.sendRedirect("login");
        }
        request.setCharacterEncoding("UTF-8");
        System.out.println("------------------------------------------------------");
        System.out.println("          BORRAR IDIOMA CONTROLLER -- POST");
        System.out.println("------------------------------------------------------");

        String ididioma = request.getParameter("ididioma");


        Idioma idioma = new Idioma();
        System.out.println("IdIdioma en controller: " + ididioma);
        int idIdioma = 0;
        if (ididioma != null) {
            idIdioma = Integer.parseInt(ididioma);
            idioma.setIdidioma(Integer.parseInt(ididioma));
        } else {
            response.sendRedirect("idioma");
            return;
        }

        //Existeix l'idioma?
        Idioma exiteixIdioma = this.idiomaService.getIdioma(idIdioma);

        if (exiteixIdioma == null) { //Si no existeix l'idioma, redorigeix a /idioma
            response.sendRedirect("idioma");
            return;
        }

        try {
            //Primero obtengo TODAS las entradas del idioma a borrar
            List<Entrada> entradas = this.entradaService.getEntradaByIdioma(idioma);

            boolean entradaBorrada = this.idiomaService.deleteIdioma(idioma);

            if (entradaBorrada) { //Si se ha borrado el idioma, borramos todas las entradas que lo tengan
                entradas.forEach( entrada -> {
                    try {
                        this.entradaService.borrarEntrada(entrada);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            response.sendRedirect("idioma");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

