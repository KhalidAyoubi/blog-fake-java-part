package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolDaoImpl implements RolDao {
    @Override
    public Rol getRolByName(String nom) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM rol WHERE nom = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setString(1, nom);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 rol si existeix)
            Rol rol = new Rol();
            while (resultSet.next()){
                rol.setId(resultSet.getInt("idrol"));
                rol.setNom(resultSet.getString("nom"));
            }

            System.out.println("Resultat get rol by name: " + rol);

            return rol;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Rol getRolById(Integer id) throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM rol WHERE idrol = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 rol si existeix)
            Rol rol = new Rol();
            while (resultSet.next()){
                rol.setId(resultSet.getInt("idrol"));
                rol.setNom(resultSet.getString("nom"));
            }

            System.out.println("Resultat get rol by id: " + rol);

            return rol;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
