<%@page import="Connection.LinkBatchRecord"%>
<%@page import="Controller.CertificatesJpaController"%>
<%@page import="Controller.CertificateFileJpaController"%>
<%@page import="Controller.CertificateFileRow"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="Method.Util"%>
<%@page import="Method.BatchRecordManifest"%>
<%@page import="Method.BatchRecordChecklist"%>
<%@page import="Method.BatchRecordVersiones"%>
<%@page import="Servlet.BatchRecordChecklistServlet"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    // Escape para texto HTML (contenido de celdas) de la lista de verificación.
    // Columna "Ítem" de Documentos del lote: número del ítem R-GC-094 al que pertenece.
    private static String itemCell(Integer item) {
        return item == null ? "<span class='text-muted'>—</span>" : "<span class='ckl-item-badge'>" + item + "</span>";
    }

    private static String itemAttr(Integer item) {
        return item == null ? "" : String.valueOf(item);
    }

    private static String h(Object o) {
        if (o == null) {
            return "";
        }
        return String.valueOf(o).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>

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

            .batch-record-row-selected td { background:#eef2ff !important; }

            .batch-record-pill {
                display:inline-flex; align-items:center; gap:5px; padding:3px 10px;
                border-radius:20px; font-size:11px; font-weight:600;
            }
            .batch-record-pill-fisico { background:#f1f2f4; color:#4b5563; }
            .batch-record-pill-lab { background:#dbeafe; color:#1d4ed8; }
            .batch-record-pill-coa { background:#f3e8ff; color:#7e22ce; }
            .batch-record-pill-lotes { background:#e0f2fe; color:#0369a1; }
            .batch-record-pill-manga { background:#fce7f3; color:#be185d; }
            .batch-record-pill-formula { background:#ccfbf1; color:#0d9488; }
            .batch-record-pill-batchrecord { background:#fee2e2; color:#b91c1c; }

            .batch-record-status { display:inline-flex; align-items:center; gap:5px; font-size:12.5px; font-weight:600; white-space:nowrap; }
            .batch-record-status-na { color:#0d6efd; }
            .batch-record-status-firmado { color:#28a745; }
            .batch-record-status-pendiente { color:#e0a800; }
            .batch-record-status-borrador { color:#8a8f98; }

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

            /* ===== Tarjetas del lote (KPIs) ===== */
            .br-kpis { display:grid; grid-template-columns:repeat(4, minmax(0,1fr)); gap:14px; margin-bottom:18px; }
            .br-kpi {
                display:flex; align-items:center; gap:14px; min-width:0;
                background:#fff; border:1px solid #e3e8f2; border-radius:12px; padding:14px 16px;
                box-shadow:0 1px 3px rgba(16,24,40,.05); color:inherit;
            }
            .br-kpi-icon {
                width:54px; height:54px; border-radius:50%; flex-shrink:0;
                display:flex; align-items:center; justify-content:center; font-size:22px;
            }
            .br-kpi-icon-blue { background:#e3eefe; color:#2f6fd6; }
            .br-kpi-icon-purple { background:#ece9fd; color:#6d5bd0; }
            .br-kpi-icon-green { background:#e3f6e8; color:#2e9d4f; }
            .br-kpi-icon-check { background:#28a745; color:#fff; box-shadow:0 0 0 6px #d6f1dd; }
            .br-kpi-body { min-width:0; flex:1; }
            .br-kpi-label { font-size:12.5px; color:#3b4a6b; font-weight:600; }
            .br-kpi-value { font-size:21px; font-weight:800; color:#0b0025; line-height:1.2; }
            .br-kpi-value-sm { font-size:15px; font-weight:700; margin-top:3px; }
            .br-kpi-sub { font-size:11.5px; color:#5b6478; margin-top:2px; overflow:hidden; text-overflow:ellipsis; }
            .br-kpi-sub b { color:#3b4a6b; font-weight:600; }
            .br-kpi-state { font-size:13px; font-weight:700; margin-left:6px; }
            .br-kpi-events { display:inline-block; margin-top:4px; font-size:11.5px; font-weight:600; color:#a06800; text-decoration:none; }
            .br-kpi-events:hover { color:#7a5000; text-decoration:underline; }
            .br-kpi-progress { height:5px; background:#dfe5dd; border-radius:3px; margin-top:7px; overflow:hidden; }
            .br-kpi-progress > div { height:100%; background:#28a745; border-radius:3px; }
            .br-kpi-link { text-decoration:none !important; transition:box-shadow .15s, transform .15s; }
            .br-kpi-link:hover { box-shadow:0 6px 16px rgba(16,24,40,.10); transform:translateY(-1px); color:inherit; }
            .br-kpi-ok { background:#eefaf1; border-color:#bfe6c8; }
            .br-kpi-ok .br-kpi-state { color:#1f8a3a; }
            .br-kpi-warn { background:#fff8e8; border-color:#f3d48a; }
            .br-kpi-warn .br-kpi-icon-check { background:#e0a800; box-shadow:0 0 0 6px #fbebc0; }
            .br-kpi-warn .br-kpi-state { color:#a06800; }
            .br-kpi-warn .br-kpi-progress { background:#f3e6c4; }
            .br-kpi-warn .br-kpi-progress > div { background:#e0a800; }
            .br-kpi-muted .br-kpi-icon-check { background:#adb5bd; box-shadow:0 0 0 6px #e9ecef; }

            /* ===== Distribución: lista (izquierda) + resumen (derecha) ===== */
            /* La lista y el "Resumen del Lote" quedan de la misma altura: el lateral toma la
               altura del panel de la lista y, si su contenido no cabe, se desplaza por dentro. */
            .br-layout { display:grid; grid-template-columns:minmax(0,1fr) 290px; gap:16px; align-items:stretch; margin-bottom:18px; }
            .br-layout-main { display:flex; flex-direction:column; }
            .br-layout-main > .ckl-panel { flex:1; }
            .br-layout-main { min-width:0; }

            /* ===== Lista de verificación R-GC-094 ===== */
            .ckl-panel { background:#fff; border:1px solid #e3e8f2; border-radius:12px; overflow:hidden; box-shadow:0 1px 3px rgba(16,24,40,.05); }
            .ckl-header {
                display:flex; justify-content:space-between; align-items:center; gap:14px; flex-wrap:wrap;
                padding:16px 20px; color:#1d1b3a;
                background:linear-gradient(90deg, #f3effc 0%, #ece5fb 55%, #e4dafa 100%);
                border-bottom:1px solid #e2d9f6;
            }
            .ckl-header-info { display:flex; align-items:center; gap:14px; min-width:0; }
            .ckl-shield {
                width:46px; height:46px; border-radius:12px; flex-shrink:0; font-size:20px; color:#fff;
                display:flex; align-items:center; justify-content:center;
                background:linear-gradient(135deg, #3ccf68 0%, #1f9d45 100%);
                border:3px solid #a8e6b8; box-shadow:0 2px 6px rgba(31,157,69,.25);
            }
            .ckl-title { font-weight:700; font-size:16.5px; color:#1d1b3a; }
            .ckl-code { display:inline-block; margin-left:10px; padding:3px 11px; border-radius:12px; background:#3a4fd8; color:#fff; font-size:11px; font-weight:700; letter-spacing:.3px; vertical-align:middle; }
            .ckl-subtitle { font-size:12px; color:#1d1b3a; font-weight:600; margin-top:4px; letter-spacing:.3px; text-transform:uppercase; }
            .ckl-header-actions { display:flex; gap:10px; flex-wrap:wrap; }
            .ckl-btn { font-size:13px; font-weight:600; border-radius:7px; padding:8px 16px; border:none; display:inline-flex; align-items:center; gap:8px; }
            .ckl-btn-white { background:#fff; color:#1d1b3a !important; border:1px solid #e3ddf3; box-shadow:0 1px 2px rgba(16,24,40,.06); }
            .ckl-btn-white:hover { background:#f7f5fd; }
            .ckl-btn-indigo { background:#5b5fe0; color:#fff !important; }
            .ckl-btn-indigo:hover { background:#4a4ed0; }
            .ckl-btn-green { background:#22a447; color:#fff !important; }
            .ckl-btn-green:hover { background:#1c8d3c; }
            .ckl-body { padding:12px 14px 4px 14px; }
            .ckl-empty { padding:16px 6px; color:#6c757d; font-size:13px; }
            .ckl-help {
                display:flex; align-items:center; gap:12px; background:#eaf2fd; border:1px solid #d3e3fa; border-radius:8px;
                padding:10px 14px; font-size:13px; color:#2c3a57; margin-bottom:12px;
            }
            .ckl-help-icon { width:28px; height:28px; border-radius:50%; background:#2f6fd6; color:#fff; display:flex; align-items:center; justify-content:center; flex-shrink:0; font-size:13px; }
            .ckl-table-wrap { overflow-x:auto; }
            .ckl-table { width:100%; border-collapse:collapse; }
            .ckl-table thead th { background:#f4f6fa; color:#3b4a6b; font-size:12.5px; font-weight:700; padding:9px 10px; border-bottom:1px solid #e3e8f2; white-space:nowrap; }
            .ckl-table tbody td { font-size:13px; padding:9px 10px; border:none; border-bottom:1px solid #eef1f6; vertical-align:middle; }
            .ckl-table thead th { border-left:none; border-right:none; border-top:none; }
            .ckl-table tbody tr:hover { background:#fafbfe; }
            .ckl-row-pend { background:#fff9ef; }
            .ckl-num { display:inline-flex; align-items:center; justify-content:center; min-width:26px; height:24px; padding:0 6px; border-radius:6px; background:#eef1f8; color:#3b4a6b; font-weight:700; font-size:12px; }
            .ckl-name { font-weight:600; color:#1d2540; }
            .ckl-code-cell { font-weight:600; color:#3b4a6b; white-space:nowrap; }
            .ckl-state { font-weight:700; font-size:13px; white-space:nowrap; }
            .ckl-ok { color:#1f9d45; }
            .ckl-na { color:#6c757d; }
            .ckl-pend { color:#d9534f; }
            .ckl-note { font-size:11px; color:#7b8496; font-weight:normal; margin-top:2px; }
            .ckl-evidence { max-width:280px; }
            .ckl-doc-link { display:flex; align-items:center; gap:7px; font-size:12px; color:#1d2540; text-decoration:none; line-height:1.6; }
            .ckl-doc-link i { color:#3b4a6b; width:14px; text-align:center; flex-shrink:0; }
            .ckl-doc-link span { overflow:hidden; text-overflow:ellipsis; white-space:nowrap; }
            .ckl-doc-link:hover { color:#2f6fd6; text-decoration:none; }
            .ckl-more { display:block; font-size:11px; color:#2f6fd6; margin-top:1px; }
            .ckl-actions { white-space:nowrap; }
            .ckl-act { font-size:12px; font-weight:600; border-radius:6px; padding:3px 10px; margin:2px; }
            .ckl-act-ok { border:1px solid #28a745; color:#1f8a3a; background:#fff; }
            .ckl-act-ok:hover { background:#28a745; color:#fff; }
            .ckl-act-na { border:1px solid #adb5bd; color:#495057; background:#fff; }
            .ckl-act-na:hover { background:#6c757d; color:#fff; }
            .ckl-act-undo { border:1px solid #f1a7ae; color:#d9534f; background:#fff5f5; }
            .ckl-act-undo:hover { background:#d9534f; color:#fff; }
            .ckl-footer { font-size:12px; color:#6c757d; padding:10px 4px; }

            /* ===== Resumen del lote (lateral) ===== */
            .br-side { position:relative; min-height:460px; background:#fff; border:1px solid #e3e8f2; border-radius:12px; box-shadow:0 1px 3px rgba(16,24,40,.05); overflow:hidden; }
            .br-side-scroll { position:absolute; inset:0; padding:16px; overflow-y:auto; overscroll-behavior:contain; scrollbar-width:thin; scrollbar-color:#c9d0de transparent; }
            .br-side::after {
                content:''; position:absolute; left:0; right:8px; bottom:0; height:26px; pointer-events:none;
                background:linear-gradient(rgba(255,255,255,0), #fff); border-radius:0 0 12px 12px;
            }
            .br-side-scroll { padding-bottom:28px; }
            .br-side-scroll::-webkit-scrollbar { width:6px; }
            .br-side-scroll::-webkit-scrollbar-thumb { background:#c9d0de; border-radius:3px; }
            .br-side-title { font-weight:800; font-size:15px; color:#0b0025; display:flex; align-items:center; gap:8px; }
            .br-side-title i { color:#2f6fd6; }
            .br-side-subtitle { font-weight:800; font-size:14px; color:#0b0025; margin:18px 0 10px; }
            .br-donut { position:relative; width:150px; height:150px; margin:14px auto 10px; }
            .br-donut svg { transform:rotate(-90deg); }
            .br-donut-track { fill:none; stroke:#e9edf3; stroke-width:12; }
            .br-donut-bar { fill:none; stroke-width:12; stroke-linecap:round; transition:stroke-dashoffset .6s; }
            .br-donut-ok .br-donut-bar { stroke:#28a745; }
            .br-donut-warn .br-donut-bar { stroke:#e0a800; }
            .br-donut-center { position:absolute; inset:0; display:flex; flex-direction:column; align-items:center; justify-content:center; }
            .br-donut-check { width:26px; height:26px; border-radius:50%; color:#fff; display:flex; align-items:center; justify-content:center; font-size:12px; margin-bottom:2px; }
            .br-donut-ok .br-donut-check { background:#28a745; }
            .br-donut-warn .br-donut-check { background:#e0a800; }
            .br-donut-pct { font-size:26px; font-weight:800; color:#0b0025; line-height:1.1; }
            .br-donut-label { font-size:12px; color:#3b4a6b; font-weight:600; }
            .br-stats { list-style:none; padding:0; margin:6px 0 0; }
            .br-stats li { font-size:13px; color:#3b4a6b; padding:5px 0; display:flex; align-items:center; gap:8px; }
            .br-stats b { color:#0b0025; }
            .br-stat-ok { color:#28a745; font-size:15px; }
            .br-stat-pend { color:#e0a800; font-size:9px; width:15px; text-align:center; }
            .br-stat-muted { color:#c3c9d4; font-size:9px; width:15px; text-align:center; }
            .br-approval { background:#f8f9fc; border:1px solid #e9ecf3; border-radius:8px; padding:10px 12px; }
            .br-side-btn { display:flex; width:100%; align-items:center; justify-content:center; gap:8px; font-size:13px; font-weight:700; border-radius:7px; padding:9px 12px; margin-bottom:9px; }
            .br-side-btn-navy { background:#1b2d6b; color:#fff !important; border:1px solid #1b2d6b; }
            .br-side-btn-navy:hover { background:#13235b; }
            .br-side-btn-green { background:#22a447; color:#fff !important; border:1px solid #22a447; margin-bottom:0; }
            .br-side-btn-green:hover { background:#1c8d3c; }
            .br-side-btn-outline { background:#fff; color:#1b2d6b !important; border:1px solid #d6dce8; }
            .br-side-btn-outline:hover { background:#f2f5fb; }

            /* Tarjeta "Estado del Batch Record" */
            .br-kpi-icon-sign { background:#1b2d6b; color:#fff; box-shadow:0 0 0 6px #dde3f5; font-size:20px; }
            .br-kpi-value-state { font-size:19px; }
            .br-kpi-sign-ok { background:#f1f4fc; border-color:#c9d3ef; }
            .br-kpi-sign-ok .br-kpi-value-state { color:#1f8a3a; }
            .br-kpi-sign-pend { background:#fff8e8; border-color:#f3d48a; }
            .br-kpi-sign-pend .br-kpi-icon-sign { background:#e0a800; box-shadow:0 0 0 6px #fbebc0; }
            .br-kpi-sign-pend .br-kpi-value-state { color:#a06800; }
            .br-kpi-sign-none .br-kpi-icon-sign { background:#adb5bd; box-shadow:0 0 0 6px #e9ecef; }
            .br-kpi-sign-none .br-kpi-value-state { color:#6c757d; }

            /* Lista: barra dividida y desglose (verificados / N/A / pendientes) */
            .br-kpi-split { display:flex; gap:3px; height:5px; margin-top:7px; border-radius:3px; overflow:hidden; }
            .br-kpi-split span { border-radius:3px; }
            .br-split-ok { background:#28a745; } .br-split-na { background:#adb5bd; } .br-split-pend { background:#e0a800; }
            .br-kpi-chips { display:flex; flex-wrap:wrap; gap:4px; margin-top:6px; }
            .br-chip { display:inline-flex; align-items:center; font-size:10.5px; font-weight:700; padding:1px 8px; border-radius:10px; white-space:nowrap; }
            .br-chip-ok { background:#dff3e5; color:#1f8a3a; }
            .br-chip-na { background:#eceef2; color:#5b6478; }
            .br-chip-pend { background:#fff1d6; color:#a06800; }
            .br-kpi-ok .br-kpi-state { color:#1f8a3a; }

            /* Lista en solo lectura (Batch Record aprobado) */
            .ckl-lock { display:flex; align-items:center; gap:12px; background:#eef1fb; border:1px solid #cdd6f1; border-radius:8px; padding:10px 14px; font-size:13px; color:#1b2d6b; margin-bottom:12px; }
            .ckl-lock-icon { width:30px; height:30px; border-radius:50%; background:#1b2d6b; color:#fff; display:flex; align-items:center; justify-content:center; flex-shrink:0; font-size:13px; }
            .ckl-locked { color:#9aa3b5; font-size:13px; }
            .ckl-pend-amber { color:#c98a00; }
            .br-stat-na { color:#adb5bd; font-size:15px; }

            /* Aprobación y versiones del Batch Record */
            .br-approval-ok { background:#eefaf1; border-color:#bfe6c8; }
            .br-approval-pend { background:#fff8e8; border-color:#f3d48a; }
            .br-versions { list-style:none; margin:0; padding:0; }
            .br-versions li { display:flex; gap:10px; padding:9px 0; border-bottom:1px dashed #e6e9f0; }
            .br-versions li:last-child { border-bottom:none; }
            .br-v-dot { width:30px; height:30px; border-radius:8px; display:flex; align-items:center; justify-content:center; flex-shrink:0; font-size:13px; }
            .br-v-ok { background:#dff3e5; color:#1f8a3a; }
            .br-v-pend { background:#fff1d6; color:#a06800; }
            .br-v-draft { background:#eceef2; color:#6c757d; }
            .br-v-old { background:#f4f6fa; color:#a0a6b2; }
            .br-v-body { min-width:0; flex:1; }
            .br-v-name { font-size:12px; font-weight:700; color:#1d2540; line-height:1.4; }
            .br-v-meta { font-size:11px; color:#7b8496; line-height:1.4; }
            .br-v-links { font-size:11px; margin-top:2px; line-height:1.4; }
            .br-v-links a { color:#2f6fd6; margin-right:10px; }
            .br-chip-v-ok { background:#dff3e5; color:#1f8a3a; }
            .br-chip-v-pend { background:#fff1d6; color:#a06800; }
            .br-chip-v-draft { background:#eceef2; color:#6c757d; }
            .br-chip-v-old { background:#f4f6fa; color:#8a8f98; }

            /* Vista del documento en ventana emergente */
            .br-viewer-overlay { display:none; position:fixed; inset:0; background:rgba(11,0,37,.55); z-index:2100; align-items:center; justify-content:center; padding:24px; }
            .br-viewer-overlay.br-viewer-open-state { display:flex; }
            .br-viewer-dialog { background:#fff; border-radius:12px; width:100%; max-width:1100px; height:88vh; display:flex; flex-direction:column; overflow:hidden; box-shadow:0 18px 50px rgba(0,0,0,.35); }
            .br-viewer-head { display:flex; align-items:center; gap:12px; padding:12px 16px; border-bottom:1px solid #e9ecef; }
            .br-viewer-titles { flex:1; min-width:0; }
            .br-viewer-name { font-size:14px; font-weight:800; color:#0b0025; overflow:hidden; text-overflow:ellipsis; white-space:nowrap; }
            .br-viewer-sub { font-size:12px; color:#7b8496; }
            .br-viewer-open { font-weight:700; border:1px solid #d6dce8; color:#1b2d6b; background:#fff; white-space:nowrap; }
            .br-viewer-close { font-size:26px; line-height:1; border:none; background:none; color:#6c757d; }
            .br-viewer-frame { flex:1; width:100%; border:none; background:#f4f6f9; }

            /* Aviso de espera al generar el Batch Record */
            .br-generando { position:fixed; inset:0; z-index:2200; background:rgba(11,0,37,.45); display:flex; align-items:center; justify-content:center; padding:16px; }
            .br-generando-box { display:flex; gap:16px; align-items:center; background:#fff; border-radius:12px; padding:20px 24px; max-width:460px; box-shadow:0 18px 50px rgba(0,0,0,.3); color:#0b0025; font-size:14px; }
            .br-generando-box > i { font-size:28px; color:#5b5fe0; }
            .br-generando-sub { font-size:12.5px; color:#6c757d; margin-top:4px; }

            /* Eventos de integración contraídos */
            .br-events-toggle { display:flex; justify-content:space-between; align-items:center; cursor:pointer; user-select:none; }
            .br-events-link { font-weight:700; font-size:12.5px; }
            .br-events-link i { transition:transform .2s; }
            .batch-record-events:not(.br-events-collapsed) .br-events-link i { transform:rotate(180deg); }
            .br-events-collapsed .br-events-body { display:none; }
            .br-events-collapsed .batch-record-events-header { border-bottom:none; }
            .batch-record-events { background:#fffaf0; border-color:#f3e2b8; }

            @media (max-width: 1200px) {
                .br-kpis { grid-template-columns:repeat(2, minmax(0,1fr)); }
                .br-layout { grid-template-columns:minmax(0,1fr); }
                .br-side { min-height:0; }
                .br-side::after { display:none; }
                .br-side-scroll { position:static; overflow:visible; }
            }
            @media (max-width: 620px) {
                .br-kpis { grid-template-columns:minmax(0,1fr); }
                .ckl-header-actions { width:100%; }
                .ckl-btn { flex:1; justify-content:center; }
            }

            /* "Imprimir reporte": solo la lista de verificación */
            @media print {
                body * { visibility:hidden !important; }
                #checklistRgc094, #checklistRgc094 * { visibility:visible !important; }
                #checklistRgc094 { position:absolute; left:0; top:0; width:100%; border:none; box-shadow:none; }
                .ckl-no-print { display:none !important; }
                .ckl-header { -webkit-print-color-adjust:exact; print-color-adjust:exact; }
            }

            .ckl-item-badge { display:inline-block; min-width:26px; text-align:center; padding:1px 6px; border-radius:10px; background:#eef0fd; color:#4b54c8; font-size:11px; font-weight:700; }
            .ckl-overlay { display:none; position:fixed; inset:0; background:rgba(11,0,37,.45); z-index:2000; align-items:center; justify-content:center; padding:16px; }
            .ckl-overlay.ckl-open { display:flex; }
            .ckl-dialog { background:#fff; border-radius:8px; width:100%; max-width:520px; box-shadow:0 10px 30px rgba(0,0,0,.25); }
            .ckl-dialog-header { display:flex; justify-content:space-between; align-items:center; padding:14px 18px; border-bottom:1px solid #e9ecef; }
            .ckl-dialog-header h5 { margin:0; font-size:16px; color:#0b0025; }
            .ckl-dialog-body { padding:16px 18px; }
            .ckl-dialog-item { font-weight:600; color:#495057; margin-bottom:12px; }
            .ckl-dialog-footer { display:flex; justify-content:flex-end; gap:8px; padding:12px 18px; border-top:1px solid #e9ecef; }
        </style>
    </head>

    <body class="bg-light sidebar-mini">

        <jsp:include page="Menu.jsp" />

        <div class="main-content">
            <section class="section">


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
                                                List<CertificateFileRow> batchRecords = certController.consultFilesByLote(cliente, anio, orden, lote, "BatchRecord");

                                                long ultimaActualizacionMillis = 0L;
                                                if (archivos != null) {
                                                    for (CertificateFileRow f : archivos) {
                                                        if (f.lastModified() > ultimaActualizacionMillis) {
                                                            ultimaActualizacionMillis = f.lastModified();
                                                        }
                                                    }
                                                }
                                                if (batchRecords != null) {
                                                    for (CertificateFileRow f : batchRecords) {
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

                                                // Lista de verificación R-GC-094 (requerimiento 1.1.U04).
                                                BatchRecordChecklist.Resultado checklistLote = null;
                                                try {
                                                    checklistLote = BatchRecordChecklist.evaluar(cliente, anio, orden, lote);
                                                } catch (Exception exChecklist) {
                                                    eventosListado.add("Lista de verificación R-GC-094: no se pudo evaluar (" + exChecklist.getMessage() + ").");
                                                }
                                                boolean puedeMarcarChecklist = BatchRecordChecklistServlet.puedeMarcar(Permission);
                                                int checklistPendientes = (checklistLote != null && checklistLote.aplica) ? checklistLote.pendientes : 0;
                                                // Ítem de la lista al que pertenece cada documento: ordena las filas
                                                // de "Documentos del lote" igual que el PDF unificado.
                                                Map<String, Integer> itemPorDocumento = BatchRecordChecklist.indicePorDocumento(checklistLote);

                                                // Versiones del Batch Record y estado vigente del lote (Aprobado /
                                                // Pendiente de firma / Borrador / Sin generar). Si está aprobado, la
                                                // lista de verificación queda en solo lectura.
                                                BatchRecordVersiones.Estado versionesBr = BatchRecordVersiones.consultar(batchRecords, checklistLote != null && checklistLote.aplica);
                                                BatchRecordVersiones.Version brVigente = versionesBr.vigente;
                                                boolean checklistBloqueada = versionesBr.isAprobado();
                                                boolean puedeEditarChecklist = puedeMarcarChecklist && !checklistBloqueada;
                                    %>



                                    <%
                                        // Conteos de la lista para las tarjetas y el resumen lateral.
                                        boolean cklAplica = checklistLote != null && checklistLote.aplica;
                                        int cklTotal = 0, cklOk = 0, cklNa = 0, cklPend = 0, cklPct = 0;
                                        if (cklAplica) {
                                            cklTotal = checklistLote.total;
                                            for (BatchRecordChecklist.Item it : checklistLote.items) {
                                                if (BatchRecordChecklist.ESTADO_NA.equals(it.estado)) {
                                                    cklNa++;
                                                } else if (it.isPendiente()) {
                                                    cklPend++;
                                                } else {
                                                    cklOk++;
                                                }
                                            }
                                            cklPct = cklTotal == 0 ? 0 : (checklistLote.verificados * 100 / cklTotal);
                                        }
                                        String cklKpiClase = !cklAplica ? "br-kpi-muted" : (checklistLote.isCompleta() ? "br-kpi-ok" : "br-kpi-warn");
                                        String estadoBr = versionesBr.getEstado();
                                        String brKpiClase = BatchRecordVersiones.APROBADO.equals(estadoBr) ? "br-kpi-sign-ok"
                                                : BatchRecordVersiones.PENDIENTE.equals(estadoBr) ? "br-kpi-sign-pend" : "br-kpi-sign-none";
                                        String urlGenerar = "generarBatchRecordPdfUnificado('" + Util.jsAttr(cliente) + "', '" + Util.jsAttr(anio) + "', '"
                                                + Util.jsAttr(orden) + "', '" + Util.jsAttr(lote) + "', " + checklistPendientes + ")";
                                    %>

                                    <script>window.brLoteAprobado = <%= checklistBloqueada%>;</script>

                                    <!-- ================== BATCH RECORD: TARJETAS DEL LOTE ================== -->
                                    <div class="br-kpis">
                                        <div class="br-kpi">
                                            <div class="br-kpi-icon br-kpi-icon-blue"><i class="fas fa-flask"></i></div>
                                            <div class="br-kpi-body">
                                                <div class="br-kpi-label">Lote</div>
                                                <div class="br-kpi-value"><%= h(lote)%></div>
                                                <div class="br-kpi-sub">Orden <b><%= h(orden)%></b> · <b><%= h(cliente)%></b> · <b><%= h(anio)%></b></div>
                                                <div class="br-kpi-sub"><i class="far fa-clock"></i> Actualizado <%= ultimaActualizacionTexto%></div>
                                            </div>
                                        </div>
                                        <a class="br-kpi br-kpi-link <%= brKpiClase%>" href="#brAprobacion" title="Ver aprobación y versiones del Batch Record">
                                            <div class="br-kpi-icon br-kpi-icon-sign"><i class="fas fa-signature"></i></div>
                                            <div class="br-kpi-body">
                                                <div class="br-kpi-label">Estado del Batch Record</div>
                                                <% if (BatchRecordVersiones.APROBADO.equals(estadoBr)) {%>
                                                <div class="br-kpi-value br-kpi-value-state">Aprobado</div>
                                                <div class="br-kpi-sub"><%= h(brVigente.firmadoPor)%><%= brVigente.cargo.isEmpty() ? "" : " · " + h(brVigente.cargo)%></div>
                                                <div class="br-kpi-sub"><%= h(brVigente.firmadoEl)%></div>
                                                <% } else if (BatchRecordVersiones.PENDIENTE.equals(estadoBr)) {%>
                                                <div class="br-kpi-value br-kpi-value-state">Pendiente de firma</div>
                                                <div class="br-kpi-sub">Generado <%= h(brVigente.generado)%></div>
                                                <% } else if (BatchRecordVersiones.BORRADOR.equals(estadoBr)) {%>
                                                <div class="br-kpi-value br-kpi-value-state">Borrador</div>
                                                <div class="br-kpi-sub"><%= !brVigente.lista.isEmpty() ? "Generado con la lista incompleta (" + h(brVigente.lista) + ")"
                                                        : BatchRecordChecklist.esBorrador(brVigente.archivo.getName()) ? "Generado con la lista incompleta"
                                                        : "Generado antes de la lista de verificación"%></div>
                                                <% } else {%>
                                                <div class="br-kpi-value br-kpi-value-state">Sin generar</div>
                                                <div class="br-kpi-sub">Aún no se ha generado el Batch Record</div>
                                                <% } %>
                                            </div>
                                        </a>
                                        <div class="br-kpi">
                                            <div class="br-kpi-icon br-kpi-icon-green"><i class="fas fa-folder-open"></i></div>
                                            <div class="br-kpi-body">
                                                <div class="br-kpi-label">Total documentos</div>
                                                <div class="br-kpi-value" id="brTotalDocs">0</div>
                                                <% if (!eventosListado.isEmpty()) {%>
                                                <a href="#batchRecordEventos" class="br-kpi-events" onclick="brAbrirEventos()"><i class="fas fa-exclamation-triangle"></i> <%= eventosListado.size()%> evento<%= eventosListado.size() == 1 ? "" : "s"%> de integración</a>
                                                <% } %>
                                            </div>
                                        </div>
                                        <a class="br-kpi br-kpi-link <%= cklKpiClase%>" href="#checklistRgc094" title="Ir a la lista de verificación">
                                            <div class="br-kpi-icon br-kpi-icon-check"><i class="fas <%= cklAplica && !checklistLote.isCompleta() ? "fa-hourglass-half" : "fa-check"%>"></i></div>
                                            <div class="br-kpi-body">
                                                <% if (cklAplica) {%>
                                                <div class="br-kpi-value"><%= checklistLote.verificados%> / <%= cklTotal%>
                                                    <span class="br-kpi-state"><%= checklistLote.isCompleta() ? "resueltos" : checklistPendientes + " pendiente" + (checklistPendientes == 1 ? "" : "s")%></span>
                                                </div>
                                                <div class="br-kpi-sub">Lista de verificación <%= h(checklistLote.codigoRegistro)%></div>
                                                <div class="br-kpi-split">
                                                    <% if (cklOk > 0) {%><span class="br-split-ok" style="flex:<%= cklOk%>"></span><% } %>
                                                    <% if (cklNa > 0) {%><span class="br-split-na" style="flex:<%= cklNa%>"></span><% } %>
                                                    <% if (cklPend > 0) {%><span class="br-split-pend" style="flex:<%= cklPend%>"></span><% } %>
                                                </div>
                                                <div class="br-kpi-chips"><span class="br-chip br-chip-ok"><%= cklOk%> verificados</span><span class="br-chip br-chip-na"><%= cklNa%> N/A</span><span class="br-chip br-chip-pend"><%= cklPend%> pendientes</span></div>
                                                <% } else {%>
                                                <div class="br-kpi-label">Lista de verificación R-GC-094</div>
                                                <div class="br-kpi-sub">Sin sección configurada para este lote</div>
                                                <% } %>
                                            </div>
                                        </a>
                                    </div>

                                    <div class="br-layout">
                                        <div class="br-layout-main">
                                            <!-- ================== LISTA DE VERIFICACIÓN R-GC-094 (req. 1.1.U04) ================== -->
                                            <div class="ckl-panel" id="checklistRgc094">
                                                <div class="ckl-header">
                                                    <div class="ckl-header-info">
                                                        <span class="ckl-shield"><i class="fas fa-clipboard-check"></i></span>
                                                        <div class="ckl-header-text">
                                                            <div class="ckl-title">Lista de verificación de documentos del lote
                                                                <% if (cklAplica) {%><span class="ckl-code"><%= h(checklistLote.codigoRegistro)%> V<%= checklistLote.version%></span><% } %>
                                                            </div>
                                                            <% if (cklAplica) {%><div class="ckl-subtitle"><%= h(checklistLote.titulo)%></div><% } %>
                                                        </div>
                                                    </div>
                                                    <div class="ckl-header-actions ckl-no-print">
                                                        <% if (cklAplica) {%>
                                                        <a class="btn ckl-btn ckl-btn-white" target="_blank" href="BatchRecordChecklistPdfServlet?cliente=<%= java.net.URLEncoder.encode(cliente, "UTF-8")%>&anio=<%= java.net.URLEncoder.encode(anio, "UTF-8")%>&orden=<%= java.net.URLEncoder.encode(orden, "UTF-8")%>&lote=<%= java.net.URLEncoder.encode(lote, "UTF-8")%>"><i class="fas fa-download"></i> Exportar PDF</a>
                                                        <% } %>
                                                        <button type="button" class="btn ckl-btn ckl-btn-indigo" onclick="<%= urlGenerar%>"><i class="fas fa-file-pdf"></i> Generar Batch Record</button>
                                                        <% if (Permission.contains("[3]")) { %>
                                                        <button type="button" class="btn ckl-btn ckl-btn-green" data-bs-toggle="modal" data-bs-target="#uploadModal"><i class="fas fa-cloud-upload-alt"></i> Subir archivos</button>
                                                        <% } %>
                                                    </div>
                                                </div>
                                                <div class="ckl-body">
                                                    <% if (checklistLote == null) { %>
                                                    <div class="ckl-empty"><i class="fas fa-exclamation-triangle text-warning"></i> No se pudo evaluar la lista de verificación de este lote. Ver "Eventos de integración".</div>
                                                    <% } else if (!checklistLote.aplica) {%>
                                                    <div class="ckl-empty"><i class="fas fa-info-circle"></i> <%= h(checklistLote.diagnostico)%></div>
                                                    <% } else { %>
                                                    <% if (checklistBloqueada) {%>
                                                    <div class="ckl-lock">
                                                        <span class="ckl-lock-icon"><i class="fas fa-lock"></i></span>
                                                        <div><b>Lista en solo lectura.</b> El Batch Record fue aprobado por <%= h(brVigente.firmadoPor)%> el <%= h(brVigente.firmadoEl)%>.
                                                            Para corregir un ítem, genere un nuevo Batch Record: la lista se habilitará y requerirá una nueva firma.</div>
                                                    </div>
                                                    <% } else {%>
                                                    <div class="ckl-help">
                                                        <span class="ckl-help-icon"><i class="fas fa-info"></i></span>
                                                        <div>
                                                            Los ítems con <i class="fas fa-check-circle ckl-ok"></i> los trae el sistema. Para los pendientes adjunte el documento y confirme, o márquelos N/A.<br>
                                                            El Batch Record se puede generar con pendientes (borrador), pero no se puede aprobar hasta completar la lista.
                                                        </div>
                                                    </div>
                                                    <% } %>
                                                    <div class="ckl-table-wrap">
                                                        <table class="ckl-table">
                                                            <thead>
                                                                <tr>
                                                                    <th class="text-center" width="44">#</th>
                                                                    <th>Nombre del registro</th>
                                                                    <th width="100">Código</th>
                                                                    <th>Verificación</th>
                                                                    <th>Evidencia</th>
                                                                    <% if (puedeMarcarChecklist) { %><th class="text-center ckl-no-print" width="<%= checklistBloqueada ? 80 : 170%>">Acciones</th><% } %>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <% for (BatchRecordChecklist.Item item : checklistLote.items) {
                                                                        String estadoClase;
                                                                        String estadoIcono;
                                                                        String estadoTexto;
                                                                        if (BatchRecordChecklist.ESTADO_SISTEMA.equals(item.estado)) {
                                                                            estadoClase = "ckl-ok";
                                                                            estadoIcono = "fa-check-circle";
                                                                            estadoTexto = "Verificado por el sistema";
                                                                        } else if (BatchRecordChecklist.ESTADO_VERIFICADO.equals(item.estado)) {
                                                                            estadoClase = "ckl-ok";
                                                                            estadoIcono = "fa-check-circle";
                                                                            estadoTexto = "Verificado";
                                                                        } else if (BatchRecordChecklist.ESTADO_NA.equals(item.estado)) {
                                                                            estadoClase = "ckl-na";
                                                                            estadoIcono = "fa-minus-circle";
                                                                            estadoTexto = "N/A";
                                                                        } else {
                                                                            estadoClase = "ckl-pend";
                                                                            estadoIcono = "fa-times-circle";
                                                                            estadoTexto = "Pendiente";
                                                                        }
                                                                        String detalleItem = BatchRecordChecklist.ESTADO_SISTEMA.equals(item.estado) || item.isPendiente()
                                                                                ? item.detalle
                                                                                : (item.usuario + " · " + item.fecha + (item.observacion.isEmpty() ? "" : " · " + item.observacion));
                                                                        String etiquetaItem = item.numero + ". " + item.nombre + " (" + item.codigoRegistro + ")";
                                                                %>
                                                                <tr class="<%= item.isPendiente() ? "ckl-row-pend" : ""%>">
                                                                    <td class="text-center"><span class="ckl-num"><%= item.numero%></span></td>
                                                                    <td>
                                                                        <div class="ckl-name"><%= h(item.nombre)%></div>
                                                                        <% if (!item.nota.isEmpty()) {%><div class="ckl-note"><%= h(item.nota)%></div><% } %>
                                                                    </td>
                                                                    <td class="ckl-code-cell"><%= h(item.codigoRegistro)%></td>
                                                                    <td>
                                                                        <div class="ckl-state <%= estadoClase%>"><i class="fas <%= estadoIcono%>"></i> <%= estadoTexto%></div>
                                                                        <% if (detalleItem != null && !detalleItem.trim().isEmpty()) {%><div class="ckl-note"><%= h(detalleItem)%></div><% } %>
                                                                    </td>
                                                                    <td class="ckl-evidence">
                                                                        <% int maxLinks = 3;
                                                                            for (int d = 0; d < item.documentos.size() && d < maxLinks; d++) {
                                                                                Map<String, Object> docItem = item.documentos.get(d);
                                                                                Object urlDoc = docItem.get("url");
                                                                                if (urlDoc == null) {
                                                                                    continue;
                                                                                }
                                                                                String nombreDoc = String.valueOf(docItem.get("nombre"));
                                                                                boolean esImagen = nombreDoc.toLowerCase().matches(".*\\.(png|jpe?g|gif)$");
                                                                        %>
                                                                        <a class="ckl-doc-link" href="<%= h(urlDoc)%>" target="_blank" title="<%= h(nombreDoc)%>" onclick="brVerEnPanel(this, '<%= Util.jsAttr(nombreDoc)%>', '<%= Util.jsAttr(docItem.get("origen"))%>', '<%= Util.jsAttr(urlDoc)%>'); return false;"><i class="<%= esImagen ? "far fa-image" : "fas fa-external-link-alt"%>"></i> <span><%= h(nombreDoc)%></span></a>
                                                                        <% }
                                                                            if (item.documentos.size() > maxLinks) {%>
                                                                        <a class="ckl-more" href="#batchRecordDocs">+ <%= item.documentos.size() - maxLinks%> más en "Documentos del lote"</a>
                                                                        <% }
                                                                            if (item.documentos.isEmpty()) { %>
                                                                        <span class="text-muted">—</span>
                                                                        <% } %>
                                                                    </td>
                                                                    <% if (puedeMarcarChecklist) { %>
                                                                    <td class="text-center ckl-actions ckl-no-print">
                                                                        <% if (checklistBloqueada) {
                                                                                if (item.marcaManual || item.isPendiente()) {%>
                                                                        <span class="ckl-locked" title="Lista aprobada: genere un nuevo Batch Record para modificarla"><i class="fas fa-lock"></i></span>
                                                                        <% }
                                                                        } else if (item.isPendiente()) {%>
                                                                        <button type="button" class="btn btn-sm ckl-act ckl-act-ok" onclick="cklAbrir('confirmar', '<%= Util.jsAttr(item.key)%>', '<%= Util.jsAttr(etiquetaItem)%>')"><i class="fas fa-check"></i> Confirmar</button>
                                                                        <button type="button" class="btn btn-sm ckl-act ckl-act-na" onclick="cklAbrir('na', '<%= Util.jsAttr(item.key)%>', '<%= Util.jsAttr(etiquetaItem)%>')">N/A</button>
                                                                        <% } else if (item.marcaManual && !BatchRecordChecklist.ESTADO_SISTEMA.equals(item.estado)) {%>
                                                                        <button type="button" class="btn btn-sm ckl-act ckl-act-undo" onclick="cklDeshacer('<%= Util.jsAttr(item.key)%>', '<%= Util.jsAttr(item.numero + ". " + item.nombre)%>')"><i class="fas fa-undo"></i> Deshacer</button>
                                                                        <% } %>
                                                                    </td>
                                                                    <% } %>
                                                                </tr>
                                                                <% } %>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="ckl-footer"><i class="fas fa-info-circle"></i> <%= cklTotal%> registro<%= cklTotal == 1 ? "" : "s"%></div>
                                                    <% } %>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- ================== RESUMEN DEL LOTE (lateral) ================== -->
                                        <aside class="br-side ckl-no-print">
                                            <div class="br-side-scroll">
                                            <div class="br-side-title"><i class="fas fa-chart-bar"></i> Resumen del Lote</div>
                                            <% if (cklAplica) {
                                                    double circunferencia = 2 * Math.PI * 58;
                                                    double desplazamiento = circunferencia * (1 - cklPct / 100.0);%>
                                            <div class="br-donut <%= checklistLote.isCompleta() ? "br-donut-ok" : "br-donut-warn"%>">
                                                <svg viewBox="0 0 140 140" width="140" height="140" aria-hidden="true">
                                                    <circle cx="70" cy="70" r="58" class="br-donut-track"></circle>
                                                    <circle cx="70" cy="70" r="58" class="br-donut-bar"
                                                            stroke-dasharray="<%= String.format(java.util.Locale.US, "%.2f", circunferencia)%>"
                                                            stroke-dashoffset="<%= String.format(java.util.Locale.US, "%.2f", desplazamiento)%>"></circle>
                                                </svg>
                                                <div class="br-donut-center">
                                                    <span class="br-donut-check"><i class="fas <%= checklistLote.isCompleta() ? "fa-check" : "fa-hourglass-half"%>"></i></span>
                                                    <div class="br-donut-pct"><%= cklPct%>%</div>
                                                    <div class="br-donut-label"><%= checklistLote.isCompleta() ? "Resuelto" : "En progreso"%></div>
                                                </div>
                                            </div>
                                            <ul class="br-stats">
                                                <li><i class="fas fa-check-circle br-stat-ok"></i> <b><%= cklOk%></b> Verificados</li>
                                                <li><i class="fas fa-minus-circle br-stat-na"></i> <b><%= cklNa%></b> No aplica (N/A)</li>
                                                <li><i class="fas fa-circle <%= cklPend > 0 ? "br-stat-pend" : "br-stat-muted"%>"></i> <b><%= cklPend%></b> Pendientes</li>
                                            </ul>
                                            <% } else {%>
                                            <div class="ckl-note mt-2">Este lote no tiene una sección de la lista de verificación R-GC-094.</div>
                                            <% } %>

                                            <!-- APROBADO: firma de la Coordinadora Documental ([41]) sobre el Batch Record vigente. -->
                                            <div class="br-side-subtitle" id="brAprobacion">Aprobación</div>
                                            <% if (BatchRecordVersiones.APROBADO.equals(estadoBr)) {%>
                                            <div class="br-approval br-approval-ok">
                                                <div class="ckl-state ckl-ok"><i class="fas fa-signature"></i> Aprobado</div>
                                                <div class="ckl-note">Por <b><%= h(brVigente.firmadoPor)%></b> el <%= h(brVigente.firmadoEl)%></div>
                                            </div>
                                            <% } else if (cklAplica && !checklistLote.isCompleta()) {%>
                                            <div class="br-approval">
                                                <div class="ckl-state ckl-pend"><i class="fas fa-lock"></i> Bloqueada</div>
                                                <div class="ckl-note">Complete o marque N/A <%= checklistPendientes == 1 ? "el ítem pendiente" : "los " + checklistPendientes + " ítems pendientes"%> para poder aprobar.</div>
                                            </div>
                                            <% } else if (brVigente == null) {%>
                                            <div class="br-approval">
                                                <div class="ckl-state ckl-na"><i class="fas fa-file-pdf"></i> Lista completa</div>
                                                <div class="ckl-note">Genere el Batch Record para firmar la aprobación.</div>
                                                <button type="button" class="btn btn-sm br-side-btn br-side-btn-green mt-2" onclick="<%= urlGenerar%>"><i class="fas fa-file-pdf"></i> Generar Batch Record</button>
                                            </div>
                                            <% } else if (!brVigente.firmable) {%>
                                            <div class="br-approval">
                                                <div class="ckl-state ckl-na"><i class="fas fa-pencil-alt"></i> Regenere el PDF</div>
                                                <div class="ckl-note">El último Batch Record se generó con la lista incompleta y quedó como borrador. Genérelo de nuevo para poder firmarlo.</div>
                                                <button type="button" class="btn btn-sm br-side-btn br-side-btn-green mt-2" onclick="<%= urlGenerar%>"><i class="fas fa-file-pdf"></i> Generar de nuevo</button>
                                            </div>
                                            <% } else if (Permission.contains("[41]")) {%>
                                            <div class="br-approval br-approval-pend">
                                                <div class="ckl-state ckl-pend-amber"><i class="fas fa-hourglass-half"></i> Pendiente de su firma</div>
                                                <div class="ckl-note">Batch Record generado el <%= h(brVigente.generado)%>.</div>
                                                <button type="button" class="btn btn-sm br-side-btn br-side-btn-green mt-2" onclick="confirmFirmarBatchRecord('<%= Util.jsAttr(cliente)%>', '<%= Util.jsAttr(anio)%>', '<%= Util.jsAttr(orden)%>', '<%= Util.jsAttr(lote)%>', '<%= brVigente.archivo.getId()%>')"><i class="fas fa-signature"></i> Firmar / Aprobar</button>
                                            </div>
                                            <% } else {%>
                                            <div class="br-approval br-approval-pend">
                                                <div class="ckl-state ckl-pend-amber"><i class="fas fa-hourglass-half"></i> Pendiente de aprobación</div>
                                                <div class="ckl-note">La firma la realiza la Coordinadora Documental.</div>
                                            </div>
                                            <% } %>

                                            <!-- Versiones del Batch Record (la más reciente es la vigente). -->
                                            <div class="br-side-subtitle">Batch Record generados</div>
                                            <% if (versionesBr.versiones.isEmpty()) {%>
                                            <div class="ckl-note">Aún no se ha generado ningún Batch Record para este lote.</div>
                                            <% } else { %>
                                            <ul class="br-versions">
                                                <% int maxVersiones = 4;
                                                    for (int vi = 0; vi < versionesBr.versiones.size() && vi < maxVersiones; vi++) {
                                                        BatchRecordVersiones.Version ver = versionesBr.versiones.get(vi);
                                                        String vClase, vIcono, vChip, vMeta;
                                                        if (BatchRecordVersiones.APROBADO.equals(ver.estado)) {
                                                            vClase = "ok"; vIcono = "fa-check"; vChip = "Aprobado";
                                                            vMeta = "Generado " + ver.generado + " · firmado " + ver.firmadoEl + " por " + ver.firmadoPor;
                                                        } else if (BatchRecordVersiones.PENDIENTE.equals(ver.estado)) {
                                                            vClase = "pend"; vIcono = "fa-hourglass-half"; vChip = "Pendiente de firma";
                                                            vMeta = "Generado " + ver.generado + (ver.lista.isEmpty() ? "" : " · lista " + ver.lista);
                                                        } else if (BatchRecordVersiones.BORRADOR.equals(ver.estado)) {
                                                            vClase = "draft"; vIcono = "fa-pencil-alt"; vChip = "Borrador";
                                                            vMeta = "Generado " + ver.generado
                                                                    + (!ver.lista.isEmpty() ? " · lista incompleta (" + ver.lista + ")"
                                                                    : BatchRecordChecklist.esBorrador(ver.archivo.getName()) ? " · lista incompleta"
                                                                    : " · anterior a la lista de verificación") + " · no firmable";
                                                        } else {
                                                            vClase = "old"; vIcono = "fa-archive"; vChip = "Reemplazado";
                                                            vMeta = ver.archivo.getName().contains("_FIRMADO_")
                                                                    ? "Aprobado " + ver.firmadoEl + " por " + ver.firmadoPor
                                                                    : "Generado " + ver.generado;
                                                        }
                                                        String urlVer = "FileDownloadProxyServlet?id=" + ver.archivo.getId() + "&modo=inline";
                                                %>
                                                <li>
                                                    <span class="br-v-dot br-v-<%= vClase%>"><i class="fas <%= vIcono%>"></i></span>
                                                    <div class="br-v-body">
                                                        <div class="br-v-name"><%= h(ver.getEtiqueta())%> <span class="br-chip br-chip-v-<%= vClase%>"><%= vChip%></span></div>
                                                        <div class="br-v-meta"><%= h(vMeta)%></div>
                                                        <div class="br-v-links">
                                                            <a href="<%= urlVer%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ver.archivo.getName())%>', 'Batch Record PDF Unificado', '<%= urlVer%>'); return false;"><i class="far fa-eye"></i> Ver</a>
                                                            <a href="#" onclick="abrirHistorialFirmas('BATCH_RECORD', '<%= ver.archivo.getId()%>'); return false;"><i class="fas fa-history"></i> Historial</a>
                                                        </div>
                                                    </div>
                                                </li>
                                                <% } %>
                                            </ul>
                                            <% if (versionesBr.versiones.size() > maxVersiones) {%>
                                            <a class="ckl-more" href="#batchRecordDocs" onclick="brFiltrarCategoria('batchrecord');">+ <%= versionesBr.versiones.size() - maxVersiones%> anteriores en la pestaña "Batch Record"</a>
                                            <% } %>
                                            <% } %>

                                            <% if (cklAplica) { %>
                                            <div class="br-side-subtitle">Reporte</div>
                                            <button type="button" class="btn br-side-btn br-side-btn-outline" onclick="cklImprimir()"><i class="fas fa-print"></i> Imprimir lista de verificación</button>
                                            <% } %>
                                            </div>
                                        </aside>
                                    </div>

                                    <% if (puedeEditarChecklist && checklistLote != null && checklistLote.aplica) {%>
                                    <!-- Ventana para confirmar / marcar N/A un ítem (sin depender del modal de Bootstrap) -->
                                    <div class="ckl-overlay" id="cklOverlay" onclick="if (event.target === this) cklCerrar();">
                                        <div class="ckl-dialog" role="dialog" aria-modal="true" aria-labelledby="cklDialogTitle">
                                            <form id="cklForm" action="BatchRecordChecklistServlet" method="post" enctype="multipart/form-data">
                                                <div class="ckl-dialog-header">
                                                    <h5 id="cklDialogTitle">Confirmar ítem</h5>
                                                    <button type="button" class="close" onclick="cklCerrar()"><span>&times;</span></button>
                                                </div>
                                                <div class="ckl-dialog-body">
                                                    <input type="hidden" name="cliente" value="<%= h(cliente)%>">
                                                    <input type="hidden" name="anio" value="<%= h(anio)%>">
                                                    <input type="hidden" name="orden" value="<%= h(orden)%>">
                                                    <input type="hidden" name="lote" value="<%= h(lote)%>">
                                                    <input type="hidden" name="accion" id="cklAccion" value="confirmar">
                                                    <input type="hidden" name="itemKey" id="cklItemKey" value="">
                                                    <p class="ckl-dialog-item" id="cklDialogItem"></p>
                                                    <div class="form-group" id="cklFileGroup">
                                                        <label for="cklFile">Documento (opcional)</label>
                                                        <input type="file" id="cklFile" name="file" class="form-control" accept=".pdf,.png,.jpg,.jpeg,.gif">
                                                        <small class="form-text text-muted">Solo pdf, png, jpg, jpeg, gif. Queda en los archivos físicos del lote y entra al Batch Record unificado.</small>
                                                    </div>
                                                    <div class="form-group mb-0">
                                                        <label for="cklObservacion" id="cklObsLabel">Observación (opcional)</label>
                                                        <textarea id="cklObservacion" name="observacion" class="form-control" rows="2" maxlength="500"></textarea>
                                                    </div>
                                                </div>
                                                <div class="ckl-dialog-footer">
                                                    <button type="button" class="btn btn-secondary" onclick="cklCerrar()">Cancelar</button>
                                                    <button type="submit" class="btn btn-green" id="cklSubmit"><i class="fas fa-check"></i> Confirmar</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <% } %>

                                    <!-- ================== BATCH RECORD: TABS DE CATEGORÍA ================== -->
                                    <ul class="nav nav-tabs batch-record-tabs" id="batchRecordTabs">
                                        <li class="nav-item"><a class="nav-link active" href="#" data-br-tab="todos" onclick="brFiltrarCategoria('todos'); return false;">Todos <span class="batch-record-tab-count" id="brCountTodos">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="fisico" onclick="brFiltrarCategoria('fisico'); return false;"><i class="fas fa-file"></i> Archivos físicos <span class="batch-record-tab-count" id="brCountFisico">0</span></a></li>
                                        <li class="nav-item"><a class="nav-link" href="#" data-br-tab="batchrecord" onclick="brFiltrarCategoria('batchrecord'); return false;"><i class="fas fa-file-pdf"></i> Batch Record <span class="batch-record-tab-count" id="brCountBatchRecord">0</span></a></li>
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
                                    <div class="batch-record-main" id="batchRecordDocs">
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
                                                        <a class="dropdown-item" href="#" onclick="brOrdenar('checklist'); return false;">Lista de verificación</a>
                                                        <a class="dropdown-item" href="#" onclick="brOrdenar('fecha'); return false;">Más reciente</a>
                                                        <a class="dropdown-item" href="#" onclick="brOrdenar('nombre'); return false;">Nombre A-Z</a>
                                                    </div>
                                                </div>
                                            </div>
                                    <div class="batch-record-table-scroll" style="overflow-x:auto;">
                                    <table class="table table-hover table-files mb-0">
                                        <thead>
                                            <tr>
                                                <th width="56" class="text-center" title="Ítem de la lista de verificación R-GC-094">Ítem</th>
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
                                            <tr class="file-row batch-record-row" data-category="fisico" data-status="na" data-timestamp="<%= archivo.lastModified()%>" data-item="<%= itemAttr(itemPorDocumento.get(BatchRecordChecklist.claveArchivo(archivo.getId())))%>">
                                                <td class="text-center"><%= itemCell(itemPorDocumento.get(BatchRecordChecklist.claveArchivo(archivo.getId())))%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(archivo.getName())%>', 'Archivo Físico', '<%= Util.jsAttr(relPath)%>')"><span class="batch-record-ext-chip <%= archivoExtClass%>"><%= archivoExtLabel%></span> <%= archivo.getName()%></td>
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
                                                            <a class="dropdown-item" href="<%= relPath%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(archivo.getName())%>', 'Archivo Físico', '<%= Util.jsAttr(relPath)%>'); return false;"><i class="fas fa-eye"></i> Ver original</a>
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
                                                if (batchRecords != null && !batchRecords.isEmpty()) {
                                                    for (CertificateFileRow batchRecord : batchRecords) {
                                                        String relPathBr = "FileDownloadProxyServlet?id=" + batchRecord.getId() + "&modo=inline";
                                                        String relPathBrDescarga = "FileDownloadProxyServlet?id=" + batchRecord.getId() + "&modo=adjunto";
                                                        String fechaBr = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(batchRecord.lastModified()));
                                                        String generadoPor = batchRecord.getUploadedByName() != null ? batchRecord.getUploadedByName() : "—";
                                                        // Aprobación documental (Coordinadora Documental, permiso [41]):
                                                        // BatchRecordSignServlet reemplaza el PDF por uno "_FIRMADO_".
                                                        boolean brAprobado = batchRecord.getName().contains("_FIRMADO_");
                                                        // Estado de la versión: Aprobado (vigente firmada), Pendiente de firma (vigente
                                                        // firmable), Borrador (lista incompleta) o Reemplazado (hay otra más reciente).
                                                        BatchRecordVersiones.Version brVersion = versionesBr.buscar(batchRecord.getId());
                                                        String brEstado = brVersion != null ? brVersion.estado : (brAprobado ? BatchRecordVersiones.REEMPLAZADO : BatchRecordVersiones.BORRADOR);
                                                        boolean brFirmable = brVersion != null && brVersion.firmable;
                                            %>
                                            <tr class="file-row batch-record-row" data-category="batchrecord" data-status="<%= brAprobado ? "firmado" : "pendiente"%>" data-timestamp="<%= batchRecord.lastModified()%>" data-item="<%= itemAttr(null)%>">
                                                <td class="text-center"><%= itemCell(null)%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(batchRecord.getName())%>', 'Batch Record PDF Unificado', '<%= Util.jsAttr(relPathBr)%>')"><span class="batch-record-ext-chip batch-record-ext-pdf">PDF</span> <%= batchRecord.getName()%></td>
                                                <td><span class="batch-record-pill batch-record-pill-batchrecord">Batch Record</span></td>
                                                <% if (BatchRecordVersiones.APROBADO.equals(brEstado)) { %>
                                                <td><span class="batch-record-status batch-record-status-firmado"><i class="fas fa-check-circle"></i> Aprobado</span></td>
                                                <% } else if (BatchRecordVersiones.PENDIENTE.equals(brEstado)) { %>
                                                <td><span class="batch-record-status batch-record-status-pendiente"><i class="fas fa-hourglass-half"></i> Pendiente de firma</span></td>
                                                <% } else if (BatchRecordVersiones.REEMPLAZADO.equals(brEstado)) { %>
                                                <td><span class="batch-record-status batch-record-status-borrador" title="Hay un Batch Record más reciente para este lote."><i class="fas fa-archive"></i> Reemplazado</span></td>
                                                <% } else { %>
                                                <td><span class="batch-record-status batch-record-status-borrador" title="Se generó con la lista de verificación R-GC-094 incompleta: no se puede firmar. Complete la lista y genere el Batch Record de nuevo."><i class="fas fa-pencil-alt"></i> Borrador · lista incompleta</span></td>
                                                <% } %>
                                                <td><%= fechaBr%></td>
                                                <td><%= brAprobado ? "Aprobado por " : "Generado por "%><%= generadoPor%></td>
                                                <td class="text-center">
                                                    <div class="batch-record-menu">
                                                        <button type="button" class="batch-record-menu-btn" onclick="brToggleMenu(event, this)" title="Acciones">
                                                            <i class="fas fa-ellipsis-v"></i>
                                                        </button>
                                                        <div class="dropdown-menu batch-record-menu-list">
                                                            <a class="dropdown-item" href="<%= relPathBr%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(batchRecord.getName())%>', 'Batch Record PDF Unificado', '<%= Util.jsAttr(relPathBr)%>'); return false;"><i class="fas fa-eye"></i> Ver original</a>
                                                            <a class="dropdown-item" href="<%= relPathBrDescarga%>" download><i class="fas fa-download"></i> Descargar</a>
                                                            <a class="dropdown-item" href="#" onclick="abrirHistorialFirmas('BATCH_RECORD', '<%= batchRecord.getId()%>'); return false;"><i class="fas fa-history"></i> Historial de firmas</a>
                                                            <% if (brFirmable && Permission.contains("[41]")) { %>
                                                            <div class="dropdown-divider"></div>
                                                            <a class="dropdown-item text-success" href="#" onclick="confirmFirmarBatchRecord('<%= Util.jsAttr(cliente)%>','<%= Util.jsAttr(anio)%>','<%= Util.jsAttr(orden)%>','<%= Util.jsAttr(lote)%>','<%= batchRecord.getId()%>'); return false;"><i class="fas fa-signature"></i> Firmar / Aprobar</a>
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
                                            <tr class="file-row batch-record-row" data-category="lab" data-status="na" data-item="<%= itemAttr(itemPorDocumento.get(BatchRecordChecklist.claveUrl(ArgLink[3])))%>">
                                                <td class="text-center"><%= itemCell(itemPorDocumento.get(BatchRecordChecklist.claveUrl(ArgLink[3])))%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ArgLink[2])%>', 'Registros LAB', '<%= Util.jsAttr(ArgLink[3])%>')"><span class="batch-record-src-box batch-record-src-box-lab"><img src="Interface/Imagen/Registros_lab_Logo.png" alt=""></span> <%= ArgLink[1]%> — <%= ArgLink[2]%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(ArgLink[1])%>', '<%= Util.jsAttr(ArgLink[2])%>', '<%= Util.jsAttr(ArgLink[3])%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= ArgLink[3]%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ArgLink[2])%>', 'Registros LAB', '<%= Util.jsAttr(ArgLink[3])%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
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
                                                StringBuilder MaterialCodigosBuilder = new StringBuilder();
                                                lst_certificate = CertificatesJpa.ConsultCertificatesBatchRecord(orden, lote);
                                                if (lst_certificate != null) {
                                                    for (int i = 0; i < lst_certificate.size(); i++) {
                                                        Object[] ArgCertificate = (Object[]) lst_certificate.get(i);
                                                        MaterialBatch += ArgCertificate[4];
                                                        if (ArgCertificate.length > 5 && ArgCertificate[5] != null) {
                                                            // Codigos de recepción de material (tinta) seleccionados al
                                                            // crear el certificado: el tab de Generación de Lotes solo
                                                            // debe mostrar los anexos de esa recepción puntual, no todas
                                                            // las que comparten el mismo lote.
                                                            for (String codigo : Util.extractSelectedReceptionCodes(ArgCertificate[5].toString())) {
                                                                if (MaterialCodigosBuilder.length() > 0) {
                                                                    MaterialCodigosBuilder.append(",");
                                                                }
                                                                MaterialCodigosBuilder.append(codigo);
                                                            }
                                                        }
                                            %>
                                            <tr class="file-row batch-record-row" data-category="coa" data-status="na" data-item="<%= itemAttr(itemPorDocumento.get(BatchRecordChecklist.claveUrl(String.valueOf(ArgCertificate[3]))))%>">
                                                <td class="text-center"><%= itemCell(itemPorDocumento.get(BatchRecordChecklist.claveUrl(String.valueOf(ArgCertificate[3]))))%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ArgCertificate[2])%>', 'Certificados COA', '<%= Util.jsAttr(ArgCertificate[3])%>')"><span class="batch-record-src-box batch-record-src-box-coa"><img src="Interface/Imagen/LogoSText.fw.png" alt=""></span> <%= ArgCertificate[1]%> — <%= ArgCertificate[2]%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(ArgCertificate[1])%>', '<%= Util.jsAttr(ArgCertificate[2])%>', '<%= Util.jsAttr(ArgCertificate[3])%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= ArgCertificate[3]%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ArgCertificate[2])%>', 'Certificados COA', '<%= Util.jsAttr(ArgCertificate[3])%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>    
                                            <%
                                                }
                                            }
                                            %>
                                            <%
                                                lst_material = LinkBatch.AttachmentBatchRecord(MaterialBatch, MaterialCodigosBuilder.toString());
                                                if (lst_material != null) {
                                                    for (int i = 0; i < lst_material.size(); i++) {
                                                        String[] ArgBatchRaw = Util.parseResult(lst_material.get(i));
                                                        // Registros antiguos de Generación de Lotes a veces traen
                                                        // "<a href=\"...\">texto</a>" completo en vez de un nombre
                                                        // plano; se normaliza aquí antes de usarlo en cualquier lado.
                                                        String[] ArgBatch = ArgBatchRaw.clone();
                                                        ArgBatch[2] = Util.extractFileName(ArgBatchRaw[2]);
                                            %>
                                            <tr class="file-row batch-record-row" data-category="lotes" data-status="na" data-item="<%= itemAttr(itemPorDocumento.get(BatchRecordChecklist.claveAnexo(ArgBatchRaw[2])))%>">
                                                <td class="text-center"><%= itemCell(itemPorDocumento.get(BatchRecordChecklist.claveAnexo(ArgBatchRaw[2])))%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ArgBatch[3])%>', 'Generación de Lotes', 'DownloadGL?File_name=<%= Util.jsAttr(ArgBatch[2].trim())%>')"><span class="batch-record-src-box batch-record-src-box-lotes"><img src="Interface/Imagen/Generacion_lotes.png" alt=""></span> <%= ArgBatch[1]%> — <%= ArgBatch[3]%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(ArgBatch[1])%>', '<%= Util.jsAttr(ArgBatch[3])%>', 'DownloadGL?File_name=<%= Util.jsAttr(ArgBatch[2].trim())%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="DownloadGL?File_name=<%= ArgBatch[2].trim()%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(ArgBatch[3])%>', 'Generación de Lotes', 'DownloadGL?File_name=<%= Util.jsAttr(ArgBatch[2].trim())%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
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
                                            <tr class="file-row batch-record-row" data-category="manga" data-status="na" data-item="<%= itemAttr(null)%>">
                                                <td class="text-center"><%= itemCell(null)%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(mangaNombre)%>', 'Inspección Manga', '<%= Util.jsAttr(mangaUrl)%>')"><span class="batch-record-src-box batch-record-src-box-manga"><img src="Interface/Imagen/Inspeccion_manga_new.png" alt=""></span> <%= mangaTipo%> — <%= mangaNombre%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(mangaTipo)%>', '<%= Util.jsAttr(mangaNombre)%>', '<%= Util.jsAttr(mangaUrl)%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= mangaUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(mangaNombre)%>', 'Inspección Manga', '<%= Util.jsAttr(mangaUrl)%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
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
                                            <tr class="file-row batch-record-row" data-category="manga" data-status="na" data-item="<%= itemAttr(null)%>">
                                                <td class="text-center"><%= itemCell(null)%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(despejeNombre)%>', 'Inspección Manga', '<%= Util.jsAttr(despejeUrl)%>')"><span class="batch-record-src-box batch-record-src-box-manga"><img src="Interface/Imagen/Inspeccion_manga_new.png" alt=""></span> <%= despejeTipo%> — <%= despejeNombre%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(despejeTipo)%>', '<%= Util.jsAttr(despejeNombre)%>', '<%= Util.jsAttr(despejeUrl)%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= despejeUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(despejeNombre)%>', 'Inspección Manga', '<%= Util.jsAttr(despejeUrl)%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
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
                                            <tr class="file-row batch-record-row" data-category="manga" data-status="na" data-item="<%= itemAttr(null)%>">
                                                <td class="text-center"><%= itemCell(null)%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(cabeceraNombre)%>', 'Inspección Manga', '<%= Util.jsAttr(cabeceraUrl)%>')"><span class="batch-record-src-box batch-record-src-box-manga"><img src="Interface/Imagen/Inspeccion_manga_new.png" alt=""></span> <%= cabeceraTipo%> — <%= cabeceraNombre%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(cabeceraTipo)%>', '<%= Util.jsAttr(cabeceraNombre)%>', '<%= Util.jsAttr(cabeceraUrl)%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= cabeceraUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(cabeceraNombre)%>', 'Inspección Manga', '<%= Util.jsAttr(cabeceraUrl)%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
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
                                            <tr class="file-row batch-record-row" data-category="formula" data-status="na" data-item="<%= itemAttr(null)%>">
                                                <td class="text-center"><%= itemCell(null)%></td>
                                                <td class="batch-record-doc-name" style="cursor:pointer;" onclick="brVerEnPanel(this, '<%= Util.jsAttr(formulaNombre)%>', 'Control Fórmulas', '<%= Util.jsAttr(formulaUrl)%>')"><span class="batch-record-src-box batch-record-src-box-formula"><img src="Interface/Imagen/Control_formulas_new.png" alt=""></span> <%= formulaTipo%> — <%= formulaNombre%></td>
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
                                                            <a class="dropdown-item" href="#" onclick="verPdfIndividual('<%= Util.jsAttr(formulaTipo)%>', '<%= Util.jsAttr(formulaNombre)%>', '<%= Util.jsAttr(formulaUrl)%>'); return false;"><i class="fas fa-file-pdf"></i> Generar PDF</a>
                                                            <a class="dropdown-item" href="<%= formulaUrl%>" target="_blank" onclick="brVerEnPanel(this, '<%= Util.jsAttr(formulaNombre)%>', 'Control Fórmulas', '<%= Util.jsAttr(formulaUrl)%>'); return false;"><i class="fas fa-eye"></i> Ver registro</a>
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

                                    </div>

                                    <!-- Vista del documento: ventana emergente (antes panel lateral fijo) -->
                                    <div class="br-viewer-overlay" id="brViewerModal" onclick="if (event.target === this) brCerrarVisor();">
                                        <div class="br-viewer-dialog" role="dialog" aria-modal="true" aria-labelledby="brViewerName">
                                            <div class="br-viewer-head">
                                                <div class="br-viewer-titles">
                                                    <div class="br-viewer-name" id="brViewerName"></div>
                                                    <div class="br-viewer-sub" id="brViewerSub"></div>
                                                </div>
                                                <a id="brViewerOpenLink" class="btn btn-sm br-side-btn-outline br-viewer-open" href="#" target="_blank"><i class="fas fa-external-link-alt"></i> Abrir en pestaña nueva</a>
                                                <button type="button" class="close br-viewer-close" onclick="brCerrarVisor()" aria-label="Cerrar"><span>&times;</span></button>
                                            </div>
                                            <iframe id="brViewerFrame" class="br-viewer-frame" src="about:blank" title="Vista del documento"></iframe>
                                        </div>
                                    </div>

                                    <!-- ================== BATCH RECORD: EVENTOS DE INTEGRACIÓN ================== -->
                                    <div class="batch-record-events br-events-collapsed" id="batchRecordEventos">
                                        <div class="batch-record-events-header br-events-toggle" onclick="brAlternarEventos()">
                                            <span><i class="fas fa-exclamation-triangle"></i> <b><%= eventosListado.size()%></b> evento<%= eventosListado.size() == 1 ? "" : "s"%> de integración</span>
                                            <span class="br-events-link"><span class="br-events-label">Ver detalle</span> <i class="fas fa-chevron-down"></i></span>
                                        </div>
                                        <div class="br-events-body">
                                        <% if (eventosListado.isEmpty()) { %>
                                        <div class="batch-record-events-empty">Sin eventos de integración recientes.</div>
                                        <% } else { %>
                                        <% for (String evento : eventosListado) { %>
                                        <div class="batch-record-event-row">
                                            <i class="fas fa-exclamation-triangle text-warning"></i>
                                            <span class="batch-record-event-text"><%= evento%></span>
                                        </div>
                                        <% } %>
                                        <% } %>
                                        </div>
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

            // Aprobación documental del Batch Record (Coordinadora Documental, permiso [41]).
            // POST porque modifica: reemplaza el PDF pendiente por el firmado.
            function confirmFirmarBatchRecord(cliente, anio, orden, lote, id) {
                swal({
                    title: "¿Firmar y aprobar el Batch Record?",
                    text: "Su firma se agregará en la portada (Revisión y aprobación documental) como aval de que el expediente del lote está correctamente parametrizado. El PDF pendiente será reemplazado por el aprobado.",
                    icon: "info",
                    buttons: {
                        cancel: {
                            text: "Cancelar",
                            visible: true,
                            className: "btn btn-secondary"
                        },
                        confirm: {
                            text: "Sí, firmar",
                            value: true,
                            className: "btn btn-green"
                        }
                    }
                }).then(function (confirm) {
                    if (!confirm) {
                        return;
                    }
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'BatchRecordSignServlet';
                    var campos = {cliente: cliente, anio: anio, orden: orden, lote: lote, id: id};
                    for (var nombre in campos) {
                        var input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = nombre;
                        input.value = campos[nombre];
                        form.appendChild(input);
                    }
                    document.body.appendChild(form);
                    form.submit();
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
            } else if ("br_sign_success".equals(msg)) {
            %>
            iziToast.success({
                title: 'Batch Record aprobado',
                message: 'Se firmó el Batch Record y quedó registrado como aprobado.',
                position: 'bottomRight'
            });
            <%
            } else if ("br_sign_permission".equals(msg)) {
            %>
            iziToast.error({
                title: 'Sin permiso',
                message: 'Su rol no tiene permiso para firmar / aprobar el Batch Record.',
                position: 'bottomRight'
            });
            <%
            } else if ("br_sign_no_signature".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Firma no registrada',
                message: 'No tiene una firma registrada. Cárguela en Perfil > Actualizar Firma e inténtelo de nuevo.',
                position: 'bottomRight'
            });
            <%
            } else if ("br_sign_already".equals(msg)) {
            %>
            iziToast.info({
                title: 'Ya aprobado',
                message: 'Este Batch Record ya está firmado.',
                position: 'bottomRight'
            });
            <%
            } else if ("br_sign_legacy".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Vuelva a generarlo',
                message: 'Este Batch Record se generó antes de habilitar la aprobación documental y no tiene el espacio de firma. Genere el Batch Record de nuevo y firme el nuevo PDF.',
                position: 'bottomRight',
                timeout: 9000
            });
            <%
            } else if ("br_sign_checklist_pending".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Lista de verificación incompleta',
                message: 'No se puede aprobar el Batch Record: la lista de verificación R-GC-094 tiene <%= Util.jsString(request.getParameter("pend") == null ? "" : request.getParameter("pend").replaceAll("[^0-9]", ""))%> ítem(s) pendiente(s). Confírmelos o márquelos N/A y vuelva a generar el Batch Record.',
                position: 'bottomRight',
                timeout: 10000
            });
            <%
            } else if ("br_sign_checklist_stale".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Vuelva a generar el Batch Record',
                message: 'Este PDF se generó cuando la lista de verificación R-GC-094 tenía pendientes (o antes de la lista). La lista ya está completa: genere el Batch Record de nuevo y firme el nuevo PDF.',
                position: 'bottomRight',
                timeout: 10000
            });
            <%
            } else if ("br_generated".equals(msg)) {
            %>
            iziToast.success({
                title: 'Batch Record generado',
                message: 'El Batch Record se generó, se abrió en otra pestaña y quedó pendiente de firma.',
                position: 'bottomRight',
                timeout: 7000
            });
            <%
            } else if ("br_generated_draft".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Batch Record generado como borrador',
                message: 'La lista de verificación tenía pendientes: este PDF es un borrador y no se puede firmar. Complete la lista y genérelo de nuevo.',
                position: 'bottomRight',
                timeout: 9000
            });
            <%
            } else if ("br_sign_outdated".equals(msg)) {
            %>
            iziToast.warning({
                title: 'Batch Record reemplazado',
                message: 'Solo se puede aprobar el Batch Record más reciente del lote. Firme la versión vigente (ver "Batch Record generados").',
                position: 'bottomRight',
                timeout: 9000
            });
            <%
            } else if ("ckl_locked".equals(msg)) {
            %>
            iziToast.info({
                title: 'Lista aprobada',
                message: 'La lista de verificación está en solo lectura porque el Batch Record ya fue aprobado. Para corregirla, genere un nuevo Batch Record.',
                position: 'bottomRight',
                timeout: 9000
            });
            <%
            } else if ("ckl_ok".equals(msg)) {
            %>
            iziToast.success({
                title: 'Lista de verificación',
                message: 'Ítem verificado.',
                position: 'bottomRight'
            });
            <%
            } else if ("ckl_na".equals(msg)) {
            %>
            iziToast.success({
                title: 'Lista de verificación',
                message: 'Ítem marcado N/A.',
                position: 'bottomRight'
            });
            <%
            } else if ("ckl_undo".equals(msg)) {
            %>
            iziToast.info({
                title: 'Lista de verificación',
                message: 'Se deshizo la marca del ítem.',
                position: 'bottomRight'
            });
            <%
            } else if ("ckl_permission".equals(msg)) {
            %>
            iziToast.error({
                title: 'Sin permiso',
                message: 'Su rol no puede modificar la lista de verificación.',
                position: 'bottomRight'
            });
            <%
            } else if ("ckl_error".equals(msg)) {
            %>
            iziToast.error({
                title: 'Error',
                message: 'No se pudo actualizar la lista de verificación. Recargue la página e inténtelo de nuevo.',
                position: 'bottomRight'
            });
            <%
            } else if ("br_sign_error".equals(msg)) {
            %>
            iziToast.error({
                title: 'Error',
                message: 'No se pudo firmar el Batch Record.',
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
                    const categoria = row.getAttribute('data-category');
                    // "Todos" no incluye Batch Record: son PDFs generados (histórico de
                    // trazabilidad), no documentos fuente, y mezclarlos ahí saturaba la vista
                    // general. Solo se ven entrando a su propia pestaña.
                    const coincideCategoria = brCategoriaActiva === 'todos'
                            ? categoria !== 'batchrecord'
                            : categoria === brCategoriaActiva;
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
                    if (criterio === 'checklist') {
                        // Mismo orden que el PDF unificado: por ítem de la lista R-GC-094;
                        // lo que no pertenece a ningún ítem va al final, en su orden original.
                        const itemA = parseInt(a.getAttribute('data-item') || '0', 10) || 100000;
                        const itemB = parseInt(b.getAttribute('data-item') || '0', 10) || 100000;
                        if (itemA !== itemB) {
                            return itemA - itemB;
                        }
                        return parseInt(a.getAttribute('data-orig') || '0', 10) - parseInt(b.getAttribute('data-orig') || '0', 10);
                    }
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
                document.getElementById('brOrdenLabel').textContent = criterio === 'nombre' ? 'Nombre A-Z'
                        : (criterio === 'checklist' ? 'Lista de verificación' : 'Más reciente');
                brCerrarMenus();
            }

            function brActualizarResumen() {
                const filas = document.querySelectorAll('.batch-record-row');
                const conteos = {fisico: 0, batchrecord: 0, lab: 0, coa: 0, lotes: 0, manga: 0, formula: 0};

                filas.forEach(function (row) {
                    const categoria = row.getAttribute('data-category');
                    if (conteos.hasOwnProperty(categoria)) {
                        conteos[categoria]++;
                    }
                });

                const total = filas.length;
                document.getElementById('brTotalDocs').textContent = total;
                // "Todos" no cuenta los Batch Record (ver brAplicarVisibilidad): mismo criterio.
                document.getElementById('brCountTodos').textContent = total - conteos.batchrecord;
                document.getElementById('brCountFisico').textContent = conteos.fisico;
                document.getElementById('brCountBatchRecord').textContent = conteos.batchrecord;
                document.getElementById('brCountLab').textContent = conteos.lab;
                document.getElementById('brCountCoa').textContent = conteos.coa;
                document.getElementById('brCountLotes').textContent = conteos.lotes;
                document.getElementById('brCountManga').textContent = conteos.manga;
                document.getElementById('brCountFormula').textContent = conteos.formula;
            }

            document.addEventListener('DOMContentLoaded', function () {
                var filas = document.querySelectorAll('.batch-record-row');
                var conItem = false;
                filas.forEach(function (row, i) {
                    row.setAttribute('data-orig', i);
                    if (row.getAttribute('data-item')) {
                        conItem = true;
                    }
                });
                // Si el lote tiene lista de verificación, se muestra en su orden por defecto.
                if (conItem) {
                    brOrdenar('checklist');
                }
                brActualizarResumen();
                brAplicarVisibilidad();
            });

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

            // ===== Lista de verificación R-GC-094 =====
            // "Imprimir reporte": el CSS @media print deja visible solo la lista de verificación.
            function cklImprimir() {
                window.print();
            }

            function cklAbrir(accion, itemKey, etiqueta) {
                var overlay = document.getElementById('cklOverlay');
                if (!overlay) return;
                var esNa = accion === 'na';
                document.getElementById('cklAccion').value = accion;
                document.getElementById('cklItemKey').value = itemKey;
                document.getElementById('cklDialogItem').textContent = etiqueta;
                document.getElementById('cklDialogTitle').textContent = esNa ? 'Marcar ítem como N/A' : 'Confirmar ítem';
                document.getElementById('cklFileGroup').style.display = esNa ? 'none' : '';
                document.getElementById('cklFile').value = '';
                document.getElementById('cklObservacion').value = '';
                document.getElementById('cklObsLabel').textContent = esNa ? 'Motivo / observación (opcional)' : 'Observación (opcional)';
                document.getElementById('cklSubmit').innerHTML = esNa ? 'Marcar N/A' : '<i class="fas fa-check"></i> Confirmar';
                overlay.classList.add('ckl-open');
            }

            function cklCerrar() {
                var overlay = document.getElementById('cklOverlay');
                if (overlay) overlay.classList.remove('ckl-open');
            }

            document.addEventListener('keydown', function (e) {
                if (e.key === 'Escape') cklCerrar();
            });

            (function () {
                var form = document.getElementById('cklForm');
                var file = document.getElementById('cklFile');
                if (!form || !file) return;
                file.addEventListener('change', function () {
                    if (file.files.length > 0 && !extensionPermitida(file.files[0].name)) {
                        iziToast.error({title: 'Formato no permitido', message: 'Solo se permiten pdf, png, jpg, jpeg, gif.', position: 'bottomRight'});
                        file.value = '';
                    }
                });
                form.addEventListener('submit', function () {
                    // Evita doble envío mientras sube el archivo.
                    var btn = document.getElementById('cklSubmit');
                    btn.disabled = true;
                    btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';
                });
            })();

            function cklDeshacer(itemKey, etiqueta) {
                swal({
                    title: "¿Deshacer la marca?",
                    text: "El ítem \"" + etiqueta + "\" volverá a quedar pendiente. Si se adjuntó un archivo, se conserva en el lote.",
                    icon: "warning",
                    buttons: {
                        cancel: {text: "Cancelar", visible: true, className: "btn btn-secondary"},
                        confirm: {text: "Sí, deshacer", value: true, className: "btn btn-danger"}
                    },
                    dangerMode: true
                }).then(function (ok) {
                    if (!ok) return;
                    var base = document.getElementById('cklForm');
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'BatchRecordChecklistServlet';
                    var campos = {
                        cliente: base.cliente.value, anio: base.anio.value, orden: base.orden.value, lote: base.lote.value,
                        accion: 'deshacer', itemKey: itemKey
                    };
                    for (var nombre in campos) {
                        var input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = nombre;
                        input.value = campos[nombre];
                        form.appendChild(input);
                    }
                    document.body.appendChild(form);
                    form.submit();
                });
            }

            // Vista del documento en ventana emergente (misma firma que el antiguo panel lateral).
            function brVerEnPanel(elemento, nombre, categoria, url) {
                document.getElementById('brViewerName').textContent = nombre;
                document.getElementById('brViewerSub').textContent = categoria || '';
                document.getElementById('brViewerFrame').src = url;
                document.getElementById('brViewerOpenLink').href = url;
                document.getElementById('brViewerModal').classList.add('br-viewer-open-state');

                document.querySelectorAll('.batch-record-row-selected').forEach(function (row) {
                    row.classList.remove('batch-record-row-selected');
                });
                var fila = elemento && elemento.closest ? elemento.closest('tr.batch-record-row') : null;
                if (fila) {
                    fila.classList.add('batch-record-row-selected');
                }
                brCerrarMenus();
            }

            function brCerrarVisor() {
                var modal = document.getElementById('brViewerModal');
                if (!modal) return;
                modal.classList.remove('br-viewer-open-state');
                document.getElementById('brViewerFrame').src = 'about:blank';
            }

            document.addEventListener('keydown', function (e) {
                if (e.key === 'Escape') brCerrarVisor();
            });

            // Eventos de integración: contraídos por defecto.
            function brAlternarEventos(forzarAbrir) {
                var caja = document.getElementById('batchRecordEventos');
                if (!caja) return;
                var abrir = forzarAbrir === true ? true : caja.classList.contains('br-events-collapsed');
                caja.classList.toggle('br-events-collapsed', !abrir);
                var label = caja.querySelector('.br-events-label');
                if (label) label.textContent = abrir ? 'Ocultar detalle' : 'Ver detalle';
            }

            function brAbrirEventos() {
                brAlternarEventos(true);
            }
        </script>

        <script src="Interface/Content/Assets/js/jspdf.umd.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/js/signature-history.js"></script>
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

            function generarBatchRecordPdfUnificado(cliente, anio, orden, lote, pendientesChecklist, reemplazoConfirmado) {
                if (!orden || !lote) {
                    alert("No hay información suficiente del lote para generar el Batch Record.");
                    return;
                }

                // Lote aprobado: un Batch Record nuevo pasa a ser el vigente, desbloquea la
                // lista de verificación y requiere una nueva firma.
                if (window.brLoteAprobado && !reemplazoConfirmado && typeof swal !== "undefined") {
                    swal({
                        title: "El lote ya está aprobado",
                        text: "Si genera un nuevo Batch Record, reemplazará al aprobado: la lista de verificación se habilitará para cambios y el nuevo PDF necesitará la firma de la Coordinadora Documental.",
                        icon: "warning",
                        buttons: {
                            cancel: {text: "Cancelar", visible: true, className: "btn btn-secondary"},
                            confirm: {text: "Generar nuevo", value: true, className: "btn btn-danger"}
                        }
                    }).then(function (ok) {
                        if (ok) {
                            generarBatchRecordPdfUnificado(cliente, anio, orden, lote, pendientesChecklist, true);
                        }
                    });
                    return;
                }

                // Con pendientes en la lista R-GC-094 se genera igual (borrador),
                // pero se avisa: ese PDF no se va a poder aprobar.
                if (pendientesChecklist > 0 && typeof swal !== "undefined") {
                    swal({
                        title: "Lista de verificación incompleta",
                        text: "La lista de verificación R-GC-094 tiene " + pendientesChecklist + " ítem(s) pendiente(s). "
                                + "Puede generar el Batch Record como borrador, pero no se podrá aprobar hasta completar la lista y volver a generarlo.",
                        icon: "warning",
                        buttons: {
                            cancel: {text: "Cancelar", visible: true, className: "btn btn-secondary"},
                            confirm: {text: "Generar borrador", value: true, className: "btn btn-danger"}
                        }
                    }).then(function (ok) {
                        if (ok) {
                            generarBatchRecordPdfUnificado(cliente, anio, orden, lote, 0, true);
                        }
                    });
                    return;
                }

                const params = new URLSearchParams({
                    orden: orden,
                    lote: lote,
                    cliente: cliente || '',
                    anio: anio || ''
                });

                // La pestaña del PDF se abre ya (dentro del clic, para que el navegador no
                // la bloquee) con un mensaje de espera; cuando el servidor termina y archiva
                // el PDF, se le carga el archivo y ESTA página se recarga para mostrar el
                // estado, la aprobación y las versiones actualizados.
                var pestana = window.open('', '_blank');
                if (pestana) {
                    try {
                        pestana.document.write('<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Generando Batch Record...</title></head>'
                                + '<body style="font-family:Nunito,Segoe UI,Arial,sans-serif;color:#0b0025;display:flex;align-items:center;justify-content:center;height:90vh;">'
                                + '<div style="text-align:center"><div style="font-size:18px;font-weight:700;">Generando el Batch Record unificado...</div>'
                                + '<div style="color:#6c757d;margin-top:6px;">Esto puede tardar unos segundos. El PDF se abrirá aquí automáticamente.</div></div></body></html>');
                        pestana.document.close();
                    } catch (e) {
                    }
                }
                brMostrarGenerando(true);

                fetch('BatchRecordPdfGenerateServlet?' + params.toString() + '&formato=json', {credentials: 'same-origin'})
                        .then(function (r) {
                            return r.json().catch(function () {
                                return {ok: false, error: 'El servidor no respondió correctamente (HTTP ' + r.status + ').'};
                            });
                        })
                        .then(function (res) {
                            if (!res.ok || !res.id) {
                                throw new Error(res.error || 'No se pudo generar el Batch Record.');
                            }
                            var urlPdf = 'FileDownloadProxyServlet?id=' + encodeURIComponent(res.id) + '&modo=inline';
                            if (pestana && !pestana.closed) {
                                pestana.location.href = urlPdf;
                            } else {
                                window.open(urlPdf, '_blank');
                            }
                            var recarga = new URLSearchParams({cliente: cliente || '', anio: anio || '', orden: orden, lote: lote,
                                msg: res.borrador ? 'br_generated_draft' : 'br_generated'});
                            window.location.href = 'FileManager.jsp?' + recarga.toString() + '#brAprobacion';
                        })
                        .catch(function (err) {
                            brMostrarGenerando(false);
                            if (pestana && !pestana.closed) {
                                pestana.close();
                            }
                            if (typeof iziToast !== "undefined") {
                                iziToast.error({title: 'Batch Record', message: err.message, position: 'bottomRight', timeout: 9000});
                            } else {
                                alert(err.message);
                            }
                        });
            }

            // Aviso de espera mientras el servidor genera el Batch Record (evita doble clic).
            function brMostrarGenerando(mostrar) {
                var capa = document.getElementById('brGenerandoOverlay');
                if (!capa && mostrar) {
                    capa = document.createElement('div');
                    capa.id = 'brGenerandoOverlay';
                    capa.className = 'br-generando';
                    capa.innerHTML = '<div class="br-generando-box"><i class="fas fa-circle-notch fa-spin"></i>'
                            + '<div><b>Generando el Batch Record unificado...</b>'
                            + '<div class="br-generando-sub">Compilando el expediente completo en el servidor. Al terminar, el PDF se abre en la otra pestaña y esta página se actualiza.</div></div></div>';
                    document.body.appendChild(capa);
                }
                if (capa) {
                    capa.style.display = mostrar ? 'flex' : 'none';
                }
            }
        </script>
    </body>
</html>
