package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * @author andres
 */
public class Paciente {

    private long id;
    private String dni;
    private String ci;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String numSeguridadSocial;
    private long idCama;
    private Cama cama;
    private LocalDate fechaIngreso;
    private LocalDate fechaAlta;
    private String observaciones;
    private final Connection conexionBD;

    public Paciente(long id, Connection conexionBD) {
        this.id = id;
        this.conexionBD = conexionBD;
    }

    public boolean setData(
            long id,
            String dni,
            String ci,
            String nombre,
            String apellido1,
            String apellido2,
            LocalDate fechaNacimiento,
            String numSeguridadSocial,
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
        resultado &= setNumSeguridadSocial(numSeguridadSocial);
        resultado &= setIdCama(idCama);
        resultado &= setFechaIngreso(fechaIngreso);
        resultado &= setFechaAlta(fechaAlta);
        resultado &= setObservaciones(observaciones);

        return resultado;
    }

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

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }

    public boolean setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSeguridadSocial = numSeguridadSocial;

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
    
    public boolean inicializarDesdeBD() {
        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = new String("SELECT * FROM Medicos WHERE eliminado IS NULL AND Id = " + this.getId());

            Statement Vinculo = this.conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(
                            resultado.getLong("Id"),
                            resultado.getString("DNI"),
                            resultado.getString("CI"),
                            resultado.getString("Nombre"),
                            resultado.getString("Apellido1"),
                            resultado.getString("Apellido2"),
                            resultado.getDate("FechaNacimiento").toLocalDate(),
                            resultado.getString("NumSeguridadSocial"),
                            resultado.getLong("IdCama"),
                            resultado.getDate("FechaIngreso").toLocalDate(),
                            resultado.getDate("FechaAlta").toLocalDate(),
                            resultado.getString("Observaciones")
                    );
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