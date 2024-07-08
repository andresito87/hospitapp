package models;

import java.util.Date;

/**
 *
 * @author andres
 */
public class Paciente {

    private long id;
    private String dni;
    private String ci;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Date fechaNacimiento;
    private String numSeguridadSocial;
    private long idCama;
    private Date fechaIngreso;
    private Date fechaAlta;
    private String observaciones;

    public boolean setData(
            long id,
            String dni,
            String ci,
            String nombre,
            String apellido1,
            String apellido2,
            Date fechaNacimiento,
            String numSeguridadSocial,
            long idCama,
            Date fechaIngreso,
            Date fechaAlta,
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public boolean setFechaNacimiento(Date fechaNacimiento) {
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;

        return true;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public boolean setFechaAlta(Date fechaAlta) {
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

    public boolean inicializarDesdeBD() {

        return true;
    }

    public void anhadir() {

    }

    public void modificar() {

    }

    public void eliminar() {

    }

}