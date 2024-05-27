package org.eclipse.jakarta.hello.controller;

import jakarta.faces.component.ActionSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.*;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;
import org.eclipse.jakarta.hello.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "idioma", value = "/idioma")
public class IdiomaController extends HttpServlet {

    IdiomaService idiomaService;

    public IdiomaController() {
        IdiomaDao idiomaDao = new IdiomaDaoImpl();
        this.idiomaService = new IdiomaServiceImpl(idiomaDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("username") == null || request.getSession().getAttribute("username").equals("")) {
            response.sendRedirect("login");
        }

        System.out.println("------------------------------------------------------");
        System.out.println("               IDIOMA CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");
        try {
            List<Idioma> idiomas = this.idiomaService.getIdiomas();
            request.setAttribute("idiomas", idiomas);

            //Retornam el rol que hi ha a la session de l'usuari
            request.setAttribute("rol", request.getSession().getAttribute("rol"));
            request.getRequestDispatcher("idioma.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("username") == null || request.getSession().getAttribute("username").equals("")) {
            response.sendRedirect("login");
        }

        request.setCharacterEncoding("UTF-8");
        System.out.println("------------------------------------------------------");
        System.out.println("               IDIOMA CONTROLLER -- POST");
        System.out.println("------------------------------------------------------");
        String action = request.getParameter("action");

        String ididioma = request.getParameter("ididioma");
        String codi = request.getParameter("codi");
        String nom = request.getParameter("nom");
        String defecte = request.getParameter("defecte");

        Idioma idioma = new Idioma();
        System.out.println("IdIdioma en controller: " + ididioma);
        if (ididioma != null) {
            idioma.setIdidioma(Integer.parseInt(ididioma));
        }
        System.out.println("IdIdiomaIdioma en controller: " + idioma.getIdidioma());
        idioma.setCodi(codi);
        System.out.println("Codi en controller: " + idioma.getCodi());
        idioma.setNom(nom);
        System.out.println("Nom en controller: " + nom);
        System.out.println("Defecte en controller: " + defecte);
        if (!defecte.isEmpty()) {
            idioma.setDefecte(Integer.parseInt(defecte));
        }
        System.out.println("DefecteIdioma en controller: " + idioma.getDefecte());

        System.out.println("Crear:: " + idioma.getNom());
        boolean campoNull = nom.isEmpty() || codi.isEmpty();
        System.out.println(campoNull);
        boolean camposNull = nom.isEmpty() && codi.isEmpty();
        System.out.println(camposNull);

        try {
            switch (action) {
                case "editar":
                    System.out.println("------------------------------------------------------");
                    System.out.println("                        EDITAR");
                    System.out.println("------------------------------------------------------");
                    this.idiomaService.updateIdioma(idioma);
                    break;
                case "crear":
                    if (campoNull || camposNull) {
                        break;
                    }

                    System.out.println("------------------------------------------------------");
                    System.out.println("                        CREAR");
                    System.out.println("------------------------------------------------------");
                default:
                    this.idiomaService.createIdioma(idioma);
            }

            response.sendRedirect("idioma");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

