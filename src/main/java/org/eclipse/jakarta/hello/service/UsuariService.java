package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.SQLException;
import java.util.List;

public interface UsuariService {
    boolean register(Usuari usuari, String password) throws SQLException;
    Usuari login(String username, String password) throws SQLException;
    Usuari findByUsername(String username) throws SQLException;
    public List<Usuari> findAll() throws SQLException;
    boolean deteleUsuari(Usuari usuari) throws SQLException;
    boolean update(Usuari usuari) throws SQLException;
}
