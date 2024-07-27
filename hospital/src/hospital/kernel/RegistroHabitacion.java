package hospital.kernel;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author andres
 */
public class RegistroHabitacion {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private Connection conexionBD;

    private long id;
    private long idPaciente;
    private long idHabitacion;
    private LocalDateTime fechaEntrada;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public RegistroHabitacion(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public RegistroHabitacion(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, long idPaciente,
            long idHabitacion, java.sql.Timestamp fechaEntrada) {
        boolean devolucion;

        try {
            this.setId(id);
            this.setIdPaciente(idPaciente);
            this.setIdHabitacion(idHabitacion);
            devolucion = this.setFechaEntrada(fechaEntrada);
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setData(long id, long idPaciente,
            long idHabitacion, LocalDateTime fechaEntrada) {
        boolean devolucion;

        try {
            this.setId(id);
            this.setIdPaciente(idPaciente);
            this.setIdHabitacion(idHabitacion);
            devolucion = this.setFechaEntrada(fechaEntrada);
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setIdHabitacion(long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public boolean setFechaEntrada(LocalDateTime fechaEntrada) {
        boolean devolucion;

        try {
            this.fechaEntrada = fechaEntrada;

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaEntrada(java.sql.Timestamp fechaEntrada) {
        boolean devolucion;

        try {
            if (fechaEntrada != null) {
                this.fechaEntrada = fechaEntrada.toLocalDateTime();
            } else {
                this.fechaEntrada = null;
            }

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public long getIdHabitacion() {
        return idHabitacion;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public String getFechaAlta(String formato) {
        String devolucion = null;
        String formatoFecha;

        try {
            if (formato == null) {
                formatoFecha = "dd/MM/yyyy H:m:s";
            } else {
                formatoFecha = formato;
            }

            if (this.fechaEntrada != null) {
                devolucion = this.fechaEntrada.format(DateTimeFormatter.ofPattern(formatoFecha));
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

// </editor-fold>
}
