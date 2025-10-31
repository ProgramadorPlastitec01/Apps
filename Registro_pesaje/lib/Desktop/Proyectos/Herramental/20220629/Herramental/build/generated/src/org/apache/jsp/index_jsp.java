package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/tlds/tld_resultado.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_resultados_MuestraResultados_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_resultados_MuestraResultados_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_resultados_MuestraResultados_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <link type=\"image/png\" href=\"Interfaz/Contenido/images/herramental.ico\" rel=\"icon\" >\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n");
      out.write("        <title>Herramental proceso</title>\n");
      out.write("        <!-- CSS Principal -->\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "Encabezado.jsp", out, false);
      out.write("\n");
      out.write("            <style>\n");
      out.write("                .placeholder-white::placeholder { color:#98958A; }\n");
      out.write("                #btnft:hover{\n");
      out.write("                    background-color: #f9c827;\n");
      out.write("                }\n");
      out.write("            </style>\n");
      out.write("        </head>\n");
      out.write("        <!--        <body style=\"font-size: 13px;background: linear-gradient(to left,#f5f5f6 50%, #CAA427 50% );\">-->\n");
      out.write("        <body style=\"font-size: 13px; background: rgb(249,200,39);\n");
      out.write("              background: linear-gradient(157deg, rgba(249,200,39,1) 0%, rgba(84,70,24,1) 88%);\">\n");
      out.write("            <div style=\"background-color: white; width: 480px; margin: auto; border-radius: 20px; display: block; margin-top: 20px;\">\n");
      out.write("                <div>\n");
      out.write("                    <center style=' padding-bottom: 10px;'>\n");
      out.write("                        <img src=\"Interfaz/Contenido/images/logoT2.fw.png\" style='margin-top: 10%; margin-bottom: 10px;'><br>\n");
      out.write("                        <br>\n");
      out.write("                        <img src=\"Interfaz/Contenido/images/herramental.png\" alt=\"Logo\" width=\"120\" height=\"120\" ><br><br>\n");
      out.write("                        <br>\n");
      out.write("\n");
      out.write("                        <form action=\"Login?opc=1\" method=\"post\" >\n");
      out.write("                            <input type=\"hidden\" name=\"slc_idM\" id=\"idM\"/>\n");
      out.write("                            <input type=\"hidden\" name=\"est\" id=\"idM\" value=\"0\"/>\n");
      out.write("                            <input type=\"tex t\" autocomplete=\"off\" name=\"Txt_user\" id=\"Txt_user\" placeholder=\"Usuario\" class=\"placeholder-white\" style=\"background-color:#fff;color: #f9c827;border-bottom:2px solid #f9c827;border-right: none;border-left: none;border-top: none;\" onchange='javascript:this.value = this.value.toUpperCase();' /><br />\n");
      out.write("                            <input type=\"password\" autocomplete=\"off\" name=\"Txt_password\" id=\"Txt_password\" placeholder=\"Contraseña\" class=\"placeholder-white\" style=\"background-color:#fff;color: #f9c827;border-bottom:2px solid #f9c827;border-right: none;border-left: none;border-top: none;\"/><br /><br>\n");
      out.write("                            <input type=\"submit\" id=\"btnft\" value=\"Iniciar\" style=\"background-color: #000;color:#f9c827;\"/><br/><br/>\n");
      out.write("                            <!--                            <b>Vp. 00.00.00</b>\n");
      out.write("                                                        <b>Vp. 01.03.01</b>\n");
      out.write("                                                        <b>Vp. 01.06.01</b>\n");
      out.write("                                                        <b>Va. 01.06.01</b>\n");
      out.write("                                                        <b>Va. 02.10.02</b>\n");
      out.write("                                                        <b>Va. 03.15.03</b>-->\n");
      out.write("                            <b style='color: #000;size: 13px;'>Va. 04.17.03</b><br>\n");
      out.write("                        </form>\n");
      out.write("                    </center>\n");
      out.write("                </div>\n");
      out.write("                <hr style=\"margin-bottom: -10px;\">\n");
      out.write("                <div style=\"width: auto;text-align: justify;color:#454343; padding: 30px; border-radius: 0px 0px 20px 20px;font-size: 12px;\" align=\"left\">\n");
      out.write("                    <P><b>HERRAMENTAL PROCESO: </b>Este sistema de información es el encargado de facilitar la consulta y movimientos de los herramentales en los diferentes procesos de la organización, permitiendo alertar atravez de pendientes de <b>herramentales</b> o <b>maquinas</b> las diferentes actividades a realizar\n");
      out.write("                    El sistema como ayuda virtual permite al usuario acceder a la información de manera segura, rapida y confiable para poder realizar en cada uno de los procesos del registro una adecuada manipulación.\n");
      out.write("                    </P>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        ");
      if (_jspx_meth_resultados_MuestraResultados_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("        <!--        <center>\n");
      out.write("                    <div style=\"width: 800px;margin-top: 100px;\" align='center'>\n");
      out.write("                        <div style=\"width: 150px;height: 110px;background-color: #CAA427;color: #fff;top: 220px;position: fixed;\">\n");
      out.write("                        </div>\n");
      out.write("                        <div style=\"width: 600px;\">\n");
      out.write("                            <div style=\"float: left;width: 300px;height: 300px;color: #CAA427;\">\n");
      out.write("                                <br /><br /><br /><br /><br /><br /><br />\n");
      out.write("                                <form action=\"Login?opc=1\" method=\"post\">\n");
      out.write("                                    <input type=\"hidden\" name=\"slc_idM\" id=\"idM\"/>\n");
      out.write("                                    <input type=\"hidden\" name=\"est\" id=\"idM\" value=\"0\"/>\n");
      out.write("                                    <input type=\"text\" name=\"Txt_user\" id=\"Txt_user\" placeholder=\"Usuario\" class=\"placeholder-white\" style=\"background-color:#CAA427;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;\" onchange='javascript:this.value = this.value.toUpperCase();'/><br />\n");
      out.write("                                    <input type=\"password\" name=\"Txt_password\" id=\"Txt_password\" placeholder=\"Contraseña\" class=\"placeholder-white\" style=\"background-color:#CAA427;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;\"/><br />\n");
      out.write("                                    <input type=\"submit\" value=\"Iniciar\" style=\"background-color: #f5f5f6;color:#CAA427\"/><br/><br/>\n");
      out.write("                                    <b>Vp. 00.00.00</b>\n");
      out.write("                                    <b>Vp. 01.03.01</b>\n");
      out.write("                                    <b>Vp. 01.06.01</b>\n");
      out.write("                                    <b>Va. 01.06.01</b>\n");
      out.write("                                    <b>Va. 02.10.02</b>\n");
      out.write("                                    <b>Va. 03.15.03</b>\n");
      out.write("                                    <b style='color: #f5f5f6;size: 13px'>Va. 04.17.03</b>\n");
      out.write("                                </form>\n");
      out.write("                            </div>\n");
      out.write("                            <div style=\"float: left;width: 300px;height: 300px;color: #B4045F;\">\n");
      out.write("                                <br /><br />\n");
      out.write("                                <img src=\"Interfaz/Contenido/images/logoT2.fw.png\" alt=\"Logo\"/>\n");
      out.write("                                <br /><br />\n");
      out.write("                                <img src=\"Interfaz/Contenido/images/LogoTest7.fw.png\" alt=\"Logo\" width=\"220\" height=\"220\" />\n");
      out.write("                            </div>\n");
      out.write("                            <div style=\"float: left;width: 600px;height: 180px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;\">\n");
      out.write("                                <div style=\"width: 500px;margin-top: 10px;text-align: justify;color:grey\" align=\"left\">\n");
      out.write("                                    <b>Herramental proceso </b>Este sistema de información es el encargado de facilitar la consulta y movimientos de los herramentales en los diferentes procesos de la organización, permitiendo alertar atravez de pendientes de herramentales o maquinas las diferentes actividades a realizar\n");
      out.write("                                    <br />El sistema como ayuda virtual permite al usuario acceder a la información de manera segura, rapida y confiable para poder realizar en cada uno de los procesos del registro una adecuada manipulación.\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </center>-->\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_resultados_MuestraResultados_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  resultados:MuestraResultados
    Tags.Tag_resultado _jspx_th_resultados_MuestraResultados_0 = (Tags.Tag_resultado) _jspx_tagPool_resultados_MuestraResultados_nobody.get(Tags.Tag_resultado.class);
    _jspx_th_resultados_MuestraResultados_0.setPageContext(_jspx_page_context);
    _jspx_th_resultados_MuestraResultados_0.setParent(null);
    int _jspx_eval_resultados_MuestraResultados_0 = _jspx_th_resultados_MuestraResultados_0.doStartTag();
    if (_jspx_th_resultados_MuestraResultados_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_resultados_MuestraResultados_nobody.reuse(_jspx_th_resultados_MuestraResultados_0);
      return true;
    }
    _jspx_tagPool_resultados_MuestraResultados_nobody.reuse(_jspx_th_resultados_MuestraResultados_0);
    return false;
  }
}
