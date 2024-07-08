package models;

/**
 *
 * @author andres
 */
public class Habitacion {

    private long id;
    private int numHabitacion;
    private int numPlanta;
    private int numPlazas;
    private String observaciones;

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
