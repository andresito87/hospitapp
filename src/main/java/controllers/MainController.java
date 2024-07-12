package controllers;

import database.DB;
import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MainController  {
    private final Stage stage = new Stage();
    
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
        stage.setScene(new Scene(root, 1000, 600));
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
