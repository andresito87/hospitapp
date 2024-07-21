package database;

import models.Diagnostico;
import models.Medico;
import models.Paciente;
import models.VisitaMedica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DB {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    // Datos de conexion a la DB de XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    //Datos de conexion a la DB de MV Debian
    //private static final String URL = "jdbc:mysql://192.168.1.10:3306/Hospital";
    //private static final String USER = "usuariohospital";
    //private static final String PASSWORD = "asdasd";


    private static Connection connection;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Conexion y Desconexion">
    public static Connection connect() {
        if (DB.connection == null) {
            try {
                DB.connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión DB establecida");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos");
            }
        }
        return DB.connection;
    }

    public static void closeConnection() {
        if (DB.connection != null) {
            try {
                DB.connection.close();
                System.out.println("Conexión DB cerrada");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la base de datos");
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos para Médicos">
    public static List<Medico> obtenerMedicos() {
        String sql = "SELECT * FROM Medicos WHERE eliminado IS NULL";
        List<Medico> medicos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Medico medico = new Medico(resultSet.getLong("id"), connection);
                if (medico.inicializarDesdeBD()) {
                    medicos.add(medico);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los médicos de la base de datos");
        }
        return medicos;
    }

    public static boolean saveMedician(Medico medician) {
        String sql = "INSERT INTO Medicos (numColegiado, nombre, apellido1, apellido2, observaciones) VALUES (?, ?, ?, ?, ?)";
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, medician.getNumColegiado());
            preparedStatement.setString(2, medician.getNombre());
            preparedStatement.setString(3, medician.getApellido1());
            preparedStatement.setString(4, medician.getApellido2());
            preparedStatement.setString(5, medician.getObservaciones());
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("Error al guardar el médico en la base de datos");
        }
        return result;
    }

    public static List<Medico> obtenerMedicosFiltered(Map<String, String> filterData) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Medicos");
        if (!filterData.isEmpty()) {
            sql.append(" WHERE ");
            for (Map.Entry<String, String> entry : filterData.entrySet()) {
                sql.append(entry.getKey()).append(" LIKE '%").append(entry.getValue()).append("%' AND ");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 5));

        }
        List<Medico> medicos = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery(sql.toString());
            while (resultSet.next()) {
                Medico medico = new Medico(resultSet.getLong("id"), connection);
                if (medico.inicializarDesdeBD()) {
                    medicos.add(medico);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los médicos de la base de datos");
        }
        return medicos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos para Pacientes">
    public static List<Paciente> obtenerPacientes() {
        String sql = "SELECT * FROM Pacientes WHERE eliminado IS NULL";
        List<Paciente> pacientes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Paciente paciente = new Paciente(resultSet.getLong("id"), connection);

                if (paciente.inicializarDesdeBD()) {
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los pacientes de la base de datos");
        }
        
        return pacientes;
    }

    public static List<Paciente> obtenerPacientesFiltered(Map<String, String> checkedMap) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Pacientes");
        if (!checkedMap.isEmpty()) {
            sql.append(" WHERE ");
            for (Map.Entry<String, String> entry : checkedMap.entrySet()) {
                sql.append(entry.getKey()).append(" LIKE '%").append(entry.getValue()).append("%' AND ");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 5));
        }
        List<Paciente> pacientes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery(sql.toString());
            while (resultSet.next()) {
                Paciente paciente = new Paciente(resultSet.getLong("id"), connection);
                if (paciente.inicializarDesdeBD()) {
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los pacientes de la base de datos");
        }
        return pacientes;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos para Diagnósticos">
    public static List<Diagnostico> obtenerDiagnosticosPaciente(long id) {
        String sql = "SELECT * FROM Diagnosticos WHERE idPaciente = ? AND eliminado IS NULL";
        List<Diagnostico> diagnosticos = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                diagnosticos.add(new Diagnostico(resultSet.getLong("id"), connection));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los diagnósticos del paciente de la base de datos");
        }
        return diagnosticos;
    }

    public static List<Diagnostico> obtenerDiagnosticosMedico(long id){
        String sql = "SELECT * FROM Diagnosticos WHERE idMedico = ?";
        List<Diagnostico> diagnosticos = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                diagnosticos.add(new Diagnostico(resultSet.getLong("id"), connection));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los diagnósticos del médico de la base de datos");
        }
        return diagnosticos;
    }

    public static Medico obtenerMedico(long idMedico) {
        String sql = "SELECT * FROM Medicos WHERE id = ? AND eliminado IS NULL";
        Medico medico = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idMedico);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                medico = new Medico(resultSet.getLong("id"), connection);
                medico.inicializarDesdeBD();
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el médico de la base de datos");
        }
        return medico;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static List<VisitaMedica> obtenerVisitasMedicasPaciente(long id) {
        String sql = "SELECT * FROM VisitasMedicas WHERE idPaciente = ? AND eliminado IS NULL";
        List<VisitaMedica> visitasMedicas = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                visitasMedicas.add(new VisitaMedica(resultSet.getLong("id"), connection));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las visitas médicas del paciente de la base de datos");
        }
        return visitasMedicas;
    }

    // </editor-fold>

}
