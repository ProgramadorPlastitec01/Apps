<%@page import="Mail.Conector"%>
<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory, org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>
<%
    Calendar cal = Calendar.getInstance();
    String ano = cal.get(Calendar.YEAR) + "";
    String mes = "";
    if ((cal.get(Calendar.MONTH) + 1) < 10) {
        mes = "0" + (cal.get(Calendar.MONTH) + 1);
    } else {
        mes = (cal.get(Calendar.MONTH) + 1) + "";
    }
    String dia = "";
    if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
        dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
    } else {
        dia = cal.get(Calendar.DAY_OF_MONTH) + "";
    }
    int hora = cal.get(Calendar.HOUR_OF_DAY);
    int minuto = cal.get(Calendar.MINUTE);
    int segundo = cal.get(Calendar.SECOND);
    String FileDocs = "";
    Conector Conect = new Conector();
    List hidden = new ArrayList();
    boolean result = false;
    if (ServletFileUpload.isMultipartContent(request)) {
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List fileItemsList = servletFileUpload.parseRequest(request);
        String file_name = "";
        String[] adjunto = null;
        String extension = "";
        FileItem fileItem = null;
        Iterator it = fileItemsList.iterator();
        while (it.hasNext()) {
            FileItem fileItemTemp = (FileItem) it.next();
            if (fileItemTemp.isFormField()) {
                hidden.add(fileItemTemp.getString());
            } else {
                fileItem = fileItemTemp;
                try {
                    file_name = fileItem.getName();
                    file_name = new String(file_name.getBytes("ISO-8859-1"), "UTF-8");
                    file_name = file_name
                            .replace("Á", "A")
                            .replace("É", "E")
                            .replace("Í", "I")
                            .replace("Ó", "O")
                            .replace("Ú", "U")
                            .replace("á", "a")
                            .replace("é", "e")
                            .replace("í", "i")
                            .replace("ó", "o")
                            .replace("ú", "u")
                            .replace("Ñ", "N")
                            .replace("ñ", "n")
                            .replace(" ", "_");
                    String Route = getServletContext().getRealPath("//Interfaz//Contenido//SagrilaftDocs//Attach//");
                    File dir_name = new File(Route);
//                            String dir_name = "\\\\172.16.1.176\\d\\Programador.TI2\\Desktop\\SagrilaftData\\";
                    if (file_name == "") {
                    } else {
                        adjunto = file_name.replace(".", "/").split("/");
                        for (int i = 0; i < adjunto.length - 1; i++) {
                            if (i == 0) {
                                file_name = adjunto[0].toString();
                            } else {
                                file_name = file_name + "_" + adjunto[i].toString();
                            }
                            file_name = file_name.replace("Á", "A")
                                    .replace("É", "E")
                                    .replace("Í", "I")
                                    .replace("Ó", "O")
                                    .replace("Ú", "U")
                                    .replace("Ñ", "N")
                                    .replace("á", "a")
                                    .replace("é", "e")
                                    .replace("í", "i")
                                    .replace("ó", "o")
                                    .replace("ú", "u")
                                    .replace("Ñ", "N")
                                    .replace(" ", "_")
                                    .replace("(", "_")
                                    .replace(")", "_");
                        }
                        extension = adjunto[(adjunto.length - 1)].toString();
                        file_name = Integer.parseInt(hidden.get(0).toString()) + "_" + file_name + "_" + ano + mes + dia + "_" + hora + minuto + "." + extension;
//                        FileDocs += "[" + file_name + "]";
                    }

                    File saveTo = new File(dir_name, file_name);
                    fileItem.write(saveTo);
                } catch (Exception e) {
                    file_name = "Error";
//                    break;
                }
                FileDocs += "[" + file_name + "]";
            }
        }
        if (file_name == "") {
            file_name = "null";
        }

        int IdDoc = Integer.parseInt(hidden.get(0).toString());
        String IdFiles = hidden.get(1).toString();
        String ValidAction = hidden.get(2).toString();
        String DataValid = "";
        try {
            DataValid = hidden.get(3).toString();
            DataValid = new String(DataValid.getBytes("ISO-8859-1"), "UTF-8");
            DataValid = DataValid
                    .replace("Á", "A")
                    .replace("É", "E")
                    .replace("Í", "I")
                    .replace("Ó", "O")
                    .replace("Ú", "U")
                    .replace("á", "a")
                    .replace("é", "e")
                    .replace("í", "i")
                    .replace("ó", "o")
                    .replace("ú", "u")
                    .replace("Ñ", "N")
                    .replace("ñ", "n")
                    .replace(" ", "_");
        } catch (Exception e) {
            DataValid = "NA";
        }
        boolean resultA = false;
        String FileDelete = "";
        try {
            FileDelete = hidden.get(4).toString();
        } catch (Exception e) {
            FileDelete = "NA";
        }
        if (!FileDelete.toString().equals("NA")) {
            try {
                String dir_name = getServletContext().getRealPath("/Interfaz/Contenido/SagrilaftDocs/Attach/");
                File saveTo = new File(dir_name + FileDelete);
                resultA = saveTo.delete();
            } catch (Exception e) {
            }
        }
        request.getRequestDispatcher("ClientSection?opt=15&IdDoc=" + IdDoc + "&TxtValidAction=" + ValidAction + "&txtIdFiles=" + IdFiles + "&Txt_FilesDoc=" + FileDocs + "&Txt_ValidNew=" + DataValid + "").forward(request, response);
    }
%>
