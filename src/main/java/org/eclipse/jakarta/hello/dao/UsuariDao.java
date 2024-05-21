package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.SQLException;
import java.util.List;

public interface UsuariDao {
    boolean register(Usuari usuari, String password) throws SQLException;
    Usuari login(String username, String password) throws SQLException;
    Usuari findByUsername(String username) throws SQLException;
    List<Usuari> findAll() throws SQLException;
    boolean deteleUsuari(Usuari usuari) throws SQLException;
}
