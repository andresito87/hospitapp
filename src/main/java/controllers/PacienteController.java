package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Paciente;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PacienteController implements Initializable {

    private static Paciente paciente;

    @FXML
    private TextField textId;
    
    @FXML
    private TextField textDni;

    @FXML
    private TextField textCi;

    @FXML
    private TextField textFecNac;

    @FXML
    private TextField textIdCama;

    @FXML
    private TextField textNumSegSocial;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textApellido1;

    @FXML
    private TextField textApellido2;

    @FXML
    private TextField textFecIngreso;

    @FXML
    private TextField textFecAlta;

    @FXML
    private TextField textObservaciones;

    public static void setPaciente(Paciente paciente) {
        PacienteController.paciente = paciente;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (paciente != null) {
            textId.setText(String.valueOf(paciente.getId()));
            textDni.setText(paciente.getDni());
            textCi.setText(paciente.getCi());
            textFecNac.setText(paciente.getFechaNacimiento().toString());
            textIdCama.setText(String.valueOf(paciente.getIdCama()));
            textNumSegSocial.setText(paciente.getNumSegSocial());
            textNombre.setText(paciente.getNombre());
            textApellido1.setText(paciente.getApellido1());
            textApellido2.setText(paciente.getApellido2());
            textFecIngreso.setText(paciente.getFechaIngreso().toString());
            textFecAlta.setText(paciente.getFechaAlta() == null ? "" : paciente.getFechaAlta().toString());
            textObservaciones.setText(paciente.getObservaciones());
        }
        if (textId != null) {
            textId.setText(String.valueOf(paciente.getId()));
        }
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        textCi.setText("");
        textFecNac.setText("");
        textIdCama.setText("");
        textNumSegSocial.setText("");
        textNombre.setText("");
        textApellido1.setText("");
        textApellido2.setText("");
        textFecIngreso.setText("");
        textFecAlta.setText("");
        textObservaciones.setText("");
    }

    public void callUpdate(MouseEvent mouseEvent) {
        if (textCi.getText().isEmpty()
                || textFecNac.getText().isEmpty()
                || textIdCama.getText().isEmpty()
                || textNumSegSocial.getText().isEmpty()
                || textNombre.getText().isEmpty()
                || textApellido1.getText().isEmpty()
                || textApellido2.getText().isEmpty()
                || textFecIngreso.getText().isEmpty()
                || textFecAlta.getText().isEmpty()
                || textObservaciones.getText().isEmpty()) {

            System.out.println("Error: Campos vacíos");
            return;
        }
        try {
            paciente.setCi(textCi.getText());
            paciente.setFechaNacimiento(LocalDate.parse(textFecNac.getText()));
            paciente.setIdCama(Integer.parseInt(textIdCama.getText()));
            paciente.setNumSeguridadSocial(textNumSegSocial.getText());
            paciente.setNombre(textNombre.getText());
            paciente.setApellido1(textApellido1.getText());
            paciente.setApellido2(textApellido2.getText());
            paciente.setFechaIngreso(LocalDate.parse(textFecIngreso.getText()));
            paciente.setFechaAlta(LocalDate.parse(textFecAlta.getText()));
            paciente.setObservaciones(textObservaciones.getText());
        } catch (Exception e) {
            System.out.println("Error: Información incorrecta");
            return;
        }

        if (paciente.modificar()) {
            System.out.println("Medico modificado correctamente");
        }
    }


    public void callCreate(MouseEvent mouseEvent) {
        if (textCi.getText().isEmpty()
                || textFecNac.getText().isEmpty()
                || textIdCama.getText().isEmpty()
                || textNumSegSocial.getText().isEmpty()
                || textNombre.getText().isEmpty()
                || textApellido1.getText().isEmpty()
                || textApellido2.getText().isEmpty()
                || textFecIngreso.getText().isEmpty()
                || textFecAlta.getText().isEmpty()
                || textObservaciones.getText().isEmpty()) {
            System.out.println("Error: Campos vacíos");
            return;
        }
        try {
            paciente.setCi(textCi.getText());
            paciente.setFechaNacimiento(LocalDate.parse(textFecNac.getText()));
            paciente.setIdCama(Integer.parseInt(textIdCama.getText()));
            paciente.setNumSeguridadSocial(textNumSegSocial.getText());
            paciente.setNombre(textNombre.getText());
            paciente.setApellido1(textApellido1.getText());
            paciente.setApellido2(textApellido2.getText());
            paciente.setFechaIngreso(LocalDate.parse(textFecIngreso.getText()));
            paciente.setFechaAlta(LocalDate.parse(textFecAlta.getText()));
            paciente.setObservaciones(textObservaciones.getText());
        } catch (Exception e) {
            System.out.println("Error: Información incorrecta");
            return;
        }

        if (paciente.agregar()) {
            System.out.println("Medico creado correctamente");
        }
    }


}
