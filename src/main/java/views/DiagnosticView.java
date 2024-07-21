package views;



public class DiagnosticView {
    private  String codigo;
    private  String fecha;
    private  String medico;

    public DiagnosticView(String codigo, String fecha, String medico) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.medico = medico;
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

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }
}
