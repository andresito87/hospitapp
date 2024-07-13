package models;

import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author andres
 */
public class VisitaMedica {
    
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private long idMedico;
    private long idPaciente;
    private Date fecha;
    private String observaciones;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public VisitaMedica(long id, Connection conexionBD) {
        this.id = id;
        this.conexionBD = conexionBD;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos GET y SET">
    public long getId() {
        return id;
    }

    public boolean setId(long id) {
        this.id = id;

        return true;
    }

    public long getIdMedico() {
        return idMedico;
    }

    public boolean setIdMedico(long idMedico) {
        this.idMedico = idMedico;

        return true;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public boolean setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;

        return true;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean setFecha(Date fecha) {
        this.fecha = fecha;

        return true;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public boolean setObservaciones(String observaciones) {
        this.observaciones = observaciones;

        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos DB y SetData">
    public boolean inicializarDesdeBD(){

        return true;
    }

    public boolean setData(
            long id,
            long idMedico,
            long idPaciente,
            Date fecha,
            String observaciones
    ) {

        boolean resultado = setId(id);
        resultado &= setIdMedico(idMedico);
        resultado &= setIdPaciente(idPaciente);
        resultado &= setFecha(fecha);
        resultado &= setObservaciones(observaciones);

        return resultado;
    }

    public void anhadir() {

    }

    public void modificar() {

    }

    public void eliminar() {

    }
    // </editor-fold>

}

