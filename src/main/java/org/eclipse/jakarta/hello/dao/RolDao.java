package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Rol;

import java.sql.SQLException;
import java.util.List;

public interface RolDao {
    Rol getRolByName(String nom) throws SQLException;
    Rol getRolById(Integer id) throws SQLException;
    List<Rol> getRols() throws SQLException;
}
