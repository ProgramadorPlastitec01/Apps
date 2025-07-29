<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory, org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>


<%
    
//ESTE CODIGO REALIZA SUBIDA DE LOS ARCHIVOS ADJUNTOS DE CADA HOJA DE VIDA, CUAL ES LA IDEA DE ESTO; ES QUE SELECCIONE LOS 4 ARCHIVOS PRINCIPALES, 
//SE GUARDAN Y LISTO ESA ES LA PRIMERA PARTE, LA SEGUNDA PARTE ES LA EDICION DE LOS ARCHIVOS, PARA ESTO SE MANEJA EL MISMO FORMULARIO Y ESTE MISMO ARCHIVO,
//SE RECIBE EL OBJETO 3 COMO RESULTADO DEL NOMBRE DEL ARCHIVO QUE SE ESTA CAMBIANDO, EN LA LINEA 103 SE REALIZA UN RECORDDIO PARA RECONOCER CUAL ES EL CAMPO 
//QUE SE ESTA CAMBIANDO, POSTERIORMENTE RECIBE EN EL OBJETO 4 EL NOMBRE DEL ARCHIVO QUE HABIA ANTEIRORMENTE PARA ELIMINAR UY EVITAR LLENAR LA CARPETA DE ARCHIVOS BASURA
    
    
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
                    FileNames = hidden.get(3).toString();
                } catch (Exception e) {
                }
            } else {
                fileItem = fileItemTemp;

                try {
                    file_name = fileItem.getName();
                    String nameBf = file_name;
                    file_name = new String(file_name.getBytes("ISO-8859-1"), "UTF-8");

                    String Route = getServletContext().getRealPath("//Interface//Content//ResumeFiles//");
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
                        }
                        extension = adjunto[(adjunto.length - 1)].toString();
                        file_name = file_name + "_" + ano + mes + dia + "_" + hora + minuto + "." + extension;
//                        FileDocs += "[" + file_name + "]";
                    }
                    FileNames = FileNames.replace(nameBf, file_name);
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

        int idComputer = Integer.parseInt(hidden.get(0).toString());
        int idPcHead = Integer.parseInt(hidden.get(1).toString());
        String TypeDoc = hidden.get(2).toString();
        int idDetail = 0;
        try {
            idDetail = Integer.parseInt(hidden.get(6).toString());
        } catch (Exception e) {
        }
        boolean resultA = false;
        String FileDelete = "";
        try {
            FileDelete = hidden.get(4).toString();
        } catch (Exception e) {
            FileDelete = "NA";
        }
        String newFiles = "";
        int temp = 0;
        try {
            String[] allDocs = hidden.get(5).toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
            String[] fileUpload = hidden.get(3).toString().replace("]", "").replace("[", "").split("/");
            for (int i = 0; i < allDocs.length; i++) {
                String[] sdr = allDocs[i].toString().split("/");
                if (sdr[0].equals(fileUpload[0])) {
                    newFiles += "[" + fileUpload[0] + "/" + FileDocs.replace("[", "") + "";
                } else {
                    newFiles += "[" + sdr[0] + "/" + sdr[1] + "]";
                }
            }
            FileNames = newFiles;
            temp = 1;
        } catch (Exception e) {
        }

        if (!FileDelete.toString().equals("NA")) {
            try {
                String dir_name = getServletContext().getRealPath("/Interface/Content/ResumeFiles/");
                File saveTo = new File(dir_name + FileDelete);
                resultA = saveTo.delete();
            } catch (Exception e) {
            }
        }
        request.getRequestDispatcher("Computer?opt=4&IdComputer=" + idComputer + "&idpcHead=" + idPcHead + "&type=" + TypeDoc + "&fileDocs=" + FileNames + "&idDetail=" + idDetail + "&xtemp=" + temp + "").forward(request, response);
    }
%>
