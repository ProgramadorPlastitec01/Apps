<%@page import="Controladoras.CargoJpaController"%>
<%@page import="Email.Actividad"%>
<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory,org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>
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
            List hidden = new ArrayList();
            Actividad actividad = new Actividad();
            CargoJpaController jpa_cargo = new CargoJpaController();
            HttpSession sesion = request.getSession();
            boolean result = false;
            if (ServletFileUpload.isMultipartContent(request)) {
                ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
                List fileItemsList = servletFileUpload.parseRequest(request);
                String file_name = "";
                String filtro = "";
                List info = null;
                int idC = 0;
                String[] adjunto = null;
                String extension = "";
                FileItem fileItem = null;
                Iterator it = fileItemsList.iterator();
                while (it.hasNext()) {
                    FileItem fileItemTemp = (FileItem) it.next();
                    if (fileItemTemp.isFormField()) {
                        hidden.add(fileItemTemp.getString());
                    } else {
                        idC = Integer.parseInt(sesion.getAttribute("Cargo").toString());
                        info = jpa_cargo.ConsultaCargosPorId(idC);
                        Object[] obj_cargo = (Object[]) info.get(0);
                        fileItem = fileItemTemp;
                        try {
                            file_name = fileItem.getName().toString().toUpperCase().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ñ", "N").replace(" ", "_");
                            //String dir_name = "\\\\172.16.2.117\\d\\Sistemas de informacion\\Dis_Desarrollo\\Adjuntos_proyectos\\" + hidden.get(1);
                            String dir_name = "\\\\172.16.2.117\\d\\Sistemas de informacion\\Bitacora_general\\Archivos_adjuntos\\" + obj_cargo[10] + "\\";
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
                            }
                            File saveTo = new File(dir_name + file_name);
                            fileItem.write(saveTo);
                        } catch (Exception e) {
                            file_name = "N/A";
                            //request.getRequestDispatcher("Actividad?op=1&idC=" + Integer.parseInt(hidden.get(0).toString()) + "&idA=0&txt_bus=").forward(request, response);
                            break;
                        }

                    }
                    //request.getRequestDispatcher("Proyecto?var=14&id_proyecto=1").forward(request, response);
                }
                if (file_name == "") {
                    file_name = "null";
                }
                filtro = hidden.get(0).toString();
                result = actividad.ModificarActividad(hidden, file_name);
                request.getRequestDispatcher("Actividad?op=3&resultM=" + result + "&txt_bus=" + filtro + "").forward(request, response);
            }
%>