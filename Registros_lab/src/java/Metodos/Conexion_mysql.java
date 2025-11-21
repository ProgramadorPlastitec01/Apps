package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion_mysql {

    public static void main(String[] args) {
        Conexion_mysql db = new Conexion_mysql();
        db.MySQLConnect();
    }
    Connection conexion = null;
    Statement comando = null;
    ResultSet registro;

    public Connection MySQLConnect() {
        try {
            //Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost:3306/Copec";
            String usuario = "root";
            String pass = "";
            conexion = DriverManager.getConnection(servidor, usuario, pass);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
            JOptionPane.showMessageDialog(null, "Conexión Exitosa");
            return conexion;
        }
    }
}
