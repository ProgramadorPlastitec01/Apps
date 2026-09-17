package Servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Stamps the signed-in user's registered signature (session "Firma", an
 * image under /Interface/Uploads/Signature/) onto a pending "documento de
 * soporte", at every page/position the user picked interactively in
 * SupportDocumentSign.jsp (a PDF.js viewer that shows every page at once and
 * lets the user drop as many signature copies as needed, anywhere). Saves the
 * result as a new file (suffix "_FIRMADO_{usuario}_{timestamp}") in the same
 * SupportDocs/ folder and removes the pending original, so a document is
 * always exactly one of "pendiente de firma" or "firmado", never both.
 *
 * "firmas" is a single request parameter packing every placement as
 * "pagina:xFrac:yFrac:widthFrac" entries separated by ";" (built client-side
 * in SupportDocumentSign.jsp). xFrac/yFrac/widthFrac are fractions (0..1) of
 * that page's rendered width/height, so they don't depend on the viewer's
 * zoom level; xFrac/yFrac describe the top-left corner in image (top-down)
 * coordinates, and PDF page coordinates start at the bottom-left, so the Y
 * axis is flipped when drawing.
 *
 * Gated by permission code [39] ("Adjuntar y firmar documento de soporte"),
 * created via the Role/Permission admin screen (Role.java) — update this
 * literal if that permission is registered with a different id.
 */
@WebServlet("/SupportDocumentSignServlet")
public class SupportDocumentSignServlet extends HttpServlet {

    private static final String PERMISSION_CODE = "[39]";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String permission;
        try {
            permission = session.getAttribute("Permisos").toString();
        } catch (Exception e) {
            permission = "";
        }
        String firma;
        try {
            firma = session.getAttribute("Firma").toString();
        } catch (Exception e) {
            firma = "";
        }
        String userName;
        try {
            userName = session.getAttribute("Nombres").toString();
        } catch (Exception e) {
            userName = "Usuario";
        }

        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        String archivo = request.getParameter("archivo");

        String redirectBase = "FileManager.jsp"
                + "?cliente=" + cliente + "&anio=" + anio + "&orden=" + orden + "&lote=" + lote;

        if (!permission.contains(PERMISSION_CODE) || firma == null || firma.trim().isEmpty()) {
            response.sendRedirect(redirectBase + "&msg=error_permission");
            return;
        }

        if (archivo == null || archivo.contains("_FIRMADO_")) {
            // El documento ya está firmado (o el nombre no vino): nada que firmar de nuevo.
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        String firmasParam = request.getParameter("firmas");
        if (firmasParam == null || firmasParam.trim().isEmpty()) {
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        String basePath = getServletContext().getRealPath("/Certificates");
        File supportDocsDir = new File(basePath
                + File.separator + cliente
                + File.separator + anio
                + File.separator + orden
                + File.separator + lote
                + File.separator + "SupportDocs");
        File origen = new File(supportDocsDir, archivo);

        if (!origen.isFile()) {
            response.sendRedirect(redirectBase + "&msg=file_not_found");
            return;
        }

        String signaturePath = getServletContext().getRealPath("/Interface/Uploads/Signature/" + firma);
        File signatureFile = new File(signaturePath);
        if (!signatureFile.isFile()) {
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        int dot = archivo.toLowerCase().lastIndexOf(".pdf");
        String baseName = dot > 0 ? archivo.substring(0, dot) : archivo;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String safeUserName = userName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
        File destino = new File(supportDocsDir, baseName + "_FIRMADO_" + safeUserName + "_" + timestamp + ".pdf");

        try (PDDocument doc = PDDocument.load(origen)) {
            PDImageXObject signatureImage = PDImageXObject.createFromFile(signatureFile.getAbsolutePath(), doc);
            float aspect = (float) signatureImage.getHeight() / (float) signatureImage.getWidth();

            String[] firmas = firmasParam.split(";");
            int aplicadas = 0;
            for (String firmaEntry : firmas) {
                if (firmaEntry.trim().isEmpty()) {
                    continue;
                }
                String[] partes = firmaEntry.split(":");
                if (partes.length != 4) {
                    continue;
                }

                int pagina = Integer.parseInt(partes[0]);
                double xFrac = Double.parseDouble(partes[1]);
                double yFrac = Double.parseDouble(partes[2]);
                double widthFrac = Double.parseDouble(partes[3]);

                if (pagina < 1 || pagina > doc.getNumberOfPages()) {
                    continue;
                }

                PDPage page = doc.getPage(pagina - 1);
                PDRectangle box = page.getMediaBox();

                float drawWidth = (float) (widthFrac * box.getWidth());
                float drawHeight = drawWidth * aspect;

                float x = box.getLowerLeftX() + (float) (xFrac * box.getWidth());
                float yTop = box.getLowerLeftY() + (float) (yFrac * box.getHeight());
                float y = box.getHeight() - yTop - drawHeight;

                try (PDPageContentStream content = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    content.drawImage(signatureImage, x, y, drawWidth, drawHeight);
                }
                aplicadas++;
            }

            if (aplicadas == 0) {
                response.sendRedirect(redirectBase + "&msg=error_sign");
                return;
            }

            doc.save(destino);
        } catch (Exception ex) {
            getServletContext().log("Error firmando documento de soporte: " + archivo, ex);
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        // El documento pendiente queda reemplazado por el firmado: nunca deben
        // coexistir ambos estados para el mismo documento de soporte.
        origen.delete();

        response.sendRedirect(redirectBase + "&msg=support_sign_success");
    }
}
