<%@page import="Controladores.ParametrosJpaController"%>
<%@page import="Controladores.InstrumentoMedicionJpaController"%>
<%@page import="Email.Actividad"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.servlet.ServletFileUpload,org.apache.commons.fileupload.disk.DiskFileItemFactory,org.apache.commons.io.FilenameUtils,java.io.File, java.io.*,java.util.*,javax.servlet.*" %>
<%

    ParametrosJpaController JpaParametro = new ParametrosJpaController();
    List lst_parametro = JpaParametro.consultarParametros("AdjuntosMetrologia");
    String global_rute = "";
    if (lst_parametro != null) {
        Object[] obj_param = (Object[]) lst_parametro.get(0);
        global_rute = obj_param[1].toString();
    }

    boolean resultA = false;
    boolean resultV = false;
    List hidden = new ArrayList();
    InstrumentoMedicionJpaController jpa_intrumento = new InstrumentoMedicionJpaController();
    if (ServletFileUpload.isMultipartContent(request)) {
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List fileItemsList = servletFileUpload.parseRequest(request);
        String fileItem = null;
        Iterator it = fileItemsList.iterator();
        while (it.hasNext()) {
            FileItem fileItemTemp = (FileItem) it.next();
            if (fileItemTemp.isFormField()) {
                hidden.add(fileItemTemp.getString());
            } else {
            }
        }
        fileItem = hidden.get(3).toString();
        try {
            String dir_name = global_rute + fileItem;
            File saveTo = new File(dir_name);
            resultA = saveTo.delete();
        } catch (Exception e) {
        }
        int idV = Integer.parseInt(hidden.get(0).toString());
        int idI = Integer.parseInt(hidden.get(1).toString());
        int idTp = Integer.parseInt(hidden.get(2).toString());
        String tipoIF = hidden.get(3).toString();
        String dias = hidden.get(4).toString();
        String filtro = hidden.get(5).toString();
        resultV = jpa_intrumento.eliminarVerificacionInstrumento(idV);
        request.getRequestDispatcher("Instrumento_medicion?opc=3&resultA=" + resultA + "&resultV=" + resultV + "&idI=" + idI + "&idTp=" + idTp + "&EvE=" + 1 + "&idV=" + 0 + "&lstTipoIF=" + tipoIF + "&txt_dias=" + dias + "&txt_bus=" + filtro + "").forward(request, response);

    }

%>