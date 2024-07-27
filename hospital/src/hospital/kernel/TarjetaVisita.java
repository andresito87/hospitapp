package hospital.kernel;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author andres
 */
public class TarjetaVisita {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private Connection conexionBD;

    private long id;
    private long numero;
    private LocalDate fechaCaducidad;
    private long idPaciente;
    private Paciente paciente;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public TarjetaVisita(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public TarjetaVisita(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, long numero,
            Date fechaCaducidad, long idPaciente) {

        this.setId(id);
        this.setData(id, numero, fechaCaducidad, idPaciente);

        return true;
    }

    public boolean setData(long id, long numero,
            LocalDate fechaCaducidad, long idPaciente) {

        this.setId(id);
        this.setNumero(numero);
        this.setFechaCaducidad(fechaCaducidad);
        this.setIdPaciente(idPaciente);

        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public long getNumero() {
        return numero;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public String getFechaCaducidad(String formato) {
        String devolucion = null;
        String formatoFecha;

        try {
            if (formato == null) {
                formatoFecha = "dd/MM/yyyy";
            } else {
                formatoFecha = formato;
            }

            if (this.fechaCaducidad != null) {
                devolucion = this.fechaCaducidad.format(DateTimeFormatter.ofPattern(formatoFecha));
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public Paciente getPaciente() {

        try {
            this.paciente = Paciente.getPaciente(this.idPaciente, conexionBD);
        } catch (Exception ex) {
            this.paciente = null;
        }

        return this.paciente;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión de la Base de Datos">
    public boolean inicializarDesdeBD() {

        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = new String("SELECT * FROM TarjetasVisitas WHERE eliminado IS NULL AND Id = " + this.getId());

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getInt("numero"),
                            resultado.getDate("fechaCaducidad"),
                            resultado.getLong("idPaciente"));
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

    public boolean agregar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = new String("INSERT INTO TarjetasVisitas "
                    + "(numero,fechaCaducidad, idPaciente) "
                    + "VALUES (" + this.getNumero() + ",'" + this.getFechaCaducidad() + "',"
                    + this.getIdPaciente() + ")");

            comandoSQL = this.conexionBD.prepareStatement(cadenaSQL, Statement.RETURN_GENERATED_KEYS);
            comandoSQL.execute();

            claveGenerada = comandoSQL.getGeneratedKeys();
            if (claveGenerada.next()) {

                this.setId(claveGenerada.getLong(1));

                devolucion = true;
            } else {
                devolucion = false;
            }

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean modificar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement Comando_SQL;

        try {

            cadenaSQL = "UPDATE TarjetasVisitas SET "
                    + "numero = " + this.getNumero() + ", "
                    + "fechaCaducidad = " + this.getFechaCaducidad() + ", "
                    + "idPaciente = " + this.getIdPaciente() + " "
                    + "WHERE Id = " + this.getId();

            Comando_SQL = this.conexionBD.prepareStatement(cadenaSQL);
            Comando_SQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean eliminar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement Comando_SQL;

        try {

            cadenaSQL = "UPDATE TarjetasVisitas SET eliminado=CURRENT_TIMESTAMP "
                    + "WHERE Id = " + this.getId();

            Comando_SQL = this.conexionBD.prepareStatement(cadenaSQL);
            Comando_SQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;

    }

// </editor-fold>
}
