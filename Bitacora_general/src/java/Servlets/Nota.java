package Servlets;

import Controladoras.NotasJpaController;
import Email.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Nota extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            NotasJpaController jpa_nota = new NotasJpaController();
            int idArea = Integer.parseInt(sesion.getAttribute("Area").toString());
            int IdArea = 0;
            int IdNota = 0;
            int Enviado = 0;
            String descripcion = "";
            String IdUsuarioR = "";
            String fecha = "";
            String asunto = "";
            String responsableR = "";
            String filtro = "";
            boolean resultado = false;
            List revisado = null;
            Email correo = new Email();
            if (opc <= 10) {
                switch (opc) {
                    case 1:
                        filtro = request.getParameter("txt_bus");
                        IdNota = Integer.parseInt(request.getParameter("idN").toString());
                        if (filtro == null || filtro.isEmpty()) {
                            if (IdNota == 0) {
                                request.setAttribute("Consultanota", jpa_nota.ConsultaNotasPorIdArea(idArea));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("Consultanota", jpa_nota.ConsultaNotasPorIdArea(idArea));
                                request.setAttribute("ConsultanotaRM", jpa_nota.ConsultarNotasPorId(IdNota));
                                request.setAttribute("filtro", filtro);
                            }
                        } else {
                            if (IdNota == 0) {
                                request.setAttribute("Consultanota", jpa_nota.ConsultarNotasPorFiltro(filtro));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("Consultanota", jpa_nota.ConsultarNotasPorFiltro(filtro));
                                request.setAttribute("ConsultanotaRM", jpa_nota.ConsultarNotasPorId(IdNota));
                                request.setAttribute("filtro", filtro);
                            }
                        }
                        request.getRequestDispatcher("nota.jsp").forward(request, response);
                        break;
                    case 2:
                        IdArea = Integer.parseInt(request.getParameter("SeIdarea").toString());
                        responsableR = request.getParameter("txt_registro").toString();
                        fecha = request.getParameter("txt_fecha").toString();
                        asunto = request.getParameter("txt_asunto").toString();
                        descripcion = request.getParameter("text_descripcion").toString();
                        resultado = jpa_nota.RegistroNota(IdArea, responsableR, fecha, asunto, descripcion);
                        if (resultado) {
                            request.setAttribute("ResultadoNota", resultado);
                        } else {
                            request.setAttribute("ResultadoNota", resultado);
                        }
                        request.getRequestDispatcher("Nota?op=1&idN=" + 0 + "&txt_bus=").forward(request, response);
                        break;
                    case 3:
                        filtro = request.getParameter("txt_bus");
                        IdNota = Integer.parseInt(request.getParameter("idN").toString());
                        asunto = request.getParameter("txt_asuntoM").toString();
                        descripcion = request.getParameter("text_descripcionM").toString();
                        resultado = jpa_nota.ModificarNota(IdNota, asunto, descripcion);
                        if (resultado) {
                            request.setAttribute("ResultadoNotaM", resultado);
                        } else {
                            request.setAttribute("ResultadoNotaM", resultado);
                        }
                        request.getRequestDispatcher("Nota?op=1&idN=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                    case 4:
                        IdNota = Integer.parseInt(request.getParameter("idN").toString());
                        IdUsuarioR = request.getParameter("idU");
                        revisado = jpa_nota.ConsultarNotasPorId(IdNota);
                        Object[] obj_revisado = (Object[]) revisado.get(0);
                        if (obj_revisado[6] == null || obj_revisado[6].equals("")) {
                            jpa_nota.AgregarIdUsuarioRevisado(IdNota, IdUsuarioR);
                        } else {
                            IdUsuarioR = obj_revisado[6].toString() + "-" + IdUsuarioR;
                            jpa_nota.AgregarIdUsuarioRevisado(IdNota, IdUsuarioR);
                        }
                        request.getRequestDispatcher("Nota?op=5&idN=" + IdNota + "").forward(request, response);
                        break;
                    case 5:
                        IdNota = Integer.parseInt(request.getParameter("idN").toString());
                        request.setAttribute("Consultanota", jpa_nota.ConsultarNotasPorId(IdNota));
                        request.getRequestDispatcher("nota.jsp").forward(request, response);
                        break;
                    case 6:
                        filtro = request.getParameter("txt_bus");
                        IdArea = Integer.parseInt(request.getParameter("idA").toString());
                        IdNota = Integer.parseInt(request.getParameter("idN").toString());
                        Enviado = Integer.parseInt(request.getParameter("Env").toString());
                        resultado = jpa_nota.ModificarEnviadoNota(IdNota, Enviado);
                        correo.mailEnviaNota(IdNota,IdArea);
                        if (resultado) {
                            request.setAttribute("ResultadoNotaEn", resultado);
                        } else {
                            request.setAttribute("ResultadoNotaEn", resultado);
                        }
                        request.getRequestDispatcher("Nota?op=1&idN=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            out.close();


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
