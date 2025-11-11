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
                                                  .replace(" ", "_")
                                                  .replace("(", "_")
                                                  .replace(")", "_");
                        }
                        extension = adjunto[(adjunto.length - 1)].toString();
                        file_name = file_name + "_" + ano + mes + dia + "_" + hora + minuto + "." + extension;
                        FileDocs += "[" + file_name + "]";
                    }
                    
                    File saveTo = new File(dir_name, file_name);
                    fileItem.write(saveTo);
                } catch (Exception e) {
                    file_name = "N/A";
                    break;
                }
            }
        }
        if (file_name == "") {
            file_name = "null";
        }
        int IdSegmentation = Integer.parseInt(hidden.get(0).toString());
        String Format = hidden.get(1).toString();
        String Date = hidden.get(2).toString();
        String Affair = hidden.get(3).toString();
        String Description = hidden.get(4).toString();
        int IdVisit = Integer.parseInt(hidden.get(5).toString());
        String AttachOld = "";
        try {
            AttachOld = hidden.get(6).toString();
            AttachOld = new String(AttachOld.getBytes("ISO-8859-1"), "UTF-8");
            AttachOld = AttachOld
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
            if (FileDocs.equals("")) {
                FileDocs = AttachOld;
                AttachOld = "NA";
            }
        } catch (Exception e) {
            AttachOld = "NA";
        }
        boolean resultA = false;
        if (!AttachOld.toString().equals("NA")) {
            try {
                AttachOld.toString().replace("//", "");
                String dir_name = getServletContext().getRealPath("/Interfaz/Contenido/SagrilaftDocs/Attach/");
                File saveTo = new File(dir_name + AttachOld);
                resultA = saveTo.delete();
            } catch (Exception e) {
            }
        }
        request.getRequestDispatcher("Segmentation?opt=4&IdSegmentation=" + IdSegmentation + "&Format=" + Format + "&Txt_Date=" + Date + "&Txt_Affair=" + Affair + "&Txt_Description=" + Description + "&Txt_FilesDoc=" + FileDocs + "&IdVisit=" + IdVisit + "").forward(request, response);
    }
%>
