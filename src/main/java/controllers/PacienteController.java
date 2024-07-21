package controllers;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Diagnostico;
import models.Medico;
import models.Paciente;
import models.VisitaMedica;
import views.DiagnosticView;
import views.MedicianVisitView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PacienteController implements Initializable {

    private static Paciente paciente;

    @FXML
    public TableView<DiagnosticView> tableDiagnostics;

    @FXML
    public TableView<MedicianVisitView> tableMediciansVisits;

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

    @FXML
    private TableColumn<Diagnostico, String> codigoDiagnosticoColumn;

    @FXML
    private TableColumn<Diagnostico, String> fechaDiagnosticoColum;

    @FXML
    private TableColumn<Diagnostico, String> medicoDiagnosticoColumn;

    @FXML
    private TableColumn<VisitaMedica, String> medicoVisitaMedicaColumn;

    @FXML
    private TableColumn<VisitaMedica, LocalDate> fechaVisitaMedicaColumn;

    ObservableList<DiagnosticView> diagnosticosObservableList = FXCollections.observableArrayList();


    public static void setPaciente(Paciente paciente) {
        PacienteController.paciente = paciente;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (paciente != null) {
            // cargar datos generales del paciente
            loadPaciente();
            
            // cargar datos de diagnósticos del paciente
            loadDiagnostics();

            // cargar datos de visitas médicas del paciente
            loadMediciansVisits();
        }
        if (textId != null) {
            textId.setText(String.valueOf(paciente.getId()));
        }
    }

    private void loadPaciente() {
        // cargar datos generales del paciente
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

    private void loadDiagnostics() {
        try {
            // Configurar las fábricas de celdas (esto solo se hace una vez)
            codigoDiagnosticoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            fechaDiagnosticoColum.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            medicoDiagnosticoColumn.setCellValueFactory(new PropertyValueFactory<>("medico"));

            // Obtener los diagnósticos del paciente
            List<Diagnostico> diagnosticos = DB.obtenerDiagnosticosPaciente(paciente.getId());

            // Crear una lista de DiagnosticoView
            List<DiagnosticView> diagnosticosViewList = new ArrayList<>();
            for (Diagnostico diagnostico : diagnosticos) {
                Diagnostico diagnosticoAux = new Diagnostico(diagnostico.getId(), DB.getConnection());
                diagnosticoAux.inicializarDesdeBD();
                Medico medico = new Medico(diagnosticoAux.getIdMedico(), DB.getConnection());
                medico.inicializarDesdeBD();
                String fullNombre = medico.getNombre() + " " + medico.getApellido1() + " " + medico.getApellido2();
                diagnosticosViewList.add(new DiagnosticView(diagnosticoAux.getCodigo(), diagnosticoAux.getFecha().toString(), fullNombre));
            }

            // Asignar la lista de DiagnosticoView a la tabla
            diagnosticosObservableList.setAll(diagnosticosViewList);

            // Asignar la lista observable a la tabla
            tableDiagnostics.setItems(diagnosticosObservableList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadMediciansVisits() {
        try {
            // Configurar las fábricas de celdas (esto solo se hace una vez)
            medicoVisitaMedicaColumn.setCellValueFactory(new PropertyValueFactory<>("medico"));
            fechaVisitaMedicaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

            // Obtener las visitas médicas del paciente
            List<VisitaMedica> visitasMedicas = DB.obtenerVisitasMedicasPaciente(paciente.getId());

            // Crear una lista de MedicianVisitView
            List<MedicianVisitView> mediciansVisitsViewList = new ArrayList<>();
            for (VisitaMedica visitaMedica : visitasMedicas) {
                VisitaMedica visitaMedicaAux = new VisitaMedica(visitaMedica.getId(), DB.getConnection());
                visitaMedicaAux.inicializarDesdeBD();
                System.out.println("Medico: " + visitaMedicaAux.getIdMedico());
                Medico medico = new Medico(visitaMedicaAux.getIdMedico(), DB.getConnection());
                medico.inicializarDesdeBD();
                String fullNombre = medico.getNombre() + " " + medico.getApellido1() + " " + medico.getApellido2();
                mediciansVisitsViewList.add(new MedicianVisitView(visitaMedicaAux.getFecha().toString(), fullNombre));
            }

            // Asignar la lista de MedicianVisitView a la tabla
            ObservableList<MedicianVisitView> mediciansVisitsObservableList = FXCollections.observableArrayList(mediciansVisitsViewList);
            tableMediciansVisits.setItems(mediciansVisitsObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cleanPageText(MouseEvent mouseEvent) {
        // buscar el tabPane donde esta el boton que se presiono
        Node tabPane = ((Node) mouseEvent.getSource()).getParent().getParent();
        // limpiar todos los campos de texto en ese tabPane
        clearTextFields(tabPane);
    }

    private void clearTextFields(Node tabPane) {
        // limpiar todos los campos de texto en el tabPane
        for (Node node : tabPane.lookupAll(".text-field")) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
        }
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
