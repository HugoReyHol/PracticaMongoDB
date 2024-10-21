package org.example.practicamongodb.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.example.practicamongodb.model.Coche;
import org.example.practicamongodb.util.ConnectionDB;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class CocheDAO {

    public static boolean guardarCoche(Coche coche) {
        MongoCollection<Document> collection = ConnectionDB.getCon().getDatabase("practicaMongo").getCollection("coches");

        Gson gson = new Gson();

        int cantidad;
        try {
            cantidad = (int) collection.find().sort(descending("_id")).first().get("_id");
            cantidad++;

        } catch (Exception e) {
            cantidad = 0;

        }

        coche.set_id(cantidad);

        Document find = collection.find(eq("matricula", coche.getMatricula())).first();

        if (find != null) return false;

        Document c = Document.parse(gson.toJson(coche));

        collection.insertOne(c);

        return true;

    }

    public static boolean actualizarCoche(Coche cocheAntiguo, Coche cocheNuevo) {
        MongoCollection<Document> collection = ConnectionDB.getCon().getDatabase("practicaMongo").getCollection("coches");

        Gson gson = new Gson();

        Document filtro = new Document("matricula", new Document("$eq", cocheNuevo.getMatricula()))
                .append("_id", new Document("$ne", cocheAntiguo.get_id()));

        // eq("matricula", cocheNuevo.getMatricula())
        // new Document("$eq", cocheNuevo.getMatricula()).append("$ne", String.valueOf(cocheAntiguo.get_id()))
        try (MongoCursor<Document> f = collection.find(filtro).cursor()) {
            System.out.println(f);
            //if (f.hasNext()) f.next();
            if (f.hasNext()) return false;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Document cA = Document.parse(gson.toJson(cocheAntiguo));
        Document cN = Document.parse(gson.toJson(cocheNuevo));

//      collection.replaceOne(cA, cN);

        // TODO cambiar forma de update para que no aplique a _id
        Document acuatizar = new Document("$set", cN);

        collection.updateOne(cA, acuatizar);

        return true;

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

        Gson gson = new Gson();

        try (MongoCursor<Document> listaCoches = collection.find().iterator()) {
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
