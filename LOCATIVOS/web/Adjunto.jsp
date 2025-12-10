<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory,org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>
<%
//FECHA
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
            List hidden = new ArrayList();
            int id_origen = 0;
            String siglatura = "";
            String tipo_origen = "";
            String observacion = "";
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
                            if (fileItem.getSize() != 0) {
                                file_name = fileItem.getName().toString().toUpperCase().replace("?", "A").replace("?", "E").replace("?", "I").replace("?", "O").replace("?", "U").replace("?", "N").replace(" ", "_");
                                String dir_name = "\\\\172.16.2.122\\d\\Sistemas de informacion\\Locativos\\Evidencias\\" + hidden.get(1) + "\\" + hidden.get(2) + "\\";
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
                                File saveTo = new File(dir_name + file_name);
                                fileItem.write(saveTo);
                            } else {
                                request.getRequestDispatcher("Solicitud?opc=1").forward(request, response);
                                break;
                            }
                        } catch (Exception e) {
                            request.getRequestDispatcher("Solicitud?opc=1").forward(request, response);
                            break;
                        }
                    }
                    //request.getRequestDispatcher("Proyecto?var=14&id_proyecto=1").forward(request, response);
                }
                id_origen = Integer.parseInt(hidden.get(0).toString());
                siglatura = hidden.get(1).toString();
                tipo_origen = hidden.get(2).toString();
                observacion = hidden.get(3).toString();
                request.getRequestDispatcher("Solicitud?opc=7&Id_origen=" + id_origen + "&file_name=" + file_name + "&Tipo_origen=" + tipo_origen + "&observaciones=" + observacion + "&area=" + siglatura).forward(request, response);
            }
%>
