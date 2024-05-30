package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;

public interface UsuariHasRolDao {
    boolean setRolToUsuari(Usuari usuari, Rol rol);
    Integer usuarsRolByUsername(Usuari usuari);
    boolean updateRolToUsuari(Usuari usuari, Rol rol);
}
