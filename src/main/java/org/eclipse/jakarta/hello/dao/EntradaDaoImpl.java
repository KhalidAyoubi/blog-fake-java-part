package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EntradaDaoImpl implements EntradaDao{
    @Override
    public boolean createEntrada(Entrada entrada/*, Idioma idioma*/) throws SQLException {
        boolean insertEntrada = insertEntrada(entrada);
        Entrada newEntrada = getEntradaByUsernameOrderedByDateLimit1(entrada);
        boolean insertEntradaIdioma = insertEntradaIdioma(entrada, newEntrada.getId(), entrada.getIdioma());

        return insertEntradaIdioma && insertEntrada;
    }

    private boolean insertEntrada(Entrada entrada) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO entrada (data, publica, autor) VALUES (?, ?, ?)";
            System.out.println("inserEntrada: " + sql);

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            Timestamp data = new Timestamp(entrada.getData().getTime());
            preparedStatement.setTimestamp(1, data);
            System.out.println("Data timestamp: " + data);
            preparedStatement.setInt(2, entrada.getPublica());
            preparedStatement.setString(3, entrada.getAutor().getUsername());


            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean insertEntradaIdioma(Entrada entrada, int entrada_id, Idioma idioma) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO entrada_has_idioma (entrada_id, idioma_ididioma, titol, descripcio) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setInt(1, entrada_id);
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
    public Entrada getEntradaByUsernameOrderedByDateLimit1(Entrada entradap) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM entrada WHERE autor = ? ORDER BY data DESC LIMIT 1;";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setString(1, entradap.getAutor().getUsername());

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
    public List<Entrada> getEntradaByIdioma(Idioma idiomaP) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM entrada_has_idioma where idioma_ididioma = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setInt(1, idiomaP.getIdidioma());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Entrada> entrades = new ArrayList<>();
            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            while (resultSet.next()){
                Entrada entrada = getEntradaById(resultSet.getInt("entrada_id"));

                entrades.add(entrada);
            }

            System.out.println("Resultat find entrada content: " + entrades);

            return entrades;
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
            System.out.println("updateEntrada en EntradaImpl: " + sql);

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            // Crear un formato de fecha/hora
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Dar formato a la fecha/hora
            String data = formatter.format(entrada.getData());

            preparedStatement.setString(1, data);
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
