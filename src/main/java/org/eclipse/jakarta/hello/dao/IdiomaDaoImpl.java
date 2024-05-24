package org.eclipse.jakarta.hello.dao;

import jakarta.persistence.Id;
import org.eclipse.jakarta.hello.config.MysqlConnection;
import org.eclipse.jakarta.hello.model.Idioma;
import org.eclipse.jakarta.hello.model.Rol;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IdiomaDaoImpl implements IdiomaDao{
    @Override
    public Idioma getIdioma(int ididioma) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM idioma WHERE ididioma = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);
            preparedStatement.setInt(1, ididioma);

            ResultSet resultSet = preparedStatement.executeQuery();

            Idioma idioma = new Idioma();
            while (resultSet.next()){
                idioma.setIdidioma(resultSet.getInt("ididioma"));
                idioma.setCodi(resultSet.getString("codi"));
                idioma.setNom(resultSet.getString("nom"));
                idioma.setDefecte(resultSet.getInt("defecte"));

            }

            System.out.println("Resultat getIdiomaById: " + idioma);
            if (idioma.getNom() == null){
                return null;
            }

            return idioma;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Idioma> getIdiomas() {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "SELECT * FROM idioma";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Idioma> idiomas = new ArrayList<>();
            while (resultSet.next()){
            Idioma idioma = new Idioma();
                idioma.setIdidioma(resultSet.getInt("ididioma"));
                idioma.setCodi(resultSet.getString("codi"));
                idioma.setNom(resultSet.getString("nom"));
                idioma.setDefecte(resultSet.getInt("defecte"));

                idiomas.add(idioma);
            }

            System.out.println("Resultat getAllIdiomas: " + idiomas);

            return idiomas;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean createIdioma(Idioma idioma) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "INSERT INTO idioma (codi, nom, defecte) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setString(1, idioma.getCodi());
            preparedStatement.setString(2, idioma.getNom());
            preparedStatement.setInt(3, idioma.getDefecte());
            System.out.println("CREAR: " + sql);
            System.out.println(idioma.getCodi() + " " + idioma.getNom() + " " + idioma.getDefecte());
            System.out.println("*********");

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateIdioma(Idioma idioma) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "UPDATE idioma SET codi = ?, nom = ?, defecte = ? WHERE ididioma = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setString(1, idioma.getCodi());
            preparedStatement.setString(2, idioma.getNom());
            preparedStatement.setInt(3, idioma.getDefecte());
            preparedStatement.setInt(4, idioma.getIdidioma());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteIdioma(Idioma idioma) {
        try {
            MysqlConnection connection = MysqlConnection.getInstance();

            String sql = "DELETE FROM idioma where ididioma = ?";

            PreparedStatement preparedStatement = connection.getConnexio().prepareStatement(sql);

            preparedStatement.setInt(1, idioma.getIdidioma());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
