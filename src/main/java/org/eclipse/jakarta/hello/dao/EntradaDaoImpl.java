package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Entrada;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EntradaDaoImpl implements EntradaDao{
    @Override
    public boolean createEntrada(Entrada entrada) throws SQLException {
        boolean insertEntrada = insertEntrada(entrada);
        boolean insertEntradaIdioma = insertEntrada(entrada);

        return false;
    }

    private boolean insertEntrada(Entrada entrada) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO entrada (data, publica, autor) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setDate(1, (Date) entrada.getData());
            preparedStatement.setInt(2, entrada.getPublica());
            preparedStatement.setString(3, entrada.getAutor().getUsername());


            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Entrada> getEntradas() throws SQLException {
        return List.of();
    }

    @Override
    public Entrada getEntradaById(int id) throws SQLException {
        return null;
    }

    @Override
    public Entrada updateEntrada(Entrada entrada) throws SQLException {
        return null;
    }

    @Override
    public boolean borrarEntrada(int id) throws SQLException {
        return false;
    }
}
