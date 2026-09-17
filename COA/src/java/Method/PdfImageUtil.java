package Method;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Renders a single image file (png/jpg/jpeg/gif) as a one-page A4 PDF,
 * centered and scaled to fit. Shared by BatchRecordPdfGenerateServlet
 * (renderPhysicalFile/renderRemoteBinaryFile) and SupportDocumentUploadServlet,
 * which both need to turn an uploaded image into a PDF so it can go through
 * the same PDFBox signature-stamping/merge pipeline as a native PDF.
 */
public class PdfImageUtil {

    public static void imageToPdf(File source, File destination) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDImageXObject image = PDImageXObject.createFromFile(source.getAbsolutePath(), doc);

            float margin = 20f;
            float maxWidth = page.getMediaBox().getWidth() - margin * 2;
            float maxHeight = page.getMediaBox().getHeight() - margin * 2;
            float scale = Math.min(maxWidth / image.getWidth(), maxHeight / image.getHeight());
            float drawWidth = image.getWidth() * scale;
            float drawHeight = image.getHeight() * scale;
            float x = (page.getMediaBox().getWidth() - drawWidth) / 2;
            float y = (page.getMediaBox().getHeight() - drawHeight) / 2;

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                content.drawImage(image, x, y, drawWidth, drawHeight);
            }
            doc.save(destination);
        }
    }
}
