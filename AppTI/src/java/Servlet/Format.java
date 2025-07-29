package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.FormatControllerJpa;

public class Format extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            FormatControllerJpa FormatJpa = new FormatControllerJpa();

            int opt = Integer.parseInt(request.getParameter("opt"));
            int idfmt = 0, version = 0, state = 0, temp = 0;
            String code = "", name = "", dataFromat = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    try {
                        idfmt = Integer.parseInt(request.getParameter("idfmt"));
                    } catch (Exception e) {
                        idfmt = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    request.setAttribute("idfmt", idfmt);
                    request.setAttribute("temp", temp);
                    request.getRequestDispatcher("Format.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        idfmt = Integer.parseInt(request.getParameter("idfmt"));
                    } catch (Exception e) {
                        idfmt = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    code = request.getParameter("txtCode");
                    name = request.getParameter("txtName");
                    version = Integer.parseInt(request.getParameter("nmbVersion"));
                    if (idfmt > 0 && temp == 0) {
                        result = FormatJpa.UpdateFormat(idfmt, code, name, version);
                        request.setAttribute("UpdateFormat", result);
                    } else {
                        if (temp == 2) {
                            dataFromat = request.getParameter("txtFormat");
                            result = FormatJpa.RegisterFormatNewVersion(code, name, version, dataFromat);
                        } else {
                            result = FormatJpa.RegisterFormat(code, name, version);
                        }
                        request.setAttribute("RegisterFormat", result);
                    }
                    request.getRequestDispatcher("Format?opt=1&idfmt=0").forward(request, response);
                    break;
                case 3:
                    try {
                        idfmt = Integer.parseInt(request.getParameter("idfmt"));
                    } catch (Exception e) {
                        idfmt = 0;
                    }
                    state = Integer.parseInt(request.getParameter("state"));
                    result = FormatJpa.UpdateFormatState(idfmt, state);
                    request.setAttribute("UpdateFormat", result);
                    request.getRequestDispatcher("Format?opt=1&idfmt=0").forward(request, response);
                    break;
                case 4:
                    try {
                        idfmt = Integer.parseInt(request.getParameter("idfmt"));
                    } catch (Exception e) {
                        idfmt = 0;
                    }
                    dataFromat = request.getParameter("txtFormat");
                    result = FormatJpa.UpdateFormatData(idfmt, dataFromat);
                    request.setAttribute("UpdateDataFormat", result);
                    request.getRequestDispatcher("Format?opt=1&idfmt=0").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Format.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
