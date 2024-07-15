package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * @author andres
 */
public class Diagnostico {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private LocalDate fecha;
    private long idMedico;
    private long idPaciente;
    private int tipo;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean setFecha(LocalDate fecha) {
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

    public int getTipo() {
        return tipo;
    }

    public boolean setTipo(int tipo) {
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

        boolean devolucion = true;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * FROM Diagnosticos WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getDate("fecha").toLocalDate(),
                            resultado.getLong("idMedico"),
                            resultado.getLong("idPaciente"),
                            resultado.getInt("tipo"),
                            resultado.getString("codigo"),
                            resultado.getString("descripcion"),
                            resultado.getString("Observaciones"));
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
            LocalDate fecha,
            long idMedico,
            long idPaciente,
            int tipo,
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

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO Diagnosticos "
                    + "(fecha, idMedico, idPaciente, tipo, codigo, descripcion, observaciones) "
                    + "VALUES ('" + this.getFecha() + "',"
                    + this.getIdMedico() + "," + this.getIdPaciente() + ","
                    + this.getTipo() + ",'" + this.getCodigo() + "','" 
                    + this.getDescripcion() + "','" + this.getObservaciones() + "')";

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

            cadenaSQL = "UPDATE Diagnosticos SET "
                    + "fecha = '" + this.getFecha() + "', "
                    + "idMedico = " + this.getIdMedico() + ", "
                    + "idPaciente = " + this.getIdPaciente() + ", "
                    + "tipo = " + this.getTipo() + ", "
                    + "codigo = '" + this.getCodigo() + "', "
                    + "descripcion = '" + this.getDescripcion() + "', "
                    + "observaciones = '" + this.getObservaciones() + "' "
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

            cadenaSQL = "UPDATE Diagnosticos SET eliminado=CURRENT_TIMESTAMP "
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

