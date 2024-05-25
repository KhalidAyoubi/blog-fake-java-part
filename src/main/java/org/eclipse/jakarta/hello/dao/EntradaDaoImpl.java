package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntradaDaoImpl implements EntradaDao{
    @Override
    public boolean createEntrada(Entrada entrada, Idioma idioma) throws SQLException {
        boolean insertEntrada = insertEntrada(entrada);
        Entrada newEntrada = getEntradaByDate(entrada.getData());
        boolean insertEntradaIdioma = insertEntradaIdioma(newEntrada, idioma);

        if (insertEntradaIdioma && insertEntrada) {
            return true;
        }

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

    private boolean insertEntradaIdioma(Entrada entrada, Idioma idioma) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO entrada_has_idioma (entrada_id, idioma_ididioma, titol, descripcio) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setInt(1, entrada.getId());
            preparedStatement.setInt(2, idioma.getIdidioma());
            preparedStatement.setString(3, entrada.getTitol());
            preparedStatement.setString(4, entrada.getDescripcio());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Entrada getEntradaByDate(java.util.Date data) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM entrada WHERE data = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setDate(1, (Date) data);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            Entrada entrada = new Entrada();
            while (resultSet.next()){
                entrada.setId(resultSet.getInt("id"));
                entrada.setData(resultSet.getDate("data"));
                entrada.setPublica(resultSet.getInt("publica"));
            }

            System.out.println("Resultat find entrada by data: " + entrada);

            return entrada;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public List<Entrada> getEntradas() throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM entrada";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Entrada> entradas = new ArrayList<>();
            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            while (resultSet.next()){
                Entrada entrada = new Entrada();
                entrada.setId(resultSet.getInt("id"));
                entrada.setData(resultSet.getDate("data"));
                entrada.setPublica(resultSet.getInt("publica"));

                Usuari usuari = new Usuari();
                usuari.setUsername(resultSet.getString("autor"));
                entrada.setAutor(usuari);

                Entrada entradaContent = getEntradaContent(entrada);

                entrada.setTitol(entradaContent.getTitol());
                entrada.setDescripcio(entradaContent.getDescripcio());

                Idioma idioma = new Idioma();
                idioma.setIdidioma(entradaContent.getIdioma().getIdidioma());
                entrada.setIdioma(idioma);

                entradas.add(entrada);
            }

            System.out.println("Resultat find entrades: " + entradas);

            return entradas;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }



    @Override
    public Entrada getEntradaById(int id) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM entrada where id = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            Entrada entrada = new Entrada();
            while (resultSet.next()){
                entrada.setId(resultSet.getInt("id"));
                entrada.setData(resultSet.getDate("data"));
                entrada.setPublica(resultSet.getInt("publica"));

                Usuari usuari = new Usuari();
                usuari.setUsername(resultSet.getString("autor"));
                entrada.setAutor(usuari);

                Entrada entradaContent = getEntradaContent(entrada);

                entrada.setTitol(entradaContent.getTitol());
                entrada.setDescripcio(entradaContent.getDescripcio());

                Idioma idioma = new Idioma();
                idioma.setIdidioma(entradaContent.getIdioma().getIdidioma());
                entrada.setIdioma(idioma);

            }

            System.out.println("Resultat find entrada by id: " + entrada);

            return entrada;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean borrarEntrada(Entrada entrada) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "DELETE FROM entrada where id = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setInt(1, entrada.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Entrada getEntradaContent(Entrada entradaid) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM entrada_has_idioma where entrada_id = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setInt(1, entradaid.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            Entrada entrada = new Entrada();
            while (resultSet.next()){
                entrada.setId(resultSet.getInt("entrada_id"));
                //entrada.setData(resultSet.getDate("data"));
                //entrada.setPublica(resultSet.getInt("publica"));
                entrada.setTitol(resultSet.getString("titol"));
                entrada.setDescripcio(resultSet.getString("descripcio"));

                //Usuari usuari = new Usuari();
                //usuari.setUsername(resultSet.getString("autor"));
                //entrada.setAutor(usuari);

                Idioma idioma = new Idioma();
                idioma.setIdidioma(resultSet.getInt("idioma_ididioma"));
                entrada.setIdioma(idioma);
            }

            System.out.println("Resultat find entrada content: " + entrada);

            return entrada;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateEntrada(Entrada entrada) throws SQLException {
        boolean updateEntradaInfo = this.updateEntradaInfo(entrada);
        boolean updateEntradaContent = this.updateEntradaContent(entrada);

        if (updateEntradaInfo && updateEntradaContent){
            return true;
        }

        return false;
    }

    private boolean updateEntradaInfo(Entrada entrada){
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "UPDATE entrada SET data = ?, publica = ?, autor = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setDate(1, (Date) entrada.getData());
            preparedStatement.setInt(2, entrada.getPublica());
            preparedStatement.setString(3, entrada.getAutor().getUsername());
            preparedStatement.setInt(4, entrada.getId());


            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean updateEntradaContent(Entrada entrada) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "UPDATE entrada_has_idioma SET idioma_ididioma = ?, titol = ?, descripcio = ? WHERE entrada_id = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setInt(1, entrada.getIdioma().getIdidioma());
            preparedStatement.setString(2, entrada.getTitol());
            preparedStatement.setString(3, entrada.getDescripcio());
            preparedStatement.setInt(4, entrada.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
