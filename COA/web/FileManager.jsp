<%@page import="Connection.LinkBatchRecord"%>
<%@page import="Controller.CertificatesJpaController"%>
<%@page import="Controller.CertificateFileJpaController"%>
<%@page import="Controller.CertificateFileRow"%>
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

            /* ================== BREADCRUMB ================== */
            .breadcrumb-bar {
                background:#fff;
                border:1px solid #e9ecef;
                border-radius:8px;
                padding:10px 16px;
                margin-bottom:20px;
                box-shadow:0 1px 3px rgba(0,0,0,0.04);
            }
            .breadcrumb-bar .breadcrumb {
                background:transparent;
                margin:0;
                padding:0;
                font-size:14px;
            }
            .breadcrumb-bar .breadcrumb-item a {
                color:#6777ef;
                font-weight:500;
                text-decoration:none;
            }
            .breadcrumb-bar .breadcrumb-item a:hover {
                text-decoration:underline;
            }
            .breadcrumb-bar .breadcrumb-item.active {
                color:#0b0025;
                font-weight:600;
            }

            /* ================== CARPETAS ================== */
            .folder-item .card {
                border:1px solid #e9ecef !important;
                border-radius:10px;
                transition: transform .15s ease, box-shadow .15s ease, border-color .15s ease;
            }
            .folder-item .card:hover {
                transform: translateY(-3px);
                box-shadow:0 8px 20px rgba(11,0,37,0.12) !important;
                border-color:#6777ef !important;
            }
            .folder-item .card i.fa-folder {
                color:#6777ef !important;
                font-size:44px !important;
            }
            .folder-item .card h6 {
                margin-top:10px;
                font-weight:600;
                color:#0b0025;
                word-break: break-word;
            }

            /* ================== ENCABEZADO / TOOLBAR DEL LOTE ================== */
            .lote-toolbar {
                background:#fff;
                border:1px solid #e9ecef;
                border-radius:8px;
                padding:16px;
                margin-bottom:16px;
            }
            .lote-toolbar .btn {
                border-radius:6px;
                font-weight:500;
            }

            /* ================== EVENTOS DE INTEGRACIÓN ================== */
            .integration-alert {
                background:#fff8e6;
                border:1px solid #ffe4a3;
                border-left:4px solid #ffc107;
                border-radius:6px;
                padding:10px 16px;
                margin-bottom:16px;
                color:#7a5b00;
            }
            .integration-alert h6 {
                color:#946200;
                font-size:13px;
                font-weight:600;
                margin-bottom:6px;
            }
            .integration-alert ul {
                font-size:13px;
            }

            /* ================== TABLA DE ARCHIVOS ================== */
            .table-files {
                border:1px solid #e9ecef;
                border-radius:8px;
                overflow:hidden;
            }
            .table-files thead th {
                background:#0b0025;
                color:#fff;
                font-weight:600;
                font-size:13px;
                border:none;
                vertical-align:middle;
            }
            .table-files tbody tr:hover {
                background:#f3f4fb;
            }
            .table-files td {
                vertical-align:middle;
                font-size:12.5px;
                padding:8px 8px;
            }
            .table-files thead th {
                padding:8px 8px;
                font-size:11.5px;
            }

            /* ================== BATCH RECORD (namespaced, no toca .btn/.table/.card globales) ================== */
            .batch-record-header {
                background:#fff;
                border:1px solid #e9ecef;
                border-left:5px solid #6777ef;
                border-radius:10px;
                padding:16px 20px;
                margin-bottom:16px;
                display:flex;
                justify-content:space-between;
                align-items:flex-start;
                flex-wrap:wrap;
                gap:12px;
                box-shadow:0 1px 3px rgba(16,24,40,0.05);
            }
            .batch-record-lote-title { font-size:20px; font-weight:700; color:#0b0025; }
            .batch-record-lote-subtitle { font-size:13px; color:#6c757d; margin-top:2px; }
            .batch-record-header-right { display:flex; align-items:center; gap:20px; flex-wrap:wrap; }
            .batch-record-updated-label { font-size:11px; color:#9aa0ac; font-weight:600; }
            .batch-record-updated-value { font-size:13px; color:#495057; font-weight:600; margin-top:2px; }
            .batch-record-events-badge {
                display:inline-flex; align-items:center; gap:8px;
                background:#fff8e6; border:1px solid #ffe4a3; border-radius:8px;
                padding:8px 14px; color:#7a5b00; font-size:12.5px; font-weight:600;
                text-decoration:none;
            }
            .batch-record-events-badge:hover { color:#5c4500; text-decoration:none; }
            .batch-record-events-count {
                background:#dc3545; color:#fff; font-size:11px; font-weight:700;
                border-radius:9px; padding:1px 7px;
            }

            .batch-record-summary {
                display:grid; grid-template-columns:repeat(3, minmax(0,1fr));
                gap:14px; margin-bottom:16px;
            }
            .batch-record-summary-card {
                background:#fff; border:1px solid #e9ecef; border-top:3px solid #e9ecef; border-radius:10px;
                padding:14px 16px; display:flex; align-items:center; gap:12px;
            }
            .batch-record-summary-card:nth-child(1) { border-top-color:#6777ef; }
            .batch-record-summary-card:nth-child(2) { border-top-color:#28a745; }
            .batch-record-summary-card:nth-child(3) { border-top-color:#e0a800; }
            .batch-record-summary-icon {
                width:34px; height:34px; border-radius:8px; flex-shrink:0;
                display:flex; align-items:center; justify-content:center; font-size:15px;
            }
            .batch-record-summary-icon-total { background:#eef0fd; color:#6777ef; }
            .batch-record-summary-icon-firmados { background:#e9f9ee; color:#28a745; }
            .batch-record-summary-icon-pendientes { background:#fff6e6; color:#e0a800; }
            .batch-record-summary-value { font-size:20px; font-weight:700; color:#0b0025; line-height:1; }
            .batch-record-summary-label { font-size:12px; color:#6c757d; margin-top:3px; }
            .batch-record-summary-sub { font-size:11px; color:#9aa0ac; margin-top:1px; }

            .batch-record-actions {
                display:flex; flex-wrap:wrap; gap:10px; margin-bottom:16px; justify-content: flex-end;
            }
            .batch-record-actions .btn-green {
                background:linear-gradient(180deg,#22c55e,#16a34a) !important;
                border-color:#16a34a !important; color:#fff !important;
                box-shadow:0 2px 6px rgba(22,163,74,0.35);
            }
            .batch-record-actions .btn-primary {
                background:linear-gradient(180deg,#7c86f5,#6777ef) !important;
                border-color:#6777ef !important; color:#fff !important;
                box-shadow:0 2px 6px rgba(103,119,239,0.35);
            }

            .batch-record-tabs.nav-tabs {
                border-bottom:1px solid #e9ecef; margin-bottom:16px;
                flex-wrap:nowrap; overflow-x:auto; overflow-y:hidden; -webkit-overflow-scrolling:touch;
            }
            .batch-record-tabs.nav-tabs .nav-item { flex-shrink:0; }
            .batch-record-tabs.nav-tabs .nav-link {
                color:#6c757d; font-size:13px; font-weight:500; border:none;
                border-radius:8px; padding:9px 14px; cursor:pointer; white-space:nowrap;
            }
            .batch-record-tabs.nav-tabs .nav-link.active {
                color:#fff; font-weight:700; background:#6a7bff2e;
            }
            .batch-record-tab-count { color:#adb5bd; font-weight:600; margin-left:4px; }
            .batch-record-tabs.nav-tabs .nav-link.active .batch-record-tab-count { color:#dfe1fc; }

            .batch-record-main {
                display:flex; gap:12px; align-items:flex-start; margin-bottom:16px;
            }
            .batch-record-doc-panel {
                flex-grow:1; min-width:0; background:#fff; border:1px solid #e9ecef;
                border-radius:10px; overflow:hidden;
            }
            .batch-record-doc-panel-header {
                padding:12px 16px; border-bottom:1px solid #f0f1f3;
            }
            .batch-record-doc-panel-title { font-size:15px; font-weight:700; color:#0b0025; }
            .batch-record-doc-name {
                font-weight:600; color:#0b0025;
                max-width:280px; white-space:normal; word-break:break-word; overflow-wrap:anywhere;
            }
            .batch-record-src-icon {
                width:16px; height:16px; object-fit:contain; vertical-align:-3px; margin-right:2px;
            }
            .batch-record-src-icon-lg {
                width:20px; height:20px; object-fit:contain; vertical-align:-5px; margin-right:3px;
            }
            .batch-record-src-box {
                display:inline-flex; align-items:center; justify-content:center;
                width:24px; height:24px; border-radius:6px; vertical-align:-7px; margin-right:6px;
            }
            .batch-record-src-box img { width:28px; height:28px; object-fit:contain; }
            .batch-record-src-box-lab { background:#dbeafe; }
            .batch-record-src-box-coa { background:#f3e8ff; }
            .batch-record-src-box-lotes { background:#e0f2fe; }
            .batch-record-src-box-manga { background:#fce7f3; }
            .batch-record-src-box-formula { background:#ccfbf1; }
            .batch-record-ext-chip {
                display:inline-flex; align-items:center; justify-content:center;
                width:22px; height:22px; border-radius:5px; font-size:7.5px; font-weight:700; color:#fff;
                vertical-align:-6px; margin-right:5px;
            }
            .batch-record-ext-pdf { background:#dc2626; }
            .batch-record-ext-img { background:#2563eb; }

            .batch-record-viewer {
                width:280px; flex-shrink:0; background:#fff; border:1px solid #e9ecef;
                border-radius:10px; min-height:420px; display:flex; flex-direction:column;
            }
            .batch-record-viewer-header {
                padding:12px 16px; border-bottom:1px solid #f0f1f3; font-size:13.5px; font-weight:700; color:#0b0025;
            }
            .batch-record-viewer-empty {
                padding:30px 20px; text-align:center; color:#adb5bd; font-size:12.5px;
            }
            .batch-record-viewer-empty i { font-size:28px; display:block; margin-bottom:10px; }
            .batch-record-viewer-content { display:none; flex-direction:column; flex-grow:1; min-height:0; }
            .batch-record-viewer-meta { padding:10px 14px; border-bottom:1px solid #f0f1f3; }
            .batch-record-viewer-name { font-size:12.5px; font-weight:700; color:#0b0025; word-break:break-all; }
            .batch-record-viewer-sub { font-size:11px; color:#9aa0ac; margin-top:2px; }
            .batch-record-viewer-frame { width:100%; flex-grow:1; min-height:340px; border:none; }
            .batch-record-viewer-actions { padding:10px 14px; border-top:1px solid #f0f1f3; }
            .batch-record-row-selected td { background:#eef2ff !important; }

            .batch-record-pill {
                display:inline-flex; align-items:center; gap:5px; padding:3px 10px;
                border-radius:20px; font-size:11px; font-weight:600;
            }
            .batch-record-pill-fisico { background:#f1f2f4; color:#4b5563; }
            .batch-record-pill-soporte { background:#ffedd5; color:#c2410c; }
            .batch-record-pill-lab { background:#dbeafe; color:#1d4ed8; }
            .batch-record-pill-coa { background:#f3e8ff; color:#7e22ce; }
            .batch-record-pill-lotes { background:#e0f2fe; color:#0369a1; }
            .batch-record-pill-manga { background:#fce7f3; color:#be185d; }
            .batch-record-pill-formula { background:#ccfbf1; color:#0d9488; }

            .batch-record-status { display:inline-flex; align-items:center; gap:5px; font-size:12.5px; font-weight:600; white-space:nowrap; }
            .batch-record-status-na { color:#0d6efd; }
            .batch-record-status-firmado { color:#28a745; }
            .batch-record-status-pendiente { color:#e0a800; }

            .batch-record-events {
                background:#fff; border:1px solid #e9ecef; border-radius:10px; overflow:hidden;
            }
            .batch-record-events-header {
                padding:12px 16px; border-bottom:1px solid #f0f1f3; font-size:13px; font-weight:700; color:#946200;
            }
            .batch-record-events-empty { padding:14px 16px; color:#adb5bd; font-size:12.5px; }
            .batch-record-event-row {
                padding:10px 16px; border-top:1px solid #f7f7f8; display:flex; gap:10px; align-items:center; font-size:12.5px;
            }
            .batch-record-event-row:first-child { border-top:none; }
            .batch-record-event-text { color:#6c757d; }

            .batch-record-menu { position:relative; display:inline-block; }
            .batch-record-menu-btn {
                background:#fff; border:1px solid #e5e7eb; border-radius:6px;
                width:28px; height:28px; padding:0; display:inline-flex; align-items:center; justify-content:center;
                color:#6b7280; line-height:1;
            }
            .batch-record-menu-btn:hover { background:#f3f4f6; }
            .batch-record-menu-list { min-width:175px; font-size:13px; z-index:2000; }
            .batch-record-menu-list .dropdown-item { cursor:pointer; }
            .batch-record-menu-list .dropdown-item i { width:16px; text-align:center; margin-right:4px; }

            .batch-record-toolbar {
                display:flex; gap:10px; align-items:center; padding:10px 16px;
                border-bottom:1px solid #f0f1f3; flex-wrap:wrap;
            }
            .batch-record-search {
                flex-grow:1; min-width:160px; display:flex; align-items:center; gap:7px;
                border:1px solid #e5e7eb; border-radius:7px; padding:6px 11px; background:#fafafa; color:#9ca3af;
            }
            .batch-record-search input {
                border:none; background:transparent; outline:none; flex-grow:1; font-size:12.5px; color:#1f2937;
            }
            .batch-record-toolbar-btn {
                background:#fff; border:1px solid #d1d5db; border-radius:7px; padding:6px 12px;
                font-size:12.5px; color:#374151; white-space:nowrap;
            }
            .batch-record-toolbar-btn:hover { background:#f8f9fa; }
            .batch-record-filter-label {
                font-size:10.5px; font-weight:700; color:#9aa0ac; padding:4px 16px 2px; text-transform:uppercase;
            }
            .batch-record-filter-list label.dropdown-item { display:flex; align-items:center; gap:7px; margin-bottom:0; }

            @media (max-width: 992px) {
                .batch-record-main { flex-direction:column; }
                .batch-record-viewer { width:100%; }
                .batch-record-summary { grid-template-columns:1fr 1fr; }
                .batch-record-header { flex-direction:column; align-items:flex-start; }
                .batch-record-header-right { width:100%; justify-content:space-between; }
            }
            @media (max-width: 620px) {
                .batch-record-summary { grid-template-columns:1fr; }
                .batch-record-actions .btn span.batch-record-btn-label { display:none; }
                .table-files thead { display:none; }
                .table-files, .table-files tbody, .table-files tr, .table-files td { display:block; width:100%; }
                .table-files tr { border-top:1px solid #f2f3f5; padding:8px 4px; }
                .table-files td { padding:3px 12px !important; border:none !important; }
            }
        </style>
    </head>

    <body class="bg-light sidebar-mini">

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

                                        // Los archivos de Batch Record ya no viven en el filesystem
                                        // local (web/Certificates/...): el binario está en Office
                                        // Platform y esta tabla de metadatos es la única fuente de
                                        // verdad para navegar y listar documentos.
                                        CertificateFileJpaController certController = new CertificateFileJpaController();
                                    %>

                                    <!-- ================== BREADCRUMB ================== -->
                                    <div class="breadcrumb-bar d-flex justify-content-between align-items-center flex-wrap">
                                        <nav aria-label="breadcrumb">
                                            <ol class="breadcrumb">
                                                <li class="breadcrumb-item"><a href="FileManager.jsp"><i class="fas fa-home mr-1"></i>Clientes</a></li>
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
                                        <div class="input-group" style="width:auto; min-width:240px;">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text bg-white">
                                                    <i class="fas fa-filter text-muted"></i>
                                                </div>
                                            </div>
                                            <input type="text"
                                                   id="filterInput"
                                                   class="form-control"
                                                   placeholder="Filtrar carpetas o archivos..."
                                                   onkeyup="filterItems()"
                                                   style="width:auto; float:none;">
                                        </div>
                                    </div>
                                    <Alert:Alert/>

                                    <!-- ================== LISTADO ================== -->
                                    <%
                                        {

                                            /* ================== ARCHIVOS (LOTE) ================== */
                                            if (lote != null) {
                                                List<CertificateFileRow> archivos = certController.consultFilesByLote(cliente, anio, orden, lote, "");
                                                List<CertificateFileRow> soportes = certController.consultFilesByLote(cliente, anio, orden, lote, "SupportDocs");

                                                long ultimaActualizacionMillis = 0L;
                                                if (archivos != null) {
                                                    for (CertificateFileRow f : archivos) {
                                                        if (f.lastModified() > ultimaActualizacionMillis) {
                                                            ultimaActualizacionMillis = f.lastModified();
                                                        }
                                                    }
                                                }
                                                if (soportes != null) {
                                                    for (CertificateFileRow f : soportes) {
                                                        if (f.lastModified() > ultimaActualizacionMillis) {
                                                            ultimaActualizacionMillis = f.lastModified();
                                                        }
                                                    }
                                                }
                                                String ultimaActualizacionTexto = ultimaActualizacionMillis > 0
                                                        ? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(ultimaActualizacionMillis))
                                                        : "—";

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



                                    <!-- ================== BATCH RECORD: HEADER DEL LOTE ================== -->
                                    <div class="batch-record-header">
                                        <div>
                                            <div class="batch-record-lote-title">Lote <%= lote%></div>
                                            <div class="batch-record-lote-subtitle">Orden: <%= orden%>&nbsp;&nbsp;&nbsp;Cliente: <%= cliente%>&nbsp;&nbsp;&nbsp;Año: <%= anio%></div>
                                        </div>
                                        <div class="batch-record-header-right">
                                            <div>
                                                <div class="batch-record-updated-label">Última actualización</div>
                                                <div class="batch-record-updated-value"><i class="fas fa-clock"></i> <%= ultimaActualizacionTexto%></div>
                                            </div>
                                            <% if (!eventosListado.isEmpty()) { %>
                                            <a href="#batchRecordEventos" class="batch-record-events-badge">
                                                <i class="fas fa-triangle-exclamation"></i>
                                                Eventos de integración
                                                <span class="batch-record-events-count"><%= eventosListado.size()%></span>
                                            </a>
                                            <% } %>
                                        </div>
                                    </div>

                                    <!-- ================== BATCH RECORD: RESUMEN ================== -->
                                    <div class="batch-record-summary">
                                        <div class="batch-record-summary-card">
                                            <div class="batch-record-summary-icon batch-record-summary-icon-total"><i class="fas fa-file-alt"></i></div>
                                            <div>
                                                <div class="batch-record-summary-value" id="brTotalDocs">0</div>
                                                <div class="batch-record-summary-label">Total documentos</div>
                                            </div>
                                        </div>
                                        <div class="batch-record-summary-card">
                                            <div class="batch-record-summary-icon batch-record-summary-icon-firmados"><i class="fas fa-check-circle"></i></div>
                                            <div>
                                                <div class="batch-record-summary-value" id="brFirmados">0</div>
                                                <div class="batch-record-summary-label">Soportes firmados</div>
                                                <div class="batch-record-summary-sub" id="brFirmadosPct"></div>
                                            </div>
                                        </div>
                                        <div class="batch-record-summary-card">
                                            <div class="batch-record-summary-icon batch-record-summary-icon-pendientes"><i class="fas fa-clock"></i></div>
                                            <div>
                                                <div class="batch-record-summary-value" id="brPendientes">0</div>
                                                <div class="batch-record-summary-label">Soportes pendientes de firma</div>
                                                <div class="batch-record-summary-sub" id="brPendientesPct"></div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- ================== BATCH RECORD: ACCIONES ================== -->
                                    <div class="batch-record-actions">
                                        <button type="button" class="btn btn-danger font-weight-bold" style="background:#dc3545; border-color:#dc3545; box-shadow:0 2px 6px rgba(220,53,69,0.3);" onclick="generarBatchRecordPdfUnificado('<%= cliente%>', '<%= anio%>', '<%= orden%>', '<%= lote%>')">
                                            <i class="fas fa-file-pdf"></i> <span class="batch-record-btn-label">Generar Batch Record PDF Unificado</span>
                                        </button>
                                        <% if (Permission.contains("[3]")) { %>
                                        <button type="button" class="btn btn-green" data-bs-toggle="modal" data-bs-target="#uploadModal">
                                            <i class="fas fa-upload"></i> <span class="batch-record-btn-label">Subir archivos</span>
                                        </button>
                                        <% } %>
                                        <% if (Permission.contains("[39]")) { %>
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#supportUploadModal">
                                            <i class="fas fa-file-signature"></i> <span class="batch-record-btn-label">Adjuntar documento de soporte</span>
                                        </button>
                                        <% } %>
                                    </div>

                                    <!-- ================== BATCH RECORD: TABS DE CATEGORÍA ================== -->
                                    <ul class="nav nav-tabs batch-record-tabs" id="batchRecordTabs">
                                        <li class="nav-item"><a class="nav-link active" href="#" data-br-tab="todos" onclick="brFiltrarCategoria('todos'); return false;">Todos <span class="batch-record-tab-count" id="brCountTodos">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="fisico" onclick="brFiltrarCategoria('fisico'); return false;"><i class="fas fa-file"></i> Archivos físicos <span class="batch-record-tab-count" id="brCountFisico">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="soporte" onclick="brFiltrarCategoria('soporte'); return false;"><i class="fas fa-paperclip"></i> Soporte <span class="batch-record-tab-count" id="brCountSoporte">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="lab" onclick="brFiltrarCategoria('lab'); return false;"><img class="batch-record-src-icon" src="Interface/Imagen/Registros_lab_Logo.png" alt=""> Registros LAB <span class="batch-record-tab-count" id="brCountLab">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="coa" onclick="brFiltrarCategoria('coa'); return false;"><img class="batch-record-src-icon" src="Interface/Imagen/LogoSText.fw.png" alt=""> Certificados COA <span class="batch-record-tab-count" id="brCountCoa">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="lotes" onclick="brFiltrarCategoria('lotes'); return false;"><img class="batch-record-src-icon" src="Interface/Imagen/Generacion_lotes.png" alt=""> Generación de Lotes <span class="batch-record-tab-count" id="brCountLotes">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="manga" onclick="brFiltrarCategoria('manga'); return false;"><img class="batch-record-src-icon" src="Interface/Imagen/Inspeccion_manga_new.png" alt=""> Inspección Manga <span class="batch-record-tab-count" id="brCountManga">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="formula" onclick="brFiltrarCategoria('formula'); return false;"><img class="batch-record-src-icon" src="Interface/Imagen/Control_formulas_new.png" alt=""> Control Fórmulas <span class="batch-record-tab-count" id="brCountFormula">0</span></a></li>
                                    </ul>

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
                                    <div class="batch-record-main">
                                        <div class="batch-record-doc-panel">
                                            <div class="batch-record-doc-panel-header">
                                                <div class="batch-record-doc-panel-title">Documentos del lote</div>
                                            </div>
                                            <div class="batch-record-toolbar">
                                                <div class="batch-record-search">
                                                    <i class="fas fa-search"></i>
                                                    <input type="text" id="brSearchInput" placeholder="Buscar documento..." onkeyup="brBuscarDocumento()">
                                                </div>
                                                <div class="batch-record-menu">
                                                    <button type="button" class="batch-record-toolbar-btn" onclick="brToggleMenu(event, this)">
                                                        <i class="fas fa-filter"></i> Filtros
                                                    </button>
                                                    <div class="dropdown-menu batch-record-menu-list batch-record-filter-list" onclick="event.stopPropagation();">
                                                        <div class="batch-record-filter-label">Estado</div>
                                                        <label class="dropdown-item"><input type="checkbox" class="br-filter-status" value="firmado" checked onchange="brAplicarFiltrosEstado()"> Firmado</label>
                                                        <label class="dropdown-item"><input type="checkbox" class="br-filter-status" value="pendiente" checked onchange="brAplicarFiltrosEstado()"> Pendiente</label>
                                                        <label class="dropdown-item"><input type="checkbox" class="br-filter-status" value="na" checked onchange="brAplicarFiltrosEstado()"> Disponible</label>
                                                    </div>
                                                </div>
                                                <div class="batch-record-menu">
                                                    <button type="button" class="batch-record-toolbar-btn" onclick="brToggleMenu(event, this)">
                                                        Ordenar: <span id="brOrdenLabel">Más reciente</span> <i class="fas fa-chevron-down"></i>
                                                    </button>
                                                    <div class="dropdown-menu batch-record-menu-list">
                                                        <a class="dropdown-item" href="#" onclick="brOrdenar('fecha'); return false;">Más reciente</a>
                                                        <a class="dropdown-item" href="#" onclick="brOrdenar('nombre'); return false;">Nombre A-Z</a>
                                                    </div>
                                                </div>
                                            </div>
                                    <div class="batch-record-table-scroll" style="overflow-x:auto;">
                                    <table class="table table-hover table-files mb-0">
                                        <thead>
                                            <tr>
                                                <th>Documento</th>
                                                <th>Categoría</th>
                                                <th>Estado</th>
                                                <th>Fecha</th>
                                                <th>Origen</th>
                                                <th width="56" class="text-center">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody id="fileTable">

                                            <%
                                                if (archivos != null && !archivos.isEmpty()) {
                                                    for (CertificateFileRow archivo : archivos) {
                                                        String relPath = "FileDownloadProxyServlet?id=" + archivo.getId() + "&modo=inline";
                                                        String relPathDescarga = "FileDownloadProxyServlet?id=" + archivo.getId() + "&modo=adjunto";
                                                        String fechaArchivo = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(archivo.lastModified()));
                                                        String archivoExtLower = archivo.getName().toLowerCase();
                                                        String archivoExtClass = "batch-record-ext-img";
                                                        String archivoExtLabel = "IMG";
                                                        if (archivoExtLower.endsWith(".pdf")) {
                                                            archivoExtClass = "batch-record-ext-pdf";
                                                            archivoExtLabel = "PDF";
                                                        } else if (archivoExtLower.endsWith(".png")) {
                                                            archivoExtLabel = "PNG";
                                                        } else if (archivoExtLower.endsWith(".jpg") || archivoExtLower.endsWith(".jpeg")) {
                                                            archivoExtLabel = "JPG";
                                                        } else if (archivoExtLower.endsWith(".gif")) {
                                                            archivoExtLabel = "GIF";
                                                        }
                                            %>
                                            <tr class="file-row batch-record-row" data-category="fisico" data-status="na" data-timestamp="<%= archivo.lastModified()%>">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= archivo.getName()%>', 'Archivo Físico', '<%= relPath%>')"><span class="batch-record-ext-chip <%= archivoExtClass%>"><%= archivoExtLabel%></span> <%= archivo.getName()%></td>
                                                <td><span class="batch-record-pill batch-record-pill-fisico">Archivo Físico</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td><%= fechaArchivo%></td>
                                                <td>Físico</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('Archivo Físico', '<%= archivo.getName()%>', '<%= relPath%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= relPath%>" target="_blank" onclick="brVerEnPanel(this, '<%= archivo.getName()%>', 'Archivo Físico', '<%= relPath%>'); return false;"><i class="fas fa-eye"></i> Ver original</a>
                                                            <a class="dropdown-item" href="<%= relPathDescarga%>" download><i class="fas fa-download"></i> Descargar</a>
                                                            <% if (Permission.contains("[4]")) { %>
                                                            <div class="dropdown-divider"></div>
                                                            <a class="dropdown-item text-danger" href="#" onclick="confirmDeleteFile('<%= cliente%>','<%= anio%>','<%= orden%>','<%= lote%>','<%= archivo.getId()%>'); return false;"><i class="fas fa-trash"></i> Eliminar</a>
                                                            <% } %>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="lab" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= ArgLink[2]%>', 'Registros LAB', '<%= ArgLink[3]%>')"><span class="batch-record-src-box batch-record-src-box-lab"><img src="Interface/Imagen/Registros_lab_Logo.png" alt=""></span> <%= ArgLink[1]%> — <%= ArgLink[2]%></td>
                                                <td><span class="batch-record-pill batch-record-pill-lab">Registros LAB</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>LAB</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= ArgLink[1]%>', '<%= ArgLink[2]%>', '<%= ArgLink[3]%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= ArgLink[3]%>" target="_blank" onclick="brVerEnPanel(this, '<%= ArgLink[2]%>', 'Registros LAB', '<%= ArgLink[3]%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="coa" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= ArgCertificate[2]%>', 'Certificados COA', '<%= ArgCertificate[3]%>')"><span class="batch-record-src-box batch-record-src-box-coa"><img src="Interface/Imagen/LogoSText.fw.png" alt=""></span> <%= ArgCertificate[1]%> — <%= ArgCertificate[2]%></td>
                                                <td><span class="batch-record-pill batch-record-pill-coa">Certificados COA</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>COA</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= ArgCertificate[1]%>', '<%= ArgCertificate[2]%>', '<%= ArgCertificate[3]%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= ArgCertificate[3]%>" target="_blank" onclick="brVerEnPanel(this, '<%= ArgCertificate[2]%>', 'Certificados COA', '<%= ArgCertificate[3]%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="lotes" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= ArgBatch[3]%>', 'Generación de Lotes', 'DownloadGL?File_name=<%= ArgBatch[2].trim()%>')"><span class="batch-record-src-box batch-record-src-box-lotes"><img src="Interface/Imagen/Generacion_lotes.png" alt=""></span> <%= ArgBatch[1]%> — <%= ArgBatch[3]%></td>
                                                <td><span class="batch-record-pill batch-record-pill-lotes">Generación de Lotes</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>Lotes</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= ArgBatch[1]%>', '<%= ArgBatch[3]%>', 'DownloadGL?File_name=<%= ArgBatch[2].trim()%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="DownloadGL?File_name=<%= ArgBatch[2].trim()%>" target="_blank" onclick="brVerEnPanel(this, '<%= ArgBatch[3]%>', 'Generación de Lotes', 'DownloadGL?File_name=<%= ArgBatch[2].trim()%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="manga" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= mangaNombre%>', 'Inspección Manga', '<%= mangaUrl%>')"><span class="batch-record-src-box batch-record-src-box-manga"><img src="Interface/Imagen/Inspeccion_manga_new.png" alt=""></span> <%= mangaTipo%> — <%= mangaNombre%></td>
                                                <td><span class="batch-record-pill batch-record-pill-manga">Inspección Manga</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>Manga</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= mangaTipo%>', '<%= mangaNombre%>', '<%= mangaUrl%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= mangaUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= mangaNombre%>', 'Inspección Manga', '<%= mangaUrl%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="manga" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= despejeNombre%>', 'Inspección Manga', '<%= despejeUrl%>')"><span class="batch-record-src-box batch-record-src-box-manga"><img src="Interface/Imagen/Inspeccion_manga_new.png" alt=""></span> <%= despejeTipo%> — <%= despejeNombre%></td>
                                                <td><span class="batch-record-pill batch-record-pill-manga">Inspección Manga</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>Manga</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= despejeTipo%>', '<%= despejeNombre%>', '<%= despejeUrl%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= despejeUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= despejeNombre%>', 'Inspección Manga', '<%= despejeUrl%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="manga" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= cabeceraNombre%>', 'Inspección Manga', '<%= cabeceraUrl%>')"><span class="batch-record-src-box batch-record-src-box-manga"><img src="Interface/Imagen/Inspeccion_manga_new.png" alt=""></span> <%= cabeceraTipo%> — <%= cabeceraNombre%></td>
                                                <td><span class="batch-record-pill batch-record-pill-manga">Inspección Manga</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>Manga</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= cabeceraTipo%>', '<%= cabeceraNombre%>', '<%= cabeceraUrl%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= cabeceraUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= cabeceraNombre%>', 'Inspección Manga', '<%= cabeceraUrl%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
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
                                            <tr class="file-row batch-record-row" data-category="formula" data-status="na">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= formulaNombre%>', 'Control Fórmulas', '<%= formulaUrl%>')"><span class="batch-record-src-box batch-record-src-box-formula"><img src="Interface/Imagen/Control_formulas_new.png" alt=""></span> <%= formulaTipo%> — <%= formulaNombre%></td>
                                                <td><span class="batch-record-pill batch-record-pill-formula">Control Fórmulas</span></td>
                                                <td><span class="batch-record-status batch-record-status-na"><i class="fas fa-check-circle"></i> Disponible</span></td>
                                                <td>—</td>
                                                <td>Fórmula</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= formulaTipo%>', '<%= formulaNombre%>', '<%= formulaUrl%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= formulaUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= formulaNombre%>', 'Control Fórmulas', '<%= formulaUrl%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                            <%
                                                if (soportes != null) {
                                                    for (CertificateFileRow soporte : soportes) {
                                                        String nombreSoporte = soporte.getName();
                                                        boolean firmado = nombreSoporte.contains("_FIRMADO_");
                                                        String relPathSoporte = "FileDownloadProxyServlet?id=" + soporte.getId() + "&modo=inline";
                                                        String relPathSoporteDescarga = "FileDownloadProxyServlet?id=" + soporte.getId() + "&modo=adjunto";
                                                        String soporteExtLower = nombreSoporte.toLowerCase();
                                                        String soporteExtClass = "batch-record-ext-img";
                                                        String soporteExtLabel = "IMG";
                                                        if (soporteExtLower.endsWith(".pdf")) {
                                                            soporteExtClass = "batch-record-ext-pdf";
                                                            soporteExtLabel = "PDF";
                                                        } else if (soporteExtLower.endsWith(".png")) {
                                                            soporteExtLabel = "PNG";
                                                        } else if (soporteExtLower.endsWith(".jpg") || soporteExtLower.endsWith(".jpeg")) {
                                                            soporteExtLabel = "JPG";
                                                        } else if (soporteExtLower.endsWith(".gif")) {
                                                            soporteExtLabel = "GIF";
                                                        }
                                            %>
                                            <tr class="file-row batch-record-row" data-category="soporte" data-status="<%= firmado ? "firmado" : "pendiente"%>" data-timestamp="<%= soporte.lastModified()%>">
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= nombreSoporte%>', 'Documento de soporte', '<%= relPathSoporte%>')"><span class="batch-record-ext-chip <%= soporteExtClass%>"><%= soporteExtLabel%></span> <%= nombreSoporte%></td>
                                                <td><span class="batch-record-pill batch-record-pill-soporte">Documento de soporte</span></td>
                                                <td>
                                                    <% if (firmado) { %>
                                                    <span class="batch-record-status batch-record-status-firmado"><i class="fas fa-check-circle"></i> Firmado</span>
                                                    <% } else { %>
                                                    <span class="batch-record-status batch-record-status-pendiente"><i class="fas fa-clock"></i> Pendiente de firma</span>
                                                    <% } %>
                                                </td>
                                                <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(soporte.lastModified()))%></td>
                                                <td>Soporte</td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="<%= relPathSoporte%>" target="_blank" onclick="brVerEnPanel(this, '<%= nombreSoporte%>', 'Documento de soporte', '<%= relPathSoporte%>'); return false;"><i class="fas fa-eye"></i> Ver documento</a>
                                                            <a class="dropdown-item" href="<%= relPathSoporteDescarga%>" download><i class="fas fa-download"></i> Descargar</a>
                                                            <% if (!firmado && Permission.contains("[39]")) { %>
                                                            <a class="dropdown-item" href="#" onclick="window.open('SupportDocumentSign.jsp?cliente=<%= cliente%>&anio=<%= anio%>&orden=<%= orden%>&lote=<%= lote%>&id=<%= soporte.getId()%>', '_blank'); return false;"><i class="fas fa-file-signature"></i> Firmar</a>
                                                            <% } %>
                                                            <% if (Permission.contains("[4]")) { %>
                                                            <div class="dropdown-divider"></div>
                                                            <a class="dropdown-item text-danger" href="#" onclick="confirmDeleteSupportFile('<%= cliente%>','<%= anio%>','<%= orden%>','<%= lote%>','<%= soporte.getId()%>'); return false;"><i class="fas fa-trash"></i> Eliminar</a>
                                                            <% } %>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                        </tbody>
                                    </table>
                                    </div>
                                        </div>

                                        <div class="batch-record-viewer">
                                            <div class="batch-record-viewer-header">
                                                Vista del documento
                                            </div>
                                            <div class="batch-record-viewer-empty" id="brViewerEmpty">
                                                <i class="fas fa-file-alt"></i>
                                                <p>Selecciona "Ver documento"/"Ver registro" en el menú <i class="fas fa-ellipsis-v"></i> de una fila para verlo aquí.</p>
                                            </div>
                                            <div class="batch-record-viewer-content" id="brViewerContent">
                                                <div class="batch-record-viewer-meta">
                                                    <div class="batch-record-viewer-name" id="brViewerName"></div>
                                                    <div class="batch-record-viewer-sub" id="brViewerSub"></div>
                                                </div>
                                                <iframe id="brViewerFrame" class="batch-record-viewer-frame" src="" title="Vista previa del documento"></iframe>
                                                <div class="batch-record-viewer-actions">
                                                    <a id="brViewerOpenLink" class="btn btn-outline-secondary btn-sm btn-block" href="#" target="_blank">
                                                        <i class="fas fa-external-link-alt"></i> Abrir en pestaña nueva
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- ================== BATCH RECORD: EVENTOS DE INTEGRACIÓN ================== -->
                                    <div class="batch-record-events" id="batchRecordEventos">
                                        <div class="batch-record-events-header">
                                            <i class="fas fa-triangle-exclamation"></i> Eventos de integración recientes <b><%= eventosListado.size()%></b>
                                        </div>
                                        <% if (eventosListado.isEmpty()) { %>
                                        <div class="batch-record-events-empty">Sin eventos de integración recientes.</div>
                                        <% } else { %>
                                        <% for (String evento : eventosListado) { %>
                                        <div class="batch-record-event-row">
                                            <i class="fas fa-triangle-exclamation text-warning"></i>
                                            <span class="batch-record-event-text"><%= evento%></span>
                                        </div>
                                        <% } %>
                                        <% } %>
                                    </div>

                                    <%
                                        /* ================== CARPETAS ================== */
                                    } else {

                                        // Reemplaza la navegación por carpetas físicas: cada nivel
                                        // lista los valores distintos ya registrados en
                                        // certificate_files para el nivel anterior.
                                        List<String> carpetas;
                                        if (cliente == null) {
                                            carpetas = certController.consultDistinctClientes();
                                        } else if (anio == null) {
                                            carpetas = certController.consultDistinctAnios(cliente);
                                        } else if (orden == null) {
                                            carpetas = certController.consultDistinctOrdenes(cliente, anio);
                                        } else {
                                            carpetas = certController.consultDistinctLotes(cliente, anio, orden);
                                        }

                                    %>

                                    <div class="row g-3 mt-2"  id="folderGrid">
                                        <%                                            if (carpetas != null && !carpetas.isEmpty()) {
                                                for (String nombreCarpeta : carpetas) {

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
                                                        link += "cliente=" + nombreCarpeta;
                                                    } else if (anio == null) {
                                                        link += "anio=" + nombreCarpeta;
                                                    } else if (orden == null) {
                                                        link += "orden=" + nombreCarpeta;
                                                    } else {
                                                        link += "lote=" + nombreCarpeta;
                                                    }
                                        %>

                                        <div class="col-6 col-md-4 col-lg-3 folder-item">
                                            <div class="card text-center p-3">
                                                <a href="<%=link%>" class="text-decoration-none text-dark">
                                                    <i class="fas fa-folder"></i>
                                                    <h6><%= nombreCarpeta%></h6>
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
            function confirmDeleteFile(cliente, anio, orden, lote, id) {

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
                                + "&id=" + encodeURIComponent(id);
                    }

                });
            }

            function confirmDeleteSupportFile(cliente, anio, orden, lote, id) {

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
                                + "&id=" + encodeURIComponent(id);
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

            var brCategoriaActiva = 'todos';

            function brFiltrarCategoria(categoria) {
                brCategoriaActiva = categoria;
                document.querySelectorAll('#batchRecordTabs .nav-link').forEach(function (link) {
                    link.classList.toggle('active', link.getAttribute('data-br-tab') === categoria);
                });
                brAplicarVisibilidad();
            }

            function brAplicarVisibilidad() {
                document.querySelectorAll('.batch-record-row').forEach(function (row) {
                    const coincideCategoria = brCategoriaActiva === 'todos' || row.getAttribute('data-category') === brCategoriaActiva;
                    const oculto = row.getAttribute('data-search-hidden') === '1' || row.getAttribute('data-filter-hidden') === '1';
                    row.style.display = (coincideCategoria && !oculto) ? '' : 'none';
                });
            }

            function brBuscarDocumento() {
                const texto = document.getElementById('brSearchInput').value.toLowerCase();
                document.querySelectorAll('.batch-record-row').forEach(function (row) {
                    row.setAttribute('data-search-hidden', row.innerText.toLowerCase().indexOf(texto) === -1 ? '1' : '0');
                });
                brAplicarVisibilidad();
            }

            function brAplicarFiltrosEstado() {
                const activos = Array.prototype.map.call(document.querySelectorAll('.br-filter-status:checked'), function (cb) {
                    return cb.value;
                });
                document.querySelectorAll('.batch-record-row').forEach(function (row) {
                    const estado = row.getAttribute('data-status');
                    row.setAttribute('data-filter-hidden', activos.indexOf(estado) === -1 ? '1' : '0');
                });
                brAplicarVisibilidad();
            }

            function brOrdenar(criterio) {
                const tbody = document.getElementById('fileTable');
                const filas = Array.prototype.slice.call(tbody.querySelectorAll('.batch-record-row'));
                filas.sort(function (a, b) {
                    if (criterio === 'nombre') {
                        const nombreA = a.querySelector('.batch-record-doc-name').innerText.trim();
                        const nombreB = b.querySelector('.batch-record-doc-name').innerText.trim();
                        return nombreA.localeCompare(nombreB);
                    }
                    const fechaA = parseInt(a.getAttribute('data-timestamp') || '0', 10);
                    const fechaB = parseInt(b.getAttribute('data-timestamp') || '0', 10);
                    return fechaB - fechaA;
                });
                filas.forEach(function (fila) {
                    tbody.appendChild(fila);
                });
                document.getElementById('brOrdenLabel').textContent = criterio === 'nombre' ? 'Nombre A-Z' : 'Más reciente';
                brCerrarMenus();
            }

            function brActualizarResumen() {
                const filas = document.querySelectorAll('.batch-record-row');
                const conteos = {fisico: 0, soporte: 0, lab: 0, coa: 0, lotes: 0, manga: 0, formula: 0};
                let firmados = 0;
                let pendientesSoporte = 0;
                let totalSoporte = 0;

                filas.forEach(function (row) {
                    const categoria = row.getAttribute('data-category');
                    if (conteos.hasOwnProperty(categoria)) {
                        conteos[categoria]++;
                    }
                    if (categoria === 'soporte') {
                        totalSoporte++;
                        if (row.getAttribute('data-status') === 'firmado') {
                            firmados++;
                        } else {
                            pendientesSoporte++;
                        }
                    }
                });

                const total = filas.length;
                document.getElementById('brTotalDocs').textContent = total;
                document.getElementById('brCountTodos').textContent = total;
                document.getElementById('brCountFisico').textContent = conteos.fisico;
                document.getElementById('brCountSoporte').textContent = conteos.soporte;
                document.getElementById('brCountLab').textContent = conteos.lab;
                document.getElementById('brCountCoa').textContent = conteos.coa;
                document.getElementById('brCountLotes').textContent = conteos.lotes;
                document.getElementById('brCountManga').textContent = conteos.manga;
                document.getElementById('brCountFormula').textContent = conteos.formula;

                document.getElementById('brFirmados').textContent = firmados;
                document.getElementById('brPendientes').textContent = pendientesSoporte;

                if (totalSoporte > 0) {
                    document.getElementById('brFirmadosPct').textContent = Math.round((firmados / totalSoporte) * 100) + '% de los soportes';
                    document.getElementById('brPendientesPct').textContent = Math.round((pendientesSoporte / totalSoporte) * 100) + '% de los soportes';
                } else {
                    document.getElementById('brFirmadosPct').textContent = 'Sin documentos de soporte';
                    document.getElementById('brPendientesPct').textContent = '';
                }
            }

            document.addEventListener('DOMContentLoaded', brActualizarResumen);

            function brCerrarMenus() {
                document.querySelectorAll('.batch-record-menu-list.show').forEach(function (m) {
                    m.classList.remove('show');
                });
            }

            function brToggleMenu(evt, btn) {
                evt.stopPropagation();
                var menu = btn.nextElementSibling;
                var yaAbierto = menu.classList.contains('show');
                brCerrarMenus();
                if (yaAbierto) {
                    return;
                }
                var rect = btn.getBoundingClientRect();
                menu.style.position = 'fixed';
                menu.style.top = (rect.bottom + 4) + 'px';
                menu.style.left = 'auto';
                menu.style.right = (window.innerWidth - rect.right) + 'px';
                menu.classList.add('show');
            }

            document.addEventListener('click', brCerrarMenus);
            document.addEventListener('scroll', brCerrarMenus, true);

            function brVerEnPanel(elemento, nombre, categoria, url) {
                document.getElementById('brViewerEmpty').style.display = 'none';
                document.getElementById('brViewerContent').style.display = 'flex';
                document.getElementById('brViewerName').textContent = nombre;
                document.getElementById('brViewerSub').textContent = categoria;
                document.getElementById('brViewerFrame').src = url;
                document.getElementById('brViewerOpenLink').href = url;

                document.querySelectorAll('.batch-record-row-selected').forEach(function (row) {
                    row.classList.remove('batch-record-row-selected');
                });
                var fila = elemento.closest('tr');
                if (fila) {
                    fila.classList.add('batch-record-row-selected');
                }
                brCerrarMenus();
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

                // La extensión real vive en "nombre" (el archivo puede venir de
                // FileDownloadProxyServlet?id=..., que no termina en .pdf/.png).
                var nombreLower = (nombre || url).toLowerCase();

                if (nombreLower.endsWith(".pdf")) {
                    window.open(url, '_blank');
                    return;
                }

                if (nombreLower.match(/\.(png|jpg|jpeg|gif)$/)) {
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
