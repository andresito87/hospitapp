package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author andres
 */
public class Habitacion {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private int numHabitacion;
    private int numPlanta;
    private int numPlazas;
    private String observaciones;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public Habitacion(long id, Connection conexionBD) {
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

    public int getNumHabitacion() {
        return numHabitacion;
    }

    public boolean setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;

        return true;
    }

    public int getNumPlanta() {
        return numPlanta;
    }

    public boolean setNumPlanta(int numPlanta) {
        this.numPlanta = numPlanta;

        return true;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public boolean setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;

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
            cadenaSQL = "SELECT * FROM Habitacion WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getInt("numHabitacion"),
                            resultado.getInt("numPlanta"),
                            resultado.getInt("numPlazas"),
                            resultado.getString("observaciones"));
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
            int numHabitacion,
            int numPlanta,
            int numPlazas,
            String observaciones
    ) {

        boolean resultado = setId(id);
        resultado &= setNumHabitacion(numHabitacion);
        resultado &= setNumPlanta(numPlanta);
        resultado &= setNumPlazas(numPlazas);
        resultado &= setObservaciones(observaciones);

        return resultado;
    }

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO Habitacion "
                    + "(numHabitacion, numPlanta, numPlazas, observaciones) "
                    + "VALUES ('" + this.getNumHabitacion() + "', "
                    + "'" + this.getNumPlanta() + "', "
                    + "'" + this.getNumPlazas() + "', "
                    + "'" + this.getObservaciones() + "')";

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

            cadenaSQL = "UPDATE Habitacion SET "
                    + "id = '" + this.getId() + "', "
                    + "numHabitacion = '" + this.getNumHabitacion() + "', "
                    + "numPlanta = '" + this.getNumPlanta() + "', "
                    + "numPlazas = '" + this.getNumPlazas() + "', "
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

            cadenaSQL = "UPDATE Habitacion SET eliminado=CURRENT_TIMESTAMP "
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
