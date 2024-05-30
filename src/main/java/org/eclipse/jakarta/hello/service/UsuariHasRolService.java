package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;

public interface UsuariHasRolService {
    boolean setRolToUsuari(Usuari usuari, Rol rol);
    Integer usuarsRolByUsername(Usuari usuari);
    boolean updateRolToUsuari(Usuari usuari, Rol rol);
}
