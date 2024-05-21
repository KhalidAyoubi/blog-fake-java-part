package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.SQLException;

public interface UsuariService {
    boolean register(Usuari usuari, String password) throws SQLException;
    Usuari login(String username, String password) throws SQLException;
    Usuari findByUsername(String username) throws SQLException;
}
