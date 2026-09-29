package Method;

import Controller.UserSignatureJpaController;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;

/**
 * Imágenes de firma de usuario en el Gestor de Archivos (Office Platform,
 * carpeta "COA/Firmas"). Reemplaza la escritura en
 * web/Interface/Uploads/Signature/ que hacía UpdateSignatureUser.
 *
 * Las firmas registradas antes de esta migración (ej. "firma.png") solo
 * existen en esa carpeta local: {@link #open} las sigue leyendo desde ahí
 * (solo lectura) para que los certificados ya firmados no pierdan la imagen.
 */
public class SignatureStorage {

    public static final String LEGACY_FOLDER = "/Interface/Uploads/Signature/";

    /**
     * Sube la imagen al gestor con un nombre único (firma_{idUser}_{fecha}.ext)
     * y la registra en user_signatures. Devuelve el nombre generado, que es lo
     * que se guarda en user.signature.
     */
    public static String upload(int idUser, String originalFileName, InputStream contenido, String mimeType,
            String userId, String userName) throws IOException {

        String extension = "";
        int dot = originalFileName != null ? originalFileName.lastIndexOf('.') : -1;
        if (dot >= 0) {
            extension = originalFileName.substring(dot).toLowerCase().replaceAll("[^a-z0-9.]", "");
        }
        String fileName = "firma_" + idUser + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + extension;

        long folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", "Firmas");
        OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                folderId, fileName, "Firma de usuario " + idUser, contenido, mimeType, userId, userName);

        boolean registrado = new UserSignatureJpaController().registerSignature(idUser, fileName,
                resultado.fileId, resultado.uuid, resultado.mimeType, resultado.size);
        if (!registrado) {
            throw new IOException("La firma se subió al gestor (fileId " + resultado.fileId + ") pero no se pudo registrar en user_signatures.");
        }
        return fileName;
    }

    /** Abre la imagen de firma {@code fileName}: primero en el gestor, luego (legado) en la carpeta local. Devuelve null si no existe. */
    public static SignatureContent open(ServletContext ctx, String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()
                || fileName.contains("/") || fileName.contains("\\") || fileName.contains("..")) {
            return null;
        }

        Object[] ref = new UserSignatureJpaController().consultByFileName(fileName);
        if (ref != null) {
            OfficePlatformService.OfficeDownload descarga = OfficePlatformService.descargarArchivo((Long) ref[0]);
            SignatureContent contenido = new SignatureContent();
            contenido.inputStream = descarga.inputStream;
            contenido.contentType = descarga.contentType != null ? descarga.contentType : (String) ref[1];
            contenido.contentLength = descarga.contentLength;
            return contenido;
        }

        String legacyPath = ctx.getRealPath(LEGACY_FOLDER + fileName);
        File legacy = legacyPath != null ? new File(legacyPath) : null;
        if (legacy != null && legacy.isFile()) {
            SignatureContent contenido = new SignatureContent();
            contenido.inputStream = new FileInputStream(legacy);
            contenido.contentType = ctx.getMimeType(legacy.getName());
            contenido.contentLength = legacy.length();
            return contenido;
        }
        return null;
    }

    /** Igual que {@link #open} pero materializa la imagen en memoria (para PDFBox). */
    public static byte[] readBytes(ServletContext ctx, String fileName) throws IOException {
        SignatureContent contenido = open(ctx, fileName);
        if (contenido == null) {
            return null;
        }
        try (InputStream in = contenido.inputStream) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int leidos;
            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
            }
            return out.toByteArray();
        }
    }

    public static class SignatureContent {
        public InputStream inputStream;
        public String contentType;
        public long contentLength;
    }
}
