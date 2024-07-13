package models;

import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author andres
 */
public class TarjetaVisita {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private int numero;
    private Date fechaCaducidad;
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos DB y SetData">
    public boolean inicializarDesdeBD() {

        return true;
    }

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

    public void anhadir() {

    }

    public void modificar() {

    }

    public void eliminar() {

    }
    // </editor-fold>

}

