package org.eclipse.jakarta.hello.model;

import lombok.Data;

import java.util.Date;

public @Data class Entrada {
    private int id;
    private Date data;
    private int publica;
    private Usuari autor;
    private Idioma idioma;
    private String titol;
    private String descripcio;
}
