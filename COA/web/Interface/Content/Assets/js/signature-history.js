/*
 * Historial de firmas (requerimiento 1.1.U03): modal con línea de tiempo al
 * estilo "audit trail" (Título / ID / Estado y, por evento, fecha, hora,
 * quién, IP, aplicativo y equipo). Lo consume SignatureHistoryServlet.
 *
 * Uso: abrirHistorialFirmas('CERTIFICADO' | 'SOPORTE' | 'BATCH_RECORD', ref)
 *
 * Autocontenido (estilos propios, sin depender de Bootstrap) porque se usa en
 * páginas con versiones distintas de Bootstrap (Visual/GenerateReport vs
 * FileManager).
 */
(function () {
    var CSS = ''
        + '.sgh-overlay{position:fixed;inset:0;background:rgba(0,0,0,.45);z-index:10050;display:flex;align-items:center;justify-content:center;padding:16px;}'
        + '.sgh-modal{background:#fff;border-radius:10px;width:100%;max-width:720px;max-height:88vh;display:flex;flex-direction:column;box-shadow:0 10px 40px rgba(0,0,0,.25);font-family:inherit;color:#212529;}'
        + '.sgh-header{display:flex;justify-content:space-between;align-items:center;padding:14px 20px;border-bottom:1px solid #e5e7eb;}'
        + '.sgh-header h5{margin:0;font-size:16px;font-weight:700;color:#0b0025;}'
        + '.sgh-close{border:none;background:none;font-size:24px;line-height:1;cursor:pointer;color:#6b7280;}'
        + '.sgh-body{overflow-y:auto;padding:18px 22px;}'
        + '.sgh-meta{display:grid;grid-template-columns:150px 1fr;row-gap:10px;column-gap:16px;padding-bottom:16px;border-bottom:1px solid #e5e7eb;font-size:13px;}'
        + '.sgh-meta dt{font-weight:700;letter-spacing:.04em;text-transform:uppercase;color:#374151;margin:0;}'
        + '.sgh-meta dd{margin:0;word-break:break-word;}'
        + '.sgh-dot{display:inline-block;width:9px;height:9px;border-radius:50%;margin-right:6px;vertical-align:middle;}'
        + '.sgh-st-completo{background:#28a745;}.sgh-st-pendiente{background:#e0a800;}.sgh-st-gestion{background:#17a2b8;}.sgh-st-eliminado{background:#dc3545;}'
        + '.sgh-event{display:grid;grid-template-columns:80px 130px 1fr;column-gap:16px;padding:16px 0;border-bottom:1px solid #f1f3f5;font-size:13px;}'
        + '.sgh-event:last-child{border-bottom:none;}'
        + '.sgh-icon{text-align:center;color:#374151;}'
        + '.sgh-icon i{font-size:20px;display:block;margin-bottom:4px;}'
        + '.sgh-icon span{font-size:10px;font-weight:700;letter-spacing:.06em;text-transform:uppercase;}'
        + '.sgh-ev-firmado .sgh-icon{color:#1e7e34;}.sgh-ev-finalizado .sgh-icon{color:#0069d9;}'
        + '.sgh-when b{display:block;}'
        + '.sgh-when small{color:#6b7280;}'
        + '.sgh-what b{display:block;font-weight:600;}'
        + '.sgh-what div{color:#4b5563;margin-top:2px;}'
        + '.sgh-what .sgh-detail{color:#6b7280;font-style:italic;}'
        + '.sgh-empty{padding:30px;text-align:center;color:#6b7280;}'
        + '.sgh-footer{padding:10px 20px;border-top:1px solid #e5e7eb;text-align:right;font-size:12px;color:#6b7280;}'
        + '@media (max-width:560px){.sgh-meta{grid-template-columns:1fr;row-gap:2px;}.sgh-meta dd{margin-bottom:8px;}.sgh-event{grid-template-columns:56px 1fr;row-gap:6px;}.sgh-what{grid-column:2;}}';

    var ICONOS = {
        REGISTRADO: {icono: 'fas fa-file-alt', texto: 'Registrado'},
        FINALIZADO: {icono: 'fas fa-paper-plane', texto: 'Finalizado'},
        FIRMADO: {icono: 'fas fa-signature', texto: 'Firmado'}
    };

    function esc(v) {
        return String(v == null ? '' : v)
            .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;').replace(/'/g, '&#39;');
    }

    function asegurarEstilos() {
        if (document.getElementById('sgh-styles')) {
            return;
        }
        var style = document.createElement('style');
        style.id = 'sgh-styles';
        style.textContent = CSS;
        document.head.appendChild(style);
    }

    function cerrar() {
        var o = document.getElementById('sgh-overlay');
        if (o) {
            o.parentNode.removeChild(o);
        }
        document.removeEventListener('keydown', onKey);
    }

    function onKey(e) {
        if (e.key === 'Escape') {
            cerrar();
        }
    }

    function render(body, data) {
        var html = '<dl class="sgh-meta">'
            + '<dt>Título</dt><dd>' + esc(data.titulo) + '</dd>'
            + '<dt>Tipo</dt><dd>' + esc(data.tipo) + '</dd>'
            + '<dt>ID documento</dt><dd>' + esc(data.documentoId) + '</dd>'
            + (data.lote ? '<dt>Lote</dt><dd>' + esc(data.lote) + '</dd>' : '')
            + '<dt>Estado</dt><dd><span class="sgh-dot sgh-st-' + esc(data.estadoClase || 'gestion') + '"></span>' + esc(data.estado) + '</dd>'
            + '</dl>';

        var eventos = data.eventos || [];
        if (eventos.length === 0) {
            html += '<div class="sgh-empty"><i class="fas fa-info-circle"></i> Aún no hay eventos registrados para este documento.</div>';
        } else {
            eventos.forEach(function (ev) {
                var ic = ICONOS[ev.evento] || {icono: 'fas fa-circle', texto: ev.etiqueta};
                var origen = [];
                if (ev.ip) {
                    origen.push('IP: ' + esc(ev.ip));
                }
                if (ev.aplicativo) {
                    origen.push('Aplicativo: ' + esc(ev.aplicativo));
                }
                if (ev.equipo) {
                    origen.push(esc(ev.equipo));
                }
                html += '<div class="sgh-event sgh-ev-' + esc(String(ev.evento || '').toLowerCase()) + '">'
                    + '<div class="sgh-icon"><i class="' + ic.icono + '"></i><span>' + esc(ic.texto) + '</span></div>'
                    + '<div class="sgh-when"><b>' + esc(ev.fecha) + '</b><span>' + esc(ev.hora) + '</span></div>'
                    + '<div class="sgh-what"><b>' + esc(ev.descripcion) + '</b>'
                    + (ev.rol ? '<div>Rol: ' + esc(ev.rol) + '</div>' : '')
                    + (origen.length ? '<div>' + origen.join(' · ') + '</div>' : '')
                    + (ev.detalle ? '<div class="sgh-detail">' + esc(ev.detalle) + '</div>' : '')
                    + '</div></div>';
            });
        }
        body.innerHTML = html;
    }

    window.abrirHistorialFirmas = function (tipo, ref) {
        asegurarEstilos();
        cerrar();

        var overlay = document.createElement('div');
        overlay.id = 'sgh-overlay';
        overlay.className = 'sgh-overlay';
        overlay.innerHTML = '<div class="sgh-modal" role="dialog" aria-modal="true" aria-labelledby="sgh-title">'
            + '<div class="sgh-header"><h5 id="sgh-title"><i class="fas fa-history"></i> Historial de firmas</h5>'
            + '<button type="button" class="sgh-close" aria-label="Cerrar">&times;</button></div>'
            + '<div class="sgh-body"><div class="sgh-empty"><i class="fas fa-spinner fa-spin"></i> Cargando historial...</div></div>'
            + '<div class="sgh-footer">Fechas y horas registradas por el servidor de COA.</div>'
            + '</div>';
        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) {
                cerrar();
            }
        });
        overlay.querySelector('.sgh-close').addEventListener('click', cerrar);
        document.body.appendChild(overlay);
        document.addEventListener('keydown', onKey);

        var body = overlay.querySelector('.sgh-body');
        fetch('SignatureHistoryServlet?tipo=' + encodeURIComponent(tipo) + '&ref=' + encodeURIComponent(ref),
            {credentials: 'same-origin'})
            .then(function (res) {
                return res.json().then(function (json) {
                    if (!res.ok) {
                        throw new Error(json && json.error ? json.error : 'Error ' + res.status);
                    }
                    return json;
                });
            })
            .then(function (data) {
                render(body, data);
            })
            .catch(function (err) {
                body.innerHTML = '<div class="sgh-empty" style="color:#c62828;"><i class="fas fa-exclamation-triangle"></i> No fue posible consultar el historial de firmas. ' + esc(err.message) + '</div>';
            });
    };
})();
