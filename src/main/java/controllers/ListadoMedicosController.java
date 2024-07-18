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

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ListadoMedicosController implements Initializable {

    private final Stage stage = new Stage();
    ObservableList<Medico> medicosObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Medico> medicosTable;
    @FXML
    private TableColumn<Medico, Long> idColumn;
    @FXML
    private TableColumn<Medico, Long> numColegiadoColumn;
    @FXML
    private TableColumn<Medico, String> nombreColumn;
    @FXML
    private TableColumn<Medico, String> apellido1Column;
    @FXML
    private TableColumn<Medico, String> apellido2Column;
    @FXML
    private TableColumn<Medico, String> observacionesColumn;
    @FXML
    ContextMenu contextMenu = new ContextMenu();
    @FXML
    private AnchorPane anchorPane = new AnchorPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Medicians table
        medicosObservableList.addAll(DB.obtenerMedicos());

        if (medicosTable != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            numColegiadoColumn.setCellValueFactory(new PropertyValueFactory<>("numColegiado"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            apellido1Column.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
            apellido2Column.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
            observacionesColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

            medicosTable.setItems(medicosObservableList);

            addListeners();

        }
    }

    private void addListeners() {
        // Define the row factory for the table
        medicosTable.setRowFactory(tv -> {
            TableRow<Medico> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton().toString().equals("SECONDARY")) {
                    Medico rowData = row.getItem();
                    handleRowClick(event, rowData);
                } else {
                    contextMenu.hide();
                }
            });
            return row;
        });
    }

    private void handleRowClick(MouseEvent event, Medico medician) {
        MedicoController.setMedician(medician);

        // Crear las opciones del menú
        MenuItem option1 = new MenuItem("Modificar");
        MenuItem option2 = new MenuItem("Eliminar");

        // Añadir acciones a las opciones del menú
        option1.setOnAction(event1 -> {
            try {
                callMediciansModifyForm(event, medician);
            } catch (IOException e) {
                System.out.println("Error al abrir el formulario de modificación de médicos");
            }

        });
        option2.setOnAction(event1 -> callDeleteMedician(event));

        // Limpiar las opciones anteriores y añadir nuevas opciones
        contextMenu.getItems().clear();
        contextMenu.getItems().addAll(option1, option2);

        // Mostrar el menú contextual
        contextMenu.show(medicosTable, event.getScreenX(), event.getScreenY());

    }

    public void callMediciansModifyForm(MouseEvent mouseEvent, Medico medico) throws IOException {
        if (medico == null) {
            return;
        }
        MedicoController.setMedician(medico);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("mediciansModifyForm.fxml")));
        
        stage.setTitle("Modificar médico");
        stage.getIcons().add(new Image("images/logo-medicos.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    private void callDeleteMedician(MouseEvent event) {
        Medico medico = medicosTable.getSelectionModel().getSelectedItem();
        if (medico != null) {
            if (medico.eliminar()) {
                medicosObservableList.remove(medico);
            } else {
                System.out.println("Error al eliminar el médico");
            }
        }
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        // Clear all text fields in the form
        clearTextFields(medicosTable.getParent());
        // Uncheck all checkboxes in the form
        uncheckedAllCheckboxes(medicosTable.getParent());
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
        this.anchorPane = (AnchorPane) medicosTable.getParent().lookup("#anchorPane");

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

        medicosObservableList.clear();
        List<Medico> medicos = DB.obtenerMedicosFiltered(checkedMap);
        if (!medicos.isEmpty()) {
            medicosObservableList.addAll(medicos);
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

        MedicoController.setMedician(null);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("mediciansCreateForm.fxml")));
        stage.setTitle("Crear médico");
        stage.getIcons().add(new Image("images/logo-medicos.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();

    }

    public void callModifyForm(MouseEvent mouseEvent) {
        Medico medico = medicosTable.getSelectionModel().getSelectedItem();
        if (medico != null) {
            try {
                callMediciansModifyForm(mouseEvent, medico);
            } catch (IOException e) {
                System.out.println("Error al abrir el formulario de modificación de médicos");
            }
        }
    }

    public void callDelete(MouseEvent mouseEvent) {
        Medico medico = medicosTable.getSelectionModel().getSelectedItem();
        if (medico != null) {
            if (medico.eliminar()) {
                medicosObservableList.remove(medico);
            } else {
                System.out.println("Error al eliminar el médico");
            }
        }
    }
    
}
