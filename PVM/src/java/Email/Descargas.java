package Email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

public class Descargas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
//            //String ruta = request.getParameter("ruta_proyecto").toString();
            String nombre_archivo = request.getParameter("file_name").toString();
            String archivo = "\\\\172.16.2.117\\d\\Sistemas de informacion\\PVM\\adjuntos\\" + nombre_archivo;
            File f = new File(archivo);
            String mimeType = new MimetypesFileTypeMap().getContentType(archivo);
            response.setContentType(mimeType);//Se define  el tipo de archivo a descargar
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre_archivo + "\"");
            InputStream in = new FileInputStream(f);
            ServletOutputStream outs = response.getOutputStream();
            int bit = 3072;
            try {
                while ((bit) >= 0) {
                    bit = in.read();
                    outs.write(bit);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(System.out);
            }
            outs.flush();
            outs.close();
            in.close();
        } finally {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
