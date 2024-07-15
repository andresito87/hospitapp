package controllers;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Medico;
import models.Paciente;

import java.net.URL;
import java.util.ResourceBundle;

public class ListadoPacientesController implements Initializable {
    
    private final Stage stage = new Stage();
    ObservableList<Paciente> pacientesObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Paciente> pacientesTable;
    @FXML
    private TableColumn<Paciente, Long> idColumn;
    @FXML
    private TableColumn<Paciente, String> ciColumn;
    @FXML
    private TableColumn<Paciente, String> fecNacColumn;
    @FXML
    private TableColumn<Paciente, Long> idCamaColumn;
    @FXML
    private TableColumn<Paciente, String> numSSColumn;
    @FXML
    private TableColumn<Paciente, String> nombreColumn;
    @FXML
    private TableColumn<Paciente, String> apellido1Column;
    @FXML
    private TableColumn<Paciente, String> apellido2Column;
    @FXML
    private TableColumn<Paciente, String> fecIngresoColumn;
    @FXML
    private TableColumn<Paciente, String> fecAltaColumn;
    @FXML
    private TableColumn<Paciente, String> observacionesColumn;
    @FXML
    ContextMenu contextMenu = new ContextMenu();
    @FXML
    private AnchorPane anchorPane = new AnchorPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Patients table
        pacientesObservableList.addAll(DB.obtenerPacientes());

        if (pacientesTable != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
           

            pacientesTable.setItems(pacientesObservableList);

            addListeners();

        }
    }

    private void addListeners() {
    }

    public void closeWindow(MouseEvent mouseEvent) {
        
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        // Clear all text fields in the form
        clearTextFields(pacientesTable.getParent());
        // Uncheck all checkboxes in the form
        uncheckedAllCheckboxes(pacientesTable.getParent());
    }

    private void clearTextFields(Parent root) {
        // Clear all text fields in the form
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof Parent) {
                clearTextFields((Parent) node);
            }
        }
    }

    private void uncheckedAllCheckboxes(Parent root) {
        // Uncheck all checkboxes in the form
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
            if (node instanceof Parent) {
                uncheckedAllCheckboxes((Parent) node);
            }
        }
    }
}
