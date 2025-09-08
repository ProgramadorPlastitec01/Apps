
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Controller.KnowledgeJpaController"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentacion Tecnica</title>
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/knowledge.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
    </head>
    <body>
        <div class="card">
            <div class="card-body">
                <nav class="navbar bg-primary">
                    <img src="Interface/Imagen/Logo_app/LogoSideW.fw.png" alt="" style="width: 13%;">
                    <a class="navbar-brand" href="Knowledge.jsp">💻 Documentación técnica</a>
                    <div class="form-inline">
                        <input class="form-control" id="searchInput" type="search" placeholder="Filtro" aria-label="Search">
                        <button class="btn bg-green " type="submit"><i class="fas fa-search"></i></button>
                    </div>
                </nav>
            </div>

            <div class="card-body Card1">
                <%
                    KnowledgeJpaController KnowledgdeJpa = new KnowledgeJpaController();
                    List lst_detail = null, lst_group = null, lst_title = null;
                    lst_group = KnowledgdeJpa.ConsultKnowledgeBaseGroup();
                    if (lst_group != null) {
                        for (int i = 0; i < lst_group.size(); i++) {
                            Object[] ObjGroup = (Object[]) lst_group.get(i);
                            String Category = ObjGroup[2].toString();
                %>
                <section class="box-shadow br-12 mb-3 SectionColor">
                    <div class="col-12 align-items-center DivKno">
                        <h2 class="mb-3"><b style="color:#33bf98"><%= Category%></b></h2>
                    </div>
                    <div class="row OutMargin">
                        <%
                            lst_title = KnowledgdeJpa.ConsultKnowledgeBaseGroupTitle(Category);
                            if (lst_title != null) {
                                int total = lst_title.size();
                                for (int o = 0; o < total; o++) {
                                    Object[] ObjTitle = (Object[]) lst_title.get(o);
                                    String Title = ObjTitle[2].toString();
                        %>
                        <div class="col-md-6 p-4 
                             <%= ((o % 2 == 0) && !(o == total - 1)) ? "DetailBorderRight" : ""%> 
                             <%= ((total % 2 == 0 && o < total - 2) || (total % 2 != 0 && o < total - 1))
                                     ? "DetailBorderBottom" : ""%>">
                            <!--<div class="col-md-6 p-4 DetailBorderBottom DetailBorderRight">-->
                            <h5><i class="far fa-folder-open mr-2" style="font-size: 22px;"></i><b class="ColorTitle"><%= Title%></b></h5>
                                    <%
                                        lst_detail = KnowledgdeJpa.ConsultKnowledgeBaseActive(Category, Title);
                                        if (lst_detail != null) {
                                            for (int d = 0; d < lst_detail.size(); d++) {
                                                Object[] ObjDetail = (Object[]) lst_detail.get(d);
                                                int Id = Integer.parseInt(ObjDetail[0].toString());
                                                String Attach = ObjDetail[3].toString();
                                                String Description = ObjDetail[4].toString();
                                    %>
                            <ul class="fw-no-bullet ms-36">
                                <li>
                                    <a href="Interface/Content/KnowledgeFiles/<%= Attach%>"   onclick="enviarId(<%= Id%>); return false;"  class="d-flex" target="_blank" rel="noopener noreferrer">
                                        <div class="pe-8">
                                            <span class="icon-article"></span>
                                        </div>
                                        <div class="line-clamp-2 linkColor " role="link">
                                            <span class="opcion DescriptionText"><i class="far fa-copy mr-2" style="font-size: 22px;"></i><%= Description%></span>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                            <%
                                    } // cierre for detalle
                                } // cierre if detalle
                            %>
                        </div> <!-- ✅ cierre col-md-6 -->
                        <%
                                } // cierre for titulo
                            } // cierre if titulo
                        %>
                    </div> <!-- cierre row -->
                </section>
                <%
                        } // cierre for grupo
                    } // cierre if grupo
%>
            </div>
            <div id="noResultsMessage" style="display: none; text-align: center; margin: 20px;">
                <h2>No se encontraron resultados</h2>
            </div>
        </div>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let rows = document.querySelectorAll(".row");

                rows.forEach(row => {
                    let cols = row.querySelectorAll(".col-md-6"); // ✅ corregido

                    if (cols.length === 2) {
                        // Al segundo de la fila (derecha) se le quita el borde derecho
                        cols[1].classList.add("no-border-right");
                    }
                });
            });
        </script>
        <script>
            function enviarId(id) {
                var form = document.createElement("form");
                form.method = "post";
                form.action = "GeneralNotSession?opt=1";

                var input = document.createElement("input");
                input.type = "hidden";
                input.name = "IdKnowledge";
                input.value = id;
                form.appendChild(input);

                document.body.appendChild(form);
                form.submit();
            }

        </script>
        <script src="Interface/Content/Assets/js/knowledge.js"></script>
        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
    </body>

</html>
