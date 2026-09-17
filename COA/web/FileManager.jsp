<%@page import="java.io.File"%>
<%@page import="java.io.FileFilter"%>
<%@page import="Connection.LinkBatchRecord"%>
<%@page import="Controller.CertificatesJpaController"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="Method.Util"%>
<%@page import="Method.BatchRecordManifest"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Batch Record</title>

        <link rel="stylesheet" href="Interface/Content/Assets/css/attach.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
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
                                        List lst_summary = null;
                                        List<String> eventosListado = new ArrayList<String>();
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

                                                BatchRecordManifest.MangaResult mangaResultado = BatchRecordManifest.consultarInspeccionManga(orden, lote);
                                                Map<String, Object> docManga = mangaResultado.documento;
                                                if (mangaResultado.diagnostico != null) {
                                                    eventosListado.add("Inspección Manga (Resumen Estadístico): " + mangaResultado.diagnostico);
                                                }

                                                BatchRecordManifest.MangaListResult despejeResultado = BatchRecordManifest.consultarRegistrosDespejeManga(orden, lote);
                                                List<Map<String, Object>> docsDespeje = despejeResultado.documentos;
                                                if (despejeResultado.diagnostico != null) {
                                                    eventosListado.add("Inspección Manga (Registros de Despeje): " + despejeResultado.diagnostico);
                                                }

                                                BatchRecordManifest.MangaResult cabeceraResultado = BatchRecordManifest.consultarRegistrosCabeceraManga(orden, lote);
                                                Map<String, Object> docCabecera = cabeceraResultado.documento;
                                                if (cabeceraResultado.diagnostico != null) {
                                                    eventosListado.add("Inspección Manga (Registros de Cabecera): " + cabeceraResultado.diagnostico);
                                                }

                                                BatchRecordManifest.MangaListResult formulaResultado = BatchRecordManifest.consultarRegistrosFormula(orden, lote);
                                                List<Map<String, Object>> docsFormula = formulaResultado.documentos;
                                                if (formulaResultado.diagnostico != null) {
                                                    eventosListado.add("Control Fórmulas (R-PI-004): " + formulaResultado.diagnostico);
                                                }
                                    %>



                                    <!-- ================== ENCABEZADO DE LOTE Y BOTÓN PDF UNIFICADO ================== -->
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <div>
                                            <h5 class="m-0">
                                                <i class="fas fa-folder-open" style="font-size: 22px; color:#6777ef;"></i> Archivos del lote: <b style="color:#0b0025; font-size:18px;"><%= lote%></b>
                                            </h5>
                                        </div>
                                        <div class="d-flex" style="gap:10px;">
                                            <button type="button" class="btn btn-danger font-weight-bold mr-2" style="background:#dc3545; border-color:#dc3545; border-radius:6px; box-shadow:0 2px 6px rgba(220,53,69,0.3);" onclick="generarBatchRecordPdfUnificado('<%= cliente%>', '<%= anio%>', '<%= orden%>', '<%= lote%>')">
                                                <i class="fas fa-file-pdf"></i> Generar Batch Record PDF Unificado
                                            </button>
                                            <% if (Permission.contains("[3]")) { %>
                                            <button type="button" class="btn btn-green" data-bs-toggle="modal" data-bs-target="#uploadModal">
                                                <i class="fas fa-upload"></i> Subir archivos
                                            </button>
                                            <% } %>
                                            <% if (Permission.contains("[39]")) { %>
                                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#supportUploadModal">
                                                <i class="fas fa-file-signature"></i> Adjuntar documento de soporte
                                            </button>
                                            <% } %>
                                        </div>
                                    </div>

                                    <!-- ================== EVENTOS DE INTEGRACIÓN (fuentes externas) ================== -->
                                    <% if (!eventosListado.isEmpty()) { %>
                                    <div class="alert alert-warning" role="alert" style="border-left:4px solid #ffc107;">
                                        <h6 class="mb-2">
                                            <i class="fas fa-triangle-exclamation"></i> Eventos de integración
                                        </h6>
                                        <ul class="mb-0" style="padding-left:18px;">
                                            <% for (String evento : eventosListado) { %>
                                            <li><%= evento%></li>
                                            <% } %>
                                        </ul>
                                    </div>
                                    <% } %>

                                    <% if (Permission.contains("[3]")) { %>
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

                                                <form id="uploadForm" action="FileManagerServlet" method="post" enctype="multipart/form-data">

                                                    <div class="modal-body">

                                                        <input type="hidden" name="cliente" value="<%= cliente%>">
                                                        <input type="hidden" name="anio" value="<%= anio%>">
                                                        <input type="hidden" name="orden" value="<%= orden%>">
                                                        <input type="hidden" name="lote" value="<%= lote%>">

                                                        <div class="form-group">
                                                            <label>Seleccionar archivos</label>
                                                            <input type="file" id="filesInput" name="files" multiple class="form-control"
                                                                   accept=".pdf,.png,.jpg,.jpeg,.gif">
                                                            <small class="form-text text-muted">
                                                                Solo pdf, png, jpg, jpeg, gif. No se aceptan Word/Excel/PowerPoint
                                                                (doc, docx, xls, xlsx, ppt, pptx): no se pueden incluir en el Batch Record unificado.
                                                            </small>
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
                                    <% } %>
                                    <% if (Permission.contains("[39]")) { %>
                                    <div class="modal fade" id="supportUploadModal" tabindex="-1" role="dialog">
                                        <div class="modal-dialog modal-md modal-dialog-centered" role="document">
                                            <div class="modal-content">

                                                <div class="modal-header">
                                                    <h5 class="modal-title">
                                                        <i class="fas fa-file-signature text-primary"></i>
                                                        Adjuntar documento de soporte
                                                    </h5>
                                                    <button type="button" class="close" data-dismiss="modal">
                                                        <span>&times;</span>
                                                    </button>
                                                </div>

                                                <form id="supportUploadForm" action="SupportDocumentUploadServlet" method="post" enctype="multipart/form-data">

                                                    <div class="modal-body">

                                                        <input type="hidden" name="cliente" value="<%= cliente%>">
                                                        <input type="hidden" name="anio" value="<%= anio%>">
                                                        <input type="hidden" name="orden" value="<%= orden%>">
                                                        <input type="hidden" name="lote" value="<%= lote%>">

                                                        <div class="form-group">
                                                            <label>Documento (carta de cliente u otro soporte)</label>
                                                            <input type="file" id="supportFileInput" name="file" class="form-control"
                                                                   accept=".pdf,.png,.jpg,.jpeg,.gif">
                                                            <small class="form-text text-muted">
                                                                Solo pdf, png, jpg, jpeg, gif. Quedará vinculado al lote y disponible
                                                                para firmar con la firma registrada del Director de Calidad.
                                                            </small>
                                                        </div>

                                                    </div>

                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                            Cancelar
                                                        </button>
                                                        <button type="submit" class="btn btn-primary">
                                                            <i class="fas fa-cloud-upload-alt"></i> Adjuntar
                                                        </button>
                                                    </div>

                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                    <% } %>
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
                                                        String relPath = "Certificates/" + cliente + "/" + anio + "/" + orden + "/" + lote + "/" + archivo.getName();
                                            %>
                                            <tr class="file-row">
                                                <td>Archivo Físico</td>
                                                <td><%= archivo.getName()%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('Archivo Físico', '<%= archivo.getName()%>', '<%= relPath%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info mr-2"
                                                           href="<%= relPath%>"
                                                           target="_blank"
                                                           title="Ver archivo original">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                        <!-- DESCARGAR -->
                                                        <a class="btn btn-success mr-2"
                                                           href="<%= relPath%>"
                                                           download
                                                           title="Descargar archivo">
                                                            <i class="fas fa-download"></i>
                                                        </a>
                                                        <% if (Permission.contains("[4]")) { %>
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
                                                        <% } %>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                }
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

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= ArgLink[1]%>', '<%= ArgLink[2]%>', '<%= ArgLink[3]%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="<%= ArgLink[3]%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>    
                                            <%
                                                }
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

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= ArgCertificate[1]%>', '<%= ArgCertificate[2]%>', '<%= ArgCertificate[3]%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="<%= ArgCertificate[3]%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>    
                                            <%
                                                }
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

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= ArgBatch[1]%>', '<%= ArgBatch[3]%>', 'DownloadGL?File_name=<%= ArgBatch[2].trim()%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="DownloadGL?File_name=<%= ArgBatch[2].trim()%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>    
                                            <%
                                                }
                                            }
                                            %>
                                            <%
                                                if (docManga != null) {
                                                    String mangaTipo = String.valueOf(docManga.get("tipo"));
                                                    String mangaNombre = String.valueOf(docManga.get("nombre"));
                                                    String mangaUrl = "MangaResumenViewServlet?orden=" + java.net.URLEncoder.encode(orden, "UTF-8")
                                                            + "&lote=" + java.net.URLEncoder.encode(lote, "UTF-8");
                                            %>
                                            <tr class="file-row">
                                                <td><%= mangaTipo%></td>
                                                <td><%= mangaNombre%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= mangaTipo%>', '<%= mangaNombre%>', '<%= mangaUrl%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="<%= mangaUrl%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                            <%
                                                if (docsDespeje != null) {
                                                    for (int i = 0; i < docsDespeje.size(); i++) {
                                                        Map<String, Object> docDespeje = docsDespeje.get(i);
                                                        String despejeTipo = String.valueOf(docDespeje.get("tipo"));
                                                        String despejeNombre = String.valueOf(docDespeje.get("nombre"));
                                                        String despejeUrl = "MangaDespejeViewServlet?orden=" + java.net.URLEncoder.encode(orden, "UTF-8")
                                                                + "&lote=" + java.net.URLEncoder.encode(lote, "UTF-8")
                                                                + "&indice=" + i;
                                            %>
                                            <tr class="file-row">
                                                <td><%= despejeTipo%></td>
                                                <td><%= despejeNombre%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= despejeTipo%>', '<%= despejeNombre%>', '<%= despejeUrl%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="<%= despejeUrl%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                            <%
                                                if (docCabecera != null) {
                                                    String cabeceraTipo = String.valueOf(docCabecera.get("tipo"));
                                                    String cabeceraNombre = String.valueOf(docCabecera.get("nombre"));
                                                    String cabeceraUrl = "MangaCabeceraViewServlet?orden=" + java.net.URLEncoder.encode(orden, "UTF-8")
                                                            + "&lote=" + java.net.URLEncoder.encode(lote, "UTF-8");
                                            %>
                                            <tr class="file-row">
                                                <td><%= cabeceraTipo%></td>
                                                <td><%= cabeceraNombre%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= cabeceraTipo%>', '<%= cabeceraNombre%>', '<%= cabeceraUrl%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="<%= cabeceraUrl%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                            <%
                                                if (docsFormula != null) {
                                                    for (int i = 0; i < docsFormula.size(); i++) {
                                                        Map<String, Object> docFormula = docsFormula.get(i);
                                                        String formulaTipo = String.valueOf(docFormula.get("tipo"));
                                                        String formulaNombre = String.valueOf(docFormula.get("nombre"));
                                                        String formulaUrl = "FormulaViewServlet?orden=" + java.net.URLEncoder.encode(orden, "UTF-8")
                                                                + "&lote=" + java.net.URLEncoder.encode(lote, "UTF-8")
                                                                + "&indice=" + i;
                                            %>
                                            <tr class="file-row">
                                                <td><%= formulaTipo%></td>
                                                <td><%= formulaNombre%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER EN PDF -->
                                                        <button type="button" class="btn btn-danger mr-2" style="background:#dc3545; border-color:#dc3545;"
                                                                onclick="verPdfIndividual('<%= formulaTipo%>', '<%= formulaNombre%>', '<%= formulaUrl%>')"
                                                                title="Ver en formato PDF">
                                                            <i class="fas fa-file-pdf"></i> PDF
                                                        </button>

                                                        <!-- VER ORIGINAL -->
                                                        <a class="btn btn-info"
                                                           href="<%= formulaUrl%>"
                                                           target="_blank"
                                                           title="Ver registro">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                            <%
                                                File supportDocsDir = new File(currentPath + File.separator + "SupportDocs");
                                                File[] soportes = supportDocsDir.listFiles();
                                                if (soportes != null) {
                                                    for (File soporte : soportes) {
                                                        String nombreSoporte = soporte.getName();
                                                        boolean firmado = nombreSoporte.contains("_FIRMADO_");
                                                        String relPathSoporte = "Certificates/" + cliente + "/" + anio + "/" + orden + "/" + lote + "/SupportDocs/" + nombreSoporte;
                                            %>
                                            <tr class="file-row">
                                                <td>Documento de Soporte <%= firmado ? "<span class='badge badge-success'>Firmado</span>" : "<span class='badge badge-warning'>Pendiente de firma</span>"%></td>
                                                <td><%= nombreSoporte%></td>
                                                <td class="text-center">
                                                    <div class="btn-group btn-group-sm">

                                                        <!-- VER -->
                                                        <a class="btn btn-info mr-2"
                                                           href="<%= relPathSoporte%>"
                                                           target="_blank"
                                                           title="Ver documento">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                        <!-- DESCARGAR -->
                                                        <a class="btn btn-success mr-2"
                                                           href="<%= relPathSoporte%>"
                                                           download
                                                           title="Descargar documento">
                                                            <i class="fas fa-download"></i>
                                                        </a>

                                                        <% if (!firmado && Permission.contains("[39]")) { %>
                                                        <!-- FIRMAR -->
                                                        <button type="button"
                                                                class="btn btn-primary mr-2"
                                                                title="Firmar documento"
                                                                onclick="window.open('SupportDocumentSign.jsp?cliente=<%= cliente%>&anio=<%= anio%>&orden=<%= orden%>&lote=<%= lote%>&archivo=<%= nombreSoporte%>', '_blank')">
                                                            <i class="fas fa-file-signature"></i>
                                                        </button>
                                                        <% } %>

                                                        <% if (Permission.contains("[4]")) { %>
                                                        <!-- ELIMINAR -->
                                                        <button type="button"
                                                                class="btn btn-danger"
                                                                title="Eliminar documento"
                                                                onclick="confirmDeleteSupportFile(
                                                                                '<%= cliente%>',
                                                                                '<%= anio%>',
                                                                                '<%= orden%>',
                                                                                '<%= lote%>',
                                                                                '<%= nombreSoporte%>'
                                                                                )">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                        <% } %>

                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                    }
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

            function confirmDeleteSupportFile(cliente, anio, orden, lote, archivo) {

                swal({
                    title: "¿Eliminar documento de soporte?",
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
                                + "&carpeta=SupportDocs"
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
            } else if ("upload_partial".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Subida parcial',
                message: 'Algunos archivos se subieron, pero otros se rechazaron por tener un formato no permitido (solo pdf, png, jpg, jpeg, gif)',
                position: 'bottomRight'
            });
            <%
            } else if ("error_extension".equals(msg)) {
            %>
            iziToast.error({
                title: 'Formato no permitido',
                message: 'El archivo no se subió: solo se permiten pdf, png, jpg, jpeg, gif (Word/Excel/PowerPoint no se pueden incluir en el Batch Record unificado)',
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
            } else if ("support_upload_success".equals(msg)) {
            %>
            iziToast.success({
                title: 'Éxito',
                message: 'Documento de soporte adjuntado correctamente. Ya puede firmarlo.',
                position: 'bottomRight'
            });
            <%
            } else if ("support_upload_partial".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Formato no permitido',
                message: 'El documento no se adjuntó: solo se permiten pdf, png, jpg, jpeg, gif.',
                position: 'bottomRight'
            });
            <%
            } else if ("support_sign_success".equals(msg)) {
            %>
            iziToast.success({
                title: 'Documento firmado',
                message: 'El documento de soporte fue firmado correctamente.',
                position: 'bottomRight'
            });
            <%
            } else if ("error_permission".equals(msg)) {
            %>
            iziToast.error({
                title: 'Sin permiso',
                message: 'No tiene permiso para realizar esta acción, o no tiene una firma registrada.',
                position: 'bottomRight'
            });
            <%
            } else if ("error_sign".equals(msg)) {
            %>
            iziToast.error({
                title: 'Error al firmar',
                message: 'No se pudo firmar el documento de soporte.',
                position: 'bottomRight'
            });
            <%
                }
            %>
        </script>
        <script>
            // Misma lista blanca que valida FileManagerServlet en el servidor
            // (ver ALLOWED_EXTENSIONS): solo pdf/imagen, porque es lo único que
            // el Batch Record PDF unificado sabe fusionar. Esto es solo una
            // alerta temprana en el navegador; la validación real (la que no
            // se puede saltar) es la del servidor.
            var ALLOWED_UPLOAD_EXTENSIONS = ['.pdf', '.png', '.jpg', '.jpeg', '.gif'];

            function extensionPermitida(nombreArchivo) {
                var nombre = nombreArchivo.toLowerCase();
                return ALLOWED_UPLOAD_EXTENSIONS.some(function (ext) {
                    return nombre.endsWith(ext);
                });
            }

            (function () {
                var filesInput = document.getElementById('filesInput');
                var uploadForm = document.getElementById('uploadForm');
                if (!filesInput || !uploadForm) {
                    return;
                }

                function archivosInvalidos() {
                    var invalidos = [];
                    for (var i = 0; i < filesInput.files.length; i++) {
                        if (!extensionPermitida(filesInput.files[i].name)) {
                            invalidos.push(filesInput.files[i].name);
                        }
                    }
                    return invalidos;
                }

                function avisarInvalidos(invalidos) {
                    if (typeof iziToast !== "undefined") {
                        iziToast.error({
                            title: 'Formato no permitido',
                            message: 'No se puede subir el archivo. Solo se permiten pdf, png, jpg, jpeg, gif.',
                            position: 'bottomRight',
                            timeout: 6000
                        });
                    } else {
                        alert('No se puede subir: ' + invalidos.join(', ') + '. Solo se permiten pdf, png, jpg, jpeg, gif.');
                    }
                }

                filesInput.addEventListener('change', function () {
                    var invalidos = archivosInvalidos();
                    if (invalidos.length > 0) {
                        avisarInvalidos(invalidos);
                        filesInput.value = '';
                    }
                });

                uploadForm.addEventListener('submit', function (e) {
                    var invalidos = archivosInvalidos();
                    if (invalidos.length > 0) {
                        e.preventDefault();
                        avisarInvalidos(invalidos);
                        filesInput.value = '';
                    }
                });
            })();

            (function () {
                var supportFileInput = document.getElementById('supportFileInput');
                var supportUploadForm = document.getElementById('supportUploadForm');
                if (!supportFileInput || !supportUploadForm) {
                    return;
                }

                supportUploadForm.addEventListener('submit', function (e) {
                    if (supportFileInput.files.length > 0 && !extensionPermitida(supportFileInput.files[0].name)) {
                        e.preventDefault();
                        avisarInvalidos([supportFileInput.files[0].name]);
                        supportFileInput.value = '';
                    }
                });
            })();

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

        <script src="Interface/Content/Assets/js/jspdf.umd.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/js/BoostratModel.js"></script>

        <script>
            // A partir de aquí, la generación de PDF (individual y Batch Record unificado) ya NO
            // se hace en el navegador con html2canvas/jsPDF: se delega a HtmlToPdfServlet y
            // BatchRecordPdfGenerateServlet, que renderizan con Chrome headless en el servidor
            // (mismo motor real de "Imprimir > Guardar como PDF") y guardan una copia del PDF
            // resultante junto a los demás documentos del lote.

            function verPdfIndividual(tipo, nombre, url) {
                if (!url) return;

                if (url.toLowerCase().endsWith(".pdf")) {
                    window.open(url, '_blank');
                    return;
                }

                if (url.toLowerCase().match(/\.(png|jpg|jpeg|gif)$/)) {
                    const { jsPDF } = window.jspdf;
                    const doc = new jsPDF('p', 'mm', 'a4');
                    const img = new Image();
                    img.crossOrigin = "Anonymous";
                    img.onload = function () {
                        const imgWidth = 198;
                        const imgHeight = (img.height * imgWidth) / img.width;
                        doc.addImage(img, 'JPEG', 6, 6, imgWidth, Math.min(imgHeight, 280));
                        window.open(doc.output('bloburl'), '_blank');
                    };
                    img.src = url;
                    return;
                }

                if (typeof iziToast !== "undefined") {
                    iziToast.info({
                        title: 'Generando PDF',
                        message: 'Generando PDF del registro en el servidor...',
                        position: 'topRight',
                        timeout: 2500
                    });
                }

                window.open('HtmlToPdfServlet?proxyUrl=' + encodeURIComponent(url), '_blank');
            }

            function verPdfResumenLab(formId) {
                const form = document.getElementById(formId);
                if (!form) return;

                if (typeof iziToast !== "undefined") {
                    iziToast.info({
                        title: 'Generando PDF',
                        message: 'Generando PDF del resumen de Registros LAB en el servidor...',
                        position: 'topRight',
                        timeout: 2500
                    });
                }

                // Reenvía el mismo formulario (acción + campos) hacia HtmlToPdfServlet, agregando
                // "proxyUrl" para indicarle a qué sistema remoto conectarse, y abre el PDF resultante
                // en una pestaña nueva.
                const proxyForm = document.createElement('form');
                proxyForm.method = 'POST';
                proxyForm.action = 'HtmlToPdfServlet';
                proxyForm.target = '_blank';

                const addHidden = function (name, value) {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = name;
                    input.value = value;
                    proxyForm.appendChild(input);
                };

                addHidden('proxyUrl', form.action);
                form.querySelectorAll('input').forEach(function (input) {
                    addHidden(input.name, input.value);
                });

                document.body.appendChild(proxyForm);
                proxyForm.submit();
                proxyForm.remove();
            }

            function generarBatchRecordPdfUnificado(cliente, anio, orden, lote) {
                if (!orden || !lote) {
                    alert("No hay información suficiente del lote para generar el Batch Record.");
                    return;
                }

                if (typeof iziToast !== "undefined") {
                    iziToast.info({
                        title: 'Batch Record Auditoría',
                        message: 'Compilando expediente completo en el servidor, esto puede tardar unos segundos...',
                        position: 'topRight',
                        timeout: 4000
                    });
                }

                const params = new URLSearchParams({
                    orden: orden,
                    lote: lote,
                    cliente: cliente || '',
                    anio: anio || ''
                });

                window.open('BatchRecordPdfGenerateServlet?' + params.toString(), '_blank');
            }
        </script>
    </body>
</html>
