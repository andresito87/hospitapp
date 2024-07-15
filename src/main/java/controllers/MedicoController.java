package controllers;

import database.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Medico;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicoController implements Initializable {
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
        if (medician != null) {
            textNumColegiado.setText(String.valueOf(medician.getNumColegiado()));
            textNombre.setText(medician.getNombre());
            textApellido1.setText(medician.getApellido1());
            textApellido2.setText(medician.getApellido2());
            textObservaciones.setText(medician.getObservaciones());
        }
        if (textId != null) {
            textId.setText(String.valueOf(medician.getId()));
        }
    }


    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        textNumColegiado.setText("");
        textNombre.setText("");
        textApellido1.setText("");
        textApellido2.setText("");
        textObservaciones.setText("");
    }

    public void callUpdate(MouseEvent mouseEvent) {

        if (textNumColegiado.getText().isEmpty() || textNombre.getText().isEmpty() || textApellido1.getText().isEmpty() || textApellido2.getText().isEmpty()) {
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

        if (medician.modificar()) {
            System.out.println("Medico modificado correctamente");
        }
    }


    public void callCreate(MouseEvent mouseEvent) {
        if (textNumColegiado.getText().isEmpty() || textNombre.getText().isEmpty() || textApellido1.getText().isEmpty() || textApellido2.getText().isEmpty()) {
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

        if (medician.agregar()) {
            System.out.println("Medico creado correctamente");
        }
    }
}
