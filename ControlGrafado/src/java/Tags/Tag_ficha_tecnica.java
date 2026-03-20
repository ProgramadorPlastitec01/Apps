package Tags;

import Controladores.FichaTecnicaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_ficha_tecnica extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        FichaTecnicaJpaController jpa_fichaT = new FichaTecnicaJpaController();
        int id_rol = Integer.parseInt(sesion.getAttribute("id_rol").toString());
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        String codigo = (String) pageContext.getRequest().getAttribute("codigo");
        int id_fichaT = Integer.parseInt(pageContext.getRequest().getAttribute("id_fichaT").toString());
        List lst_fichasT = null;
        List lst_fichaT = null;
        List lst_versionFT = null;
        try {
            out.print("<div id='sidebar' style='width:290px;'>");
            if (id_fichaT == 0) {
                //<editor-fold defaultstate="collapsed" desc="registrar ficha tecnica">
                out.print("<h3>Registrar datos de control</h3>");
                if (id_rol == 2 || id_rol == 1) {
                    out.print("<form action='Ficha_tecnica?opc=2' method='post' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<input type='hidden' name='idF' value='" + id_fichaT + "'>");
                    out.print("<b>Codigo:</b><br />");
                    if (codigo != null) {
                        out.print("<input type='text' name='txt_codigo' value='" + codigo + "' id='codigo-id' placeholder='Codigo producto' style='width:297px;'>");
                    } else {
                        out.print("<input type='text' name='txt_codigo' id='codigo-id' placeholder='Codigo producto' style='width:297px;'>");
                    }
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('codigo-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Length, { minimum: 4, maximum: 4} );");
                    out.print("</script><br />");
                    out.print("</form>");
                    out.print("<form action='Ficha_tecnica?opc=3' method='post' onsubmit='registroF();'>");
                    out.print("<input type='hidden' name='idF' value='" + id_fichaT + "'>");
                    if (pageContext.getRequest().getAttribute("productos") != null) {
                        List ltapdt = (List) pageContext.getRequest().getAttribute("productos");
                        out.print("<b>Producto:</b><br />");
                        out.print("<select name='slt_producto' id='producto-id' style='width:297px;'>");
                        out.print("<option value='' style='display:none'>Seleccione un producto</option>");
                        for (int i = 0; i < ltapdt.size(); i++) {
                            out.print("<option value='" + ltapdt.get(i) + "'>" + ltapdt.get(i) + "</option>");
                        }
                        out.print("</select><br /><br />");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('producto-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                    } else {
                        out.print("<b>Producto:</b><br />");
                        out.print("<select name='slt_producto' id='producto-id' style='width:297px;'>");
                        out.print("<option value='' style='display:none'>Seleccione un producto</option>");
                        out.print("</select><br /><br />");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('producto-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                    }
                    out.print("<h3>Control dimensional</h3>");
                    out.print("<b>Registro:</b><br/>");
                    out.print("<select name='slt_registro' id='registro-id' style='width:297px;'>");
                    out.print("<option value='0' style='display:none;'>SELECCIONE REGISTRO</option>");
                    out.print("<option value='R-GC-116'>R-GC-116</option>");
                    out.print("<option value='R-GC-014'>R-GC-014</option>");
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('registro-id');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("<b>Ficha Técnica:</b><br/>");
                    out.print("<input type='text' name='txt_codigo_ficha' id='ficha-id' placeholder='Código FT(FT-E-????)' style='width:297px;' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('ficha-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Ficha_tecnica);");
                    out.print("</script>");
                    out.print("<b>Versión:</b><br/>");
                    out.print("<input type='text' name='txt_version' id='version-id' placeholder='Versión' style='width:297px;'/><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('version-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Enteros2 );");
                    out.print("</script>");
                    out.print("<b>Longitud a Introducir Y1:</b><br/>");
                    out.print("<input type='text' name='txt_y1' id='y1-id' placeholder='Y1' style='width:148px;'/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('y1-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> +</b><input type='text' name='txt_desvMx_y1' id='Mxy1-id' placeholder='Desv +' style='width:55px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mxy1-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> -</b><input type='text' name='txt_desvMn_y1' id='Mny1-id'  placeholder='Desv -' style='width:55px;'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mny1-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b>Diametro Exterior X1:</b><br/>");
                    out.print("<input type='text' name='txt_x1' id='x1-id' placeholder='X1' style='width:148px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('x1-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> +</b><input type='text' name='txt_desvMx_x1' id='Mxx1-id' placeholder='Desv +' style='width:55px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mxx1-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> -</b><input type='text' name='txt_desvMn_x1' id='Mnx1-id' placeholder='Desv -' style='width:55px;'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mnx1-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b>Altura Portapistón Y2:</b><br/>");
                    out.print("<input type='text' name='txt_y2' id='y2-id' placeholder='Y2' style='width:148px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('y2-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> +</b><input type='text' name='txt_desvMx_y2' id='Mxy2-id' placeholder='Desv +' style='width:55px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mxy2-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> -</b><input type='text' name='txt_desvMn_y2' id='Mny2-id' placeholder='Desv -' style='width:55px;'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mny2-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b>Diametro de Conformado X2:</b><br/>");
                    out.print("<input type='text' name='txt_x2' id='x2-id' placeholder='X2' style='width:148px;'/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('x2-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> +</b><input type='text' name='txt_desvMx_x2' id='Mxx2-id' placeholder='Desv +' style='width:55px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mxx2-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> -</b><input type='text' name='txt_desvMn_x2' id='Mnx2-id' placeholder='Desv -' style='width:55px;'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mnx2-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b>Diametro Maximo de Conexión X3:</b><br/>");
                    out.print("<input type='text' name='txt_x3' id='x3-id' placeholder='X3' style='width:148px;'/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('x3-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> +</b><input type='text' name='txt_desvMx_x3' id='Mxx3-id' placeholder='Desv +' style='width:55px;'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mxx3-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<b> -</b><input type='text' name='txt_desvMn_x3' id='Mnx3-id' placeholder='Desv -' style='width:55px;'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Mnx3-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Decimal );");
                    out.print("</script>");
                    out.print("<input type='hidden' name='txt_atr' id='validate_atr'  value='0.0'/>");
                    out.print("<input type='hidden' name='txt_atrMx' id='validatedesv_atr_p' value='0.0'/>");
                    out.print("<input type='hidden' name='txt_atrMn' id='validatedesv_atr_n' value='0.0'/>");
                    out.print("<input type='submit' id='btsubmit' value='Registrar'>");
                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "        </div>");
                    out.print("</form>");
                } else {
                    out.print("<center><img src='Interfaz/Contenido/Iconos/Alert.png' width='70'><br /><b style='color:#292929;'>Se necesita permisos<br />para registrar</b></center>");
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="actualizar ficha tecnica">
                lst_fichaT = jpa_fichaT.consultaFichaTecnicaId(id_fichaT);
                Object[] obj_fichaT = (Object[]) lst_fichaT.get(0);
                lst_versionFT = jpa_fichaT.consultaUltVersionficha(obj_fichaT[2].toString());
                Object[] obj_versionFT = (Object[]) lst_versionFT.get(0);
                out.print("<h3>Actualizar datos de control</h3>");
                out.print("<form action='Ficha_tecnica?opc=3' method='post' onsubmit='registroF();'>");
                out.print("<input type='hidden' name='idF' value='" + obj_fichaT[0] + "'>");
                out.print("<b>Modificar parametros para esta nueva version (" + (Integer.parseInt(obj_versionFT[2].toString()) + 1) + ")</b><br /><br />");
                out.print("<b>Producto:</b><br />");
                out.print("<textarea name='slt_producto' id='producto-id' style='width:297px;height: 70px;' readonly='true'>" + obj_fichaT[4] + " / " + obj_fichaT[5] + "</textarea>");
                out.print("</script>");
                out.print("<h3>Control dimensional</h3>");
                out.print("<b>Registro:</b><br/>");
                out.print("<select name='slt_registro' id='registro-id' style='width:297px;'>");
                out.print("<option value='" + obj_fichaT[24] + "' style='display:none;'>" + obj_fichaT[24] + "</option>");
                out.print("<option value='R-GC-116'>R-GC-116</option>");
                out.print("<option value='R-GC-014'>R-GC-014</option>");
                out.print("</select><br/><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('registro-id');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                out.print("</script>");
                out.print("<b>Ficha Técnica:</b><br/>");
                out.print("<input type='text' name='txt_codigo_ficha' id='ficha-id' value='" + obj_fichaT[2] + "' placeholder='Ficha técnica' readonly='true' style='width:297px;'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('ficha-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Ficha );");
                out.print("</script>");
                out.print("<b>Versión:</b><br/>");
                out.print("<input type='text' name='txt_version' id='version-id' placeholder='Versión " + (Integer.parseInt(obj_versionFT[2].toString()) + 1) + "' style='width:297px;'/><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('version-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Enteros2 );");
                out.print("</script>");
                out.print("<b>Longitud a Introducir Y1:</b><br/>");
                out.print("<input type='text' name='txt_y1' id='y1-id' placeholder='Y1' value='" + obj_fichaT[6] + "' style='width:148px;'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('y1-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> +</b><input type='text' name='txt_desvMx_y1' id='Mxy1-id' value='" + obj_fichaT[7] + "' placeholder='Desv +' style='width:55px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mxy1-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> -</b><input type='text' name='txt_desvMn_y1' id='Mny1-id' value='" + obj_fichaT[8] + "'  placeholder='Desv -' style='width:55px;'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mny1-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b>Diametro Exterior X1:</b><br/>");
                out.print("<input type='text' name='txt_x1' id='x1-id' value='" + obj_fichaT[9] + "' placeholder='X1' style='width:148px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('x1-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> +</b><input type='text' name='txt_desvMx_x1' id='Mxx1-id' value='" + obj_fichaT[10] + "' placeholder='Desv +' style='width:55px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mxx1-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> -</b><input type='text' name='txt_desvMn_x1' id='Mnx1-id' value='" + obj_fichaT[11] + "' placeholder='Desv -' style='width:55px;'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mnx1-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b>Altura Portapistón Y2:</b><br/>");
                out.print("<input type='text' name='txt_y2' id='y2-id' value='" + obj_fichaT[12] + "' placeholder='Y2' style='width:148px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('y2-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> +</b><input type='text' name='txt_desvMx_y2' id='Mxy2-id' value='" + obj_fichaT[13] + "' placeholder='Desv +' style='width:55px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mxy2-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> -</b><input type='text' name='txt_desvMn_y2' id='Mny2-id' value='" + obj_fichaT[14] + "' placeholder='Desv -' style='width:55px;'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mny2-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b>Diametro de Conformado X2:</b><br/>");
                out.print("<input type='text' name='txt_x2' id='x2-id' value='" + obj_fichaT[15] + "' placeholder='X2' style='width:148px;'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('x2-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> +</b><input type='text' name='txt_desvMx_x2' value='" + obj_fichaT[16] + "' id='Mxx2-id' placeholder='Desv +' style='width:55px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mxx2-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> -</b><input type='text' name='txt_desvMn_x2' id='Mnx2-id' value='" + obj_fichaT[17] + "' placeholder='Desv -' style='width:55px;'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mnx2-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b>Diametro Maximo de Conexión X3:</b><br/>");
                out.print("<input type='text' name='txt_x3' id='x3-id' value='" + obj_fichaT[18] + "' placeholder='X3' style='width:148px;'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('x3-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> +</b><input type='text' name='txt_desvMx_x3' id='Mxx3-id' value='" + obj_fichaT[19] + "' placeholder='Desv +' style='width:55px;'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mxx3-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<b> -</b><input type='text' name='txt_desvMn_x3' id='Mnx3-id' value='" + obj_fichaT[20] + "' placeholder='Desv -' style='width:55px;'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mnx3-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Decimal );");
                out.print("</script>");
                out.print("<input type='hidden' name='txt_atr' id='validate_atr'  value='0.0'/>");
                out.print("<input type='hidden' name='txt_atrMx' id='validatedesv_atr_p' value='0.0'/>");
                out.print("<input type='hidden' name='txt_atrMn' id='validatedesv_atr_n' value='0.0'/>");
                out.print("<input type='submit' id='btsubmit' value='Registrar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                //</editor-fold>
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content' style='width:885px'>");
            if (filtro.equals("")) {
                lst_fichasT = jpa_fichaT.consultaFichasTecnicas();
            } else {
                out.print("<a href='Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' /></a>");
                lst_fichasT = jpa_fichaT.consultaFichasTecnicasFiltro(filtro);
            }
            out.print("<div style='float:right;'>");
            out.print("<form method='post' action='Ficha_tecnica?opc=1&idF=" + 0 + "'>");
            out.print("<input type='text' name='txt_bus' placeholder='Buscar'><br/>");
            out.print("</form>");
            out.print("</div>");
            if (lst_fichasT == null) {
                out.print("<a href='Ficha_tecnica?opc=1&idT=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' width='22' height='22' title='Volver'></a>");
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                out.print("<h3>Datos de Control</h3>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td COLSPAN='8'></td>");
                out.print("</tr>");
                for (int i = 0; i < lst_fichasT.size(); i++) {
                    Object[] obj_ficha = (Object[]) lst_fichasT.get(i);
                    out.print("<tr>");
                    if ((Integer) obj_ficha[13] == 1) {
                        out.print("<th COLSPAN='5' style='text-align: center;'>Codigo (Version) : " + obj_ficha[2] + " (" + obj_ficha[3] + ")</th>");
                    } else {
                        out.print("<th COLSPAN='5' style='text-align: center;background-color:#990000;'>Codigo (Version) : " + obj_ficha[2] + " (" + obj_ficha[3] + ")</th>");
                    }
                    if (id_rol == 2 || id_rol == 1) {
                        if ((Integer) obj_ficha[13] == 1) {
                            out.print("<td align='center'><span class='fas fa-sync fa-size_small' onclick='ModificarFT(" + obj_ficha[0] + ")'></span></td>");
//                            out.print("<td align='center'><a href='Ficha_tecnica?opc=1&idF=" + obj_ficha[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Update.png' width='22' height='22'></td>");
                        }
                    }
                    if (id_rol == 2 || id_rol == 1) {
                        if ((Integer) obj_ficha[13] == 1) {
                            out.print("<td align='center' colspan='2'><span class='fas fa-check fa-size_small' onclick='Rechazar(" + obj_ficha[0] + ")'></span></td>");
//                            out.print("<td align='center'><a href='Ficha_tecnica?opc=4&idF=" + obj_ficha[0] + "&est=0&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' width='22' height='22'></a></td>");
                        } else {
                            out.print("<td align='center' colspan='2'><span class='fas fa-times fa-size_small' onclick='Aprobar(" + obj_ficha[0] + ")'></span></td>");
//                            out.print("<td align='center' colspan='2'><a href='Ficha_tecnica?opc=4&idF=" + obj_ficha[0] + "&est=1&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22'></a></td>");
                        }
                    } else if ((Integer) obj_ficha[13] == 1) {
                        out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Check.png' width='22' height='22'></a></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><a href='#'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22'></a></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>FECHA: </b>" + obj_ficha[1] + "</td>");
                    out.print("<td><b>PRODUCTO</b></td>");
                    out.print("<td colspan='3'>" + obj_ficha[4] + " / " + obj_ficha[5] + "</td>");
                    out.print("<td colspan='2'><b>REGISTRO: </b>" + obj_ficha[12] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>LONGITUD A INTRODUCIR Y1</b></td>");
                    out.print("<td align='center'>" + obj_ficha[6] + "</td>");
                    out.print("<td><b>DIÁMETRO EXTERIOR X1</b></td>");
                    out.print("<td align='center'>" + obj_ficha[7] + "</td>");
                    out.print("<td><b>ALTURA PORTAPISTÓN Y2</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_ficha[8] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>DIÁMETRO DE CONFORMADO X2</b></td>");
                    out.print("<td align='center'>" + obj_ficha[9] + "</td>");
                    out.print("<td><b>DIÁMETRO MAXIMO DE CONEXIÓN X3</b></td>");
                    out.print("<td align='center'>" + obj_ficha[7] + "</td>");
                    out.print("<td><b>ALTURA TOTAL</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_ficha[11] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='8'></td>");
                    out.print("</tr>");

                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',25);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div></div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_ficha_tecnica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
