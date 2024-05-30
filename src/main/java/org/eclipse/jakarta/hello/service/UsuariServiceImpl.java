package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.dao.UsuariDao;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.SQLException;
import java.util.List;

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

    @Override
    public List<Usuari> findAll() throws SQLException {
        return this.usuariDao.findAll();
    }

    @Override
    public boolean deteleUsuari(Usuari usuari) throws SQLException {
        return this.usuariDao.deteleUsuari(usuari);
    }

    @Override
    public boolean update(Usuari usuari) throws SQLException {
        return this.usuariDao.update(usuari);
    }
}
