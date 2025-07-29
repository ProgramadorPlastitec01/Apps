package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import Controller.SettingControllerJpa;

public class DataExport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        int opt = Integer.parseInt(request.getParameter("opt"));
        List lst_setting = null;

        try (PrintWriter out = response.getWriter()) {
            if (opt == 1) {
                // Conectar a la base de datos y obtener los datos
                Class.forName("com.mysql.jdbc.Driver");  // Asegurar que usa el conector correcto
                lst_setting = SettingJpa.ConsultSettingCategorie("ConnViewInformation");

                if (lst_setting != null) {
                    Object[] Obj_conn = (Object[]) lst_setting.get(0);
                    String[] ArgConn = Obj_conn[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");

                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://" + ArgConn[2], ArgConn[1], "");
                         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + ArgConn[4]);
                         ResultSet rs = stmt.executeQuery()) {

                        JSONArray jsonArray = new JSONArray();
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        while (rs.next()) {
                            JSONObject jsonObject = new JSONObject();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnLabel(i);  // Obtener el nombre de la columna
                                Object value = rs.getObject(i);  // Obtener el valor de la columna
                                jsonObject.put(columnName, value);  // Agregar al JSON
                            }
                            jsonArray.put(jsonObject);
                        }

                        out.print(jsonArray.toString());
                        out.flush();
                    }
                } else {
                    response.getWriter().write("{\"error\": \"Error al obtener datos\"}");
                }
            } else {
                response.getWriter().write("{\"error\": \"Opción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"Error al obtener datos\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que obtiene datos en formato JSON dinámicamente";
    }
}
