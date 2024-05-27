package org.eclipse.jakarta.hello.controller;

import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ACMPEQ;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        System.out.println("          EDITAR ENTRADA CONTROLLER -- GET");
        System.out.println("------------------------------------------------------");

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        System.out.println("entrada_id: " + id);

        try {
            if (id == null) {
                response.sendRedirect("entrades");
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

            //Ahora obtenemos todos los idiomas:
            List<Idioma> idiomas = this.idiomaService.getIdiomas();
            request.setAttribute("idiomas", idiomas);

            //acción: En este caso es editar
            request.setAttribute("accio", "editar");
            request.getRequestDispatcher("entradaForm.jsp").forward(request,response);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("------------------------------------------------------");
        System.out.println("          EDITAR ENTRADA CONTROLLER -- POST");
        System.out.println("------------------------------------------------------");

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        int entrada_id = 0;
        if (id.isEmpty()){
            response.sendRedirect("entradas");
            return;
        } else {
            entrada_id = Integer.parseInt(id);
        }

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

        String username = request.getParameter("autor");
        Usuari autor = null;
        try {
            autor = this.usuariService.findByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Obtener la fecha y hora actuales
        Date data = new Date();
        // Definir el formato deseado
        //DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Formatear la fecha y hora
        //String data = dataAra.format(formatterData);

        Entrada entrada = new Entrada();
        entrada.setId(entrada_id);
        entrada.setData(data);
        entrada.setPublica(intPublica);
        entrada.setAutor(autor);
        entrada.setIdioma(idioma);
        entrada.setTitol(titol);
        entrada.setDescripcio(descripcio);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Dar formato a la fecha/hora
        String data2 = formatter.format(entrada.getData());

        System.out.println(id + " " + titol + " " + descripcio + " " + idioma + " " + publica + " " + autor + " " + data);

        System.out.println("Id: " + id);
        System.out.println("Titol: " + titol);
        System.out.println("Descripcio: " + descripcio);
        System.out.println("Idioma: " + idioma);
        System.out.println("Publica: " + publica);
        System.out.println("Autor: " + autor);
        System.out.println("Data: " + data);
        System.out.println("Data2: " + data2);

        System.out.println("Entrada completa: " + entrada);

        try {
            this.entradaService.updateEntrada(entrada);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("login");
    }
}
