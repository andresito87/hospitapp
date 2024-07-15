package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * @author andres
 */
public class RegistroHabitacion {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private long idPaciente;
    private long idHabitacion;
    private LocalDate fechaEntrada;
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

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public boolean setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;

        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos DB y SetData">
    public boolean inicializarDesdeBD() {

        boolean devolucion = true;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * FROM RegistroHabitacion WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getLong("idPaciente"),
                            resultado.getLong("idHabitacion"),
                            resultado.getDate("fechaEntrada").toLocalDate());
                } else {
                    devolucion = false;
                }
            } else {
                devolucion = false;
            }

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setData(
            long id,
            long idPaciente,
            long idHabitacion,
            LocalDate fechaEntrada
    ) {

        boolean resultado = setId(id);
        resultado &= setIdPaciente(idPaciente);
        resultado &= setIdHabitacion(idHabitacion);
        resultado &= setFechaEntrada(fechaEntrada);

        return resultado;
    }

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO RegistroHabitacion "
                    + "(id, idPaciente, idHabitacion, fechaEntrada) "
                    + "VALUES ('" + this.getId() + "', "
                    + "'" + this.getIdPaciente() + "', "
                    + "'" + this.getIdHabitacion() + "', "
                    + "'" + this.getFechaEntrada() + "')";

            comandoSQL = this.conexionBD.prepareStatement(cadenaSQL, Statement.RETURN_GENERATED_KEYS);
            comandoSQL.execute();

            claveGenerada = comandoSQL.getGeneratedKeys();
            if (claveGenerada.next()) {

                this.setId(claveGenerada.getLong(1));

                devolucion = true;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return devolucion;
    }

    public boolean modificar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;

        try {

            cadenaSQL = "UPDATE RegistroHabitacion SET "
                    + "id = '" + this.getId() + "', "
                    + "idPaciente = '" + this.getIdPaciente() + "', "
                    + "idHabitacion = '" + this.getIdHabitacion() + "', "
                    + "fechaEntrada = '" + this.getFechaEntrada() + "' "
                    + "WHERE Id = " + this.getId();

            comandoSQL = this.conexionBD.prepareStatement(cadenaSQL);
            comandoSQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return devolucion;
    }

    public boolean eliminar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;

        try {

            cadenaSQL = "UPDATE RegistroHabitacion SET eliminado=CURRENT_TIMESTAMP "
                    + "WHERE Id = " + this.getId();

            comandoSQL = this.conexionBD.prepareStatement(cadenaSQL);
            comandoSQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return devolucion;

    }
    // </editor-fold>

}
