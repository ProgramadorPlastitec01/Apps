package Servlets;

import Controladores.AreaJpaController;
import Controladores.ClasificacionJpaController;
import Controladores.ProveedorJpaController;
import Controladores.UbicacionJpaController;
import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complementos
        extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();

            UsuarioJpaController jpacusa = new UsuarioJpaController();
            ProveedorJpaController jpacpro = new ProveedorJpaController();
            UbicacionJpaController jpacubi = new UbicacionJpaController();
            ClasificacionJpaController jpaccla = new ClasificacionJpaController();
            AreaJpaController jpacare = new AreaJpaController();

            int opc = Integer.parseInt(request.getParameter("opc").toString());
            List lst_usuarios = null;
            List lst_usuario = null;
            boolean proceso = true;
            String tipo = "";
            String filtro = "";

            String nombre = "";
            String apellido = "";

            String correo = "";
            int documento = 0;
            int codigo = 0;
            int id_rol = 0;
            int id_area = 0;
            int id_usuario = 0;

            List lst_proveedor = null;
            List lst_proveedores = null;
            String empresa = "";
            String telefono = "";
            String descripcion = "";
            int id_proveedor = 0;
            String tipop = "";

            List Ubicaciones = null;
            List lst_ubicaciones = null;
            String a = "";
            int id_ubicacion = 0;
            String planta = "";

            String tipoc = "";
            List lst_clasificacion = null;
            List lst_clasicaciones = null;
            int id_clasificacion = 0;
            String tipou = null;

            String Alerta = "";
            switch (opc) {
                case 1:
                    tipo = "Registro_usuarios";
                    lst_usuarios = jpacusa.Usuarios();
                    if (lst_usuarios == null) {
                        request.setAttribute("Lista_usuarios", null);
                    } else {
                        request.setAttribute("Lista_usuarios", lst_usuarios);
                    }
                    request.setAttribute("Usuario", tipo);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    documento = Integer.parseInt(request.getParameter("Txt_documento").toString());
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    correo = request.getParameter("Txt_correo");
                    String usuario = request.getParameter("Txt_usuario");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol").toString());
                    id_area = Integer.parseInt(request.getParameter("Cbx_area").toString());
                    String nombre_usuario = sesion.getAttribute("Nombres").toString();
                    proceso = jpacusa.Registrar_usuario(nombre, apellido, documento, codigo, correo, usuario, id_rol, id_area, nombre_usuario.toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Complementos?opc=1&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Complementos?opc=1&fto=").forward(request, response);
                    }
                    break;
                case 3:
                    tipo = "Modificar_usuarios";
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    lst_usuarios = jpacusa.Usuarios();
                    lst_usuario = jpacusa.Traer_usuario(id_usuario);
                    if ((lst_usuarios != null) || (lst_usuario != null)) {
                        request.setAttribute("Usuario", tipo);
                        request.setAttribute("Lista_usuarios", lst_usuarios);
                        request.setAttribute("Datos_usuario", lst_usuario);
                        request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    }
                    break;
                case 4:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    documento = Integer.parseInt(request.getParameter("Txt_documento").toString());
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    correo = request.getParameter("Txt_correo");
                    usuario = request.getParameter("Txt_usuario");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol").toString());
                    id_area = Integer.parseInt(request.getParameter("Cbx_area").toString());
                    nombre_usuario = sesion.getAttribute("Nombres").toString();
                    proceso = jpacusa.Modificar_usuario(id_usuario, nombre, apellido, documento, codigo, correo, usuario, id_rol, id_area, nombre_usuario.toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Complementos?opc=1&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_usuario_modificar");
                        request.setAttribute("var1", nombre + " " + apellido);
                        request.getRequestDispatcher("Complementos?opc=1&fto=").forward(request, response);
                    }
                    break;
                case 5:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    proceso = jpacusa.Desactivar_usuario(id_usuario);
                    request.getRequestDispatcher("Complementos?opc=1&fto=").forward(request, response);
                    break;
                case 6:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    proceso = jpacusa.Activar_usuario(id_usuario);
                    request.getRequestDispatcher("Complementos?opc=1&fto=").forward(request, response);
                    break;
                case 7:
                    tipop = "Registro_proveedor";
                    lst_proveedor = jpacpro.proveedores();
                    request.setAttribute("Filtro", "");
                    request.setAttribute("Lista_proveedor", lst_proveedor);
                    if (lst_proveedor != null) {
                        request.setAttribute("Proveedor", tipop);
                        request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    }
                    break;
                case 8:
                    nombre = request.getParameter("Txt_nombre");
                    empresa = request.getParameter("Txt_empresa");
                    telefono = request.getParameter("Txt_telefono");
                    correo = request.getParameter("Txt_correo");
                    descripcion = request.getParameter("Txt_descripcion");
                    nombre_usuario = sesion.getAttribute("Nombres").toString();
                    proceso = jpacpro.Registrar_proveedor(nombre, empresa, telefono, correo, descripcion, nombre_usuario.toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_proveedor");
                        request.setAttribute("var1", nombre + " de la empresa " + empresa);
                        request.getRequestDispatcher("Complementos?opc=7&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_proveedor");
                        request.setAttribute("var1", nombre + " de la empresa " + empresa);
                        request.getRequestDispatcher("Complementos?opc=7&fto=").forward(request, response);
                    }
                    break;
                case 9:
                    tipop = "Modificar_proveedor";
                    id_proveedor = Integer.parseInt(request.getParameter("Id_proveedor").toString());
                    lst_proveedores = jpacpro.proveedores();
                    lst_proveedor = jpacpro.Traer_proveedor(id_proveedor);
                    if ((lst_proveedor != null) || (lst_proveedores != null)) {
                        request.setAttribute("Proveedor", tipop);
                        request.setAttribute("Lista_proveedor", lst_proveedores);
                        request.setAttribute("Datos_proveedor", lst_proveedor);
                        request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    }
                    break;
                case 10:
                    id_proveedor = Integer.parseInt(request.getParameter("Id_proveedor").toString());
                    nombre = request.getParameter("Txt_nombre");
                    empresa = request.getParameter("Txt_empresa");
                    telefono = request.getParameter("Txt_telefono");
                    correo = request.getParameter("Txt_correo");
                    descripcion = request.getParameter("Txt_descripcion");
                    nombre_usuario = sesion.getAttribute("Nombres").toString();
                    proceso = jpacpro.Modificar_provedor(id_proveedor, nombre, empresa, telefono, correo, descripcion, nombre_usuario.toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_proveedor");
                        request.setAttribute("var1", nombre + " de la empresa " + empresa);
                        request.getRequestDispatcher("Complementos?opc=7&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_proveedor");
                        request.setAttribute("var1", nombre + " de la empresa " + empresa);
                        request.getRequestDispatcher("Complementos?opc=7&fto=").forward(request, response);
                    }
                    break;
                case 11:
                    id_proveedor = Integer.parseInt(request.getParameter("Id_proveedor").toString());
                    proceso = jpacpro.Desactivar_proveedor(id_proveedor);
                    request.getRequestDispatcher("Complementos?opc=7&fto=").forward(request, response);
                    break;
                case 12:
                    id_proveedor = Integer.parseInt(request.getParameter("Id_proveedor").toString());
                    proceso = jpacpro.Activar_proveedor(id_proveedor);
                    request.getRequestDispatcher("Complementos?opc=7&fto=").forward(request, response);
                    break;
                case 13:
                    tipo = "Ubicacion";
                    String Action = "Registar";
                    Ubicaciones = jpacubi.Ubicaciones();
                    List Areas = jpacare.Areas();
                    if (Ubicaciones != null) {
                        request.setAttribute("Lista_ubicacion", Ubicaciones);
                    } else {
                        request.setAttribute("Lista_ubicacion", Ubicaciones);
                    }
                    request.setAttribute("Ubicacion", tipo);
                    request.setAttribute("Action", Action);
                    request.setAttribute("Areas", Areas);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 14:
                    String Nombre = request.getParameter("Nombre");
                    String Tipo = request.getParameter("Tipo");
                    String Area = request.getParameter("Area");
                    proceso = jpacubi.Registrar_ubicacion(Nombre, Tipo, Area);
                    if (proceso) {
                        Alerta = "Good_Insert";
                    } else {
                        Alerta = "Bad_Insert";
                    }
                    request.setAttribute("Alerta", Alerta);
                    request.getRequestDispatcher("Complementos?opc=13").forward(request, response);
                    break;
                case 15:
                    tipo = "Ubicacion";
                    Action = "Modificar";
                    Ubicaciones = jpacubi.Ubicaciones();
                    int Id_Ubicacion = Integer.parseInt(request.getParameter("Id_Ubicacion"));
                    List Ubicacion = jpacubi.Traer_ubicacion(Id_Ubicacion);
                    Areas = jpacare.Areas();
                    request.setAttribute("Ubicacion", tipo);
                    request.setAttribute("Action", Action);
                    request.setAttribute("Id_Ubicacion", Integer.valueOf(Id_Ubicacion));
                    request.setAttribute("Lista_ubicacion", Ubicaciones);
                    request.setAttribute("Lista_ubicacion_mod", Ubicacion);
                    request.setAttribute("Areas", Areas);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 16:
                    Id_Ubicacion = Integer.parseInt(request.getParameter("Id_Ubicacion"));
                    Nombre = request.getParameter("Nombre");
                    Tipo = request.getParameter("Tipo");
                    Area = request.getParameter("Area");
                    boolean Mod = jpacubi.Modificar_ubicacion(Id_Ubicacion, Nombre, Tipo, Area);
                    if (Mod) {
                        Alerta = "Mod_Good";
                    } else {
                        Alerta = "Mod_Bad";
                    }
                    request.setAttribute("Alerta", Alerta);
                    request.getRequestDispatcher("Complementos?opc=13").forward(request, response);
                    break;
                case 17:
                    Id_Ubicacion = Integer.parseInt(request.getParameter("Id_Ubicacion"));
                    int Estado = Integer.parseInt(request.getParameter("Estado"));
                    boolean Estado_Cambio = jpacubi.Activar_ubicacion(Id_Ubicacion, Estado);
                    if (Estado_Cambio) {
                        Alerta = "Estado_Good";
                    } else {
                        Alerta = "Estado_Bad";
                    }
                    request.setAttribute("Alerta", Alerta);
                    request.getRequestDispatcher("Complementos?opc=13").forward(request, response);
                    break;
                case 19:
                    tipoc = "Registro_clasificacion";
                    lst_clasificacion = jpaccla.Clasificacion();
                    request.setAttribute("Lista_clasificacion", lst_clasificacion);
                    if (lst_clasificacion != null) {
                        request.setAttribute("Clasificacion", tipoc);
                        request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    }
                    break;
                case 20:
                    nombre = request.getParameter("Txt_nombre");
                    tipo = request.getParameter("Cbx_tipo");
                    nombre_usuario = sesion.getAttribute("Nombres").toString();
                    proceso = jpaccla.Registrar_clasificacion(nombre, tipo, nombre_usuario.toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_clasificacion");
                        request.setAttribute("var1", nombre + "");
                        request.getRequestDispatcher("Complementos?opc=19&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_clasificacion");
                        request.setAttribute("var1", nombre + "");
                        request.getRequestDispatcher("Complementos?opc=19&fto=").forward(request, response);
                    }
                    break;
                case 21:
                    tipoc = "Modificar_clasificacion";
                    id_clasificacion = Integer.parseInt(request.getParameter("Id_clasificacion").toString());
                    lst_clasicaciones = jpaccla.Clasificacion();
                    lst_clasificacion = jpaccla.Traer_clasificacion(id_clasificacion);
                    if ((lst_clasicaciones != null) || (lst_clasificacion != null)) {
                        request.setAttribute("Clasificacion", tipoc);
                        request.setAttribute("Filtro", "");
                        request.setAttribute("Lista_clasificacion", lst_clasicaciones);
                        request.setAttribute("Datos_clasificacion", lst_clasificacion);
                        request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    }
                    break;
                case 22:
                    id_clasificacion = Integer.parseInt(request.getParameter("Id_clasificacion").toString());
                    nombre = request.getParameter("Txt_nombre");
                    tipo = request.getParameter("Cbx_tipo");
                    nombre_usuario = sesion.getAttribute("Nombres").toString();
                    proceso = jpaccla.Modificar_usuario(id_clasificacion, nombre, tipo, nombre_usuario.toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_clasificacion");
                        request.setAttribute("var1", nombre + "");
                        request.getRequestDispatcher("Complementos?opc=19&fto=").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_clasificacion");
                        request.setAttribute("var1", nombre + "");
                        request.getRequestDispatcher("Complementos?opc=19&fto=").forward(request, response);
                    }
                    break;
                case 23:
                    id_clasificacion = Integer.parseInt(request.getParameter("Id_clasificacion"));
                    jpaccla.Desactivar_clasificacion(id_clasificacion);
                    request.getRequestDispatcher("Complementos?opc=19&fto=").forward(request, response);
                    break;
                case 24:
                    id_clasificacion = Integer.parseInt(request.getParameter("Id_clasificacion"));
                    jpaccla.Activar_clasificacion(id_clasificacion);
                    request.getRequestDispatcher("Complementos?opc=19&fto=").forward(request, response);
                    break;
                case 25:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    jpacusa.Restablecer_password(id_usuario);
                    request.setAttribute("Alerta", "Password_restablecido");  // alerta de exito
                    request.getRequestDispatcher("Complementos?opc=1").forward(request, response);
                    break;
                case 26:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    jpacusa.Restablecer_password(id_usuario);
                    request.setAttribute("Alerta", "Password_restablecido");  // alerta de exito
                    request.getRequestDispatcher("Salir.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}
