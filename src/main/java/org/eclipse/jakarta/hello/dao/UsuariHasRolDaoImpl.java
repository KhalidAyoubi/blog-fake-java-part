package org.eclipse.jakarta.hello.dao;

import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Rol;
import org.eclipse.jakarta.hello.model.Usuari;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuariHasRolDaoImpl implements UsuariHasRolDao {

    @Override
    public boolean setRolToUsuari(Usuari usuari, Rol rol) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO usuari_has_rol (usuari_username, rol_idrol) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setString(1, usuari.getUsername());
            preparedStatement.setInt(2, rol.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Integer usuarsRolByUsername(Usuari usuari) {
            try {
                MysqlConnection connection = MysqlConnection.getInstance();

                String sql = "SELECT * FROM usuari_has_rol WHERE usuari_username = ?";

                PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
                preparedStatement.setString(1, usuari.getUsername());

                ResultSet resultSet = preparedStatement.executeQuery();

                //Recorrem resultSet i cream els objectes (en teoría nomes ha de retornar 1 rol si existeix)
                Rol rol = new Rol();
                while (resultSet.next()){
                    rol.setId(resultSet.getInt("rol_idrol"));
                }

                System.out.println("Resultat de cerca de idrol d'usuari: " + rol.getId());
                if (rol.getId() == 0){
                    return null;
                }

                return rol.getId(); //Retornam l'id del rol asignat
            } catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
    }

    @Override
    public boolean updateRolToUsuari(Usuari usuari, Rol rol) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "UPDATE usuari_has_rol SET usuari_username = ?, rol_idrol = ? WHERE usuari_username = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setString(1, usuari.getUsername());
            preparedStatement.setInt(2, rol.getId());
            preparedStatement.setString(3, usuari.getUsername());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
