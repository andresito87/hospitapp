package hospital.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author javier
 */
public class VisitaMedica {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private final Connection conexionBD;

    private long id;

    private long idMedico;
    private Medico medico;

    private long idPaciente;
    private Paciente paciente;

    private LocalDate fecha;
    private String observaciones;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public VisitaMedica(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public VisitaMedica(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }

// </editor-fold>    
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, long idMedico,
            long idPaciente, LocalDate fecha,
            String observaciones) {

        this.setId(id);
        this.setData(idMedico, idPaciente, fecha, observaciones);
        return true;
    }

    public boolean setData(long idMedico,
            long idPaciente, LocalDate fecha,
            String observaciones) {

        this.setIdMedico(idMedico);
        this.setIdPaciente(idPaciente);
        this.setFecha(fecha);
        this.setObservaciones(observaciones);

        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public long getIdMedico() {
        return idMedico;
    }

    public Medico getMedico() {
        Medico devolucion;

        try {
            if (this.medico == null) {
                this.medico = Medico.getMedico(this.getIdMedico(), this.conexionBD);
            }
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
            if (this.paciente == null) {
                this.paciente = Paciente.getPaciente(this.getIdPaciente(), this.conexionBD);
            }
            devolucion = this.paciente;
        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public LocalDate getFecha() {
        return fecha;
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
            cadenaSQL = new String("SELECT * FROM VisitasMedicas WHERE eliminado IS NULL AND Id = " + this.getId());

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

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getLong("idMedico"),
                            resultado.getLong("idPaciente"),
                            fecha, resultado.getString("observaciones"));
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

            cadenaSQL = new String("INSERT INTO VisitasMedicas "
                    + "(idMedico,idPaciente,fecha,observaciones) "
                    + "VALUES ('" + this.getIdMedico() + "','"
                    + this.getIdPaciente() + "','" + this.getFecha() + "','"
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

            cadenaSQL = "UPDATE VisitasMedicas SET "
                    + "idMedico = '" + this.getIdMedico() + "', "
                    + "idPaciente = '" + this.getIdPaciente() + "', "
                    + "fecha = '" + this.getFecha() + "', "
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

            cadenaSQL = "UPDATE VisitasMedicas SET eliminado=CURRENT_TIMESTAMP "
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
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de visitas Médicas">
    public static VisitaMedica getVisitaMedica(long id, Connection conexionBD) {
        VisitaMedica devolucion;

        devolucion = new VisitaMedica(id, conexionBD);
        if (devolucion.inicializarDesdeBD() == false) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<VisitaMedica> getVisitasMedicas(boolean filtroFecha,
            LocalDate fechaInicio, LocalDate fechaFin,
            boolean filtroIdMedico, long idMedico,
            boolean filtroIdPaciente, long idPaciente,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ArrayList<VisitaMedica> devolucion;
        ResultSet resultado;

        try {

            resultado = getVisitasMedicasBD(filtroFecha, fechaInicio, fechaFin,
                    filtroIdMedico, idMedico,
                    filtroIdPaciente, idPaciente,
                    filtroObservaciones, observaciones,
                    conexionBD);

            devolucion = getVisitasMedicas(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<VisitaMedica> getTodosVisitasMedicas(Connection conexionBD) {
        ArrayList<VisitaMedica> devolucion = null;
        ResultSet resultado;
        VisitaMedica pacienteAux;

        try {

            resultado = getVisitasMedicasBD(false, null, null,
                    false, 0, false, 0,
                    false, null, conexionBD);

            devolucion = getVisitasMedicas(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<VisitaMedica> getVisitasMedicasPaciente(long idPaciente, Connection conexionBD) {
        ArrayList<VisitaMedica> devolucion = null;
        ResultSet resultado;
        VisitaMedica pacienteAux;

        try {

            resultado = getVisitasMedicasBD(false, null, null,
                    false, 0, true, idPaciente,
                    false, null, conexionBD);

            devolucion = getVisitasMedicas(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<VisitaMedica> getVisitasMedicasMedico(long idMedico, Connection conexionBD) {
        ArrayList<VisitaMedica> devolucion = null;
        ResultSet resultado;
        VisitaMedica pacienteAux;

        try {

            resultado = getVisitasMedicasBD(false, null, null,
                    false, idMedico, false, 0,
                    false, null, conexionBD);

            devolucion = getVisitasMedicas(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static ArrayList<VisitaMedica> getVisitasMedicas(ResultSet visitasMedicas, Connection conexionBD) {
        ArrayList<VisitaMedica> devolucion;

        VisitaMedica pacienteAux;
        LocalDate fechaAux = null;

        try {
            devolucion = new ArrayList<VisitaMedica>();
            if (visitasMedicas != null) {
                visitasMedicas.beforeFirst();

                while (visitasMedicas.next()) {

                    pacienteAux = new VisitaMedica(conexionBD);

                    if (visitasMedicas.getDate("fecha") != null) {
                        fechaAux = visitasMedicas.getDate("fecha").toLocalDate();
                    } else {
                        fechaAux = null;
                    }

                    pacienteAux.setData(visitasMedicas.getLong("id"),
                            visitasMedicas.getLong("idMedico"),
                            visitasMedicas.getLong("idPaciente"),
                            fechaAux,
                            visitasMedicas.getString("observaciones"));

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

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta a la Base de Datos (Capa de Datos)">
    private static ResultSet getVisitasMedicasBD(boolean filtroFecha,
            LocalDate fechaInicio, LocalDate fechaFin,
            boolean filtroIdMedico, long idMedico,
            boolean filtroIdPaciente, long idPaciente,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT VisitasMedicas.* "
                    + "FROM VisitasMedicas "
                    + "WHERE VisitasMedicas.eliminado IS NULL ";

            cadenaSQL = cadenaSQL + getCadenaFiltrosSQL(filtroFecha, fechaInicio, fechaFin,
                    filtroIdMedico, idMedico,
                    filtroIdPaciente, idPaciente,
                    filtroObservaciones, observaciones);

            cadenaSQL = cadenaSQL + "ORDER BY fecha";

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = Vinculo.executeQuery(cadenaSQL);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static String getCadenaFiltrosSQL(boolean filtroFecha,
            LocalDate fechaInicio, LocalDate fechaFin,
            boolean filtroIdMedico, long idMedico,
            boolean filtroIdPaciente, long idPaciente,
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
