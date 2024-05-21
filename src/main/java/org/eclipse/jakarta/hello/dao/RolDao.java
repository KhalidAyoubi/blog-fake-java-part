package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Rol;

import java.sql.SQLException;

public interface RolDao {
    Rol getRolByName(String nom) throws SQLException;
    Rol getRolById(Integer id) throws SQLException;
}
