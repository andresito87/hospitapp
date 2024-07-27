package hospital.kernel;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author andres
 */
public class Paciente {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private Connection conexionBD;

    private long id;
    private String dni;
    private String ci;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String numSeguridadSocial;
    private long idCama;
    private Cama cama;
    private LocalDate fechaIngreso;
    private LocalDate fechaAlta;
    private String observaciones;

    ArrayList<Diagnostico> diagnosticos;

    ArrayList<VisitaMedica> visitasMedicas;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public Paciente(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Paciente(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }

// </editor-fold>    
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, String dni, String ci, String nombre,
            String apellido1, String apellido2, LocalDate fechaNacimiento,
            String numSeguridadSocial, LocalDate fechaIngreso,
            LocalDate fechaAlta, long idCama, String observaciones) {
        boolean devolucion;

        try {
            this.setId(id);
            devolucion = this.setData(dni, ci, nombre, apellido1, apellido2, fechaNacimiento,
                    numSeguridadSocial, fechaIngreso, fechaAlta, idCama, observaciones);
        } catch (Exception ex) {
            devolucion = false;
        }

        return true;
    }

    public boolean setData(long id, String dni, String ci, String nombre,
            String apellido1, String apellido2, Date fechaNacimiento,
            String numSeguridadSocial, Date fechaIngreso,
            Date fechaAlta, long idCama, String observaciones) {
        this.setId(id);
        this.setData(dni, ci, nombre, apellido1, apellido2, fechaNacimiento,
                numSeguridadSocial, fechaIngreso, fechaAlta, idCama, observaciones);

        return true;
    }

