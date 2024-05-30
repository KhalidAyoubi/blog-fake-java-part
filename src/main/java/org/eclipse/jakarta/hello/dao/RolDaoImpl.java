package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Entrada;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            if (rol.getNom() == null){
                return null;
            }

            return rol;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Rol> getRols() throws SQLException {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM rol";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Rol> rols = new ArrayList<>();
            //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 usuari si existeix)
            while (resultSet.next()){
                Rol rol = new Rol();
                rol.setId(resultSet.getInt("idrol"));
                rol.setNom(resultSet.getString("nom"));

                rols.add(rol);
            }

            System.out.println("Resultat find rols: " + rols);

            return rols;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
