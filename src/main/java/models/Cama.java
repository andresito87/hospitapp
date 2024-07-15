package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

        boolean devolucion = true;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * FROM Camas WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getString("marca"),
                            resultado.getString("modelo"),
                            resultado.getInt("tipo"),
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

    public boolean agregar() {

        boolean devolucion = false;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = "INSERT INTO Camas "
                    + "(marca, modelo, tipo, observaciones) "
                    + "VALUES ('" + this.getMarca() + "',"
                    + this.getModelo() + "," + this.getTipo() + ","
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
            
            cadenaSQL = "UPDATE Camas SET "
                    + "marca = '" + this.getMarca() + "', "
                    + "modelo = '" + this.getModelo() + "', "
                    + "tipo = " + this.getTipo() + ", "
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
            
            cadenaSQL = "UPDATE Camas SET eliminado=CURRENT_TIMESTAMP "
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
