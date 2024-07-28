package hospital.kernel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author andres
 */
public class Diagnostico {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    public static final int LEVE = 0;
    public static final int MEDIO = 1;
    public static final int GRAVE = 2;
    public static final int MUY_GRAVE = 3;
    public static final int URGENTE = 4;

    private final Connection conexionBD;

    private long id;
    private LocalDate fecha;

    private long idMedico;
    private Medico medico;

    private long idPaciente;
    private Paciente paciente;

    private int tipo;
    private String codigo;
    private String descripcion;
    private String observaciones;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public Diagnostico(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Diagnostico(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, LocalDate fecha, long idMedico,
            long idPaciente, int tipo, String codigo,
            String descripcion, String observaciones) {

        this.setId(id);
        this.setData(fecha, idMedico, idPaciente, tipo, codigo, descripcion, observaciones);

        return true;
    }

    public boolean setData(LocalDate fecha, long idMedico,
            long idPaciente, int tipo, String codigo,
            String descripcion, String observaciones) {

        this.setFecha(fecha);
        this.setIdMedico(idMedico);
        this.setIdPaciente(idPaciente);
        this.setTipo(tipo);
        this.setCodigo(codigo);
        this.setDescripcion(descripcion);
        this.setObservaciones(observaciones);

        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public long getIdMedico() {
        return idMedico;
    }

    public Medico getMedico() {
        Medico devolucion;

        try {
            this.medico = Medico.getMedico(this.getIdMedico(), this.conexionBD);
            devolucion = this.medico;
        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public Paciente getPaciente() {
        Paciente devolucion;

        try {
            this.paciente = Paciente.getPaciente(this.getIdPaciente(), this.conexionBD);
            devolucion = this.paciente;
        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public int getTipo() {
        return tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión de la Base de Datos">
    public boolean inicializarDesdeBD() {

        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;
        LocalDate fecha;

        try {
            cadenaSQL = new String("SELECT * FROM Diagnosticos WHERE eliminado IS NULL AND Id = " + this.getId());

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {
                    if (resultado.getDate("fecha") != null) {
                        fecha = resultado.getDate("fecha").toLocalDate();
                    } else {
                        fecha = null;
                    }

                    devolucion = this.setData(resultado.getLong("id"), fecha,
                            resultado.getLong("idMedico"), resultado.getLong("idPaciente"),
                            resultado.getInt("tipo"), resultado.getString("codigo"),
                            resultado.getString("descripcion"),
                            resultado.getString("observaciones"));
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

            cadenaSQL = new String("INSERT INTO Diagnosticos "
                    + "(fecha,idMedico,idPaciente,tipo,codigo,descripcion,observaciones) "
                    + "VALUES ('" + this.getFecha() + "','" + this.getIdMedico() + "','"
                    + this.getIdPaciente() + "','" + this.getTipo() + "','"
                    + this.getCodigo() + "','" + this.getDescripcion() + "','"
                    + this.getObservaciones() + "')");

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

            cadenaSQL = "UPDATE Diagnosticos SET "
                    + "fecha = '" + this.getFecha() + "', "
                    + "idMedico = '" + this.getIdMedico() + "', "
                    + "idPaciente = '" + this.getIdPaciente() + "', "
                    + "tipo = '" + this.getTipo() + "', "
                    + "codigo = '" + this.getCodigo() + "', "
                    + "descripcion = '" + this.getDescripcion() + "', "
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

            cadenaSQL = "UPDATE Diagnosticos SET eliminado=CURRENT_TIMESTAMP "
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
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de Diagnosticos">
    public static Diagnostico getDiagnostico(long id, Connection conexionBD) {
        Diagnostico devolucion;

        devolucion = new Diagnostico(id, conexionBD);
        if (devolucion.inicializarDesdeBD() == false) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Diagnostico> getDiagnosticos(
            boolean filtroFecha, LocalDate fechaInicio, LocalDate fechaFin,
            boolean filtroIdMedico, long idMedico,
            boolean filtroIdPaciente, long idPaciente,
            boolean filtroTipo, int tipo,
            boolean filtroCodigo, String codigo,
            boolean filtroDescripcion, String descripcion,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ArrayList<Diagnostico> devolucion;
        ResultSet resultado;

        try {

            resultado = getDiagnosticosBD(
                    filtroFecha, fechaInicio, fechaFin,
                    filtroIdMedico, idMedico,
                    filtroIdPaciente, idPaciente,
                    filtroTipo, tipo,
                    filtroCodigo, codigo,
                    filtroDescripcion, descripcion,
                    filtroObservaciones, observaciones,
                    conexionBD);

            devolucion = getDiagnosticos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Diagnostico> getTodosDiagnosticos(Connection conexionBD) {
        ArrayList<Diagnostico> devolucion;
        ResultSet resultado;

        try {

            resultado = getDiagnosticosBD(
                    false, null, null,
                    false, 0,
                    false, 0,
                    false, 0,
                    false, null,
                    false, null,
                    false, null,
                    conexionBD);

            devolucion = getDiagnosticos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Diagnostico> getDiagnosticosPaciente(long idPaciente, Connection conexionBD) {
        ArrayList<Diagnostico> devolucion = null;
        ResultSet resultado;
        Diagnostico pacienteAux;

        try {

            resultado = getDiagnosticosBD(
                    false, null, null,
                    false, 0,
                    true, idPaciente,
                    false, 0,
                    false, null,
                    false, null,
                    false, null,
                    conexionBD);
            devolucion = getDiagnosticos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Diagnostico> getDiagnosticosPaciente(long idPaciente, int tipo,
            Connection conexionBD) {
        ArrayList<Diagnostico> devolucion = null;
        ResultSet resultado;

        try {

            resultado = getDiagnosticosBD(
                    false, null, null,
                    false, 0,
                    true, idPaciente,
                    true, tipo,
                    false, null,
                    false, null,
                    false, null,
                    conexionBD);

            devolucion = getDiagnosticos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Diagnostico> getDiagnosticosMedico(long idMedico, Connection conexionBD) {
        ArrayList<Diagnostico> devolucion = null;
        ResultSet resultado;

        try {

            resultado = getDiagnosticosBD(
                    false, null, null,
                    false, idMedico,
                    false, 0,
                    false, 0,
                    false, null,
                    false, null,
                    false, null,
                    conexionBD);

            devolucion = getDiagnosticos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Diagnostico> getDiagnosticosMedico(long idMedico, int tipo, Connection conexionBD) {
        ArrayList<Diagnostico> devolucion = null;
        ResultSet resultado;

        try {

            resultado = getDiagnosticosBD(
                    false, null, null,
                    false, idMedico, 
                    false, 0,
                    true, tipo, 
                    false, null, 
                    false, null,
                    false, null, conexionBD);

            devolucion = getDiagnosticos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de Médicos">
    public ArrayList<Medico> getMedicos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta a la Base de Datos (Capa de Datos)">        
    private static ArrayList<Diagnostico> getDiagnosticos(ResultSet diagnosticos, Connection conexionBD) {
        ArrayList<Diagnostico> devolucion;

        Diagnostico pacienteAux;
        LocalDate fechaAux = null;

        try {
            devolucion = new ArrayList<Diagnostico>();
            if (diagnosticos != null) {
                diagnosticos.beforeFirst();

                while (diagnosticos.next()) {

                    pacienteAux = new Diagnostico(conexionBD);

                    if (diagnosticos.getDate("fecha") != null) {
                        fechaAux = diagnosticos.getDate("fecha").toLocalDate();
                    } else {
                        fechaAux = null;
                    }

                    pacienteAux.setData(diagnosticos.getLong("id"), fechaAux,
                            diagnosticos.getLong("idMedico"), diagnosticos.getLong("idPaciente"),
                            diagnosticos.getInt("tipo"), diagnosticos.getString("codigo"),
                            diagnosticos.getString("descripcion"),
                            diagnosticos.getString("observaciones"));

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

    private static ResultSet getDiagnosticosBD(
            boolean filtroFecha, LocalDate fechaInicio, LocalDate fechaFin,
            boolean filtroIdMedico, long idMedico,
            boolean filtroIdPaciente, long idPaciente,
            boolean filtroTipo, int tipo,
            boolean filtroCodigo, String codigo,
            boolean filtroDescripcion, String descripcion,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT Diagnosticos.* "
                    + "FROM Diagnosticos "
                    + "WHERE Diagnosticos.eliminado IS NULL ";

            cadenaSQL = cadenaSQL + getCadenaFiltrosSQL(
                    filtroFecha, fechaInicio, fechaFin,
                    filtroIdMedico, idMedico,
                    filtroIdPaciente, idPaciente,
                    filtroTipo, tipo,
                    filtroCodigo, codigo,
                    filtroDescripcion, descripcion,
                    filtroObservaciones, observaciones);

            cadenaSQL = cadenaSQL + "ORDER BY fecha";

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = Vinculo.executeQuery(cadenaSQL);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static String getCadenaFiltrosSQL(
            boolean filtroFecha, LocalDate fechaInicio, LocalDate fechaFin,
            boolean filtroIdMedico, long idMedico,
            boolean filtroIdPaciente, long idPaciente,
            boolean filtroTipo, int tipo,
            boolean filtroCodigo, String codigo,
            boolean filtroDescripcion, String descripcion,
            boolean filtroObservaciones, String observaciones) {
        String devolucion = "";

        try {
            if (filtroFecha == true && fechaInicio != null) {
                if (fechaFin == null) {
                    fechaFin = fechaInicio;
                }
                devolucion = devolucion + "AND (fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "') ";
            }

            if (filtroIdMedico == true) {
                devolucion = devolucion + "AND idMedico =" + idMedico + " ";
            }

            if (filtroIdPaciente == true) {
                devolucion = devolucion + "AND idPaciente =" + idPaciente + " ";
            }

            if (filtroTipo == true) {
                devolucion = devolucion + "AND tipo =" + tipo + " ";
            }

            if (filtroCodigo == true && codigo != null) {
                devolucion = devolucion + "AND codigo LIKE '%" + codigo + "%' ";
            }

            if (filtroDescripcion == true && descripcion != null) {
                devolucion = devolucion + "AND descripcion LIKE '%" + descripcion + "%' ";
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
