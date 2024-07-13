package models;

import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author andres
 */
public class Diagnostico {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private Date fecha;
    private long idMedico;
    private long idPaciente;
    private String tipo;
    private String codigo;
    private String descripcion;
    private String observaciones;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public Diagnostico(long id, Connection conexionBD) {
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

    public Date getFecha() {
        return fecha;
    }

    public boolean setFecha(Date fecha) {
        this.fecha = fecha;

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

    public String getTipo() {
        return tipo;
    }

    public boolean setTipo(String tipo) {
        this.tipo = tipo;

        return true;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean setCodigo(String codigo) {
        this.codigo = codigo;

        return true;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean setDescripcion(String descripcion) {
        this.descripcion = descripcion;

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
    public boolean inicializarDesdeBD() {

        return true;
    }

    public boolean setData(
            long id,
            Date fecha,
            long idMedico,
            long idPaciente,
            String tipo,
            String codigo,
            String descripcion,
            String observaciones
    ) {

        boolean resultado = setId(id);
        resultado &= setFecha(fecha);
        resultado &= setIdMedico(idMedico);
        resultado &= setIdPaciente(idPaciente);
        resultado &= setTipo(tipo);
        resultado &= setCodigo(codigo);
        resultado &= setDescripcion(descripcion);
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

