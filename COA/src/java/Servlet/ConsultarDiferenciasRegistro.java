/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Connection.ConnectionRegistrosLAB;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Programador.TI1
 */
@WebServlet("/ConsultarDiferenciasRegistro")
public class ConsultarDiferenciasRegistro extends HttpServlet {
    @Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    try {

        String idProductoParam = request.getParameter("idProducto");
        String lote = request.getParameter("lote");
        String idLineaParam = request.getParameter("idLinea");
        String ciclo = request.getParameter("ciclo");

        if (esVacio(idProductoParam) || esVacio(lote)
                || esVacio(idLineaParam)) {
            responderError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Faltan parámetros para verificar las diferencias.");
            return;
        }

        int idProducto = Integer.parseInt(idProductoParam);
        int idLinea = Integer.parseInt(idLineaParam);

        ConnectionRegistrosLAB conexion =
                new ConnectionRegistrosLAB();

        List<Object[]> datos =
                conexion.ConsultLotesRegistroVerificar(
                        idProducto,
                        lote,
                        idLinea,
                        ciclo
                );

        List<DiferenciaRegistro> diferencias = analizarDiferencias(datos);


        Gson gson = new Gson();

        response.getWriter().print(gson.toJson(diferencias));

    } catch (Exception ex) {

        getServletContext().log("Error consultando diferencias de registros", ex);
        responderError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No fue posible consultar las diferencias.");
    }
}

    /**
     * Agrupa el par C/P por su frecuencia. En un empate se conserva el valor
     * que apareció primero en el resultado del procedimiento; LinkedHashMap
     * hace la regla determinista sin inventar un valor predominante.
     */
    public static List<DiferenciaRegistro> analizarDiferencias(List<Object[]> datos) {
        if (datos == null || datos.isEmpty()) {
            return Collections.emptyList();
        }

        List<DiferenciaRegistro> diferencias = new ArrayList<DiferenciaRegistro>();
        diferencias.addAll(analizarCampo(datos, "MANGA", 2, 3));
        diferencias.addAll(analizarCampo(datos, "DUCTO DERECHO", 4, 5));
        diferencias.addAll(analizarCampo(datos, "DUCTO IZQUIERDO", 6, 7));
        diferencias.addAll(analizarCampo(datos, "DUCTO CENTRAL", 19, 20));
        diferencias.addAll(analizarCampo(datos, "TINTA / COLOR", 10, 14));
        diferencias.addAll(analizarCampo(datos, "ENSAMBLE 1", 8, 9));
        diferencias.addAll(analizarCampo(datos, "ENSAMBLE 2", 16, 17));
        diferencias.addAll(analizarCampo(datos, "ENSAMBLE 3", 25, 26));
        diferencias.addAll(analizarCampo(datos, "ENSAMBLE 4", 27, 28));
        return diferencias;
    }

    private static List<DiferenciaRegistro> analizarCampo(List<Object[]> datos,
            String nombreCampo, int indiceC, int indiceP) {

        Map<String, Integer> frecuencias = new LinkedHashMap<String, Integer>();

        for (Object[] dato : datos) {
            if (dato == null || dato.length <= Math.max(indiceC, indiceP)) {
                continue;
            }

            int cantidad = obtenerCantidad(dato.length > 1 ? dato[1] : null);
            if (cantidad <= 0) {
                continue;
            }

            String valor = "C: " + normalizarValor(dato[indiceC])
                    + " | P: " + normalizarValor(dato[indiceP]);
            Integer acumulado = frecuencias.get(valor);
            frecuencias.put(valor, (acumulado == null ? 0 : acumulado) + cantidad);
        }

        if (frecuencias.size() <= 1) {
            return Collections.emptyList();
        }

        String valorCorrecto = null;
        int cantidadCorrecto = -1;
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            if (entrada.getValue() > cantidadCorrecto) {
                valorCorrecto = entrada.getKey();
                cantidadCorrecto = entrada.getValue();
            }
        }

        List<DiferenciaRegistro> diferencias = new ArrayList<DiferenciaRegistro>();
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            if (!entrada.getKey().equals(valorCorrecto)) {
                diferencias.add(new DiferenciaRegistro(nombreCampo,
                        valorCorrecto, entrada.getKey(), cantidadCorrecto,
                        entrada.getValue()));
            }
        }
        return diferencias;
    }

    private static int obtenerCantidad(Object valor) {
        if (valor instanceof Number) {
            return ((Number) valor).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(valor));
        } catch (Exception ex) {
            return 0;
        }
    }

    private static String normalizarValor(Object valor) {
        String texto = valor == null ? "" : String.valueOf(valor).trim();
        return texto.length() == 0 ? "N/A" : texto;
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().length() == 0;
    }

    private void responderError(HttpServletResponse response, int estado,
            String mensaje) throws IOException {
        response.setStatus(estado);
        response.getWriter().print(new Gson().toJson(
                Collections.singletonMap("mensaje", mensaje)));
    }
}
