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
        String FileNames = "";
        while (it.hasNext()) {
            FileItem fileItemTemp = (FileItem) it.next();
            if (fileItemTemp.isFormField()) {
                hidden.add(fileItemTemp.getString());
                try {
                    FileNames = hidden.get(4).toString();
                } catch (Exception e) {
                }
            } else {
                fileItem = fileItemTemp;

                try {
                    file_name = fileItem.getName().toString().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ñ", "N").replace(" ", "_");
                    String Route = getServletContext().getRealPath("//Interface//Content//KnowledgeFiles//");
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
                        FileDocs +=  file_name;
                    }

                    File saveTo = new File(dir_name, file_name);
                    fileItem.write(saveTo);
                } catch (Exception e) {
                    file_name = "N/A";
                }
            }
        }
        if (file_name == "") {
            file_name = "null";
        }

        int IdKnowledge = Integer.parseInt(hidden.get(0).toString());
        String Category = hidden.get(1).toString();
        String Tilte = hidden.get(2).toString();
        boolean resultA = false;
        String AttachOld = "";
        try {
            AttachOld = hidden.get(3).toString();
            if (FileDocs.equals("")) {
                FileDocs = AttachOld;
                AttachOld = "NA";

            }
        } catch (Exception e) {
            AttachOld = "NA";
        }
        String Description = hidden.get(4).toString();
        if (!AttachOld.toString().equals("NA")) {
            try {
                AttachOld.toString().replace("//", "");
                String dir_name = getServletContext().getRealPath("/Interface/Content/KnowledgeFiles/");
                File saveTo = new File(dir_name + AttachOld);
                resultA = saveTo.delete();
            } catch (Exception e) {
            }
        }
        request.getRequestDispatcher("Knowledge?opt=2&IdKnowledge=" + IdKnowledge + "&Category=" + Category + "&Title=" + Tilte + "&Description=" + Description + "&Attach=" + FileDocs + "").forward(request, response);
    }
%>
