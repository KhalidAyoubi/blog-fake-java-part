package org.eclipse.jakarta.hello.model;

import lombok.Data;

public @Data class Usuari {
    private String username;
    private String email;
    private String nom;
    private String cognoms;
    private Rol rol;
}
