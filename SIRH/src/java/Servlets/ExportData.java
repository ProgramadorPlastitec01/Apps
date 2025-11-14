/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controladores_BD.MenuJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ExportData", urlPatterns = {"/ExportData"})
public class ExportData extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MenuJpaController jpacmnu = new MenuJpaController();
        String file_name = request.getParameter("fnm");
        String file_path = request.getParameter("fpt");
        List lst_data_export = jpacmnu.Vistas_sirh_exe(file_path);
        response.setContentType("data:application/vnd.ms-excel;base64");
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment; filename=sample.xls");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "filename=" + file_name);
        PrintWriter out = response.getWriter();
        try {
            if (lst_data_export != null) {
                for (int i = 0; i < lst_data_export.size(); i++) {
                    Object[] obj_data_export = (Object[]) lst_data_export.get(i);
                    int cant_data_obj = obj_data_export.length;
                    String datos = "";
                    for (int j = 0; j < cant_data_obj; j++) {
                        if (j == 0) {
                            datos = obj_data_export[j].toString();
                        }
                        if (j > 0) {
                            datos = datos + "\t" + obj_data_export[j];
                        }
                    }
                    out.println(datos);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//     @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MenuJpaController jpacmnu = new MenuJpaController();
        String file_name = request.getParameter("fnm");
        String file_path = request.getParameter("fpt");
        List lst_data_export = jpacmnu.Vistas_sirh_exe(file_path);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "filename=" + file_name);
        PrintWriter out = response.getWriter();
        try {
            if (lst_data_export != null) {
                for (int i = 0; i < lst_data_export.size(); i++) {
                    Object[] obj_data_export = (Object[]) lst_data_export.get(i);
                    int cant_data_obj = obj_data_export.length;
                    String datos = "";
                    for (int j = 0; j < cant_data_obj; j++) {
                        if (j == 0) {
                            datos = obj_data_export[j].toString();
                        }
                        if (j > 0) {
                            datos = datos + "\t" + obj_data_export[j];
                        }
                    }
                    out.println(datos);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
