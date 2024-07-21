package views;

public class MedicianVisitView {
    private final String fecha;
    private final String medico;

    public MedicianVisitView(String fecha, String medico) {
        this.fecha = fecha;
        this.medico = medico;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMedico() {
        return medico;
    }
}
