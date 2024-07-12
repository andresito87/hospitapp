package controllers;

import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Medico;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static database.DB.saveMedician;

public class MedicoController implements Initializable {
    private final Stage stage = new Stage();
    private static Medico medician;

    @FXML
    private TextField textId;
    
    @FXML
    private TextField textNumColegiado;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textApellido1;

    @FXML
    private TextField textApellido2;

    @FXML
    private TextField textObservaciones;
    
    public static void setMedician(Medico medician) {
        System.out.println("Medician: " + medician);
        MedicoController.medician = medician;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textId.setText(String.valueOf(medician.getId()));
        textId.setDisable(true);
        textNumColegiado.setText(String.valueOf(medician.getNumColegiado()));
        textNombre.setText(medician.getNombre());
        textApellido1.setText(medician.getApellido1());
        textApellido2.setText(medician.getApellido2());
        textObservaciones.setText(medician.getObservaciones());
    }


    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) textId.getScene().getWindow();
        stage.close();
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        textNumColegiado.setText("");
        textNombre.setText("");
        textApellido1.setText("");
        textApellido2.setText("");
        textObservaciones.setText("");
    }

    public void callSave(MouseEvent mouseEvent) {
        
        if(textNumColegiado.getText().isEmpty() || textNombre.getText().isEmpty() || textApellido1.getText().isEmpty() || textApellido2.getText().isEmpty()) {
            System.out.println("Error: Campos vacíos");
            return;
        }
        try {
        medician.setNumColegiado(Long.parseLong(textNumColegiado.getText()));
        medician.setNombre(textNombre.getText());
        medician.setApellido1(textApellido1.getText());
        medician.setApellido2(textApellido2.getText());
        medician.setObservaciones(textObservaciones.getText());
        } catch (Exception e) {
            System.out.println("Error: Información incorrecta");
            return;
        }
        
        if(saveMedician(medician)) {
            System.out.println("Medician saved");
        } else {
            System.out.println("Error saving medician");
        }
    }

  

}
