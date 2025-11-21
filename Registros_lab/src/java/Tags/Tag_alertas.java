package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_alertas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (pageContext.getRequest().getAttribute("Alerta") != null) {
                // <editor-fold defaultstate="collapsed" desc="ALERTAS USUARIO">
                //ALERTAS USUARIOS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario ingresado no se encuentra registrado','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario " + var + " se encuentra desactivado.','info');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS LINEA">
                //ALERTAS LINEA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La línea " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La línea " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS FICHA TECNICA">
                //ALERTAS FICHA TECNICA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_ficha")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La ficha " + var + " en versión " + var2 + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_ficha")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ficha " + var + " en versión " + var2 + " no se ha registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Ficha_existente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ficha " + var + " en versión " + var2 + " ya se encuentra registrada','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS ORDEN DE PRODUCCION">
                //ALERTAS ORDEN DE PRODUCCION
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La orden de producción " + var + " ha sido registrada correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La orden de producción " + var + " ya existe.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cerrar_orden")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La orden de producción no se puede cerrar, hay productos aun abiertos.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cerrar_orden_2")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La orden de producción no se puede cerrar, no hay productos asociados.','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PRODUCTO">
                //ALERTAS PRODUCTO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_producto")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El producto " + var + " ha sido asignado a la orden de producción " + var2 + "','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_producto")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto " + var + " no ha podido ser asignado a la orden de producción " + var2 + "','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificacion_producto")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El producto " + var + " ha sido modificado a la orden de producción " + var2 + ", favor verificar los registros','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificacion_producto")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto " + var + " no ha podido ser modificado a la orden de producción " + var2 + "','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Producto_existente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Info','El producto " + var + " ya esta asociado a la orden " + var2 + "','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Adicion_producto_existente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Info','El producto " + var + " ya esta asociado a la orden " + var2 + ", favor adicionar la ficha tecnica de producto terminado','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_orden")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede abrir debido a que la orden de producción se encuentra cerrada','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_orden_2")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede cerrar debido a que no hay registros asociados','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_orden_3")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede abrir debido a que no hay registros asociados','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede cerrar debido a que hay registros aun abiertos','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PARAMETROS">
                //ALERTAS PARAMETROS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_parametro")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    if (var2.equals("2")) {
                        out.print("swal('Correcto','El parámetro " + var + " ha sido asignado para los registros de Bocas (R-PRF-011)','success');");
                    } else if (var2.equals("3")) {
                        out.print("swal('Correcto','El parámetro " + var + " ha sido asignado para los registros de Colpitt (R-PRF-013)','success');");
                    } else {
                        out.print("swal('Correcto','El parámetro " + var + " ha sido asignado para los doa tipos de registro (R-PRF-011 / 13)','success');");
                    }
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_parametro")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto " + var + " no ha podido ser asignado a los registros ya se encuentra en existencia.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_posicion_parametro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Posición asignada al parámetro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_posicion_parametro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo asignar posición al parámetro.','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS TURNOS">
                //ALERTAS TURNOS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El turno se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El turno no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Codigo_linea_errado")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El código ingresado no es el de la línea','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La actualización del turno se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La actualización del turno no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El turno no se puede abrir debido a que el producto se encuentra cerrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firmar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Ha añadido su firma en el registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_firmar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha añadido la firma en el registro','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Responsables_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Ha actualizado los responsables del registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_responsables_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha actualizar los responsables del registro','error');");
                    out.print("</script>");
                }

                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PARADA MAQUINAS REGISTRO">
                //ALERTAS PARADA MAQUINAS REGISTRO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Eliminar_parada_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Atencion','Se ha quitado la parada de maquina del registro','warning');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_eliminar_parada_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha quitado la parada de maquina del registro','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_parada_maquina_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La parada de maquina se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_parada_maquina_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La parada de maquina no se ha generado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS VERIFICACIÓN LOTE Y CODIGO">
                //ALERTAS VERIFICACIÓN LOTE Y CODIGO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_verificacion_lote_codigo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La verificacion de lote y codigo se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_verificacion_lote_codigo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La verificacion de lote y codigo no se ha generado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PARÁMETROS FRECUENCIA POR HORA">
                //ALERTAS PARÁMETROS FRECUENCIA POR HORA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_frecuencia_hora")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El parámetro de frecuencia por hora se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_frecuencia_hora")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El parámetro de frecuencia por hora no se ha generado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS SERIAL">
                //ALERTAS SERIAL
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El serial " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El serial " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizar_tipo_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El tipo de serial " + var + " ha sido actualizado masivamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizar_tipo_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El tipo de serial " + var + " no se pudo actualizar masivamente','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizar_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El serial " + var + " ha sido actualizado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizar_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El serial " + var + " no se ha actualizado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PNC">
                //ALERTAS PNC
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_pnc")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El PNC " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_pnc")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El PNC " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PARADAS DE MAQUINA">
                //ALERTAS PARADAS DE MAQUINA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_parada_maquina")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La parada de maquina " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_parada_maquina")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La parada de maquina " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS CATEGORIA">
                //ALERTAS CATEGORIA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_categoria")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La categoria " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_categoria")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La categoria " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS IMPLEMENTOS">
                //ALERTAS IMPLEMENTOS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_implementos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Los datos en electrodos/seriales/implementos ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_implementos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Los datos no ha sido registrado correctamente','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS ESPESOR SOLDADURA  BOCAS">
                //ALERTAS ESPESOR SOLDADURA  BOCAS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_espesor_bocas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Los datos de espesor soldadura en bocas ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_espesor_bocas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Los datos no ha sido registrado correctamente','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS ESPESOR SOLDADURA  COLAS">
                //ALERTAS ESPESOR SOLDADURA  COLAS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_espesor_colas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Los datos de espesor soldadura en colas ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_espesor_colas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Los datos no ha sido registrado correctamente','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PRUEBAS CALIDAD">
                //ALERTAS PRUEBAS CALIDAD
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_pruebas_calidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La prueba de calidad se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_pruebas_calidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La prueba de calidad no se ha generado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PRODUCTO NO CONFORME REGISTRO">
                //ALERTAS PRODUCTO NO CONFORME REGISTRO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_pnc_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La descripción de pnc se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_pnc_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La descripción de pnc no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Pnc_existente")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','La descripción de pnc ya tiene existencia en el registro','warning');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Valor_ingresado_pnc")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Valor ingresado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_valor_pnc")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El valor  no fue ingresado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Eliminar_pnc_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha quitado la descripción de PNC del registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_eliminar_pnc_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No Se ha quitado la descripción de PNC del registro','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS CONTROL ENTRADA DE MATERIALES">
                //ALERTAS CONTROL ENTRADA DE MATERIALES
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La entrada de material se generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La entrada de material no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Eliminar_entrada_material_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha quitado la entrada de material del registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_eliminar_entrada_material_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No Se ha quitado la entrada de material del registro','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS OBSERVACIONES">
                //ALERTAS OBSERVACIONES
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_observacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La observación se generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_observacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La observación no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_observacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La observación se generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_observacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La observación no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Eliminar_observacion_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha quitado la observación del registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_eliminar_observacion_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha quitado la observación del registro','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO HORA INSUMOS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_hora_insumo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se han registrado los ductos!','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_hora_insumo_err")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Se ha producido un error, favor comunicarse con TI','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_hora")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha registrado la hora!','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_hora_err")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Se ha producido un error, favor comunicarse con TI','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_hora_responsa")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha registrado el responsable!','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_hora_responsa_err")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Se ha producido un error, favor comunicarse con TI','error');");
                    out.print("</script>");
                }
