package views;

public class DiagnosticWithPatientsView {
    private  String codigo;
    private  String fecha;
    private  String paciente;

    public DiagnosticWithPatientsView(String codigo, String fecha, String paciente) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.paciente = paciente;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }
}
