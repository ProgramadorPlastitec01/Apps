package Vista;

import Controlador.UbicacionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class VistaMaquinaria extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            UbicacionJpaController UbiJpa = new UbicacionJpaController();
            String Accion = null;
            List list_ubicaciones = null;
            List list_Maquina = null;
            out.print("<div id='sidebar'>");
            list_ubicaciones = UbiJpa.consultaUbicacion();
            Accion = pageContext.getRequest().getAttribute("Accion").toString();
            //<editor-fold defaultstate="collapsed" desc="Modificar">
            if (Accion.equals("Consulta")) {
                list_Maquina = (List) pageContext.getRequest().getAttribute("Maquina");
                Object[] Obj_Maquina = (Object[]) list_Maquina.get(0);
                out.print("<h3>Modificar Maquina<a href='Maquinaria?op=1'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' /></a></h3>");
                out.print("<form method='post' onsubmit='registroM()' action='Maquinaria?op=4' name='form1'>");
                out.print("<input type='hidden' name='Accion' value='Modificar'/>");
                out.print("<input type='hidden' name='Id_maquina' value='"+Obj_Maquina[0]+"'/>");
                out.print("<b>Nombre:</b>");
                out.print("<input id='Nombre' type='text' name='Nombre' Value='"+Obj_Maquina[2]+"' class='input_full' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Nombre');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Ubicacion:</b>");
                out.print("<select name='Ubicacion' id='Ubicacion' style='width:100%;'>");
                for (int i = 0; i < list_ubicaciones.size(); i++) {
                    Object[] Obj_ubi = (Object[]) list_ubicaciones.get(i);
                    if (Obj_ubi[0].equals(Obj_Maquina[5])) {
                    out.print("<option value='"+Obj_ubi[0]+"' selected>"+Obj_ubi[1]+"</option>");
                    }else{
                    out.print("<option value='"+Obj_ubi[0]+"'>"+Obj_ubi[1]+"</option>");
                    }
                }
                out.print("</select><br /><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Ubicacion');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: ''} );");
                out.print("</script>");            
                out.print("<input type='submit' id='btsubmit' value='Registrar'/>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                out.print("</form>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Registro">
            else{
                out.print("<h3>Nueva Maquina</h3>");
                out.print("<form method='post' onsubmit='registroM()' action='Maquinaria?op=3' name='form1'>");
                out.print("<b>Nombre:</b>");
                out.print("<input id='Nombre' type='text' name='Nombre' class='input_full'  placeholder='Ingresar maquina' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Nombre');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Ubicacion:</b>");
                out.print("<select name='Ubicacion' id='Ubicacion' style='width:100%;'>");
                out.print("<option value='0'>Seleccione Ubicacion</option>");
                for (int i = 0; i < list_ubicaciones.size(); i++) {
                    Object[] Obj_ubi = (Object[]) list_ubicaciones.get(i);
                out.print("<option value='"+Obj_ubi[0]+"'>"+Obj_ubi[1]+"</option>");
                }
                out.print("</select><br /><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Ubicacion');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: ''} );");
                out.print("</script>");            
                out.print("<input type='submit' id='btsubmit' value='Registrar'/>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                out.print("</form>");
            }
//</editor-fold>
            out.print("</div id='sidebar'>");
            //<editor-fold defaultstate="collapsed" desc="Consulta">
            List Maquinaria = (List) pageContext.getRequest().getAttribute("Maquinaria");
            out.print("<div id='content'>");
            out.print("<h3>Maquinas registrados<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
            out.print("<div id='NavPosicion'></div>");
            out.print("<table class='table' id='resultados' style='width:100%;'>");
            out.print("<tr>");
            out.print("<th>Nombres</th>");
            out.print("<th>Ubicacion</th>");
            out.print("<th>Modificar</th>");
            out.print("<th>Estado</th>");
            out.print("</tr>");
            for (int i = 0; i < Maquinaria.size(); i++) {
                Object[] Obj_maquina = (Object[]) Maquinaria.get(i);
                out.print("<tr>");
                out.print("<td>" + Obj_maquina[2] + "</td>");
                out.print("<td>" + Obj_maquina[6] + "</td>");
                out.print("<td style='text-align: center;'><a href='Maquinaria?op=4&Id_maquina=" + Obj_maquina[0] + "&Accion=Consulta'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='30' height='30.5' /></a></td>");
                out.print("<td style='text-align: center;'>");
                if (Obj_maquina[4].equals(1)) {
                    out.print("<a href=\"Maquinaria?op=2&id_maquina=" + Obj_maquina[0] + "&estado=0\"><img src=\"Interfaz/Contenido/Iconos/Check.png\" alt=\"Logo\" width=\"30\" height=\"30.5\" title=\"Maquina Desactivada\"></a>");
                } else {
                    out.print("<a href=\"Maquinaria?op=2&id_maquina=" + Obj_maquina[0] + "&estado=1\"><img src=\"Interfaz/Contenido/Iconos/Delete.png\" alt=\"Logo\" width=\"30\" height=\"30.5\" title=\"Maquina activo\"></a>");
                }
                out.print("</td>");
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados', 9);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("</div>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(VistaMaquinaria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
