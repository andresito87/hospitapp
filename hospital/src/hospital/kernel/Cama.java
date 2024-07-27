package hospital.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author andres
 */
public class Cama {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    public static final int CAMA_NORMAL = 0;
    public static final int CAMA_PARAPLEJIA = 1;
    public static final int CAMA_TETRAPLEJIA = 2;
    public static final int CAMA_UCI = 3;
    public static final int CAMA_INFANTIL = 4;

    private Connection conexionBD;

    private long id;
    private String marca;
    private String modelo;
    private int tipo;
    private String observaciones;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public Cama(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Cama(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos SET">

    public boolean setData(long id, String marca,
            String modelo, int tipo,
            String observaciones) {

        this.setId(id);
        this.setData(marca, modelo, tipo, observaciones);

        return true;
    }

    public boolean setData(String marca,
            String modelo, int tipo,
            String observaciones) {

        this.setMarca(marca);
        this.setModelo(modelo);
        this.setTipo(tipo);
        this.setObservaciones(observaciones);

        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public String getMarca() {

        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getTipo() {
        return tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getNombreTipo() {
        String devolucion;
        try {
            switch (this.tipo) {
                case CAMA_NORMAL:
                    devolucion = "Cama normal";
                    break;
                case CAMA_PARAPLEJIA:
                    devolucion = "Cama paraplejia";
                    break;
                case CAMA_TETRAPLEJIA:
                    devolucion = "Cama tetraplejia";
                    break;
                case CAMA_UCI:
                    devolucion = "Cama uci";
                    break;
                case CAMA_INFANTIL:
                    devolucion = "Cama infantil";
                    break;
                default:
                    devolucion = "Tipo desconocido";
            }

        } catch (Exception e) {
            devolucion = null;
        }

        return devolucion;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión de la Base de Datos">
    public boolean inicializarDesdeBD() {

        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = new String("SELECT * FROM Camas WHERE eliminado IS NULL AND Id = " + this.getId());

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getString("marca"), resultado.getString("modelo"),
                            resultado.getInt("tipo"), resultado.getString("Observaciones"));
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

    public boolean agregar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = new String("INSERT INTO Camas "
                    + "(marca,modelo, tipo, observaciones) "
                    + "VALUES ('" + this.getMarca() + "','" + this.getModelo() + "',"
                    + this.getTipo() + ",'" + this.getObservaciones() + "')");

            comandoSQL = this.conexionBD.prepareStatement(cadenaSQL, Statement.RETURN_GENERATED_KEYS);
            comandoSQL.execute();

            claveGenerada = comandoSQL.getGeneratedKeys();
            if (claveGenerada.next()) {

                this.setId(claveGenerada.getLong(1));

                devolucion = true;
            } else {
                devolucion = false;
            }

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean modificar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement Comando_SQL;

        try {

            cadenaSQL = "UPDATE Camas SET "
                    + "marca = '" + this.getMarca() + "', "
                    + "modelo = '" + this.getModelo() + "', "
                    + "tipo = " + this.getTipo() + ", "
                    + "observaciones = '" + this.getObservaciones() + "' "
                    + " WHERE Id = " + this.getId();

            Comando_SQL = this.conexionBD.prepareStatement(cadenaSQL);
            Comando_SQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean eliminar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement Comando_SQL;

        try {

            cadenaSQL = "UPDATE Camas SET eliminado=CURRENT_TIMESTAMP "
                    + "WHERE Id = " + this.getId();

            Comando_SQL = this.conexionBD.prepareStatement(cadenaSQL);
            Comando_SQL.execute();

            devolucion = true;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Estáticos de la Clase">
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de Camas">
    public static Cama getCama(long id, Connection conexionBD) {
        Cama devolucion;

        devolucion = new Cama(id, conexionBD);
        if (devolucion.inicializarDesdeBD() == false) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Cama> getCamas(boolean filtroMarca, String marca,
            boolean filtroModelo, String modelo,
            boolean filtroTipo, int tipo,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ArrayList<Cama> devolucion;
        ResultSet resultado;

        try {

            resultado = getCamasBD(filtroMarca, marca,
                    filtroModelo, modelo,
                    filtroTipo, tipo,
                    filtroObservaciones, observaciones,
                    conexionBD);

            devolucion = getCamas(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Cama> getTodasCamas(Connection conexionBD) {
        ArrayList<Cama> devolucion = null;
        ResultSet resultado;

        try {
            resultado = getCamasBD(false, null, false, null,
                    false, 0,
                    false, null, conexionBD);

            devolucion = getCamas(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public ArrayList<Paciente> getTodosPacientes() {
        ArrayList<Paciente> devolucion = null;
        ResultSet resultado;

        try {
            resultado = Paciente.getPacientesBD(
                    false, null,
                    false, null,
                    false, null,
                    false, null,
                    false, null,
                    false, null, null,
                    false, null,
                    false, null, null,
                    false, null, null,
                    true, this.getId(),
                    false, null,
                    conexionBD);

            devolucion = getPacientes(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;

    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta a la Base de Datos (Capa de Datos)">
    public static ArrayList<Cama> getCamas(ResultSet camas, Connection conexionBD) {

        ArrayList<Cama> devolucion;
        Cama camaAux;

        try {
            devolucion = new ArrayList<Cama>();
            if (camas != null) {
                camas.beforeFirst();

                while (camas.next()) {

                    camaAux = new Cama(conexionBD);

                    camaAux.setData(camas.getLong("id"),
                            camas.getString("marca"), camas.getString("modelo"),
                            camas.getInt("tipo"), camas.getString("Observaciones"));

                    devolucion.add(camaAux);
                }

            } else {
                devolucion = null;
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static ResultSet getCamasBD(boolean filtroMarca, String marca,
            boolean filtroModelo, String modelo,
            boolean filtroTipo, int tipo,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * "
                    + "FROM Camas "
                    + "WHERE Camas.eliminado IS NULL ";

            cadenaSQL = cadenaSQL + getCadenaFiltrosSQL(filtroMarca, marca,
                    filtroModelo, modelo,
                    filtroTipo, tipo,
                    filtroObservaciones, observaciones);

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = Vinculo.executeQuery(cadenaSQL);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Paciente> getPacientes(ResultSet pacientes, Connection conexionBD) {

        ArrayList<Paciente> devolucion;
        Paciente pacienteAux;

        try {
            devolucion = new ArrayList<>();
            if (pacientes != null) {

                pacientes.beforeFirst();

                while (pacientes.next()) {

                    pacienteAux = new Paciente(conexionBD);
                    LocalDate fechaNacimiento = null;
                    LocalDate fechaIngreso = null;
                    LocalDate fechaAlta = null;

                    if (pacientes.getDate("fechaNacimiento") != null) {
                        fechaNacimiento = pacientes.getDate("fechaNacimiento").toLocalDate();
                    }

                    if (pacientes.getDate("fechaIngreso") != null) {
                        fechaIngreso = pacientes.getDate("fechaIngreso").toLocalDate();
                    }
                    
                    if (pacientes.getDate("fechaAlta") != null) {
                        fechaAlta = pacientes.getDate("fechaAlta").toLocalDate();
                    }

                    pacienteAux.setData(pacientes.getLong("id"),
                            pacientes.getString("dni"), pacientes.getString("ci"),
                            pacientes.getString("nombre"), pacientes.getString("apellido1"),
                            pacientes.getString("apellido2"), fechaNacimiento,
                            pacientes.getString("numSegSocial"), fechaIngreso,
                            fechaAlta, pacientes.getLong("idCama"),
                            pacientes.getString("Observaciones"));

                    devolucion.add(pacienteAux);
                }

            } else {
                devolucion = null;
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static String getCadenaFiltrosSQL(boolean filtroMarca, String marca,
            boolean filtroModelo, String modelo,
            boolean filtroTipo, int tipo,
            boolean filtroObservaciones, String observaciones) {
        String devolucion = "";

        try {
            if (filtroMarca == true && marca != null) {
                devolucion = devolucion + "AND marca LIKE '%" + marca + "%' ";
            }

            if (filtroModelo == true && modelo != null) {
                devolucion = devolucion + "AND modelo LIKE '%" + modelo + "%' ";
            }

            if (filtroTipo == true && tipo >= 0) {
                devolucion = devolucion + "AND tipo = " + tipo + " ";
            }

            if (filtroObservaciones == true && observaciones != null) {
                devolucion = devolucion + "AND observaciones LIKE '%" + observaciones + "%' ";
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

// </editor-fold>
// </editor-fold>

}
