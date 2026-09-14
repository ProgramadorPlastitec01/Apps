package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            if (rol.equals("Administrador")) {
                out.print("<h3>Permisos Administrador</h3>");
            } else if (rol.equals("Jefe_PI")) {
                out.print("<h3>Permisos Jefe Producción Insumos</h3>");
            } else if (rol.equals("Coordinador_PI")) {
                out.print("<h3>Permisos Coordinador Producción Insumos</h3>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
