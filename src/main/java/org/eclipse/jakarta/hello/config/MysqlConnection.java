package org.eclipse.jakarta.hello.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MysqlConnection {
    private Connection conn = null;

    private static MysqlConnection instance;//Aplicam patró Singleton -> private en lloc de public
    private MysqlConnection(){

        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            Properties propietats = new Properties();
            propietats.load(input);
            final String db = propietats.getProperty("mysql.db");
            final String host = propietats.getProperty("mysql.host"); //utilitzam docker amb contenidors a la mateixa xarxa, si fos en local sería localhost
            final String usuari = propietats.getProperty("mysql.usuari");
            final String password = propietats.getProperty("mysql.password");


            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+db, usuari, password);

            if (conn != null){
                System.out.println("Connectat a la BBDD ["+conn+"] - OK");
            } else {
                System.out.println("No funciona.");
            }

        } catch (Exception e){
            System.out.println("Error MySQL: " + e.getMessage());
        }
    }

    public Connection getConnexio(){
        return conn;
    }

    public void desconnexio(){
        System.out.println("Desconectant de la base de dades...");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e){
            System.out.println("No s'ha pogut tancar la base de dades.\nMotiu: "+e.getMessage());
        }
    }

    public static MysqlConnection getInstance(){ //Aplicam patró Singleton
        if (instance == null){
            instance = new MysqlConnection();
        }
        return instance;
    }
}