//</editor-fold>
                // <editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACION">
                //LIMPIAR ESTACION
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Limpiar_estacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha limpiado la estación','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_limpiar_estacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha limpiado la estación','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="BLOQUEAR ESTACION">
                //BLOQUEAR ESTACION
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Bloqueo_estacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha bloqueado la estación','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_bloqueo_estacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha bloqueado la estación','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="DESLOQUEAR ESTACION">
                //DESLOQUEAR ESTACION
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Desbloqueo_estacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha desbloqueado la estación','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_desbloqueo_estacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha desbloqueado la estación','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="RESUMEN">
                //RESUMEN
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Completar_resumen")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha completado el resumen.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_completar_resumen")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha completado el resumen.','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="SESION">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_sesion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesion','El tiempo en la sesión expiro','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contraseña")) {
                    int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("var1").toString());
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:30%;margin-top:5%;width:35%;text-align: justify'>");
//                    out.print("<a href='Sesion?opc=2' style='float:right'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:22px;height:22px;' title='Cerra modulo de registro' /></a>");
                    out.print("<center><h1>Cambiar Contraseña</h1></center>");
                    out.print("<p style=\"color:#34495e\">Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Sesion?opc=3' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='Id_usuario' value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input' class='placeholder-white'  placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' class='placeholder-white' name='Txt_password' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
                    out.print("</center>");
                    out.print("<div style='float:right;'><img src='Interfaz/Contenido/images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                    out.print("<div class='Ayuda'>");
                    out.print("<div class='label_info'><label style='color:#34495e'>El cambio de Contraseña debe contener:<br />"
                            + "-Minimo 8 caracteres<br/>\n"
                            + "-Maximo 15 caracteres<br/>\n"
                            + "-Al menos una letra mayúscula<br/>\n"
                            + "-Al menos una letra minúscula<br/>\n"
                            + "-Al menos un dígito ( Numero )<br/>\n"
                            + "-No espacios en blanco<br/>\n"
                            + "-Al menos 1 caracter especial ( $@$!%*?&#- )</label></div>");
                    out.print("</div>");
                    out.print("<center>");
                    out.print("<br><input type='submit' value='Cambiar'>");
                    out.print("</center>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                }

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Password_actualizado")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha actualizado la contraseña','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Password_restablecido")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha restablecido la contraseña por el año en curso','success');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="LIMPIAR MODULOS AUTOMATICOS">
                //LIMPIAR MODULOS AUTOMATICOS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Limpiar_modulo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto''Se ha limpiado el modulo','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_limpiar_modulo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha limpiado el modulo','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Eliminar_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto''Se ha eliminado el registro.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_eliminar_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha eliminado el registro.','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EDIT COMPROBADOR DE ERRORES">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Datos_cambiados")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se modificaron los datos por lote. ¡Revise!','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_comprobador")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Ingrese los datos en los campos o compruebe nuevamente la información','error');");
                    out.print("</script>");
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="ALERTAS PLUMATT">
                if (pageContext.getRequest().getAttribute("ActualizacionPlumat") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ActualizacionPlumat").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"Se han actualizado correctamente los ductos.\","
                                + "type:\"success\","
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Error\","
                                + "text:\"Ha ocurrido un problema, favor comunicarse con area TI.\","
                                + "type: \"error\","
                                + "});");
                        out.print("</script>");
                    }
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DESPEJE">
                if (pageContext.getRequest().getAttribute("DespejeDuplicado") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DespejeDuplicado").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"Se han duplicado el despeje correctamente.\","
                                + "type:\"success\","
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Error\","
                                + "text:\"Ha ocurrido un problema, favor comunicarse con area TI.\","
                                + "type: \"error\","
                                + "});");
                        out.print("</script>");
                    }
                }
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_alertas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
