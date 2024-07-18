package models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author andres
 */
public class Medico {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private long numColegiado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String observaciones;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public Medico(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Medico(long id, Connection conexionBD) {
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

    public long getNumColegiado() {
        return numColegiado;
    }

    public boolean setNumColegiado(long numColegiado) {
        this.numColegiado = numColegiado;

        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean setNombre(String nombre) {
        this.nombre = nombre;

        return true;
    }

    public String getApellido1() {
        return apellido1;
    }

    public boolean setApellido1(String apellido1) {
        this.apellido1 = apellido1;

        return true;
    }

    public String getApellido2() {
        return apellido2;
    }

    public boolean setApellido2(String apellido2) {
        this.apellido2 = apellido2;

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
            cadenaSQL = "SELECT * FROM Medicos WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getLong("numColegiado"),
                            resultado.getString("nombre"),
                            resultado.getString("apellido1"),
                            resultado.getString("apellido2"),
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
            long numColegiado,
            String nombre,
            String apellido1,
            String apellido2,
            String observaciones
    ) {

        boolean resultado = setId(id);
        resultado &= setNumColegiado(numColegiado);
        resultado &= setNombre(nombre);
        resultado &= setApellido1(apellido1);
        resultado &= setApellido2(apellido2);
        resultado &= setObservaciones(observaciones);

        return resultado;
    }

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO Medicos "
                    + "(numColegiado, nombre, apellido1, apellido2, observaciones) "
                    + "VALUES ('" + this.getNumColegiado() + "','"
                    + this.getNombre() + "','" + this.getApellido1() + "','"
                    + this.getApellido2() + "','"
                    + this.getObservaciones() + "')";

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

            cadenaSQL = "UPDATE Medicos SET "
                    + "numColegiado = '" + this.getNumColegiado() + "', "
                    + "nombre = '" + this.getNombre() + "', "
                    + "apellido1 = '" + this.getApellido1() + "', "
                    + "apellido2 = '" + this.getApellido2() + "', "
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


            cadenaSQL = "UPDATE Medicos SET eliminado=CURRENT_TIMESTAMP "
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

    // <editor-fold defaultstate="collapsed" desc="Métodos Estáticos">
    public static ArrayList<Medico> getTodosMedicos(Connection conexionBD) {
        ArrayList<Medico> devolucion = new ArrayList<Medico>();
        ResultSet medicos;
        Medico medicoAux;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * "
                    + "FROM Medicos "
                    + "WHERE Medicos.eliminado IS NULL "
                    + "ORDER BY apellido1,apellido2,nombre";

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            medicos = Vinculo.executeQuery(cadenaSQL);

            if (medicos != null) {
                medicos.beforeFirst();

                while (medicos.next()) {

                    medicoAux = new Medico(conexionBD);

                    medicoAux.setData(medicos.getLong("id"),
                            medicos.getLong("numColegiado"),
                            medicos.getString("nombre"),
                            medicos.getString("apellido1"),
                            medicos.getString("apellido2"),
                            medicos.getString("Observaciones"));

                    devolucion.add(medicoAux);
                }

            } else {
                devolucion = null;
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Medico> getTodosMedicos(String nombre, Connection conexionBD) {
        ArrayList<Medico> devolucion = new ArrayList<Medico>();
        ResultSet medicos;
        Medico medicoAux;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * "
                    + "FROM Medicos "
                    + "WHERE Medicos.eliminado IS NULL AND Medicos.nombre LIKE '%" + nombre + "%' "
                    + "ORDER BY apellido1,apellido2,nombre";

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            medicos = Vinculo.executeQuery(cadenaSQL);

            if (medicos != null) {
                medicos.beforeFirst();

                while (medicos.next()) {

                    medicoAux = new Medico(conexionBD);

                    medicoAux.setData(medicos.getLong("id"),
                            medicos.getLong("numColegiado"),
                            medicos.getString("nombre"),
                            medicos.getString("apellido1"),
                            medicos.getString("apellido2"),
                            medicos.getString("Observaciones"));

                    devolucion.add(medicoAux);
                }

            } else {
                devolucion = null;
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }


    // </editor-fold>
}
