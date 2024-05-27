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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "crearentrada", value = "/crearentrada")
public class CrearEntradaController extends HttpServlet {
    EntradaService entradaService;
    UsuariService usuariService;
    UsuariHasRolService usuariHasRolService;
    RolService rolService;
    IdiomaService idiomaService;

    public CrearEntradaController() {
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
        System.out.println("          CREAR ENTRADA CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");

        try {
            //Ahora obtenemos todos los idiomas:
            List<Idioma> idiomas = this.idiomaService.getIdiomas();
            request.setAttribute("idiomas", idiomas);

            request.getRequestDispatcher("entradaForm.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("          CREAR ENTRADA CONTROLLER -- POST");
        System.out.println("------------------------------------------------------");

        request.setCharacterEncoding("UTF-8");


        String titol = request.getParameter("titol");

        String descripcio = request.getParameter("descripcio");

        String idiomaid = request.getParameter("idioma");
        int ididioma = 0;
        if (idiomaid.isEmpty()){
            ididioma = 1;
        } else {
            ididioma = Integer.parseInt(idiomaid);
        }
        Idioma idioma = this.idiomaService.getIdioma(ididioma);

        String publica = request.getParameter("publica");
        int intPublica = 0;
        if (!publica.isEmpty()){
            intPublica = Integer.parseInt(publica);
        }

        String username = request.getSession().getAttribute("username").toString();
        Usuari autor = null;
        try {
            autor = this.usuariService.findByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Obtener la fecha y hora actuales
        Date data = new Date();

        Entrada entrada = new Entrada();
        entrada.setData(data);
        entrada.setPublica(intPublica);
        entrada.setAutor(autor);
        entrada.setIdioma(idioma);
        entrada.setTitol(titol);
        entrada.setDescripcio(descripcio);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Dar formato a la fecha/hora
        String data2 = formatter.format(entrada.getData());

        System.out.println(titol + " " + descripcio + " " + idioma + " " + publica + " " + autor + " " + data);

        System.out.println("Titol: " + titol);
        System.out.println("Descripcio: " + descripcio);
        System.out.println("Idioma: " + idioma);
        System.out.println("Publica: " + publica);
        System.out.println("Autor: " + autor);
        System.out.println("Data: " + data);
        System.out.println("Data2: " + data2);

        System.out.println("Entrada completa: " + entrada);

        try {
            this.entradaService.createEntrada(entrada);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("entrades");
    }
}
