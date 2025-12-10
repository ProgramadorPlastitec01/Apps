package Metodos;

import java.sql.*;

public class Conexion {

    static String login = "APPS";
    static String password = "D4ruma";
    static String url = "jdbc:mysql://172.16.2.115:3306/daruma4v3_298_produccion";

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                System.out.println("Conexión a base de datos " + url + " ... Ok");
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }
}
