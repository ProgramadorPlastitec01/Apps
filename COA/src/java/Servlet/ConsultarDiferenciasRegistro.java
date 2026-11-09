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
                conexion.ConsultLotesRegistroVerificarDirecto(
                        idProducto,
                        lote,
                        idLinea,
                        ciclo
                );

        // Todos los campos con datos del lote, tengan o no diferencias, para
        // poder corregir cualquiera (p.ej. un lote de manga mal digitado en
        // todo el lote, que no aparece como diferencia).
        List<Map<String, Object>> campos = analizarCampos(datos);

        Gson gson = new Gson();

        response.getWriter().print(gson.toJson(campos));

    } catch (Exception ex) {

        getServletContext().log("Error consultando diferencias de registros", ex);
        responderError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No fue posible consultar las diferencias.");
    }
}

    /** Campos verificables: nombre, indice C e indice P en el layout de datos. */
    private static final Object[][] CAMPOS = {
        {"MANGA", 2, 3},
        {"DUCTO DERECHO", 4, 5},
        {"DUCTO IZQUIERDO", 6, 7},
        {"DUCTO CENTRAL", 19, 20},
        {"TINTA / COLOR", 10, 14},
        {"ENSAMBLE 1", 8, 9},
        {"ENSAMBLE 2", 16, 17},
        {"ENSAMBLE 3", 25, 26},
        {"ENSAMBLE 4", 27, 28}
    };

    /**
     * Resume cada campo con todos sus valores C/P y la cantidad de registros
     * de cada uno (agrupando sin distinguir mayusculas, igual que el badge).
     * Se omiten los campos cuyo unico valor es "N/A | N/A" (componente que el
     * producto no usa). El primer valor de la lista es el predominante.
     */
    public static List<Map<String, Object>> analizarCampos(List<Object[]> datos) {
        List<Map<String, Object>> resultado = new ArrayList<Map<String, Object>>();
        if (datos == null || datos.isEmpty()) {
            return resultado;
        }
        for (Object[] def : CAMPOS) {
            String nombreCampo = (String) def[0];
            int indiceC = (Integer) def[1];
            int indiceP = (Integer) def[2];
            Map<String, String[]> pares = new LinkedHashMap<String, String[]>();
            Map<String, Integer> frecuencias = new LinkedHashMap<String, Integer>();
            int total = 0;
            for (Object[] dato : datos) {
                if (dato == null || dato.length <= Math.max(indiceC, indiceP)) {
                    continue;
                }
                int cantidad = obtenerCantidad(dato.length > 1 ? dato[1] : null);
                if (cantidad <= 0) {
                    continue;
                }
                String c = normalizarValor(dato[indiceC]);
                String p = normalizarValor(dato[indiceP]);
                String clave = (c + "|" + p).toLowerCase();
                if (!pares.containsKey(clave)) {
                    pares.put(clave, new String[]{c, p});
                }
                Integer acumulado = frecuencias.get(clave);
                frecuencias.put(clave, (acumulado == null ? 0 : acumulado) + cantidad);
                total += cantidad;
            }
            if (frecuencias.isEmpty()
                    || (frecuencias.size() == 1 && frecuencias.containsKey("n/a|n/a"))) {
                continue;
            }
            List<Map.Entry<String, Integer>> orden = new ArrayList<Map.Entry<String, Integer>>(frecuencias.entrySet());
            // Mayor frecuencia primero; en empate se conserva el orden de aparicion.
            Collections.sort(orden, new java.util.Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                    return b.getValue() - a.getValue();
                }
            });
            List<Map<String, Object>> valores = new ArrayList<Map<String, Object>>();
            for (Map.Entry<String, Integer> e : orden) {
                Map<String, Object> v = new LinkedHashMap<String, Object>();
                v.put("c", pares.get(e.getKey())[0]);
                v.put("p", pares.get(e.getKey())[1]);
                v.put("cantidad", e.getValue());
                valores.add(v);
            }
            Map<String, Object> campo = new LinkedHashMap<String, Object>();
            campo.put("campo", nombreCampo);
            campo.put("total", total);
            campo.put("diferencias", total - orden.get(0).getValue());
            campo.put("valores", valores);
            resultado.add(campo);
        }
        return resultado;
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

        // Se agrupa por una clave insensible a mayúsculas: MySQL compara
        // strings con una collation case-insensitive (utf8_general_ci), así
        // que ConsultMaterials (el badge "Diferencias encontradas") no
        // cuenta como diferencia un valor que solo cambia de mayúscula
        // ("7203-36I24-2" vs "7203-36i24-2"). Si aquí se comparara con
        // igualdad exacta de Java, se marcarían diferencias falsas que el
        // badge nunca reportó. El valor MOSTRADO conserva el casing
        // original (el primero visto para esa clave), solo la agrupación es
        // insensible a mayúsculas.
        Map<String, String> valoresMostrados = new LinkedHashMap<String, String>();
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
            String clave = valor.toLowerCase();
            if (!valoresMostrados.containsKey(clave)) {
                valoresMostrados.put(clave, valor);
            }
            Integer acumulado = frecuencias.get(clave);
            frecuencias.put(clave, (acumulado == null ? 0 : acumulado) + cantidad);
        }

        if (frecuencias.size() <= 1) {
            return Collections.emptyList();
        }

        String claveCorrecta = null;
        int cantidadCorrecto = -1;
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            if (entrada.getValue() > cantidadCorrecto) {
                claveCorrecta = entrada.getKey();
                cantidadCorrecto = entrada.getValue();
            }
        }

        List<DiferenciaRegistro> diferencias = new ArrayList<DiferenciaRegistro>();
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            if (!entrada.getKey().equals(claveCorrecta)) {
                diferencias.add(new DiferenciaRegistro(nombreCampo,
                        valoresMostrados.get(claveCorrecta), valoresMostrados.get(entrada.getKey()),
                        cantidadCorrecto, entrada.getValue()));
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
