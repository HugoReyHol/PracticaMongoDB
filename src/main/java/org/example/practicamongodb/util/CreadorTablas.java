package org.example.practicamongodb.util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CreadorTablas {

    public static void crearTablas() {
        MongoDatabase database = ConnectionDB.getCon().getDatabase("practicaMongo");

        database.createCollection("coches");
        MongoCollection<Document> collection = database.getCollection("tipos");
        collection.drop();

        database.createCollection("tipos");

        String[] listaTipos = new String[]{"Familiar", "Deportivo", "Todo Terreno"};

        collection.insertOne(new Document("tipos", new ArrayList<>(List.of(listaTipos))));

    }

    public static ArrayList<String> listarTipos() {
        MongoDatabase database = ConnectionDB.getCon().getDatabase("practicaMongo");
        
        MongoCollection<Document> collection = database.getCollection("tipos");
        
        Document tipos = collection.find().first();
        
        return (ArrayList<String>) tipos.get("tipos");

    }

}