    public boolean setData(String dni, String ci, String nombre,
            String apellido1, String apellido2, LocalDate fechaNacimiento,
            String numSeguridadSocial, LocalDate fechaIngreso,
            LocalDate fechaAlta, long idCama, String observaciones) {
        boolean devolucion;

        try {
            this.setDni(dni);
            this.setCi(ci);
            this.setNombre(nombre);
            this.setApellido1(apellido1);
            this.setApellido2(apellido2);
            devolucion = this.setFechaNacimiento(fechaNacimiento)
                    && this.setFechaIngreso(fechaIngreso)
                    && this.setFechaAlta(fechaAlta);
            this.setNumSeguridadSocial(numSeguridadSocial);
            this.setIdCama(idCama);
            this.setObservaciones(observaciones);

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setData(String dni, String ci, String nombre,
            String apellido1, String apellido2, Date fechaNacimiento,
            String numSeguridadSocial, Date fechaIngreso,
            Date fechaAlta, long idCama, String observaciones) {
        boolean devolucion;

        try {
            this.setDni(dni);
            this.setCi(ci);
            this.setNombre(nombre);
            this.setApellido1(apellido1);
            this.setApellido2(apellido2);
            devolucion = this.setFechaNacimiento(fechaNacimiento)
                    && this.setFechaIngreso(fechaIngreso)
                    && this.setFechaAlta(fechaAlta);
            this.setNumSeguridadSocial(numSeguridadSocial);
            this.setIdCama(idCama);
            this.setObservaciones(observaciones);

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setData(String dni, String ci, String nombre,
            String apellido1, String apellido2, String fechaNacimiento,
            String numSeguridadSocial, String fechaIngreso,
            String fechaAlta, Cama cama, String observaciones) {
        boolean devolucion;

        try {
            this.setDni(dni);
            this.setCi(ci);
            this.setNombre(nombre);
            this.setApellido1(apellido1);
            this.setApellido2(apellido2);
            this.setNumSeguridadSocial(numSeguridadSocial);
            this.setObservaciones(observaciones);

            devolucion = this.setFechaNacimiento(fechaNacimiento);
            devolucion = this.setFechaIngreso(fechaIngreso) && devolucion;
            devolucion = this.setFechaAlta(fechaAlta) && devolucion;
            devolucion = this.setCama(cama) && devolucion;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String Apellido1) {
        this.apellido1 = Apellido1;
    }

    public void setApellido2(String Apellido2) {
        this.apellido2 = Apellido2;
    }

    public boolean setFechaNacimiento(LocalDate fechaNacimiento) {
        boolean devolucion;

        try {
            this.fechaNacimiento = fechaNacimiento;
            devolucion = true;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaNacimiento(Date fechaNacimiento) {
        boolean devolucion;

        try {
            if (fechaNacimiento != null) {
                this.setFechaNacimiento(fechaNacimiento.toLocalDate());
            } else {
                this.fechaNacimiento = null;
            }

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaNacimiento(String fechaNacimiento) {
        boolean devolucion;

        try {
            devolucion = this.setFechaNacimiento(LocalDate.parse(fechaNacimiento,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public void setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSeguridadSocial = numSeguridadSocial;
    }

    public void setIdCama(long idCama) {
        this.idCama = idCama;
    }

    public boolean setCama(Cama cama) {
        boolean devolucion;

        try {
            if (cama != null) {
                this.idCama = cama.getId();
            } else {
                this.idCama = 0;
            }
            this.cama = cama;

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaIngreso(LocalDate fechaIngreso) {
        boolean devolucion;

        try {
            this.fechaIngreso = fechaIngreso;
            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaIngreso(Date fechaIngreso) {
        boolean devolucion;

        try {
            if (fechaIngreso != null) {
                this.setFechaIngreso(fechaIngreso.toLocalDate());
            } else {
                this.fechaIngreso = null;
            }

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaIngreso(String fechaIngreso) {
        boolean devolucion;

        try {
            devolucion = this.setFechaIngreso(LocalDate.parse(fechaIngreso,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaAlta(LocalDate fechaAlta) {
        boolean devolucion;

        try {
            this.fechaAlta = fechaAlta;
            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaAlta(Date fechaAlta) {
        boolean devolucion;

        try {
            if (fechaAlta != null) {
                this.setFechaAlta(fechaAlta.toLocalDate());
            } else {
                this.fechaAlta = null;
            }

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setFechaAlta(String fechaAlta) {
        boolean devolucion;

        try {
            devolucion = this.setFechaIngreso(LocalDate.parse(fechaAlta,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getCi() {
        return ci;
    }

    public String getNombreFormalCompleto() {
        String devolucion;

        devolucion = this.getApellido1() + " " + this.getApellido2() + ", " + this.getNombre();

        return devolucion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaNacimiento(String formato) {
        String devolucion = null;
        String formatoFecha;

        try {
            if (formato == null) {
                formatoFecha = "dd/MM/yyyy";
            } else {
                formatoFecha = formato;
            }

            if (this.fechaNacimiento != null) {
                devolucion = this.fechaNacimiento.format(DateTimeFormatter.ofPattern(formatoFecha));
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }

    public long getIdCama() {
        return idCama;
    }

    public Cama getCama() {

        if (this.cama == null) {
            this.cama = Cama.getCama(this.idCama, conexionBD);
        }

        return this.cama;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaIngreso(String formato) {
        String devolucion = null;
        String formatoFecha;

        try {
            if (formato == null) {
                formatoFecha = "dd/MM/yyyy";
            } else {
                formatoFecha = formato;
            }

            if (this.fechaIngreso != null) {
                devolucion = this.fechaIngreso.format(DateTimeFormatter.ofPattern(formatoFecha));
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public String getFechaAlta(String formato) {
        String devolucion = null;
        String formatoFecha;

        try {
            if (formato == null) {
                formatoFecha = "dd/MM/yyyy";
            } else {
                formatoFecha = formato;
            }

            if (this.fechaAlta != null) {
                devolucion = this.fechaAlta.format(DateTimeFormatter.ofPattern(formatoFecha));
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public ArrayList<Diagnostico> getDiagnosticos() {
        ArrayList<Diagnostico> devolucion;

        try {
            this.diagnosticos = Diagnostico.getDiagnosticosPaciente(this.getId(), this.conexionBD);

            devolucion = this.diagnosticos;
        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public ArrayList<VisitaMedica> getVisitasMedicas() {
        ArrayList<VisitaMedica> devolucion;

        try {
            this.visitasMedicas = VisitaMedica.getVisitasMedicasPaciente(this.getId(), this.conexionBD);
            devolucion = this.visitasMedicas;
        } catch (Exception ex) {
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
            cadenaSQL = new String("SELECT * FROM Pacientes WHERE eliminado IS NULL AND Id = " + this.getId());

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getString("dni"), resultado.getString("ci"),
                            resultado.getString("nombre"), resultado.getString("apellido1"),
                            resultado.getString("apellido2"),
                            resultado.getDate("fechaNacimiento"),
                            resultado.getString("numSegSocial"),
                            resultado.getDate("fechaIngreso"),
                            resultado.getDate("fechaAlta"),
                            resultado.getLong("idCama"),
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

    public boolean agregar() {

        boolean devolucion = true;
        String cadenaSQL;

        PreparedStatement comandoSQL;
        ResultSet claveGenerada;

        try {

            cadenaSQL = new String("INSERT INTO Pacientes "
                    + "(dni, ci, nombre, apellido1, apellido2, fechaNacimiento, "
                    + "numSegSocial, idCama, fechaIngreso, fechaAlta, observaciones) "
                    + "VALUES ('" + this.getDni() + "','" + this.getCi() + "','"
                    + this.getNombre() + "','" + this.getApellido1() + "','"
                    + this.getApellido2() + "','" + this.getFechaNacimiento() + "','"
                    + this.getNumSeguridadSocial() + "'," + this.getIdCama() + ",'"
                    + this.getFechaIngreso() + "','" + this.getFechaAlta() + "','"
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

        String fechaNacimiento = null;
        String fechaIngreso = null;
        String fechaAlta = null;

        PreparedStatement Comando_SQL;

        try {

            cadenaSQL = "UPDATE Pacientes SET "
                    + "dni = '" + this.getDni() + "', "
                    + "ci = '" + this.getCi() + "', "
                    + "nombre = '" + this.getNombre() + "', "
                    + "apellido1 = '" + this.getApellido1() + "', "
                    + "apellido2 = '" + this.getApellido2() + "', "
                    + "numSegSocial = '" + this.getNumSeguridadSocial() + "', "
                    + "idCama = '" + this.getIdCama() + "', "
                    + "observaciones = '" + this.getObservaciones() + "' ";

            if (this.getFechaNacimiento() != null) {
                cadenaSQL = cadenaSQL + ", fechaNacimiento='" + this.getFechaNacimiento().toString() + "' ";
            }

            if (this.getFechaIngreso() != null) {
                cadenaSQL = cadenaSQL + ", fechaIngreso='" + this.getFechaIngreso().toString() + "' ";
            }

            if (this.getFechaAlta() != null) {
                cadenaSQL = cadenaSQL + ", fechaAlta='" + this.getFechaAlta().toString() + "' ";
            }

            cadenaSQL = cadenaSQL + "WHERE Id = " + this.getId();

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

            cadenaSQL = "UPDATE Pacientes SET eliminado=CURRENT_TIMESTAMP "
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
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de Pacientes">
    public static Paciente getPaciente(long id, Connection conexionBD) {
        Paciente devolucion;

        devolucion = new Paciente(id, conexionBD);
        if (devolucion.inicializarDesdeBD() == false) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Paciente> getPacientes(boolean filtroDni, String dni,
            boolean filtroCi, String ci,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String Apellido1,
            boolean filtroApellido2, String Apellido2,
            boolean filtroFechaNacimiento,
            LocalDate fechaNacimientoInicio, LocalDate fechaNacimientoFin,
            boolean filtroNumSeguridadSocial, String numSeguridadSocial,
            boolean filtroFechaIngreso,
            LocalDate fechaIngresoInicio, LocalDate fechaIngresoFin,
            boolean filtroFechaAlta,
            LocalDate fechaAltaInicio, LocalDate fechaAltaFin,
            boolean filtroIdCama, long idCama,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ArrayList<Paciente> devolucion;
        ResultSet resultado;

        try {

            resultado = getPacientesBD(filtroDni, dni, filtroCi, ci,
                    filtroNombre, nombre, filtroApellido1, Apellido1,
                    filtroApellido2, Apellido2,
                    filtroFechaNacimiento, fechaNacimientoInicio, fechaNacimientoFin,
                    filtroNumSeguridadSocial, numSeguridadSocial,
                    filtroFechaIngreso, fechaIngresoInicio, fechaIngresoFin,
                    filtroFechaAlta, fechaAltaInicio, fechaAltaFin,
                    filtroIdCama, idCama, filtroObservaciones, observaciones,
                    conexionBD);

            devolucion = getPacientes(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Paciente> getTodosPacientes(Connection conexionBD) {
        ArrayList<Paciente> devolucion = null;
        ResultSet resultado;

        try {
            resultado = getPacientesBD(false, null, false, null,
                    false, null, false, null,
                    false, null,
                    false, null, null,
                    false, null,
                    false, null, null,
                    false, null, null,
                    false, 0,
                    false, null, conexionBD);

            devolucion = getPacientes(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de Habitaciones">
    public static ArrayList<Habitacion> getHabitaciones(ArrayList<Paciente> pacientes) {
        ArrayList<Habitacion> devolucion = new ArrayList<>();
        HashSet<Habitacion> habitacionesSet = new HashSet<>();

        for (Paciente paciente : pacientes) {
            Habitacion habitacion = paciente.getHabitacion();
            if (habitacion != null && habitacionesSet.add(habitacion)) {
                devolucion.add(habitacion);
            }
        }

        return devolucion;

    }

    private Habitacion getHabitacion() {
        Habitacion habitacion = null;
        ResultSet devolucion;
        String cadenaSQL = "SELECT Habitaciones.* FROM Habitaciones"
                + " INNER JOIN Ocupacionhabitaciones ON Habitaciones.id = Ocupacionhabitaciones.idHabitacion"
                + " INNER JOIN Pacientes ON Ocupacionhabitaciones.idPaciente = Pacientes.id "
                + "WHERE Pacientes.eliminado IS NULL "
                + "AND Pacientes.id = " + this.getId();

        try {
            Statement vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = vinculo.executeQuery(cadenaSQL);

            if (devolucion.next()) {
                long numHabitacion = Long.valueOf(devolucion.getInt("numHabitacion"));
                habitacion = new Habitacion(numHabitacion, conexionBD);
                if(!habitacion.inicializarDesdeBD()){
                    habitacion = null;
                }
            }
        } catch (Exception ex) {
            habitacion = null;
        }

        return habitacion;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta a la Base de Datos (Capa de Datos)">

    private static ArrayList<Paciente> getPacientes(ResultSet pacientes, Connection conexionBD) {
        ArrayList<Paciente> devolucion;

        Paciente pacienteAux;

        try {
            devolucion = new ArrayList<Paciente>();
            if (pacientes != null) {
                pacientes.beforeFirst();

                while (pacientes.next()) {

                    pacienteAux = new Paciente(conexionBD);

                    pacienteAux.setData(pacientes.getLong("id"),
                            pacientes.getString("dni"), pacientes.getString("ci"),
                            pacientes.getString("nombre"), pacientes.getString("apellido1"),
                            pacientes.getString("apellido2"),
                            pacientes.getDate("fechaNacimiento"),
                            pacientes.getString("numSegSocial"),
                            pacientes.getDate("fechaIngreso"),
                            pacientes.getDate("fechaAlta"),
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

    private static ArrayList<Paciente> getPacientesOld(ResultSet pacientes, Connection conexionBD) {
        ArrayList<Paciente> devolucion;

        Paciente pacienteAux;
        LocalDate fechaNacimientoAux = null;
        LocalDate fechaIngresoAux = null;
        LocalDate fechaAltaAux = null;

        try {
            devolucion = new ArrayList<Paciente>();
            if (pacientes != null) {
                pacientes.beforeFirst();

                while (pacientes.next()) {

                    pacienteAux = new Paciente(conexionBD);

                    if (pacientes.getDate("fechaNacimiento") != null) {
                        fechaNacimientoAux = pacientes.getDate("fechaNacimiento").toLocalDate();
                    }

                    if (pacientes.getDate("fechaIngreso") != null) {
                        fechaIngresoAux = pacientes.getDate("fechaIngreso").toLocalDate();
                    }

                    if (pacientes.getDate("fechaAlta") != null) {
                        fechaAltaAux = pacientes.getDate("fechaAlta").toLocalDate();
                    }

                    pacienteAux.setData(pacientes.getLong("id"),
                            pacientes.getString("dni"), pacientes.getString("ci"),
                            pacientes.getString("nombre"), pacientes.getString("apellido1"),
                            pacientes.getString("apellido2"),
                            fechaNacimientoAux,
                            pacientes.getString("numSegSocial"),
                            fechaIngresoAux, fechaAltaAux,
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

    public static ResultSet getPacientesBD(
            boolean filtroDni, String dni,
            boolean filtroCi, String ci,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String Apellido1,
            boolean filtroApellido2, String Apellido2,
            boolean filtroFechaNacimiento,
            LocalDate fechaNacimientoInicio, LocalDate fechaNacimientoFin,
            boolean filtroNumSeguridadSocial, String numSeguridadSocial,
            boolean filtroFechaIngreso,
            LocalDate fechaIngresoInicio, LocalDate fechaIngresoFin,
            boolean filtroFechaAlta,
            LocalDate fechaAltaInicio, LocalDate fechaAltaFin,
            boolean filtroIdCama, long idCama,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT Pacientes.* "
                    + "FROM Pacientes "
                    + "WHERE Pacientes.eliminado IS NULL ";

            cadenaSQL = cadenaSQL + getCadenaFiltrosSQL(filtroDni, dni, filtroCi, ci,
                    filtroNombre, nombre, filtroApellido1, Apellido1,
                    filtroApellido2, Apellido2,
                    filtroFechaNacimiento, fechaNacimientoInicio, fechaNacimientoFin,
                    filtroNumSeguridadSocial, numSeguridadSocial,
                    filtroFechaIngreso, fechaIngresoInicio, fechaIngresoFin,
                    filtroFechaAlta, fechaAltaInicio, fechaAltaFin,
                    filtroIdCama, idCama, filtroObservaciones, observaciones);

            cadenaSQL = cadenaSQL + "ORDER BY apellido1,apellido2,nombre";
            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = Vinculo.executeQuery(cadenaSQL);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static String getCadenaFiltrosSQL(boolean filtroDni, String dni,
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
// </editor-fold>
}
