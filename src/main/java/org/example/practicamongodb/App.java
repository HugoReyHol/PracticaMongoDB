package org.example.practicamongodb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.practicamongodb.util.ConnectionDB;
import org.example.practicamongodb.util.R;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu coches");
        stage.setScene(scene);
        stage.setOnCloseRequest(_ -> ConnectionDB.desconectar());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}