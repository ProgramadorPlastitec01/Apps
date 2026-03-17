<%@page import="Controladores.ParametrosJpaController"%>
<%@page import="Email.Actividad"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory,org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>
<%
    ParametrosJpaController JpaParametro = new ParametrosJpaController();
    List lst_parametro = JpaParametro.consultarParametros("AdjuntosMetrologia");
    String global_rute = "";
    if (lst_parametro != null) {
        Object[] obj_param = (Object[]) lst_parametro.get(0);
        global_rute = obj_param[2].toString();
    }

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
    Actividad actividad = new Actividad();
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
                    file_name = fileItem.getName().toString().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ñ", "N").replace(" ", "_");
                    String dir_name = global_rute;
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
        result = actividad.FinalizarVerificacion(hidden, file_name);
        int idI = Integer.parseInt(hidden.get(0).toString());
        int idTp = Integer.parseInt(hidden.get(1).toString());
        int idV = Integer.parseInt(hidden.get(2).toString());
        String fecha = hidden.get(3).toString();
        String filtro = hidden.get(5).toString();
        String tipoIF = hidden.get(6).toString();
        String dias = hidden.get(7).toString();
        request.getRequestDispatcher("Instrumento_medicion?opc=6&result=" + result + "&idI=" + idI + "&idTp=" + idTp + "&fecha=" + fecha + "&lstTipoIF=" + tipoIF + "&txt_dias=" + dias + "&txt_bus=" + filtro + "&idV=" + idV + "").forward(request, response);
    }
%>