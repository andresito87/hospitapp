package models;

import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author andres
 */
public class RegistroHabitacion {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private long idPaciente;
    private long idHabitacion;
    private Date fechaEntrada;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public RegistroHabitacion(long id, Connection conexionBD) {
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

    public long getIdPaciente() {
        return idPaciente;
    }

    public boolean setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;

        return true;
    }

    public long getIdHabitacion() {
        return idHabitacion;
    }

    public boolean setIdHabitacion(long idHabitacion) {
        this.idHabitacion = idHabitacion;

        return true;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public boolean setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;

        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos DB y SetData">
    public boolean inicializarDesdeBD() {

        return true;
    }

    public boolean setData(
            long id,
            long idPaciente,
            long idHabitacion,
            Date fechaEntrada
    ) {

        boolean resultado = setId(id);
        resultado &= setIdPaciente(idPaciente);
        resultado &= setIdHabitacion(idHabitacion);
        resultado &= setFechaEntrada(fechaEntrada);

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
