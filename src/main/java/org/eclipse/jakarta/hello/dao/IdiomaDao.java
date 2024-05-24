package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.model.Idioma;

import java.util.List;

public interface IdiomaDao {
    Idioma getIdioma(int ididioma);
    List<Idioma> getIdiomas();
    boolean createIdioma(Idioma idioma);
    boolean updateIdioma(Idioma idioma);
    boolean deleteIdioma(Idioma idioma);
}
