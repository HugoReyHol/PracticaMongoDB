/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.practicamongodb.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.util.Properties;


public class ConnectionDB {
    private static MongoClient con;

    public static boolean conectar()
    {
  
        try {
            Properties configuration = new Properties();
            configuration.load(R.getProperties("database.properties"));
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");
            
            final MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://"+username+":"+password+"@"+host+":"+port+"/?authSource=admin"));
            
            System.out.println("Conectada correctamente a la BD");

            con = conexion;

            return true;
        }
        catch (Exception e) {
            System.out.println("Conexion Fallida");
            System.out.println(e);
            return false;
        }
    }
    public static void desconectar(MongoClient con)
    {
        con.close();
    }

    public static MongoClient getCon() {
        return con;
    }
}
