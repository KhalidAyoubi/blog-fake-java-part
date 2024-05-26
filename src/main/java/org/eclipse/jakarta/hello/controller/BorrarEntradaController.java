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

@WebServlet(name = "borrarentrada", value = "/borrarentrada")
public class BorrarEntradaController extends HttpServlet {
    EntradaService entradaService;

    public BorrarEntradaController() {
        EntradaDao entradaDao = new EntradaDaoImpl();
        this.entradaService = new EntradaServiceImpl(entradaDao);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("          BORRAR ENTRADA CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");
        response.sendRedirect("entrades");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("          BORRAR ENTRADA CONTROLLER -- POST");
        System.out.println("-------------------------------------------------------");

        try {
            String entrada_id = request.getParameter("id");
            if (entrada_id == null) {
                return;
            }

            int id = Integer.parseInt(entrada_id);

            try {
                Entrada entrada = this.entradaService.getEntradaById(id);
                if (entrada.getTitol() != null) {
                    this.entradaService.borrarEntrada(entrada);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            response.sendRedirect("entrades");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
