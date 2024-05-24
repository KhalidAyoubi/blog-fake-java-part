package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.dao.IdiomaDao;
import org.eclipse.jakarta.hello.model.Idioma;

import java.util.List;

public class IdiomaServiceImpl implements IdiomaService {
    IdiomaDao idiomaDao;
    public IdiomaServiceImpl(IdiomaDao idiomaDao) {
        this.idiomaDao = idiomaDao;
    }
    @Override
    public Idioma getIdioma(int ididioma) {
        return this.idiomaDao.getIdioma(ididioma);
    }

    @Override
    public List<Idioma> getIdiomas() {
        return this.idiomaDao.getIdiomas();
    }

    @Override
    public boolean createIdioma(Idioma idioma) {
        return this.idiomaDao.createIdioma(idioma);
    }

    @Override
    public boolean updateIdioma(Idioma idioma) {
        return this.idiomaDao.updateIdioma(idioma);
    }

    @Override
    public boolean deleteIdioma(Idioma idioma) {
        return this.idiomaDao.deleteIdioma(idioma);
    }
}
