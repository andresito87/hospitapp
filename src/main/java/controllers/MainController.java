package controllers;

import database.DB;
import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController  implements Initializable {
    
    private final Stage stage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DB.connect();
    }
    
    @FXML
    protected void closeApp() {
        DB.closeConnection();
        System.out.println("Closing application...");
        System.exit(0);
    }
    
    public void callPatientsMantForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("patientsMantForm.fxml")));
        stage.setTitle("Listado de Pacientes");
        stage.getIcons().add(new Image("images/logo-pacientes.jpeg"));
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }

    public void callMediciansMantForm(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("mediciansMantForm.fxml")));
            stage.setTitle("Mantenimiento de Medicos");
            stage.getIcons().add(new Image("images/logo-medicos.jpeg"));
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
    }
    
}
