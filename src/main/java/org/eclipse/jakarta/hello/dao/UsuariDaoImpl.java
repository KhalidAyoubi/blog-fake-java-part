package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariDaoImpl implements UsuariDao{

    @Override
    public boolean register(Usuari usuari,String password) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO usuari (username, password, email, nom, cognoms) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setString(1, usuari.getUsername());
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, usuari.getEmail());
            preparedStatement.setString(4, usuari.getNom());
            preparedStatement.setString(5, usuari.getCognoms());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Usuari login(String username, String password) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM usuari WHERE username = ? AND password = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            Usuari usuari = new Usuari();
            while (resultSet.next()){
                usuari.setUsername(resultSet.getString("username"));
                usuari.setEmail(resultSet.getString("email"));
                usuari.setNom(resultSet.getString("nom"));
                usuari.setCognoms(resultSet.getString("cognoms"));
            }

            System.out.println("Resultat login: " + usuari);

            return usuari;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Usuari findByUsername(String username) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM usuari WHERE username = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            Usuari usuari = new Usuari();
            while (resultSet.next()){
                usuari.setUsername(resultSet.getString("username"));
                usuari.setEmail(resultSet.getString("email"));
                usuari.setNom(resultSet.getString("nom"));
                usuari.setCognoms(resultSet.getString("cognoms"));
            }

            System.out.println("Resultat find by username: " + usuari);

            return usuari;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
