package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.dao.RolDao;
import org.eclipse.jakarta.hello.dao.RolDaoImpl;
import org.eclipse.jakarta.hello.model.Rol;

import java.sql.SQLException;

public class RolServiceImpl implements RolService {
    RolDao rolDao;
    public RolServiceImpl(RolDao rolDao) {
        this.rolDao = rolDao;
    }

    @Override
    public Rol getRolByName(String nom) throws SQLException {
        return this.rolDao.getRolByName(nom);
    }

    @Override
    public Rol getRolById(Integer id) throws SQLException{
        return this.rolDao.getRolById(id);
    }
}
