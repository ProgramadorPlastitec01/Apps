package Controller;

/**
 * Fila liviana de la tabla certificate_files. Expone getName()/lastModified()
 * a propósito, imitando el subconjunto de java.io.File que ya consume
 * FileManager.jsp, para minimizar el cambio en ese JSP al migrar de listar
 * carpetas físicas a leer esta tabla de metadatos.
 */
public class CertificateFileRow {

    private final long id;
    private final long officeFileId;
    private final String name;
    private final String mimeType;
    private final Long sizeBytes;
    private final long lastModifiedMillis;
    private final String uploadedById;
    private final String uploadedByName;

    public CertificateFileRow(long id, long officeFileId, String name, String mimeType, Long sizeBytes, long lastModifiedMillis) {
        this(id, officeFileId, name, mimeType, sizeBytes, lastModifiedMillis, null, null);
    }

    public CertificateFileRow(long id, long officeFileId, String name, String mimeType, Long sizeBytes, long lastModifiedMillis,
            String uploadedById, String uploadedByName) {
        this.id = id;
        this.officeFileId = officeFileId;
        this.name = name;
        this.mimeType = mimeType;
        this.sizeBytes = sizeBytes;
        this.lastModifiedMillis = lastModifiedMillis;
        this.uploadedById = uploadedById;
        this.uploadedByName = uploadedByName;
    }

    public long getId() {
        return id;
    }

    public long getOfficeFileId() {
        return officeFileId;
    }

    public String getName() {
        return name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public long lastModified() {
        return lastModifiedMillis;
    }

    public String getUploadedById() {
        return uploadedById;
    }

    public String getUploadedByName() {
        return uploadedByName;
    }
}
