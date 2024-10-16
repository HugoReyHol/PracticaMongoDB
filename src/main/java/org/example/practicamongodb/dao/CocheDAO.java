package org.example.practicamongodb.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.example.practicamongodb.model.Coche;
import org.example.practicamongodb.util.ConnectionDB;

import java.util.ArrayList;

public class CocheDAO {

    public static void guardarCoche(Coche coche) {
        MongoCollection<Document> collection = ConnectionDB.getCon().getDatabase("practicaMongo").getCollection("coches");

        Gson gson = new Gson();

        Document c = Document.parse(gson.toJson(coche));

        collection.insertOne(c);

    }

    public static void actualizarCoche(Coche cocheAntiguo, Coche cocheNuevo) {
        MongoCollection<Document> collection = ConnectionDB.getCon().getDatabase("practicaMongo").getCollection("coches");

        Gson gson = new Gson();

        Document cA = Document.parse(gson.toJson(cocheAntiguo));
        Document cN = Document.parse(gson.toJson(cocheNuevo));

        collection.replaceOne(cA, cN);

    }

    public static void eliminarCoche(Coche coche) {
        MongoCollection<Document> collection = ConnectionDB.getCon().getDatabase("practicaMongo").getCollection("coches");

        Gson gson = new Gson();

        Document c = Document.parse(gson.toJson(coche));

        collection.deleteOne(c);

    }

    public static ArrayList<Coche> listarCoches() {
        ArrayList<Coche> coches = new ArrayList<>();

        MongoCollection<Document> collection = ConnectionDB.getCon().getDatabase("practicaMongo").getCollection("coches");

        try (MongoCursor<Document> listaCoches = collection.find().iterator()) {
            Gson gson = new Gson();

            while (listaCoches.hasNext()) {
                Coche c = gson.fromJson(listaCoches.next().toJson(), Coche.class);
                coches.add(c);

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }

        return coches;
    }
}
