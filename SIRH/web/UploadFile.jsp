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
        int IdDoc = 0;
        while (it.hasNext()) {
            FileItem fileItemTemp = (FileItem) it.next();
            if (fileItemTemp.isFormField()) {
                hidden.add(fileItemTemp.getString());
                IdDoc = Integer.parseInt(hidden.get(0).toString());
            } else {
                fileItem = fileItemTemp;
                try {
                    file_name = fileItem.getName();
                    file_name = new String(file_name.getBytes("ISO-8859-1"), "UTF-8");
                    String Route = getServletContext().getRealPath("/Fotos/");
                    File dir_name = new File(Route);
                    if (file_name == "") {
                    } else {
                        file_name = IdDoc + ".jpg";
                    }
                    try {
                        String dir_namex = getServletContext().getRealPath("/Fotos/");
                        File saveTo = new File(dir_namex + file_name);
                        result = saveTo.delete();
                    } catch (Exception e) {
                    }
                    
                    File saveTo = new File(dir_name, file_name);
                    fileItem.write(saveTo);
                    
                } catch (Exception e) {
                    file_name = "Error";
                }
            }
        }
        String typeMov = hidden.get(1).toString();
        request.getRequestDispatcher("Personal?opc=7&mnu=22&dcm=" + IdDoc + "&typeMov="+ typeMov +"").forward(request, response);
    }
%>
