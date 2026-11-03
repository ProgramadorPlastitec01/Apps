package Servlet;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Controller.CertificatesJpaController;
import Controller.SignatureHistoryJpaController;
import Method.SignatureHistory;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Consulta del historial de firmas de un documento (requerimiento 1.1.U03),
 * en JSON para el modal de línea de tiempo (Interface/Content/Assets/js/
 * signature-history.js).
 *
 * GET ?tipo=CERTIFICADO&ref={id_certificate}
 * GET ?tipo=SOPORTE&ref={id_certificate}
 * GET ?tipo=BATCH_RECORD&ref={id de certificate_files del PDF, pendiente o aprobado}
 */
@WebServlet("/SignatureHistoryServlet")
public class SignatureHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("Permisos") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("{\"error\":\"Sesión expirada.\"}");
            return;
        }

        String tipo = request.getParameter("tipo") == null ? "" : request.getParameter("tipo").trim();
        String ref = request.getParameter("ref") == null ? "" : request.getParameter("ref").trim();
        long refNum;
        try {
            refNum = Long.parseLong(ref);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Documento inválido.\"}");
            return;
        }

        Map<String, Object> salida = new LinkedHashMap<String, Object>();
        String llave;

        if (SignatureHistory.TIPO_CERTIFICADO.equals(tipo)) {
            llave = String.valueOf(refNum);
            salida.put("tipo", "Certificado de calidad");
            salida.put("documentoId", "COA-" + refNum);
            List lst = new CertificatesJpaController().ConsultCertificatesId("", (int) refNum);
            if (lst != null && !lst.isEmpty()) {
                Object[] c = (Object[]) lst.get(0);
                String consecutivo = c[3] == null ? "" : c[3].toString().trim();
                String producto = c[6] == null ? "" : c[6].toString().trim();
                salida.put("titulo", "Certificado " + consecutivo + (producto.isEmpty() ? "" : " - " + producto));
                salida.put("lote", "Orden " + valor(c[5]) + " · Lote " + valor(c[7]) + " · " + valor(c[4]));
                int estado;
                try {
                    estado = Integer.parseInt(c[8].toString());
                } catch (Exception e) {
                    estado = -1;
                }
                switch (estado) {
                    case 0:
                        salida.put("estado", "Eliminado");
                        salida.put("estadoClase", "eliminado");
                        break;
                    case 1:
                        salida.put("estado", "En gestión");
                        salida.put("estadoClase", "gestion");
                        break;
                    case 2:
                        salida.put("estado", "Finalizado - pendiente de firma");
                        salida.put("estadoClase", "pendiente");
                        break;
                    case 3:
                        salida.put("estado", "Firmado");
                        salida.put("estadoClase", "completo");
                        break;
                    default:
                        salida.put("estado", "—");
                        salida.put("estadoClase", "gestion");
                }
            } else {
                salida.put("titulo", "Certificado " + refNum);
            }
        } else if (SignatureHistory.TIPO_SOPORTE.equals(tipo)) {
            llave = String.valueOf(refNum);
            salida.put("tipo", "Documento de soporte");
            salida.put("documentoId", "SOP-COA-" + refNum);
            CertificateFileRow soporte = new CertificateFileJpaController().consultSupportFileByCertificate(refNum);
            if (soporte != null) {
                boolean firmado = soporte.getName().contains("_FIRMADO_");
                salida.put("titulo", soporte.getName());
                salida.put("estado", firmado ? "Firmado" : "Pendiente de firma");
                salida.put("estadoClase", firmado ? "completo" : "pendiente");
            } else {
                salida.put("titulo", "Documento de soporte del certificado COA-" + refNum);
                salida.put("estado", "Sin documento vigente");
                salida.put("estadoClase", "eliminado");
            }
        } else if (SignatureHistory.TIPO_BATCH_RECORD.equals(tipo)) {
            CertificateFileRow fila = new CertificateFileJpaController().consultFileById(refNum);
            if (fila == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().print("{\"error\":\"No se encontró el Batch Record.\"}");
                return;
            }
            llave = SignatureHistory.batchRecordKey(fila.getName());
            boolean aprobado = fila.getName().contains("_FIRMADO_");
            salida.put("tipo", "Batch Record");
            salida.put("documentoId", llave);
            salida.put("titulo", fila.getName());
            salida.put("estado", aprobado ? "Aprobado" : "Pendiente de aprobación");
            salida.put("estadoClase", aprobado ? "completo" : "pendiente");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Tipo de documento inválido.\"}");
            return;
        }

        List<Map<String, String>> eventos = new ArrayList<Map<String, String>>();
        for (Object[] f : new SignatureHistoryJpaController().consult(tipo, llave)) {
            Map<String, String> e = new LinkedHashMap<String, String>();
            String evento = valor(f[1]);
            String usuario = valor(f[3]);
            e.put("evento", evento);
            e.put("etiqueta", etiqueta(evento));
            e.put("descripcion", descripcion(tipo, evento) + (usuario.isEmpty() ? "" : " por " + usuario));
            e.put("detalle", valor(f[2]));
            e.put("rol", valor(f[4]));
            e.put("ip", valor(f[5]));
            e.put("aplicativo", valor(f[6]));
            e.put("equipo", SignatureHistory.describirEquipo(f[7] == null ? null : f[7].toString()));
            e.put("fecha", valor(f[8]));
            e.put("hora", valor(f[9]));
            e.put("archivo", valor(f[10]));
            eventos.add(e);
        }
        salida.put("eventos", eventos);

        response.getWriter().print(new Gson().toJson(salida));
    }

    private static String etiqueta(String evento) {
        if (SignatureHistory.EVENTO_REGISTRADO.equals(evento)) {
            return "Registrado";
        }
        if (SignatureHistory.EVENTO_FINALIZADO.equals(evento)) {
            return "Finalizado";
        }
        if (SignatureHistory.EVENTO_FIRMADO.equals(evento)) {
            return "Firmado";
        }
        return evento;
    }

    private static String descripcion(String tipo, String evento) {
        boolean br = SignatureHistory.TIPO_BATCH_RECORD.equals(tipo);
        boolean soporte = SignatureHistory.TIPO_SOPORTE.equals(tipo);
        if (SignatureHistory.EVENTO_REGISTRADO.equals(evento)) {
            return br ? "Generado" : soporte ? "Adjuntado" : "Registrado";
        }
        if (SignatureHistory.EVENTO_FINALIZADO.equals(evento)) {
            return "Finalizado y enviado a firma";
        }
        if (SignatureHistory.EVENTO_FIRMADO.equals(evento)) {
            return br ? "Firmado y aprobado" : "Firmado";
        }
        return evento;
    }

    private static String valor(Object o) {
        return o == null ? "" : o.toString().trim();
    }
}
