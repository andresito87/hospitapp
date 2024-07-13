package models;

import java.sql.Connection;

/**
 * @author andres
 */
public class Cama {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final Connection conexionBD;
    private long id;
    private String marca;
    private String modelo;
    private int tipo;
    private String observaciones;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Constructores">
    public Cama(long id, Connection conexionBD) {
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

    public String getMarca() {
        return marca;
    }

    public boolean setMarca(String marca) {
        this.marca = marca;

        return true;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean setModelo(String modelo) {
        this.modelo = modelo;

        return true;
    }

    public int getTipo() {
        return tipo;
    }

    public boolean setTipo(int tipo) {
        this.tipo = tipo;

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

        return true;
    }

    public boolean setData(
            long id,
            String marca,
            String modelo,
            int tipo,
            String observaciones
    ) {

        boolean resultado = setId(id);
        resultado &= setMarca(marca);
        resultado &= setModelo(modelo);
        resultado &= setTipo(tipo);
        resultado &= setObservaciones(observaciones);

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
