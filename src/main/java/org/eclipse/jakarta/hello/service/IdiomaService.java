package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.dao.IdiomaDao;
import org.eclipse.jakarta.hello.model.Idioma;

import java.util.List;

public interface IdiomaService {
    Idioma getIdioma(int ididioma);
    List<Idioma> getIdiomas();
    boolean createIdioma(Idioma idioma);
    boolean updateIdioma(Idioma idioma);
    boolean deleteIdioma(Idioma idioma);
}
