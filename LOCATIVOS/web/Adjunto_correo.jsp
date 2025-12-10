<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory,org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>
<%
//FECHA
    List hidden = new ArrayList();
    int id_origen = 0;
    String[] adjunto = null;
    String extension = "";
    if (ServletFileUpload.isMultipartContent(request)) {
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List fileItemsList = servletFileUpload.parseRequest(request);
        String file_name = "";
        FileItem fileItem = null;
        Iterator it = fileItemsList.iterator();
        while (it.hasNext()) {
            FileItem fileItemTemp = (FileItem) it.next();
            if (fileItemTemp.isFormField()) {
                hidden.add(fileItemTemp.getString());
            } else {
                fileItem = fileItemTemp;
                try {
                    id_origen = Integer.parseInt(hidden.get(0).toString());
                    if (fileItem.getSize() != 0) {
                        file_name = fileItem.getName().toString().toUpperCase().replace("?", "A").replace("?", "E").replace("?", "I").replace("?", "O").replace("?", "U").replace("?", "N").replace(" ", "_");
                        String dir_name = "\\\\172.16.2.122\\d\\Sistemas de informacion\\Locativos\\Adjuntos_plano\\";
                        adjunto = file_name.replace(".", "/").split("/");
                        for (int i = 0; i < adjunto.length - 1; i++) {
                            if (i == 0) {
                                file_name = adjunto[0].toString();
                            } else {
                                file_name = file_name + "_" + adjunto[i].toString();
                            }
                        }
                        extension = adjunto[(adjunto.length - 1)].toString();
                        file_name = "PLANO" + "_" + id_origen + "." + extension;
                        File saveTo = new File(dir_name + file_name);
                        fileItem.write(saveTo);
                    } else {
                        id_origen = Integer.parseInt(hidden.get(0).toString());
                        request.getRequestDispatcher("Programacion?opc=18&Id_origen=" + id_origen + "&file_name=null").forward(request, response);
                        break;
                    }
                } catch (Exception e) {
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                }
            }
            //request.getRequestDispatcher("Proyecto?var=14&id_proyecto=1").forward(request, response);
        }
        id_origen = Integer.parseInt(hidden.get(0).toString());
        request.getRequestDispatcher("Programacion?opc=18&Id_origen=" + id_origen + "&file_name=" + file_name).forward(request, response);
    }
%>