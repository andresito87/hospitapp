package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author andres
 */
public class TarjetaVisita {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private int numero;
    private LocalDate fechaCaducidad;
    private long idPaciente;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public TarjetaVisita(long id, Connection conexionBD) {
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

    public int getNumero() {
        return numero;
    }

    public boolean setNumero(int numero) {
        this.numero = numero;

        return true;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public boolean setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;

        return true;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public boolean setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;

        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos DB y SetData">
    public boolean inicializarDesdeBD() {
        
        boolean devolucion = true;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * FROM tarjetasvisitas WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getInt("numero"),
                            resultado.getDate("fechaCaducidad").toLocalDate(),
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

    public boolean setData(
            long id,
            int numero,
            LocalDate fechaCaducidad,
            long idPaciente
    ) {

        boolean resultado = setId(id);
        resultado &= setNumero(numero);
        resultado &= setFechaCaducidad(fechaCaducidad);
        resultado &= setIdPaciente(idPaciente);

        return resultado;
    }

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO tarjetasvisitas "
                    + "(numero, fechaCaducidad, idPaciente) "
                    + "VALUES ('" + this.getNumero() + "',"
                    + "'" + this.getFechaCaducidad() + "',"
                    + "'" + this.getIdPaciente() + "')";

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

            cadenaSQL = "UPDATE tarjetasvisitas SET "
                    + "numero = " + this.getNumero() + ","
                    + "fechaCaducidad = '" + this.getFechaCaducidad() + "',"
                    + "idPaciente = " + this.getIdPaciente() + " "
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

        PreparedStatement ComandoSQL;

        try {

            cadenaSQL = "UPDATE tarjetasvisitas SET eliminado=CURRENT_TIMESTAMP "
                    + "WHERE Id = " + this.getId();

            ComandoSQL = this.conexionBD.prepareStatement(cadenaSQL);
            ComandoSQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return devolucion;

    }
    // </editor-fold>

}

