package org.eclipse.jakarta.hello.service;

import org.eclipse.jakarta.hello.dao.EntradaDao;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;

import java.sql.SQLException;
import java.util.List;

public class EntradaServiceImpl implements EntradaService {
    EntradaDao entradaDao;
    public EntradaServiceImpl(EntradaDao entradaDao) {
        this.entradaDao = entradaDao;
    }
    @Override
    public boolean createEntrada(Entrada entrada/*, Idioma idioma*/) throws SQLException {
        return this.entradaDao.createEntrada(entrada/*,idioma*/);
    }

    @Override
    public List<Entrada> getEntradas() throws SQLException {
        return this.entradaDao.getEntradas();
    }

    @Override
    public Entrada getEntradaById(int id) throws SQLException {
        return this.entradaDao.getEntradaById(id);
    }

    @Override
    public Entrada getEntradaByUsernameOrderedByDateLimit1(Entrada entrada) throws SQLException {
        return this.entradaDao.getEntradaByUsernameOrderedByDateLimit1(entrada);
    }

    @Override
    public boolean updateEntrada(Entrada entrada) throws SQLException {
        return this.entradaDao.updateEntrada(entrada);
    }

    @Override
    public boolean borrarEntrada(Entrada entrada) throws SQLException {
        return this.entradaDao.borrarEntrada(entrada);
    }

    @Override
    public Entrada getEntradaContent(Entrada entrada) throws SQLException {
        return this.entradaDao.getEntradaContent(entrada);
    }

    @Override
    public List<Entrada> getEntradaByIdioma(Idioma idioma) throws SQLException {
        return this.entradaDao.getEntradaByIdioma(idioma);
    }
}
