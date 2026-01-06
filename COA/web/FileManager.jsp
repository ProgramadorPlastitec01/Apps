<%@page import="java.io.File"%>
<%@page import="java.io.FileFilter"%>
<%@page import="Connection.LinkBatchRecord"%>
<%@page import="Controller.CertificatesJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Method.Util"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestor de Archivos - COA</title>

        <link rel="stylesheet" href="Interface/Content/Assets/css/attach.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <style>
            .modal-backdrop{
                position: relative !important;
            }
            #filterInput {
                padding-left: 15px;
                width: 209px;
                float: right;

            }
        </style>
    </head>

    <body class="bg-light">

        <jsp:include page="Menu.jsp" />

        <div class="main-content">
            <section class="section">

                <div class="section-header">
                    <h1>Gestor de Archivos</h1>
                </div>

                <div class="section-body">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">

                                    <%
                                        HttpSession sesion = request.getSession();
                                        String Permission = "";
                                        LinkBatchRecord LinkBatch = new LinkBatchRecord();
                                        CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
                                        List lst_link = null;
                                        List lst_certificate = null;
                                        List lst_material = null;
                                        try {
                                            Permission = sesion.getAttribute("Permisos").toString();
                                        } catch (Exception e) {
                                        }

                                        String cliente = request.getParameter("cliente");
                                        String anio = request.getParameter("anio");
                                        String orden = request.getParameter("orden");
                                        String lote = request.getParameter("lote");

                                        String basePath = application.getRealPath("/") + "Certificates";

                                        String currentPath = basePath;
                                        if (cliente != null) {
                                            currentPath += File.separator + cliente;
                                        }
                                        if (anio != null) {
                                            currentPath += File.separator + anio;
                                        }
                                        if (orden != null) {
                                            currentPath += File.separator + orden;
                                        }
                                        if (lote != null) {
                                            currentPath += File.separator + lote;
                                        }

                                        File currentDir = new File(currentPath);
                                    %>

                                    <!-- ================== BREADCRUMB ================== -->
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div style="width: 74%">
                                            <nav aria-label="breadcrumb">
                                                <ol class="breadcrumb">
                                                    <li class="breadcrumb-item"><a href="FileManager.jsp">Clientes</a></li>
                                                        <% if (cliente != null) {%>
                                                    <li class="breadcrumb-item">
                                                        <a href="FileManager.jsp?cliente=<%=cliente%>"><%=cliente%></a>
                                                    </li>
                                                    <% } %>
                                                    <% if (anio != null) {%>
                                                    <li class="breadcrumb-item">
                                                        <a href="FileManager.jsp?cliente=<%=cliente%>&anio=<%=anio%>"><%=anio%></a>
                                                    </li>
                                                    <% } %>
                                                    <% if (orden != null) {%>
                                                    <li class="breadcrumb-item">
                                                        <a href="FileManager.jsp?cliente=<%=cliente%>&anio=<%=anio%>&orden=<%=orden%>"><%=orden%></a>
                                                    </li>
                                                    <% } %>
                                                    <% if (lote != null) {%>
                                                    <li class="breadcrumb-item active"><%=lote%></li>
                                                        <% } %>
                                                </ol>
                                            </nav>
                                        </div>
                                        <div class="row mb-3 justify-content-end">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <div class="input-group-text">
                                                        <i class="fas fa-filter"></i>
                                                    </div>
                                                </div>
                                                <input type="text"
                                                       id="filterInput"
                                                       class="form-control"
                                                       placeholder="Filtrar carpetas o archivos..."
                                                       onkeyup="filterItems()">
                                            </div>
                                        </div>
                                    </div>
                                    <Alert:Alert/>

                                    <!-- ================== LISTADO ================== -->
                                    <%
                                        if (currentDir.exists()) {

                                            /* ================== ARCHIVOS (LOTE) ================== */
                                            if (lote != null) {
                                                File[] archivos = currentDir.listFiles(new FileFilter() {
                                                    @Override
                                                    public boolean accept(File file) {
                                                        return file.isFile();
                                                    }
                                                });

                                    %>



                                    <!-- ================== SUBIDA SOLO EN LOTE ================== -->
                                    <%                                        if (Permission.contains("[2]")) {
                                    %>
                                    <div class="d-flex justify-content-between align-content-center">
                                        <div>
                                            <h5 class="mb-3">
                                                <i class="fas fa-folder-open" style="font-size: 20px; color:#dccbff"></i> Archivos del lote:<b style="color:#0b0025"> <%= lote%></b>
                                            </h5>
                                        </div>
                                        <div class="mb-3">
                                            <button class="btn btn-green"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#uploadModal">
                                                <i class="fas fa-upload"></i> Subir archivos
                                            </button>
                                        </div> 
                                    </div>


                                    <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog">
                                        <div class="modal-dialog modal-md modal-dialog-centered" role="document">
                                            <div class="modal-content">

                                                <div class="modal-header">
                                                    <h5 class="modal-title">
                                                        <i class="fas fa-folder-open text-warning"></i>
                                                        Subir documentos al lote
                                                    </h5>
                                                    <button type="button" class="close" data-dismiss="modal">
                                                        <span>&times;</span>
                                                    </button>
                                                </div>

                                                <form action="FileManagerServlet" method="post" enctype="multipart/form-data">

                                                    <div class="modal-body">

                                                        <input type="hidden" name="cliente" value="<%= cliente%>">
                                                        <input type="hidden" name="anio" value="<%= anio%>">
                                                        <input type="hidden" name="orden" value="<%= orden%>">
                                                        <input type="hidden" name="lote" value="<%= lote%>">

                                                        <div class="form-group">
                                                            <label>Seleccionar archivos</label>
                                                            <input type="file" name="files" multiple class="form-control"
                                                                   accept=".pdf,.doc,.docx,.xls,.xlsx,.png,.jpg,.jpeg">
                                                        </div>

                                                    </div>

                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                            Cancelar
                                                        </button>
                                                        <button type="submit" class="btn btn-green">
                                                            <i class="fas fa-cloud-upload-alt"></i> Subir
                                                        </button>
                                                    </div>

                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <%
                                    } else {
                                    %>

                                    <h5 class="mb-3">
                                        <i class="fas fa-folder-open text-warning"></i> Archivos del lote: <%= lote%>
                                    </h5>
                                    <%
                                        }
                                    %>
                                    <table class="table table-bordered table-hover">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Tipo</th>
                                                <th>Informacion</th>
                                                <th width="160">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody id="fileTable">

                                            <%
                                                if (archivos != null && archivos.length > 0) {
                                                    for (File archivo : archivos) {
                                            %>
                                            <tr class="file-row">
                                                <td>Archivo Fisico</td>
                                                <td><%= archivo.getName()%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER -->
                                                        <a class="btn btn-info mr-3"
                                                           href="Certificates/<%= cliente + "/" + anio + "/" + orden + "/" + lote + "/" + archivo.getName()%>"
                                                           target="_blank"
                                                           title="Ver archivo">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                        <!-- DESCARGAR -->
                                                        <a class="btn btn-success mr-3"
                                                           href="Certificates/<%= cliente + "/" + anio + "/" + orden + "/" + lote + "/" + archivo.getName()%>"
                                                           download
                                                           title="Descargar archivo">
                                                            <i class="fas fa-download"></i>
                                                        </a>

                                                        <!-- ELIMINAR -->
                                                        <button type="button"
                                                                class="btn btn-danger"
                                                                title="Eliminar archivo"
                                                                onclick="confirmDeleteFile(
                                                                                '<%= cliente%>',
                                                                                '<%= anio%>',
                                                                                '<%= orden%>',
                                                                                '<%= lote%>',
                                                                                '<%= archivo.getName()%>'
                                                                                )">
                                                            <i class="fas fa-trash"></i>
                                                        </button>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                }
                                            } else {
                                            %>
                                            <%
                                                }
                                            %>
                                            <%
                                                lst_link = LinkBatch.LinkBatchRecord(orden, lote);
                                                if (lst_link != null) {
                                                    for (int i = 0; i < lst_link.size(); i++) {
                                                        String[] ArgLink = Util.parseResult(lst_link.get(i));
                                            %>
                                            <tr class="file-row">
                                                <td><%= ArgLink[1]%></td>
                                                <td><%= ArgLink[2]%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER -->
                                                        <a class="btn btn-info mr-3"
                                                           href="<%= ArgLink[3]%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>    
                                            <%                                                }
                                                }
                                            %>
                                            <%
                                                String MaterialBatch = "";
                                                lst_certificate = CertificatesJpa.ConsultCertificatesBatchRecord(orden, lote);
                                                if (lst_certificate != null) {
                                                    for (int i = 0; i < lst_certificate.size(); i++) {
                                                        Object[] ArgCertificate = (Object[]) lst_certificate.get(i);
                                                        MaterialBatch += ArgCertificate[4];
                                            %>
                                            <tr class="file-row">
                                                <td><%= ArgCertificate[1]%></td>
                                                <td><%= ArgCertificate[2]%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER -->
                                                        <a class="btn btn-info mr-3"
                                                           href="<%= ArgCertificate[3]%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>    
                                            <%                                                }
                                                }
                                            %>
                                            <%
                                                lst_material = LinkBatch.AttachmentBatchRecord(MaterialBatch);
                                                if (lst_material != null) {
                                                    for (int i = 0; i < lst_material.size(); i++) {
                                                        String[] ArgBatch = Util.parseResult(lst_material.get(i));
                                            %>
                                            <tr class="file-row">
                                                <td><%= ArgBatch[1]%></td>
                                                <td><%= ArgBatch[3]%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER -->
                                                        <a class="btn btn-info mr-3"
                                                           href="DownloadGL?File_name=<%= ArgBatch[2].trim()%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>    
                                            <%                                                }
                                                }
                                            %>
                                        </tbody>
                                    </table>



                                    <%
                                        /* ================== CARPETAS ================== */
                                    } else {

                                        File[] carpetas = currentDir.listFiles(new FileFilter() {
                                            @Override
                                            public boolean accept(File file) {
                                                return file.isDirectory();
                                            }
                                        });

                                    %>

                                    <div class="row g-3 mt-2"  id="folderGrid">
                                        <%                                            if (carpetas != null && carpetas.length > 0) {
                                                for (File carpeta : carpetas) {

                                                    String link = "FileManager.jsp?";
                                                    if (cliente != null) {
                                                        link += "cliente=" + cliente + "&";
                                                    }
                                                    if (anio != null) {
                                                        link += "anio=" + anio + "&";
                                                    }
                                                    if (orden != null) {
                                                        link += "orden=" + orden + "&";
                                                    }

                                                    if (cliente == null) {
                                                        link += "cliente=" + carpeta.getName();
                                                    } else if (anio == null) {
                                                        link += "anio=" + carpeta.getName();
                                                    } else if (orden == null) {
                                                        link += "orden=" + carpeta.getName();
                                                    } else {
                                                        link += "lote=" + carpeta.getName();
                                                    }
                                        %>

                                        <div class="col-6 col-md-4 col-lg-3 folder-item">
                                            <div class="card text-center p-3 shadow-sm border border-warning rounded hover-card">
                                                <a href="<%=link%>" class="text-decoration-none text-dark">
                                                    <i class="fas fa-folder" style="font-size:48px;color:#f5e047ad;"></i>
                                                    <h6 class="mt-2"><%= carpeta.getName()%></h6>
                                                </a>
                                            </div>
                                        </div>

                                        <%
                                            }
                                        } else {
                                        %>
                                        <div class="col-12 text-center text-muted">
                                            No existen carpetas en este nivel.
                                        </div>
                                        <%
                                            }
                                        %>
                                    </div>

                                    <%
                                        }
                                    } else {
                                    %>
                                    <div class="alert alert-warning text-center">
                                        No existe la ruta de certificados.
                                    </div>
                                    <%
                                        }
                                    %>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </div>
        <script>
            function confirmDeleteFile(cliente, anio, orden, lote, archivo) {

                swal({
                    title: "¿Eliminar archivo?",
                    text: "Esta acción no se puede deshacer",
                    icon: "warning",
                    buttons: {
                        cancel: {
                            text: "Cancelar",
                            visible: true,
                            className: "btn btn-secondary"
                        },
                        confirm: {
                            text: "Sí, eliminar",
                            value: true,
                            className: "btn btn-danger"
                        }
                    },
                    dangerMode: true
                }).then(function (confirm) {

                    if (confirm) {
                        window.location.href =
                                "DeleteFileServlet"
                                + "?cliente=" + encodeURIComponent(cliente)
                                + "&anio=" + encodeURIComponent(anio)
                                + "&orden=" + encodeURIComponent(orden)
                                + "&lote=" + encodeURIComponent(lote)
                                + "&archivo=" + encodeURIComponent(archivo);
                    }

                });
            }
        </script>
        <script>
            <%
                String msg = request.getParameter("msg");

                if ("upload_success".equals(msg)) {
            %>
            iziToast.success({
                title: 'Éxito',
                message: 'Archivo(s) subido(s) correctamente',
                position: 'bottomRight'
            });
            <%
            } else if ("error_upload".equals(msg)) {
            %>
            iziToast.error({
                title: 'Error',
                message: 'No se pudo subir el archivo',
                position: 'bottomRight'
            });
            <%
            } else if ("delete_success".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Eliminado',
                message: 'Archivo eliminado correctamente',
                position: 'bottomRight'
            });
            <%
            } else if ("error_delete".equals(msg)) {
            %>
            iziToast.error({
                title: 'Error',
                message: '"No se pudo eliminar el archivo',
                position: 'bottomRight'
            });
            <%
            } else if ("file_not_found".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Atención',
                message: '"El archivo no existe',
                position: 'bottomRight'
            });
            <%
                }
            %>
        </script>
        <script>
            function filterItems() {
                const filter = document.getElementById("filterInput").value.toLowerCase();

                // 🔹 Filtrar archivos (tabla)
                const fileRows = document.querySelectorAll(".file-row");
                fileRows.forEach(row => {
                    const text = row.innerText.toLowerCase();
                    row.style.display = text.includes(filter) ? "" : "none";
                });

                // 🔹 Filtrar carpetas (cards)
                const folders = document.querySelectorAll(".folder-item");
                folders.forEach(folder => {
                    const text = folder.innerText.toLowerCase();
                    folder.style.display = text.includes(filter) ? "" : "none";
                });
            }
        </script>

        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/js/BoostratModel.js"></script>

    </body>
</html>
