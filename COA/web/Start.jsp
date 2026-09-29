
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/start" prefix="Start" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <style>
            /* ==================== DASHBOARD ====================
             * Tarjetas planas con acentos pastel, en lugar de los bloques de
             * color sólidos del tablero anterior.
             */
            :root {
                --dash-line: #eceff5;
                --dash-muted: #8a94a6;
                --dash-ink: #1f2a44;
            }

            .dash-header {
                margin-bottom: 18px;
            }
            .dash-header h1 {
                font-size: 26px;
                font-weight: 700;
                color: var(--dash-ink);
            }
            .dash-header-sub {
                font-size: 13px;
                color: var(--dash-muted);
                margin-top: 2px;
            }

            .dash-card {
                background: #fff;
                border: 1px solid var(--dash-line);
                border-radius: 12px;
                box-shadow: 0 1px 2px rgba(31, 42, 68, .04);
                margin-bottom: 24px;
                overflow: hidden;
            }
            .dash-card.h-100 {
                height: calc(100% - 24px) !important;
            }

            .dash-card-head {
                display: flex;
                align-items: center;
                justify-content: space-between;
                flex-wrap: wrap;
                gap: 4px 12px;
                padding: 18px 22px 14px;
            }
            .dash-card-head h4 {
                margin: 0;
                font-size: 15px;
                font-weight: 700;
                color: var(--dash-ink);
            }
            .dash-card-note {
                font-size: 12px;
                color: var(--dash-muted);
            }
            .dash-card-body {
                padding: 6px 22px 20px;
            }
            .dash-card-body.p-0 {
                padding: 0 !important;
            }
            .dash-link {
                font-size: 12.5px;
                font-weight: 600;
                color: #6777ef;
            }
            .dash-link:hover {
                color: #4b5ae0;
                text-decoration: none;
            }

            /* ---------- KPI ---------- */
            .dash-kpi {
                display: flex;
                align-items: center;
                gap: 14px;
                padding: 18px 18px;
                cursor: pointer;
                transition: transform .15s ease, box-shadow .15s ease;
            }
            .dash-kpi:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 18px rgba(31, 42, 68, .09);
            }
            .dash-kpi-icon {
                flex: 0 0 auto;
                width: 46px;
                height: 46px;
                border-radius: 13px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 18px;
            }
            .dash-ico-info {
                background: #e7f1ff;
                color: #3d7de0;
            }
            .dash-ico-warning {
                background: #fff4e0;
                color: #d98a1a;
            }
            .dash-ico-success {
                background: #d9f5e5;
                color: #1f9d63;
            }
            .dash-ico-danger {
                background: #fde8e8;
                color: #d64550;
            }
            .dash-kpi-body {
                min-width: 0;
                flex: 1 1 auto;
            }
            .dash-kpi-label {
                font-size: 11.5px;
                font-weight: 600;
                letter-spacing: .04em;
                text-transform: uppercase;
                color: var(--dash-muted);
            }
            .dash-kpi-value {
                font-size: 28px;
                font-weight: 700;
                line-height: 1.15;
                color: var(--dash-ink);
            }
            .dash-kpi-foot {
                font-size: 12px;
                margin-top: 2px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .dash-foot-ok {
                color: var(--dash-muted);
            }
            .dash-kpi-go {
                flex: 0 0 auto;
                font-size: 12px;
                color: #c9d0dd;
            }

            .dash-pill {
                display: inline-block;
                border-radius: 20px;
                padding: 2px 9px;
                font-size: 11.5px;
                font-weight: 600;
            }
            .dash-pill i {
                font-size: 10px;
            }
            .dash-pill-warn {
                background: #fff4e0;
                color: #b9730d;
            }
            .dash-pill-up {
                background: #d9f5e5;
                color: #1f9d63;
            }
            .dash-pill-down {
                background: #fde8e8;
                color: #d64550;
            }

            /* ---------- MICRO METRICAS ---------- */
            .dash-microrow {
                display: flex;
                border-top: 1px solid var(--dash-line);
                border-bottom: 1px solid var(--dash-line);
                background: #fbfcfe;
            }
            .dash-micro {
                flex: 1 1 0;
                padding: 12px 8px;
                text-align: center;
                border-right: 1px solid var(--dash-line);
            }
            .dash-micro:last-child {
                border-right: 0;
            }
            .dash-micro-value {
                font-size: 20px;
                font-weight: 700;
                color: var(--dash-ink);
                line-height: 1.2;
            }
            .dash-micro-label {
                font-size: 11.5px;
                color: var(--dash-muted);
            }

            .dash-chart {
                position: relative;
            }

            /* ---------- DONUT ---------- */
            .dash-donut {
                position: relative;
                margin-top: 6px;
            }
            .dash-donut-center {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                text-align: center;
                pointer-events: none;
            }
            .dash-donut-value {
                font-size: 24px;
                font-weight: 700;
                color: var(--dash-ink);
                line-height: 1.1;
            }
            .dash-donut-label {
                font-size: 11px;
                letter-spacing: .05em;
                text-transform: uppercase;
                color: var(--dash-muted);
            }
            .dash-legend {
                list-style: none;
                padding: 0;
                margin: 16px 0 0;
            }
            .dash-legend li {
                display: flex;
                align-items: center;
                gap: 9px;
                padding: 7px 0;
                border-bottom: 1px solid #f5f7fa;
                font-size: 13px;
            }
            .dash-legend li:last-child {
                border-bottom: 0;
            }
            .dash-dot {
                width: 9px;
                height: 9px;
                border-radius: 50%;
                flex: 0 0 auto;
            }
            .dash-legend-name {
                flex: 1 1 auto;
                color: var(--dash-ink);
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .dash-legend-val {
                font-weight: 600;
                color: var(--dash-ink);
            }
            .dash-legend-val small {
                color: var(--dash-muted);
                font-weight: 400;
            }

            /* ---------- TABLAS ---------- */
            .dash-table {
                font-size: 13.5px;
            }
            .dash-table thead th {
                border-top: 0;
                border-bottom: 1px solid var(--dash-line);
                background: #fbfcfe;
                padding: 11px 22px;
                font-size: 11.5px;
                font-weight: 600;
                letter-spacing: .04em;
                text-transform: uppercase;
                color: var(--dash-muted);
                white-space: nowrap;
            }
            .dash-table tbody td {
                border-top: 1px solid #f5f7fa;
                padding: 13px 22px;
                vertical-align: middle;
            }
            .dash-table tbody tr:hover > td {
                background: #fbfcff;
            }
            .dash-customer {
                font-weight: 600;
                color: var(--dash-ink);
            }
            .dash-code {
                display: inline-block;
                background: #eef2ff;
                color: #312e81;
                border-radius: 6px;
                padding: 3px 9px;
                font-size: 12.5px;
                font-weight: 600;
            }
            .dash-sep {
                color: #cfd6e2;
            }
            .dash-btn {
                border-radius: 8px;
                padding: 3px 10px;
            }

            .dash-bar-wrap {
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .dash-bar {
                flex: 1 1 auto;
                height: 7px;
                border-radius: 4px;
                background: #eef1f6;
                overflow: hidden;
                min-width: 50px;
            }
            .dash-bar span {
                display: block;
                height: 100%;
                border-radius: 4px;
                background: linear-gradient(90deg, #6777ef, #8f9bff);
            }
            .dash-bar-val {
                flex: 0 0 auto;
                color: var(--dash-ink);
            }

            /* ---------- ESTADOS Y ANTIGUEDAD ---------- */
            .dash-state {
                display: inline-block;
                border-radius: 20px;
                padding: 2px 10px;
                font-size: 11px;
                font-weight: 600;
                white-space: nowrap;
            }
            .dash-state-1 {
                background: #e7f1ff;
                color: #2f6fd0;
            }
            .dash-state-2 {
                background: #fff4e0;
                color: #b9730d;
            }
            .dash-state-3 {
                background: #d9f5e5;
                color: #1f9d63;
            }
            .dash-state-0 {
                background: #f1f3f7;
                color: #7b8494;
            }

            .dash-age {
                display: inline-block;
                border-radius: 8px;
                padding: 3px 11px;
                font-size: 12px;
                font-weight: 700;
                white-space: nowrap;
            }
            .dash-age-ok {
                background: #f1f3f7;
                color: #6b7686;
            }
            .dash-age-warn {
                background: #fff4e0;
                color: #b9730d;
            }
            .dash-age-crit {
                background: #fde8e8;
                color: #c9313c;
            }

            /* ---------- FEED ---------- */
            .dash-feed {
                list-style: none;
                padding: 0;
                margin: 0;
                max-height: 340px;
                overflow-y: auto;
            }
            .dash-feed li {
                display: flex;
                align-items: flex-start;
                gap: 12px;
                padding: 12px 4px;
                border-bottom: 1px solid #f5f7fa;
                cursor: pointer;
                border-radius: 8px;
            }
            .dash-feed li:last-child {
                border-bottom: 0;
            }
            .dash-feed li:hover {
                background: #fbfcff;
            }
            .dash-feed-ico {
                flex: 0 0 auto;
                width: 40px;
                height: 40px;
                border-radius: 11px;
                background: #f4f6fb;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .dash-feed-ico img {
                width: 24px;
                height: 24px;
                object-fit: contain;
            }
            .dash-feed-body {
                min-width: 0;
                flex: 1 1 auto;
            }
            .dash-feed-title {
                display: flex;
                align-items: center;
                gap: 8px;
                font-weight: 600;
                font-size: 13.5px;
                color: var(--dash-ink);
            }
            .dash-feed-sub {
                font-size: 12.5px;
                color: var(--dash-muted);
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .dash-feed-time {
                font-size: 11.5px;
                color: #aab2c0;
                margin-top: 1px;
            }

            /* ---------- VACIO ---------- */
            .dash-empty {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                gap: 10px;
                padding: 38px 20px;
                color: var(--dash-muted);
                font-size: 13.5px;
                text-align: center;
            }
            .dash-empty i {
                font-size: 26px;
                color: #cfd6e2;
            }

            @media (max-width: 575px) {
                .dash-card-head,
                .dash-table thead th,
                .dash-table tbody td {
                    padding-left: 16px;
                    padding-right: 16px;
                }
                .dash-kpi-value {
                    font-size: 24px;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
        <div class="main-content" style="min-height: 694px;">
            <Start:Start/>
        </div>
    </body>
</html>
