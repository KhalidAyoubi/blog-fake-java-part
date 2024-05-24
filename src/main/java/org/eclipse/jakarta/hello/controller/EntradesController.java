package org.eclipse.jakarta.hello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.dao.EntradaDao;
import org.eclipse.jakarta.hello.dao.EntradaDaoImpl;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.service.EntradaService;
import org.eclipse.jakarta.hello.service.EntradaServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "entrades", value = "/entrades")
public class EntradesController extends HttpServlet {
    EntradaService entradaService;
    public EntradesController() {
        EntradaDao entradaDao = new EntradaDaoImpl();
        this.entradaService = new EntradaServiceImpl(entradaDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("               ENTRADES CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");
        try {
            List<Entrada> entradas = this.entradaService.getEntradas();
            request.setAttribute("entradas", entradas);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
