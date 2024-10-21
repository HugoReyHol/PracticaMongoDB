package org.example.practicamongodb.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.practicamongodb.util.ConnectionDB;

import java.util.ArrayList;

public class TiposDAO {
    public static ArrayList<String> listarTipos() {
        MongoDatabase database = ConnectionDB.getCon().getDatabase("practicaMongo");

        MongoCollection<Document> collection = database.getCollection("tipos");

        Document tipos = collection.find().first();

        return (ArrayList<String>) tipos.get("tipos");

    }
}
