/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Mail.LeerArchivos;
//import SQL.LecturaSerial;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_pruebas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print("Bienvenido a las pruebas de registro pesaje<br>");
            //<editor-fold defaultstate="collapsed" desc="LEER ARCHIVOS TXT DESDE JAVA"> 
//            LeerArchivos a = new LeerArchivos();
//            String txt = a.leerTxt("C:\\Bascula\\Bascula.txt");
//            
//            out.print("Contenido del bloc de notas <br>");
//            txt = txt.replace("ST,GS,+", "");
//            
//            out.print("<input type='' name='' id='LeerArchivos' placeholder='' value='"+ txt +"'>");
//            out.print("<button class='btn btn-primary' onclick='refresh(\"1555\")'>Test</button>");
//</editor-fold>

            out.print("<input type='text' name='' id='botons' placeholder='' value=''>");
            out.print("<button class='' onclick='leerDatos()' id='oprimir'>PasarDatos</button>");
            out.print("<input type='' id='peso_tara' value='30'>");

            //<editor-fold defaultstate="collapsed" desc="JS">
            out.print("<script>");
            out.print("setInterval(function leerDatos() {"
                    + "    var archivoTxt = new XMLHttpRequest();"
                    + "    var fileRuta = 'Interfaz/Contenido/txt/Bascula.txt';"
                    + "    archivoTxt.open(\"GET\", fileRuta, false);"
                    + "    archivoTxt.send();"
                    + "    var txt = archivoTxt.responseText;"
                    + "    document.getElementById(\"botons\").value = txt;"
                    + "    var tara = document.getElementById(\"peso_tara\"). value;"
                    + "    if (tara === txt) {"
                    + "      alert(\"Se ha completado el peso de la bolsa\");"
                    + "    }"
                    + "}, 500);");
            out.print("</script>");
            out.print("<script>");
            out.print("function leerDatos() {"
                    + "    var archivoTxt = new XMLHttpRequest();"
                    + "    var fileRuta = 'Interfaz/Contenido/txt/Bascula.txt';"
                    + "    archivoTxt.open(\"GET\", fileRuta, false);"
                    + "    archivoTxt.send();"
                    + "    var txt = archivoTxt.responseText;"
                    + "    console.log(txt);"
                    + "    document.getElementById(\"botons\").value = txt;"
                    + "}");
            out.print("</script>");
            //</editor-fold>

        } catch (Exception e) {
            Logger.getLogger(Tag_pruebas.class.getName()).log(Level.SEVERE, null, e);
        }

        return super.doStartTag();
    }
}
