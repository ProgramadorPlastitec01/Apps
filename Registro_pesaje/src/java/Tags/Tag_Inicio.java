package Tags;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.RegistroDetalleJpaController;
import java.util.List;

public class Tag_Inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String nombre = (String) sesion.getAttribute("Nombres");
            String NombreRol = sesion.getAttribute("NombreRol").toString();
            RegistroDetalleJpaController jpa_regDetalle = new RegistroDetalleJpaController();
            List lst_regdetalle = null;
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center' style='width: 100%;text-align: center;'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12' style='width: 100%;'>");
            out.print("<h2 class='page-title'> Bienvenid" + (NombreRol.equals("Administrador") ? "o" : "a") + " " + NombreRol + " " + nombre + " al aplicativo Registro Pesaje. </h2>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");

            out.print("<div class='white-box'>");
            out.print("<div style=''>");
            out.print("<h3 class='box-title'><i class=\"fas fa-clock\"></i> Registros Agregados Recientemente</h3>");
            lst_regdetalle = jpa_regDetalle.ConsultarDetalle_ultimo();
            if (lst_regdetalle == null || lst_regdetalle.size() == 0) {
                out.print("<div class='img_ini'>");
                out.print("<img src='Interfaz/Contenido/Imagenes/404-error.png'>");
                out.print("<h1>No hay ordenes abiertas en este momento!</h1>");
                out.print("</div>");
            } else {
                for (int i = 0; i < lst_regdetalle.size(); i++) {
                    Object[] obj_dll = (Object[]) lst_regdetalle.get(i);
                    out.print("<div class='Cont_datos'>");
                    out.print("<div style='display: flex; justify-content: space-evenly'>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Orden de produccion: </b>");
                    out.print("<p> " + obj_dll[1] + " </p>");
                    out.print("</div>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Codigo de producto: </b>");
                    out.print("<p> " + obj_dll[2] + " </p>");
                    out.print("</div>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Nombre producto: </b>");
                    out.print("<p> " + obj_dll[3] + " </p>");
                    out.print("</div>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Lote: </b>");
                    out.print("<p> " + obj_dll[7] + " </p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='display: flex; justify-content: space-evenly'>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Maquina: </b>");
                    out.print("<p> " + obj_dll[9] + " </p>");
                    out.print("</div>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Recipiente: </b>");
                    out.print("<p> " + obj_dll[12] + " </p>");
                    out.print("</div>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Estiba: </b>");
                    out.print("<p> " + obj_dll[8] + " </p>");
                    out.print("</div>");
                    out.print("<div style='width: 22%;'>");
                    out.print("<b> Unidades x Kilo (Tara): </b>");
                    out.print("<p> " + obj_dll[11] + " un</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='display: flex; justify-content: center'>");
                    out.print("<div style='width: 30%;'>");
                    out.print("<b> Turno: </b>");
                    out.print("<p> " + obj_dll[13] + " </p>");
                    out.print("</div>");
//                    out.print("<div style='width: 22%;'>");
//                    out.print("<b> Grupo: </b>");
//                    String nomb = "";
//                    String[] arg_personal = obj_dll[15].toString().replace("][", "///").replace("[", " ").replace("]", "").split("///");
//                    for (int j = 0; j < arg_personal.length; j++) {
//                        nomb += arg_personal[j] + "<br>";
//                    }
//                    out.print("<p class='tooltip3'><span>" + obj_dll[14] + "</span>"
//                            + "<span class='tooltiptext' style='width: 200%;'>" + nomb + "</span></p>");
//                    out.print("</div>");
                    out.print("<div style='width: 30%;'>");
                    out.print("<b> Peso meta: </b>");
                    out.print("<p> " + obj_dll[16] + "  g</p>");
                    out.print("</div>");
                    out.print("<div style='width: 30%;'>");
                    out.print("<b> Peso actual: </b>");
                    double peso_actual = Double.parseDouble(obj_dll[17].toString());
                    out.print("<p> " + peso_actual + " g</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='width: 100%; text-align: center;'>");
                    out.print("<a href='Registro_detalle?opc=1&id_registro=" + obj_dll[6] + "&id_orden=" + obj_dll[0] + "' class='btn btn-info' style='color: white;'>Ingresar <i class=\"fas fa-external-link-alt\"></i></a>");
                    out.print("</div>");

                    out.print("</div>");
                }
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

        } catch (Exception ex) {
            Logger.getLogger(Tag_Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
