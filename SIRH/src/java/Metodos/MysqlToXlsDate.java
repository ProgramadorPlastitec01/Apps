package Metodos;

import java.io.*;
import java.sql.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;

public class MysqlToXlsDate {

    private Connection connection = null;

    String database = "sirh";
    String user = "root";
    String password = "hEMu88";

    public static void main(String filepath, String filename, String startdate, String enddate) {
        try {
            MysqlToXlsDate mysqlToXls = new MysqlToXlsDate();
            mysqlToXls.generateXls(filepath, filename, startdate, enddate);
            mysqlToXls.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MysqlToXlsDate() throws ClassNotFoundException, SQLException {
        // Create MySQL database connection
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3307/" + database;
        connection = DriverManager.getConnection(url, user, password);
    }

    public void generateXls(String tablename, String filename, String startdate, String enddate)
            throws SQLException, FileNotFoundException, IOException {

        // Create new Excel workbook and sheet
        HSSFWorkbook xlsWorkbook = new HSSFWorkbook();
        HSSFSheet xlsSheet = xlsWorkbook.createSheet();
        short rowIndex = 0;
        ResultSet rs = null;
        // Execute SQL query
        if (tablename.contains("ingresos")) {
            PreparedStatement stmt = connection.prepareStatement("select * from " + tablename + " WHERE Fecha_contrato BETWEEN '" + startdate + "' AND '" + enddate + "' ORDER BY Fecha ASC");
            rs = stmt.executeQuery();
        } else {
            PreparedStatement stmt = connection.prepareStatement("select * from " + tablename + " WHERE Fecha BETWEEN '" + startdate + "' AND '" + enddate + "' ORDER BY Fecha ASC");
            rs = stmt.executeQuery();
        }

        // Get the list of column names and store them as the first
        // row of the spreadsheet.
        ResultSetMetaData colInfo = rs.getMetaData();
        List colNames = new ArrayList();
        HSSFRow titleRow = xlsSheet.createRow(rowIndex++);

        for (int i = 1; i <= colInfo.getColumnCount(); i++) {
            colNames.add(colInfo.getColumnName(i));
            titleRow.createCell((short) (i - 1)).setCellValue(new HSSFRichTextString(colInfo.getColumnName(i)));
//            titleRow.createCell((short) (i - 1)).setCellValue(new HSSFRequestichTextString(colInfo.getColumnName(i)));
            xlsSheet.setColumnWidth((short) (i - 1), (short) 4000);
        }

        // Save all the data from the database table rows
        while (rs.next()) {
            HSSFRow dataRow = xlsSheet.createRow(rowIndex++);
            short colIndex = 0;
            for (Object colName : colNames) {
                dataRow.createCell(colIndex++).setCellValue(new HSSFRichTextString(rs.getString(colName.toString())));
            }
        }
        // Write to disk
        //xlsWorkbook.write(new FileOutputStream(filename));
        //FileOutputStream fio = new FileOutputStream("D:\\Administracion\\RRHH\\Datos RRHH\\SIRH_ARCHIVOS\\" + filename);
        //FileOutputStream fio = new FileOutputStream("G:\\Intercambio\\SIRH_ARCHIVOS\\" + filename);
//        FileOutputStream fio = new FileOutputStream("G:\\Administracion\\SIRH_Archivos\\" + filename);
//        FileOutputStream fio = new FileOutputStream("\\\\172.16.1.164\\c$\\Users\\Programador.TI1\\Desktop\\SIRH_Archivos\\" + filename);
        FileOutputStream fio = new FileOutputStream("C:\\SIRH_Archivos\\" + filename);
        xlsWorkbook.write(fio);
        xlsWorkbook.close();
        fio.close();
        //Runtime.getRuntime().exec("cmd /c start K:\\Reportes_SIRH\\" + filename);
        //Runtime.getRuntime().exec("cmd /c start %windir%\\explorer.exe K:\\Reportes_SIRH");
    }

    // Close database connection
    public void close() throws SQLException {
        connection.close();
    }
}
