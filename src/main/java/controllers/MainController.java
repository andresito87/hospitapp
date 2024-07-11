package controllers;

import Database.DB;
import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Medico;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    private final Stage stage = new Stage();
    ObservableList<Medico> medicosObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Medico> mediciansTable;
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
    protected void closeApp() {
        DB.closeConnection();
        System.out.println("Closing application...");
        System.exit(0);
    }

    @FXML
    protected void callMediciansMantForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("mediciansMantForm.fxml")));
        stage.setTitle("Médicos");
        stage.getIcons().add(new Image("images/logo-medicos.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    @FXML
    protected void callPatientsMantForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("patientsMantForm.fxml")));
        stage.setTitle("Pacientes");
        stage.getIcons().add(new Image("images/logo-pacientes.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        if (stage.getScene() != null && stage.getScene().getRoot() != null) {
            clearTextFields(stage.getScene().getRoot());
        }
    }

    private void clearTextFields(Parent root) {
        ObservableList<Node> children = root.getChildrenUnmodifiable();
        for (Node node : children) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            } else if (node instanceof Parent) {
                clearTextFields((Parent) node); // recursively clear TextFields in child nodes
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DB.connect();
        medicosObservableList.addAll(DB.obtenerMedicos());


        if (mediciansTable != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            numColegiadoColumn.setCellValueFactory(new PropertyValueFactory<>("numColegiado"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            apellido1Column.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
            apellido2Column.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
            observacionesColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

            mediciansTable.setItems(medicosObservableList);

            // Add mouse listener to each cell in the table
            addListeners();

        }
    }

    public void callMediciansModifyForm(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("mediciansModifyForm.fxml")));
        stage.setTitle("Modificar médico");
        stage.getIcons().add(new Image("images/logo-medicos.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }


    private void addListeners() {
        // Define the row factory for the table
        mediciansTable.setRowFactory(tv -> {
            TableRow<Medico> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Medico rowData = row.getItem();
                    handleRowClick(event, rowData);
                }
            });
            return row;
        });
    }

    private void handleRowClick(MouseEvent event, Medico medician) {
        MedicoController.setMedician(medician);
        // Open a new window with the selected medician data
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("mediciansModifyForm.fxml")));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Modificar médico");
        stage.getIcons().add(new Image("images/logo-medicos.jpeg"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();

    }

}
