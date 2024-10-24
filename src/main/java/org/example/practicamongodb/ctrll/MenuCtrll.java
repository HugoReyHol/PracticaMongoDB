package org.example.practicamongodb.ctrll;


import javafx.application.Platform;
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
import org.example.practicamongodb.dao.CocheDAO;
import org.example.practicamongodb.dao.TiposDAO;
import org.example.practicamongodb.model.Coche;
import org.example.practicamongodb.util.AlertUtil;
import org.example.practicamongodb.util.ConnectionDB;
import org.example.practicamongodb.util.CreadorTablas;
import java.net.URL;
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

    private Coche cocheCargado = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        if (!ConnectionDB.conectar()) Platform.exit();

        CreadorTablas.crearTablas();

        coches.addAll(CocheDAO.listarCoches());
        tablaCoches.setItems(coches);

        tipos.addAll(TiposDAO.listarTipos());
        inTipo.setItems(tipos);

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

        if (!CocheDAO.guardarCoche(c)) {
            AlertUtil.mostrarInfo("Esa matricula ya está asignada");
            return;
        }

        cocheCargado = c;

        coches.add(c);

    }

    public void onActualizar(ActionEvent actionEvent) {
        if (cocheCargado == null) {
            return;
        }

        Coche c = new Coche();

        c.setMarca(inMarca.getText());
        c.setMatricula(inMatricula.getText());
        c.setModelo(inModelo.getText());
        c.setTipo(inTipo.getValue());

        if (!CocheDAO.actualizarCoche(cocheCargado, c)) {
            AlertUtil.mostrarInfo("Esa matricula ya está asignada");
            return;
        }

        c.set_id(cocheCargado.get_id());

        coches.set(coches.indexOf(cocheCargado), c);

        tablaCoches.getSelectionModel().select(c);

        cocheCargado = c;
    }

    public void onEliminar(ActionEvent actionEvent) {
        if (cocheCargado == null){
            return;
        }

        CocheDAO.eliminarCoche(cocheCargado);

        coches.remove(cocheCargado);

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

        System.out.println(c.get_id());
    }


}