<%@page import="Controller.CertificatesJpaController"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Optional"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
        List lst_certificate = null;
        int IdCertificates = 0;
        String Html = "", IdSig = "", Code = "";
        try {
            IdCertificates = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("IdCertificates")).orElse("0"));
        } catch (NumberFormatException e) {
            IdCertificates = 0;
        }
        try {
            lst_certificate = CertificatesJpa.ConsultCertificatesIdHtml(IdCertificates);
            if (lst_certificate != null) {
                Object[] Obj_Format = (Object[]) lst_certificate.get(0);
                Html = Obj_Format[3].toString();
                Code = Obj_Format[2].toString();
                if (Obj_Format[5] != null) {
                    IdSig = Obj_Format[5].toString();
                }
            }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= Code%></title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
    </head>
    <body>
        <section class="section">
            <div class="card">
                <div class="card-header d-flex" style="justify-content: space-between;">
                    <h4><%= Code%></h4>
                    <div>
                        <button class="btn btn-outline-info btn-sm"
                                style="border-radius: 4px; padding: 2px 9px;"
                                onclick="PrintHtml()"
                                data-toggle="tooltip"
                                data-placement="top"
                                title="Imprimir">
                            <i class="fas fa-print"></i>
                        </button>
                    </div>
                </div>
            </div>
            <div id="Imprimir">
                <div id="HtmlContent">
                    <%
                        Html = Html.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        Html = Html.replaceAll("<input type=\"checkbox\"", "<input type=\"checkbox\" class=\"disabled\" ");
                        Html = Html.replaceAll("<input name=\"result\" type=\"radio\"", "<input name=\"result\" type=\"radio\" class=\"disabled\"");
                        if (IdSig != null && !IdSig.equals("")) {
                            Html = Html.replaceAll("<div id=\"SignatureImage\"></div>", "<div id=\"SignatureImage\"><img src=\"Interface/Uploads/Signature/" + IdSig + "\" height=\"58px\" alt=\"Logo\" class=\"ImgSig\"></div>");
                        } else {
                            Html = Html.replaceAll("<div id=\"SignatureImage\"></div>", "<div id=\"SignatureImage\"><span class='text-warning'>--- No existe firma asociada ---</span></div>");
                        }
                        Html = Html.replaceAll(
                                "<button class=\"btn btn-sm btn-danger me-2 mr-2\" title=\"Eliminar fila o grupo\"><i class=\"fas fa-times\"></i></button>",
                                ""
                        );
                        out.print(Html);
                    %>
                </div>
            </div>
        </section>
        <%
            } catch (Exception e) {
                out.print("<div class='alert alert-danger'>Error al cargar los datos del certificado.</div>");
            }
        %>
        <script src="Interface/Content/Assets/js/Print.js"></script>
    </body>

</html>
