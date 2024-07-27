package hospital.kernel;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author andres
 */
public class Medico {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private final Connection conexionBD;

    private long id;
    private long numColegiado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String observaciones;

    private ArrayList<Diagnostico> diagnosticos;
    private ArrayList<VisitaMedica> visitasMedicas;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public Medico(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Medico(long id, Connection conexionBD) {
        this.conexionBD = conexionBD;
        this.setId(id);
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public boolean setData(long id, long numColegiado, String nombre,
            String apellido1, String apellido2,
            String observaciones) {
        boolean devolucion;

        try {
            this.setId(id);
            devolucion = this.setData(numColegiado, nombre,
                    apellido1, apellido2, observaciones);

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setData(long numColegiado, String nombre,
            String apellido1, String apellido2,
            String observaciones) {
        boolean devolucion;

        try {
            devolucion = this.setNumColegiado(numColegiado) && this.setNombre(nombre);
            this.setApellido1(apellido1);
            this.setApellido2(apellido2);
            this.setObservaciones(observaciones);

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean setNumColegiado(long numColegiado) {
        boolean devolucion;

        if (numColegiado > 0) {
            this.numColegiado = numColegiado;
            devolucion = true;
        } else {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean setNombre(String nombre) {
        boolean devolucion;

        if (nombre != null) {
            this.nombre = nombre;
            devolucion = true;
        } else {
            devolucion = false;
        }

        return devolucion;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public long getId() {
        return id;
    }

    public long getNumColegiado() {
        return numColegiado;
    }

    public String getNombreFormalCompleto() {
        String devolucion;

        devolucion = this.getApellido1() + " " + this.getApellido2() + ", " + this.getNombre();

        return devolucion;
    }

    public String getNombre() {
        String devolucion;

        if (this.nombre != null) {
            devolucion = this.nombre;
        } else {
            devolucion = "";
        }

        return devolucion;
    }

    public String getApellido1() {
        String devolucion;

        if (this.apellido1 != null) {
            devolucion = this.apellido1;
        } else {
            devolucion = "";
        }

        return devolucion;
    }

    public String getApellido2() {
        String devolucion;

        if (this.apellido2 != null) {
            devolucion = this.apellido2;
        } else {
            devolucion = "";
        }

        return devolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public ArrayList<Diagnostico> getDiagnosticos() {

        try {
            this.diagnosticos = Diagnostico.getDiagnosticosMedico(this.getId(), conexionBD);
        } catch (Exception ex) {
            this.diagnosticos = null;
        }

        return this.diagnosticos;
    }

    public ArrayList<VisitaMedica> getVisitasMedicas() {

        try {
            this.visitasMedicas = VisitaMedica.getVisitasMedicasMedico(this.getId(), conexionBD);
        } catch (Exception ex) {
            this.visitasMedicas = null;
        }

        return this.visitasMedicas;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos para la Gestión de la Base de Datos">
    public boolean inicializarDesdeBD() {

        boolean devolucion;
        ResultSet resultado;
        String cadenaSQL;

        try {
            cadenaSQL = new String("SELECT * FROM Medicos WHERE eliminado IS NULL AND Id = " + this.getId());

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = Vinculo.executeQuery(cadenaSQL);

            if (resultado != null) {
                resultado.beforeFirst();

                if (resultado.next()) {

                    devolucion = this.setData(resultado.getLong("id"),
                            resultado.getLong("numColegiado"),
                            resultado.getString("nombre"),
                            resultado.getString("apellido1"),
                            resultado.getString("apellido2"),
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

            cadenaSQL = new String("INSERT INTO Medicos "
                    + "(numColegiado, nombre, apellido1, apellido2, observaciones) "
                    + "VALUES ('" + this.getNumColegiado() + "','"
                    + this.getNombre() + "','" + this.getApellido1() + "','"
                    + this.getApellido2() + "','" + this.getObservaciones() + "')");

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

            cadenaSQL = "UPDATE Medicos SET "
                    + "numColegiado = '" + this.getNumColegiado() + "', "
                    + "nombre = '" + this.getNombre() + "', "
                    + "apellido1 = '" + this.getApellido1() + "', "
                    + "apellido2 = '" + this.getApellido2() + "', "
                    + "observaciones = '" + this.getObservaciones() + "' "
                    + "WHERE Id = " + this.getId();

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

            cadenaSQL = "UPDATE Medicos SET eliminado=CURRENT_TIMESTAMP "
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
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta de Médicos">
    public static Medico getMedico(long id, Connection conexionBD) {
        Medico devolucion;

        devolucion = new Medico(id, conexionBD);
        if (devolucion.inicializarDesdeBD() == false) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Medico> getMedicos(boolean filtroNumColegiado, long numColegiado,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String apellido1,
            boolean filtroApellido2, String apellido2,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ArrayList<Medico> devolucion;
        ResultSet resultado;

        try {

            resultado = getMedicosBD(filtroNumColegiado, numColegiado,
                    filtroNombre, nombre,
                    filtroApellido1, apellido1,
                    filtroApellido2, apellido2,
                    filtroObservaciones, observaciones,
                    conexionBD);

            devolucion = getMedicos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Medico> getTodosMedicos(Connection conexionBD) {
        ArrayList<Medico> devolucion = null;
        ResultSet resultado;
        Medico medicoAux;

        try {

            resultado = getMedicosBD(false, 0, false, null,
                    false, null, false, null,
                    false, null, conexionBD);

            devolucion = getMedicos(resultado, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Medico> getTodosMedicos(String nombre, Connection conexionBD) {
        ArrayList<Medico> devolucion = new ArrayList<Medico>();

        try {
            devolucion = getMedicos(false, 0, true, nombre,
                    false, null, false, null,
                    false, null, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    public static ArrayList<Medico> getTodosMedicos(String apellido1, String apellido2, Connection conexionBD) {
        ArrayList<Medico> devolucion = new ArrayList<Medico>();

        try {
            devolucion = getMedicos(false, 0, false, null,
                    true, apellido1, true, apellido2,
                    false, null, conexionBD);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos de Consulta a la Base de Datos (Capa de Datos)">
    private static ArrayList<Medico> getMedicos(ResultSet medicos, Connection conexionBD) {
        ArrayList<Medico> devolucion;

        Medico medicoAux;

        try {
            devolucion = new ArrayList<Medico>();
            if (medicos != null) {
                medicos.beforeFirst();

                while (medicos.next()) {

                    medicoAux = new Medico(conexionBD);

                    medicoAux.setData(medicos.getLong("id"),
                            medicos.getLong("numColegiado"),
                            medicos.getString("nombre"),
                            medicos.getString("apellido1"),
                            medicos.getString("apellido2"),
                            medicos.getString("Observaciones"));

                    devolucion.add(medicoAux);
                }

            } else {
                devolucion = null;
            }

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static ResultSet getMedicosBD(boolean filtroNumColegiado, long numColegiado,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String apellido1,
            boolean filtroApellido2, String apellido2,
            boolean filtroObservaciones, String observaciones,
            Connection conexionBD) {
        ResultSet devolucion;
        String cadenaSQL;

        try {
            cadenaSQL = "SELECT * "
                    + "FROM Medicos "
                    + "WHERE Medicos.eliminado IS NULL ";

            cadenaSQL = cadenaSQL + getCadenaFiltrosSQL(filtroNumColegiado, numColegiado,
                    filtroNombre, nombre,
                    filtroApellido1, apellido1,
                    filtroApellido2, apellido2,
                    filtroObservaciones, observaciones);

            cadenaSQL = cadenaSQL + "ORDER BY apellido1,apellido2,nombre";

            Statement Vinculo = conexionBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            devolucion = Vinculo.executeQuery(cadenaSQL);

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;
    }

    private static String getCadenaFiltrosSQL(boolean filtroNumColegiado, long numColegiado,
            boolean filtroNombre, String nombre,
            boolean filtroApellido1, String apellido1,
            boolean filtroApellido2, String apellido2,
            boolean filtroObservaciones, String observaciones) {
        String devolucion = "";

        try {
            if (filtroNumColegiado == true) {
                devolucion = devolucion + "AND numColegiado = '" + numColegiado + "' ";
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
