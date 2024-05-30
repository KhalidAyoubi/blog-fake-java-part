package org.eclipse.jakarta.hello.service;


import org.eclipse.jakarta.hello.dao.UsuariHasRolDao;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;

public class UsuariHasRolServiceImpl implements UsuariHasRolService {
    UsuariHasRolDao usuariHasRolDao;

    public UsuariHasRolServiceImpl(UsuariHasRolDao usuariHasRolDao) {
        this.usuariHasRolDao = usuariHasRolDao;
    }

    @Override
    public boolean setRolToUsuari(Usuari usuari, Rol rol) {
        return this.usuariHasRolDao.setRolToUsuari(usuari, rol);
    }

    @Override
    public Integer usuarsRolByUsername(Usuari usuari) {
        return this.usuariHasRolDao.usuarsRolByUsername(usuari);
    }

    @Override
    public boolean updateRolToUsuari(Usuari usuari, Rol rol) {
        return this.usuariHasRolDao.updateRolToUsuari(usuari, rol);
    }
}
