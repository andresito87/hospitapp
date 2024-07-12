package database;

import models.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
/*
    //Datos de conexion a la base de datos MV Debian
    private static final String URL = "jdbc:mysql://192.168.1.10:3306/Hospital";
    private static final String USER = "usuariohospital";
    private static final String PASSWORD = "asdasd";
    private static Connection connection;
*/

    // Datos de conexion a la base de XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // Constructor de la clase, se encarga de establecer la conexion a la base de datos
    public static void connect() {
        try {
            DB.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
        }
    }

    public static void closeConnection() {
        if (DB.connection != null) {
            try {
                DB.connection.close();
                System.out.println("Conexión DB cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<Medico> obtenerMedicos() {
        String sql = "SELECT * FROM Medicos";
        List<Medico> medicos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Medico medico = new Medico(resultSet.getLong("id"), connection);
                medico.inicializarDesdeBD();
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return result;
    }

    public static List<Medico> obtenerPacientes() {
        String sql = "SELECT * FROM Pacientes";
        List<Medico> pacientes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Medico paciente = new Medico(resultSet.getLong("id"), connection);
                paciente.inicializarDesdeBD();
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }
}
