package models;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * @author andres
 */
public class Medico {

    private final Connection conexionBD;
    private long id;
    private long numColegiado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String observaciones;

    public Medico(long id, Connection conexionBD) {
        this.id = id;
        this.conexionBD = conexionBD;
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

    public boolean inicializarDesdeBD() {

        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = new String("SELECT * FROM Medicos WHERE eliminado IS NULL AND Id = " + this.getId());

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


    public void anhadir() {

    }

    public void modificar() {

    }

    public void eliminar() {

    }
}
