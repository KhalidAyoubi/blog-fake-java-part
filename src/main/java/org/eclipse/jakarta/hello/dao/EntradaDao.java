package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Entrada;

import java.sql.SQLException;
import java.util.List;

public interface EntradaDao {
    boolean createEntrada(Entrada entrada) throws SQLException;
    List<Entrada> getEntradas() throws SQLException;
    Entrada getEntradaById(int id) throws SQLException;
    Entrada updateEntrada(Entrada entrada) throws SQLException;
    boolean borrarEntrada(int id) throws SQLException;
}
