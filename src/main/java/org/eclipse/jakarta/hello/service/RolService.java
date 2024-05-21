package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.model.Rol;

import java.sql.SQLException;

public interface RolService {
    Rol getRolByName(String nom) throws SQLException;
    Rol getRolById(Integer id) throws SQLException;
}
