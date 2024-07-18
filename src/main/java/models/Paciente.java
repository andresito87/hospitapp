package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * @author andres
 */
public class Paciente {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private long id;
    private String dni;
    private String ci;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String numSegSocial;
    private long idCama;
    private Cama cama;
    private LocalDate fechaIngreso;
    private LocalDate fechaAlta;
    private String observaciones;
    private final Connection conexionBD;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public Paciente(long id, Connection conexionBD) {
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

    public String getDni() {
        return dni;
    }

    public boolean setDni(String dni) {
        this.dni = dni;

        return true;
    }

    public String getCi() {
        return ci;
    }

    public boolean setCi(String ci) {
        this.ci = ci;

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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public boolean setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;

        return true;
    }

    public String getNumSegSocial() {
        return numSegSocial;
    }

    public boolean setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSegSocial = numSeguridadSocial;

        return true;
    }

    public long getIdCama() {
        return idCama;
    }

    public boolean setIdCama(long idCama) {
        this.idCama = idCama;

        return true;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;

        return true;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public boolean setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;

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
        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * FROM Pacientes WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = this.conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();
                if (resultado.next()) {

                    devolucion = this.setData(
                            resultado.getLong("id"),
                            resultado.getString("dni"),
                            resultado.getString("ci"),
                            resultado.getString("nombre"),
                            resultado.getString("apellido1"),
                            resultado.getString("apellido2"),
                            resultado.getDate("fechaNacimiento").toLocalDate(),
                            resultado.getString("numSegSocial"),
                            resultado.getLong("idCama"),
                            resultado.getDate("fechaIngreso") == null ? null : resultado.getDate("fechaIngreso").toLocalDate(),
                            resultado.getDate("fechaAlta") == null ? null : resultado.getDate("fechaAlta").toLocalDate(),
                            resultado.getString("observaciones")
                    );
                } else {
                    devolucion = false;
                }
            } else {
                devolucion = false;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setData(
            long id,
            String dni,
            String ci,
            String nombre,
            String apellido1,
            String apellido2,
            LocalDate fechaNacimiento,
            String numSegSocial,
            long idCama,
            LocalDate fechaIngreso,
            LocalDate fechaAlta,
            String observaciones
    ) {
        boolean resultado = setId(id);
        resultado &= setDni(dni);
        resultado &= setCi(ci);
        resultado &= setNombre(nombre);
        resultado &= setApellido1(apellido1);
        resultado &= setApellido2(apellido2);
        resultado &= setFechaNacimiento(fechaNacimiento);
        resultado &= setNumSeguridadSocial(numSegSocial);
        resultado &= setIdCama(idCama);
        resultado &= setFechaIngreso(fechaIngreso);
        resultado &= setFechaAlta(fechaAlta);
        resultado &= setObservaciones(observaciones);


        return resultado;
    }

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO Pacientes "
                    + "(dni, ci, nombre, apellido1, apellido2, fechaNacimiento, "
                    + "numSegSocial, idCama, fechaIngreso, fechaAlta, observaciones) "
                    + "VALUES ('" + this.getDni() + "','" + this.getCi() + "','"
                    + this.getNombre() + "','" + this.getApellido1() + "','"
                    + this.getApellido2() + "','" + this.getFechaNacimiento() + "','"
                    + this.getNumSegSocial() + "'," + this.getIdCama() + ",'"
                    + this.getFechaIngreso() + "','" + this.getFechaAlta() + "','"
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

            cadenaSQL = "UPDATE Pacientes SET "
                    + "dni = '" + this.getDni() + "', "
                    + "ci = '" + this.getCi() + "', "
                    + "nombre = '" + this.getNombre() + "', "
                    + "apellido1 = '" + this.getApellido1() + "', "
                    + "apellido2 = '" + this.getApellido2() + "', "
                    + "fechaNacimiento = '" + this.getFechaNacimiento() + "', "
                    + "numSegSocial = '" + this.getNumSegSocial() + "', "
                    + "idCama = '" + this.getIdCama() + "', "
                    + "fechaIngreso = '" + this.getFechaIngreso() + "', "
                    + "fechaAlta = '" + this.getFechaAlta() + "', "
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

            cadenaSQL = "UPDATE Pacientes SET eliminado=CURRENT_TIMESTAMP "
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