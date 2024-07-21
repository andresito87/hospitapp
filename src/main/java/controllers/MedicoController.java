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
import views.DiagnosticWithPatientsView;
import views.MedicianVisitWithMediciansView;
import views.MedicianVisitWithPatientsView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MedicoController implements Initializable {
    private static Medico medician;

    @FXML
    public TableView<DiagnosticWithPatientsView> tableDiagnostics;

    @FXML
    public TableView<MedicianVisitWithPatientsView> tableMediciansVisits;

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

    @FXML
    private TableColumn<Diagnostico, String> codigoDiagnosticoColumn;

    @FXML
    private TableColumn<Diagnostico, String> fechaDiagnosticoColumn;

    @FXML
    private TableColumn<Diagnostico, String> pacienteDiagnosticoColumn;

    @FXML
    private TableColumn<VisitaMedica, String> pacienteVisitaMedicaColumn;

    @FXML
    private TableColumn<VisitaMedica, LocalDate> fechaVisitaMedicaColumn;


    ObservableList<DiagnosticWithPatientsView> diagnosticosObservableList = FXCollections.observableArrayList();

    public static void setMedician(Medico medician) {
        MedicoController.medician = medician;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (medician != null) {

            // cargar datos generales del paciente
            loadPaciente();

            // cargar datos de diagnósticos del paciente
            loadDiagnostics();

            // cargar datos de visitas médicas del paciente
            loadMediciansVisits();
        }
        if (textId != null) {
            textId.setText(String.valueOf(medician.getId()));
        }
    }

    private void loadPaciente() {
        textNumColegiado.setText(String.valueOf(medician.getNumColegiado()));
        textNombre.setText(medician.getNombre());
        textApellido1.setText(medician.getApellido1());
        textApellido2.setText(medician.getApellido2());
        textObservaciones.setText(medician.getObservaciones());
    }

    private void loadDiagnostics() {
        // Configurar las fábricas de celdas (esto solo se hace una vez)
        codigoDiagnosticoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        fechaDiagnosticoColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        pacienteDiagnosticoColumn.setCellValueFactory(new PropertyValueFactory<>("paciente"));

        // Obtener los diagnósticos del paciente
        List<Diagnostico> diagnosticos = DB.obtenerDiagnosticosMedico(medician.getId());

        // Crear una lista de DiagnosticoView
        List<DiagnosticWithPatientsView> diagnosticosViewList = new ArrayList<>();
        for (Diagnostico diagnostico : diagnosticos) {
            Diagnostico diagnosticoAux = new Diagnostico(diagnostico.getId(), DB.getConnection());
            diagnosticoAux.inicializarDesdeBD();
            Paciente paciente = new Paciente(diagnosticoAux.getIdPaciente(), DB.getConnection());
            paciente.inicializarDesdeBD();
            String fullNombre = paciente.getNombre() + " " + paciente.getApellido1() + " " + paciente.getApellido2();
            diagnosticosViewList.add(new DiagnosticWithPatientsView(diagnosticoAux.getCodigo(), diagnosticoAux.getFecha().toString(), fullNombre.contains("null") ? "Sin asignar" : fullNombre));
        }

        // Asignar la lista de DiagnosticoView a la tabla
        diagnosticosObservableList.setAll(diagnosticosViewList);

        // Asignar la lista observable a la tabla
        tableDiagnostics.setItems(diagnosticosObservableList);
    }

    private void loadMediciansVisits() {
        try {
            // Configurar las fábricas de celdas (esto solo se hace una vez)
            fechaVisitaMedicaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            pacienteVisitaMedicaColumn.setCellValueFactory(new PropertyValueFactory<>("paciente"));

            // Obtener las visitas médicas del paciente
            List<VisitaMedica> visitasMedicas = DB.obtenerVisitasMedicasMedico(medician.getId());

            // Crear una lista de MedicianVisitView
            List<MedicianVisitWithPatientsView> mediciansVisitsViewList = new ArrayList<>();
            for (VisitaMedica visitaMedica : visitasMedicas) {
                VisitaMedica visitaMedicaAux = new VisitaMedica(visitaMedica.getId(), DB.getConnection());
                visitaMedicaAux.inicializarDesdeBD();
                Paciente paciente = new Paciente(visitaMedicaAux.getIdPaciente(), DB.getConnection());
                if (paciente.inicializarDesdeBD()) {
                    String fullNombre = paciente.getNombre() + " " + paciente.getApellido1() + " " + paciente.getApellido2();
                    mediciansVisitsViewList.add(new MedicianVisitWithPatientsView(visitaMedicaAux.getFecha().toString(), fullNombre));
                } else {
                    mediciansVisitsViewList.add(new MedicianVisitWithPatientsView(visitaMedicaAux.getFecha().toString(), "Sin asignar"));
                }
            }
            // Asignar la lista de MedicianVisitView a la tabla
            ObservableList<MedicianVisitWithPatientsView> mediciansVisitsObservableList = FXCollections.observableArrayList(mediciansVisitsViewList);
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
        textNumColegiado.setText("");
        textNombre.setText("");
        textApellido1.setText("");
        textApellido2.setText("");
        textObservaciones.setText("");
    }

    public void callUpdate(MouseEvent mouseEvent) {

        if (textNumColegiado.getText().isEmpty()
                || textNombre.getText().isEmpty()
                || textApellido1.getText().isEmpty()
                || textApellido2.getText().isEmpty()) {
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
        if (textNumColegiado.getText().isEmpty()
                || textNombre.getText().isEmpty()
                || textApellido1.getText().isEmpty()
                || textApellido2.getText().isEmpty()) {
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
