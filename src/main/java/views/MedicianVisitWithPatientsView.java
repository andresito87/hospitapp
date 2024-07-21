package views;

public class MedicianVisitWithPatientsView {
    private final String fecha;
    private final String paciente;

    public MedicianVisitWithPatientsView(String fecha, String paciente) {
        this.fecha = fecha;
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPaciente() {
        return paciente;
    }
}
