package Database;

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
   private Connection connection;
  */
  // Datos de conexion a la base de XAMPP
  private static final String URL = "jdbc:mysql://localhost:3306/Hospital";
  private static final String USER = "root";
  private static final String PASSWORD = "";
  private Connection connection;
  
    // Constructor de la clase, se encarga de establecer la conexion a la base de datos
    public DB() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo para cerrar la conexion a la base de datos
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    public List<Medico> obtenerMedicos() {
        String sql = "SELECT * FROM Medicos";
        List<Medico> medicos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Medico medico = new Medico(
                        resultSet.getLong("id"),
                        resultSet.getLong("numColegiado"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido1"),
                        resultSet.getString("apellido2"),
                        resultSet.getString("observaciones")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            close();
            e.printStackTrace();
        }
        close();
        return medicos;
    }
    
    
}
