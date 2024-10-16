package org.example.practicamongodb.ctrll;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.bson.Document;
import org.example.practicamongodb.dao.CocheDAO;
import org.example.practicamongodb.model.Coche;
import org.example.practicamongodb.util.ConnectionDB;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class MenuCtrll implements Initializable {

    @FXML
    private TextField inMatricula, inMarca, inModelo;

    @FXML
    private ChoiceBox<String> inTipo;

    @FXML
    private TableView<Coche> tablaCoches;

    @FXML
    private TableColumn<Coche, String> colMatricula, colMarca, colModelo, colTipo;

    private final ObservableList<Coche> coches = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList();

    private MongoCollection<Document> collection;

    private Coche cocheCargado = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        ConnectionDB.conectar();

        coches.addAll(CocheDAO.listarCoches());

        tablaCoches.setItems(coches);


        // TODO collecion tipos con los distintos tipos
        ArrayList<String> t = new ArrayList<>(Arrays.asList("Deportivo", "Familiar", "TodoTerreno"));
        tipos.addAll(t);

        inTipo.setItems(tipos);

        // TODO comprobar si estan los tipos en la base de datos y si no agregarlos


    }


    public void onLimpiar(ActionEvent actionEvent) {
        inMatricula.setText("");
        inMarca.setText("");
        inModelo.setText("");
        inTipo.setValue("");
    }

    public void onGuardar(ActionEvent actionEvent) {
        Coche c = new Coche();

        c.setMarca(inMarca.getText());
        c.setMatricula(inMatricula.getText());
        c.setModelo(inModelo.getText());
        c.setTipo(inTipo.getValue());

        CocheDAO.guardarCoche(c);

        cocheCargado = c;

        coches.add(c);
        tablaCoches.refresh();

    }

    public void onActualizar(ActionEvent actionEvent) {
        cocheCargado = tablaCoches.getSelectionModel().getSelectedItem();

        if (cocheCargado == null) {
            return;
        }

        Coche c = new Coche();

        c.setMarca(inMarca.getText());
        c.setMatricula(inMatricula.getText());
        c.setModelo(inModelo.getText());
        c.setTipo(inTipo.getValue());

        CocheDAO.actualizarCoche(cocheCargado, c);

        coches.set(coches.indexOf(cocheCargado), c);

        tablaCoches.refresh();
        tablaCoches.getSelectionModel().select(c);


    }

    public void onEliminar(ActionEvent actionEvent) {
        if (cocheCargado == null){
            return;
        }

        CocheDAO.eliminarCoche(cocheCargado);

        coches.remove(cocheCargado);

        tablaCoches.refresh();

        cocheCargado = null;

        onLimpiar(actionEvent);

    }

    public void onClic(MouseEvent mouseEvent) {
        Coche c = tablaCoches.getSelectionModel().getSelectedItem();

        if (c == null){
            return;
        }

        inMatricula.setText(c.getMatricula());
        inMarca.setText(c.getMarca());
        inModelo.setText(c.getModelo());
        inTipo.setValue(c.getTipo());

        cocheCargado = c;
    }
}