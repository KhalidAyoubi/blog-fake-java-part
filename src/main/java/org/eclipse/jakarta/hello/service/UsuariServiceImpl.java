package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.dao.UsuariDao;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.SQLException;

public class UsuariServiceImpl implements UsuariService {
    private final UsuariDao usuariDao;
    public UsuariServiceImpl(UsuariDao usuariDao) {
        this.usuariDao = usuariDao;
    }
    @Override
    public boolean register(Usuari usuari, String password) throws SQLException {
        return this.usuariDao.register(usuari, password);
    }

    @Override
    public Usuari login(String username, String password) throws SQLException {
        return this.usuariDao.login(username, password);
    }

    @Override
    public Usuari findByUsername(String username) throws SQLException {
        return this.usuariDao.findByUsername(username);
    }
}
