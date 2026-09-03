package Servlet;

import Connection.ConnectionRegistrosLAB;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ActualizarDiferenciasRegistro")
public class ActualizarDiferenciasRegistro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if (!"SI".equals(request.getParameter("confirmar"))) {
                responderError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "La actualización requiere confirmación explícita.");
                return;
            }

            String idProductoParam = request.getParameter("idProducto");
            String lote = request.getParameter("lote");
            String idLineaParam = request.getParameter("idLinea");
            String ciclo = request.getParameter("ciclo");
            if (vacio(idProductoParam) || vacio(lote) || vacio(idLineaParam)) {
                responderError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "Faltan parámetros para actualizar los registros.");
                return;
            }

            int idProducto = Integer.parseInt(idProductoParam);
            int idLinea = Integer.parseInt(idLineaParam);
            ConnectionRegistrosLAB conexion = new ConnectionRegistrosLAB();

            List<Object[]> ajustes = new ArrayList<Object[]>();
            String ajustesCustomJson = request.getParameter("ajustesJson");

            if (ajustesCustomJson != null && !ajustesCustomJson.trim().isEmpty()) {
                com.google.gson.JsonArray array = new com.google.gson.JsonParser().parse(ajustesCustomJson).getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    com.google.gson.JsonObject obj = array.get(i).getAsJsonObject();
                    String campo = obj.has("campo") ? obj.get("campo").getAsString() : "";
                    String valorC = obj.has("c") ? obj.get("c").getAsString() : "";
                    String valorP = obj.has("p") ? obj.get("p").getAsString() : "";

                    if (!campo.isEmpty()) {
                        ajustes.add(new Object[]{ campo, "%", "%", valorC, valorP });
                    }
                }
            } else {
                List<Object[]> datos = conexion.ConsultLotesRegistroVerificar(
                        idProducto, lote, idLinea, ciclo);
                List<DiferenciaRegistro> diferencias =
                        ConsultarDiferenciasRegistro.analizarDiferencias(datos);
                if (diferencias.isEmpty()) {
                    responder(response, 0, 0,
                            "Los registros ya están alineados.");
                    return;
                }

                for (DiferenciaRegistro diferencia : diferencias) {
                    String[] diferente = separarPar(diferencia.getValorDiferente());
                    String[] correcto = separarPar(diferencia.getValorCorrecto());
                    ajustes.add(new Object[]{diferencia.getCampo(), diferente[0], diferente[1],
                        correcto[0], correcto[1]});
                }
            }

            // Capturar el usuario responsable desde la sesión de la aplicación
            javax.servlet.http.HttpSession session = request.getSession(false);
            String usuarioResponsable = "SISTEMA";
            if (session != null) {
                Object nom = session.getAttribute("Nombres");
                Object usr = session.getAttribute("Usuario");
                if (nom != null && !nom.toString().trim().isEmpty()) {
                    usuarioResponsable = nom.toString().trim() + (usr != null ? " (" + usr.toString().trim() + ")" : "");
                } else if (usr != null && !usr.toString().trim().isEmpty()) {
                    usuarioResponsable = usr.toString().trim();
                }
            }

            // Capturar estado previo (Antes)
            List<Object[]> datosAntes = conexion.ConsultLotesRegistroVerificar(idProducto, lote, idLinea, ciclo);
            Map<String, String> mapAntes = new LinkedHashMap<String, String>();
            if (datosAntes != null) {
                for (DiferenciaRegistro diff : ConsultarDiferenciasRegistro.analizarDiferencias(datosAntes)) {
                    mapAntes.put(diff.getCampo(), "Predominante: [" + diff.getValorCorrecto() + "] | Diferencia: [" + diff.getValorDiferente() + "]");
                }
            }

            int actualizados = conexion.ActualizarLotesRegistroVerificar(
                    idProducto, lote, idLinea, ciclo, ajustes);

            // Guardar auditoría en log_diferencias_registro
            for (Object[] ajuste : ajustes) {
                String campoName = String.valueOf(ajuste[0]);
                String valAntes = mapAntes.containsKey(campoName) ? mapAntes.get(campoName) : "Registros sin alinear";
                String valDespues = "C: " + String.valueOf(ajuste[3]) + " | P: " + String.valueOf(ajuste[4]);
                conexion.RegistrarLogDiferencia(idProducto, lote, idLinea, ciclo, campoName, valAntes, valDespues, usuarioResponsable);
            }

            List<Object[]> datosActualizados = conexion.ConsultLotesRegistroVerificar(
                    idProducto, lote, idLinea, ciclo);
            int restantes = ConsultarDiferenciasRegistro
                    .analizarDiferencias(datosActualizados).size();
            responder(response, actualizados, restantes,
                    "Información unificada correctamente.");
        } catch (Exception ex) {
            getServletContext().log("Error actualizando diferencias de registros", ex);
            responderError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No fue posible actualizar los registros.");
        }
    }

    private String[] separarPar(String valor) {
        String texto = valor == null ? "" : valor;
        String separador = " | P: ";
        int posicion = texto.indexOf(separador);
        if (!texto.startsWith("C: ") || posicion < 0) {
            throw new IllegalArgumentException("Formato de diferencia no válido.");
        }
        return new String[]{texto.substring(3, posicion),
            texto.substring(posicion + separador.length())};
    }

    private boolean vacio(String valor) {
        return valor == null || valor.trim().length() == 0;
    }

    private void responder(HttpServletResponse response, int actualizados,
            int restantes, String mensaje) throws IOException {
        Map<String, Object> resultado = new LinkedHashMap<String, Object>();
        resultado.put("actualizados", actualizados);
        resultado.put("diferenciasRestantes", restantes);
        resultado.put("mensaje", mensaje);
        response.getWriter().print(new Gson().toJson(resultado));
    }

    private void responderError(HttpServletResponse response, int estado,
            String mensaje) throws IOException {
        response.setStatus(estado);
        response.getWriter().print(new Gson().toJson(
                Collections.singletonMap("mensaje", mensaje)));
    }
}
