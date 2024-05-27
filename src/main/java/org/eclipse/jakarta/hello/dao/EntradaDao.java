package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;

import java.sql.SQLException;
import java.util.List;

public interface EntradaDao {
    boolean createEntrada(Entrada entrada/*, Idioma idioma*/) throws SQLException;
    List<Entrada> getEntradas() throws SQLException;
    Entrada getEntradaById(int id) throws SQLException;
    Entrada getEntradaByUsernameOrderedByDateLimit1(Entrada entrada) throws SQLException;
    boolean updateEntrada(Entrada entrada) throws SQLException;
    boolean borrarEntrada(Entrada entrada) throws SQLException;

    Entrada getEntradaContent(Entrada entrada) throws SQLException;

    List<Entrada> getEntradaByIdioma(Idioma idioma) throws SQLException;
}
