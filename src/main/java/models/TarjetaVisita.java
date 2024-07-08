package models;

import java.util.Date;

/**
 *
 * @author andres
 */
public class TarjetaVisita {

    private long id;
    private int numero;
    private Date fechaCaducidad;
    private long idPaciente;

    public boolean setData(
            long id,
            int numero,
            Date fecha,
            long idPaciente
    ) {

        boolean resultado = setId(id);
        resultado &= setNumero(numero);
        resultado &= setFechaCaducidad(fecha);
        resultado &= setIdPaciente(idPaciente);

        return resultado;
    }

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

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public boolean setFechaCaducidad(Date fechaCaducidad) {
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

    public void visualizar() {
        System.out.println("Id: " + this.id + "\n"
                + "Numero: " + this.numero + "\n"
                + "Fecha de caducidad: " + this.fechaCaducidad + "\n"
                + "Id Paciente: " + this.idPaciente + "\n");
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

