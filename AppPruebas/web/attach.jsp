<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory, org.apache.commons.io.FilenameUtils, java.time.LocalDateTime, java.time.format.DateTimeFormatter, java.io.File, java.util.List, java.util.Iterator" %>
<%
    // Configuración de parámetros para la carga de archivos
    int MAX_FILE_SIZE = 10 * 1024 * 1024; // Tamaño máximo del archivo (10 MB)
    String UPLOAD_DIRECTORY = getServletContext().getRealPath("/Interfaz/Contenido/dataFiles/");
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

    // Validar si la solicitud es de tipo multipart
    if (!isMultipart) {
        out.println("<h3>Error: La solicitud no es compatible con carga de archivos</h3>");
        return;
    }

    // Configuración de Apache Commons FileUpload
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setSizeMax(MAX_FILE_SIZE);

    try {
        // Procesar los elementos enviados en el formulario
        List<FileItem> items = upload.parseRequest(request);
        Iterator<FileItem> iterator = items.iterator();
        String uploadedFilePath = "";
        String idApp = "";
        String nameApp = "";
        String fileToDelete = "";
        String Setting = "";

        while (iterator.hasNext()) {
            FileItem item = iterator.next();

            if (item.isFormField()) {
                // Procesar campos de formulario
                String fieldName = item.getFieldName();
                String fieldValue = item.getString("UTF-8");

                if (fieldName.equals("idApp")) {
                    idApp = fieldValue;
                } else if (fieldName.equals("txtApp")) {
                    nameApp = fieldValue;
                } else if (fieldName.equals("txtDelt")) {
                    fileToDelete = fieldValue;
                } else if (fieldName.equals("cbxSetting")) {
                    Setting = fieldValue;
                }
            } else {
                // Procesar archivo subido
                String fileName = FilenameUtils.getName(item.getName());
                if (fileName != null && !fileName.isEmpty()) {
                    // Sanitizar nombre del archivo
                    fileName = fileName
                            .replace("Á", "A").replace("É", "E")
                            .replace("Í", "I").replace("Ó", "O")
                            .replace("Ú", "U").replace("í", "i")
                            .replace("á", "a").replace("é", "e")
                            .replace("ó", "o").replace("ú", "u")
                            .replace("Ñ", "N").replace("ñ", "n")
                            .replace(" ", "_");

                    // Agregar marca de tiempo al nombre del archivo
                    String timestamp = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    fileName = FilenameUtils.getBaseName(fileName) + "_" + timestamp + "." + FilenameUtils.getExtension(fileName);

                    // Guardar archivo en el directorio configurado
                    File uploadDir = new File(UPLOAD_DIRECTORY);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    File uploadedFile = new File(uploadDir, fileName);
                    item.write(uploadedFile);
                    uploadedFilePath = fileName;
                }
            }
        }

        // Eliminar archivo anterior si se especificó
        if (fileToDelete != null && !fileToDelete.isEmpty()) {
            File oldFile = new File(UPLOAD_DIRECTORY, fileToDelete);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        // Redirigir a la página de éxito o manejar la lógica posterior
        request.getRequestDispatcher("App?opt=2&idApp=" + idApp + "&txtApp=" + nameApp + "&cbxSetting=" + Setting + "&txtLogo=" + uploadedFilePath).forward(request, response);

    } catch (Exception e) {
        log("Error al manejar la carga de archivos", e);
        out.println("<h3>Error al procesar la carga del archivo: " + e.getMessage() + "</h3>");
    }
%>
