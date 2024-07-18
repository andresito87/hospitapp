package controllers;

import app.Main;
import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Medico;
import models.Paciente;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private TableColumn<Paciente, String> numSegSocialColumn;
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
            ciColumn.setCellValueFactory(new PropertyValueFactory<>("ci"));
            fecNacColumn.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
            idCamaColumn.setCellValueFactory(new PropertyValueFactory<>("idCama"));
            numSegSocialColumn.setCellValueFactory(new PropertyValueFactory<>("numSegSocial"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            apellido1Column.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
            apellido2Column.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
            fecIngresoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
            fecAltaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
            observacionesColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

            pacientesTable.setItems(pacientesObservableList);

            addListeners();

        }
    }

    private void addListeners() {
        // Define the row factory for the table
        pacientesTable.setRowFactory(tv -> {
            TableRow<Paciente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton().toString().equals("SECONDARY")) {
                    Paciente rowData = row.getItem();
                    handleRowClick(event, rowData);
                } else {
                    contextMenu.hide();
                }
            });
            return row;
        });
    }

    private void handleRowClick(MouseEvent event, Paciente paciente) {
        PacienteController.setPaciente(paciente);

        // Crear las opciones del menú
        MenuItem option1 = new MenuItem("Modificar");
        MenuItem option2 = new MenuItem("Eliminar");

        // Añadir acciones a las opciones del menú
        option1.setOnAction(event1 -> {
            callPatientsModifyForm(event, paciente);
        });
        option2.setOnAction(event1 -> callDeletePatient(event));

        // Limpiar las opciones anteriores y añadir nuevas opciones
        contextMenu.getItems().clear();
        contextMenu.getItems().addAll(option1, option2);

        // Mostrar el menú contextual
        contextMenu.show(pacientesTable, event.getScreenX(), event.getScreenY());

    }

    private void callPatientsModifyForm(MouseEvent mouseEvent, Paciente paciente) {
        if (paciente == null) {
            return;
        }
        try {
            PacienteController.setPaciente(paciente);
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("patientsModifyForm.fxml")));

            stage.setTitle("Modificar paciente");
            stage.getIcons().add(new Image("images/logo-pacientes.jpeg"));
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Error al abrir el formulario de modificación de pacientes");
        }

    }

    private void callDeletePatient(MouseEvent event) {
        Paciente paciente = pacientesTable.getSelectionModel().getSelectedItem();
        if (paciente != null) {
            if (paciente.eliminar()) {
                pacientesObservableList.remove(paciente);
            } else {
                System.out.println("Error al eliminar el médico");
            }
        }
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

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void callFilterInfoTable() {
        Map<String, String> checkedMap = new HashMap<>();

        // obtener el anchorPane
        this.anchorPane = (AnchorPane) pacientesTable.getParent().lookup("#anchorPane");

        // Recorrer todos los nodos hijos del AnchorPane
        anchorPane.getChildren().forEach(node -> {
            if (node instanceof CheckBox checkBox) {
                if (checkBox.isSelected()) {
                    String checkBoxId = checkBox.getId();
                    TextField associatedTextField = getTextFieldById("text" + checkBoxId);
                    if (associatedTextField != null) {
                        checkedMap.put(checkBoxId, associatedTextField.getText());
                    }
                }
            }
        });

        // hay algun checkbox seleccionado sin texto
        if (checkedMap.containsValue("")) {
            //TODO: mostrar mensaje de error en un label
            System.out.println("Hay algún campo vacío");
            return;
        }

        pacientesObservableList.clear();
        List<Paciente> pacientes = DB.obtenerPacientesFiltered(checkedMap);
        if (!pacientes.isEmpty()) {
            pacientesObservableList.addAll(pacientes);
        }

    }

    private TextField getTextFieldById(String id) {
        for (javafx.scene.Node node : anchorPane.getChildren()) {
            if (node instanceof TextField && node.getId().equals(id)) {
                return (TextField) node;
            }
        }
        return null;
    }

    public void callCreateForm(MouseEvent mouseEvent) throws IOException {
        PacienteController.setPaciente(null);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("patientsCreateForm.fxml")));
        stage.setTitle("Crear paciente");
        stage.getIcons().add(new Image("images/logo-pacientes.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public void callModifyForm(MouseEvent mouseEvent) {
        Paciente paciente = pacientesTable.getSelectionModel().getSelectedItem();
        if (paciente != null) {
            callPatientsModifyForm(mouseEvent, paciente);
        }
    }

    public void callDelete(MouseEvent mouseEvent) {
        Paciente paciente = pacientesTable.getSelectionModel().getSelectedItem();
        if (paciente != null) {
            if (paciente.eliminar()) {
                pacientesObservableList.remove(paciente);
            } else {
                System.out.println("Error al eliminar el paciente");
            }
        }
    }

}
