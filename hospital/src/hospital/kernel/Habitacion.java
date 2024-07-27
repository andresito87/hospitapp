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
public class Habitacion {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private final Connection conexionBD;

    private long id;
    private int numHabitacion;
    private int numPlanta;
    private int numPlazas;
    private String observaciones;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public Habitacion(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Habitacion(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.id = id;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, int numHabitacion, int numPlanta,
            int numPlazas, String observaciones) {

        this.setId(id);
        this.setNumHabitacion(numHabitacion);
        this.setNumPlanta(numPlanta);
        this.setNumPlazas(numPlazas);
        this.setObservaciones(observaciones);

        return true;
    }

    public boolean setData(int numHabitacion, int numPlanta,
            int numPlazas, String observaciones) {

        this.setNumHabitacion(numHabitacion);
        this.setNumPlanta(numPlanta);
        this.setNumPlazas(numPlazas);
        this.setObservaciones(observaciones);

        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public void setNumPlanta(int numPlanta) {
        this.numPlanta = numPlanta;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public int getNumHabitacion() {
        return numHabitacion;
    }

    public int getNumPlanta() {
        return numPlanta;
    }

    public int getNumPlazas() {
        return numPlazas;
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
            cadenaSQL = "SELECT * FROM Habitaciones WHERE eliminado IS NULL AND Id = " + this.getId();

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"), resultado.getInt("numHabitacion"),
                            resultado.getInt("numPlanta"), resultado.getInt("numPlazas"),
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

            cadenaSQL = new String("INSERT INTO Habitaciones "
                    + "(numHabitacion,numPlanta,numPlazas,observaciones) "
                    + "VALUES (" + this.getNumHabitacion() + "," + this.getNumPlanta() + ","
                    + this.getNumPlazas() + ",'" + this.getObservaciones() + "')");

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

            cadenaSQL = "UPDATE Habitaciones SET "
                    + "numHabitacion = '" + this.getNumHabitacion() + "', "
                    + "numPlanta = '" + this.getNumPlanta() + "', "
                    + "numPlazas = '" + this.getNumPlazas() + "', "
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

            cadenaSQL = "UPDATE Habitaciones SET eliminado=CURRENT_TIMESTAMP "
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
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión Datos de Habitaciones">
    public static ArrayList<Habitacion> getTodasHabitaciones(boolean selected, int numHabitacion, boolean selected0, int planta, boolean selected1, int plazas, boolean selected2, String text, Connection conexionBD) {
        ArrayList<Habitacion> devolucion = null;
        ResultSet resultado;

        try {
            resultado = getHabitacionesBD(false, 0, false, 0,
                    false, 0,
                    false, null, conexionBD);

            devolucion = getHabitaciones(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static ResultSet getHabitacionesBD(
            boolean filtroNumHabitacion, int numHabitacion,
            boolean filtroPlanta, int planta,
            boolean filtroPlazas, int plazas,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * "
                    + "FROM Habitaciones "
                    + "WHERE Habitaciones.eliminado IS NULL ";

            cadenaSQL = cadenaSQL + getCadenaFiltrosSQL(
                    filtroNumHabitacion, numHabitacion,
                    filtroPlanta, planta,
                    filtroPlazas, plazas,
                    filtroObservaciones, observaciones);

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = Vinculo.executeQuery(cadenaSQL);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static String getCadenaFiltrosSQL(
            boolean filtroNumHabitacion, int numHabitacion,
            boolean filtroPlanta, int planta,
            boolean filtroPlazas, int plazas,
            boolean filtroObservaciones, String observaciones) {
        String devolucion = "";

        try {
            if (filtroNumHabitacion == true && numHabitacion >= 0) {
                devolucion = devolucion + "AND numHabitacion LIKE '%" + numHabitacion + "%' ";
            }

            if (filtroPlanta == true && planta >= 0) {
                devolucion = devolucion + "AND planta LIKE '%" + planta + "%' ";
            }

            if (filtroPlazas == true && plazas >= 0) {
                devolucion = devolucion + "AND plazas = " + plazas + " ";
            }

            if (filtroObservaciones == true && observaciones != null) {
                devolucion = devolucion + "AND observaciones LIKE '%" + observaciones + "%' ";
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Habitacion> getHabitaciones(ResultSet habitaciones, Connection conexionBD) {

        ArrayList<Habitacion> devolucion;
        Habitacion habitacionAux;

        try {
            devolucion = new ArrayList<Habitacion>();
            if (habitaciones != null) {
                habitaciones.beforeFirst();

                while (habitaciones.next()) {

                    habitacionAux = new Habitacion(conexionBD);

                    habitacionAux.setData(habitaciones.getLong("id"),
                            habitaciones.getInt("numHabitacion"), habitaciones.getInt("numPlanta"),
                            habitaciones.getInt("numPlazas"), habitaciones.getString("observaciones"));

                    devolucion.add(habitacionAux);
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
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión Datos de Pacientes">
    public static ArrayList<Paciente> getTodosPacientes(long idHabitacion, Connection conexionBD) {
        ArrayList<Paciente> devolucion = null;
        ResultSet resultado;

        try {
            resultado = getPacientesBD(false, null,
                    false, null,
                    false, null, false, null,
                    false, null,
                    false, null, null,
                    false, null,
                    false, null, null,
                    false, null, null,
                    false, 0,
                    false, null, conexionBD, idHabitacion);

            devolucion = getPacientes(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static ResultSet getPacientesBD(
            boolean filtroDni, String dni,
            boolean filtroCi, String ci,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String apellido1,
            boolean filtroApellido2, String apellido2,
            boolean filtroFechaNacimiento,
            LocalDate fechaNacimientoInicio, LocalDate fechaNacimientoFin,
            boolean filtroNumSeguridadSocial, String numSeguridadSocial,
            boolean filtroFechaIngreso,
            LocalDate fechaIngresoInicio, LocalDate fechaIngresoFin,
            boolean filtroFechaAlta,
            LocalDate fechaAltaInicio, LocalDate fechaAltaFin,
            boolean filtroIdCama, long idCama,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD, long idHabitacion) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT DISTINCT Pacientes.* "
                    + "FROM Pacientes,Ocupacionhabitaciones "
                    + "WHERE Pacientes.eliminado IS NULL "
                    + "AND Pacientes.id=Ocupacionhabitaciones.idPaciente "
                    + " AND Ocupacionhabitaciones.idHabitacion=" + idHabitacion;

            cadenaSQL = cadenaSQL + getCadenaFiltrosPacienteSQL(filtroDni, dni, filtroCi, ci,
                    filtroNombre, nombre, filtroApellido1, apellido1,
                    filtroApellido2, apellido2,
                    filtroFechaNacimiento, fechaNacimientoInicio, fechaNacimientoFin,
                    filtroNumSeguridadSocial, numSeguridadSocial,
                    filtroFechaIngreso, fechaIngresoInicio, fechaIngresoFin,
                    filtroFechaAlta, fechaAltaInicio, fechaAltaFin,
                    filtroIdCama, idCama, filtroObservaciones, observaciones);

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
            devolucion = new ArrayList<Paciente>();
            if (pacientes != null) {
                pacientes.beforeFirst();

                while (pacientes.next()) {

                    pacienteAux = new Paciente(conexionBD);
                    LocalDate fechaAlta;

                    if (pacientes.getDate("fechaAlta") != null) {
                        fechaAlta = pacientes.getDate("fechaAlta").toLocalDate();
                    } else {
                        fechaAlta = null;
                    }

                    pacienteAux.setData(pacientes.getLong("id"),
                            pacientes.getString("dni"),
                            pacientes.getString("ci"),
                            pacientes.getString("nombre"),
                            pacientes.getString("apellido1"),
                            pacientes.getString("apellido2"),
                            pacientes.getDate("fechaNacimiento").toLocalDate(),
                            pacientes.getString("numSegSocial"),
                            pacientes.getDate("fechaIngreso").toLocalDate(),
                            fechaAlta,
                            pacientes.getLong("idCama"),
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

    private static String getCadenaFiltrosPacienteSQL(
            boolean filtroDni, String dni,
            boolean filtroCi, String ci,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String apellido1,
            boolean filtroApellido2, String apellido2,
            boolean filtroFechaNacimiento, LocalDate fechaNacimientoInicio, LocalDate fechaNacimientoFin,
            boolean filtroNumSeguridadSocial, String numSeguridadSocial,
            boolean filtroFechaIngreso, LocalDate fechaIngresoInicio, LocalDate fechaIngresoFin,
            boolean filtroFechaAlta, LocalDate fechaAltaInicio, LocalDate fechaAltaFin,
            boolean filtroIdCama, long idCama,
            boolean filtroObservaciones, String observaciones) {
        String devolucion = "";

        try {
            if (filtroDni == true && dni != null) {
                devolucion = devolucion + "AND dni LIKE '%" + dni + "%' ";
            }

            if (filtroCi == true && ci != null) {
                devolucion = devolucion + "AND ci LIKE '%" + ci + "%' ";
            }

            if (filtroNombre == true && nombre != null) {
                devolucion = devolucion + "AND nombre LIKE '%" + nombre + "%' ";
            }

            if (filtroApellido1 == true && apellido1 != null) {
                devolucion = devolucion + "AND apellido1 LIKE '%" + apellido1 + "%' ";
            }

            if (filtroApellido2 == true && apellido2 != null) {
                devolucion = devolucion + "AND apellido2 LIKE '%" + apellido2 + "%' ";
            }

            if (filtroFechaNacimiento == true && fechaNacimientoInicio != null) {
                if (fechaNacimientoFin == null) {
                    fechaNacimientoFin = fechaNacimientoInicio;
                }
                devolucion = devolucion + "AND (fechaNacimiento BETWEEN '" + fechaNacimientoInicio + "' AND '" + fechaNacimientoFin + "') ";
            }

            if (filtroNumSeguridadSocial == true && numSeguridadSocial != null) {
                devolucion = devolucion + "AND numSegSocial LIKE '%" + numSeguridadSocial + "%' ";
            }

            if (filtroFechaIngreso == true && fechaIngresoInicio != null) {
                if (fechaIngresoFin == null) {
                    fechaIngresoFin = fechaIngresoInicio;
                }
                devolucion = devolucion + "AND (fechaIngreso BETWEEN '" + fechaIngresoInicio + "' AND '" + fechaIngresoFin + "') ";
            }

            if (filtroFechaAlta == true && fechaAltaInicio != null) {
                if (fechaAltaFin == null) {
                    fechaAltaFin = fechaAltaInicio;
                }
                devolucion = devolucion + "AND (fechaAlta BETWEEN '" + fechaAltaInicio + "' AND '" + fechaAltaFin + "') ";
            }

            if (filtroIdCama == true) {
                devolucion = devolucion + "AND idCama=" + idCama + " ";
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
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión Datos de Camas">
    
// </editor-fold>
// </editor-fold>
}
