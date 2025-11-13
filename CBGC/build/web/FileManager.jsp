<%@page import="java.io.FileFilter"%>
<%@page import="java.io.File"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/css/attach.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <title>Gestor de Archivos</title>
    </head>
    <body class="bg-light">

        <!-- Menú lateral -->
        <jsp:include page="Menu.jsp" />

        <div class="main-content" style="min-height: 694px;">
            <section class='section'>
                <div class='section-header'>
                    <h1>Gestor de Archivos</h1>
                </div>

                <div class='section-body'>
                    <div class='row'>
                        <div class='col-12'>
                            <div class='card'>
                                <%
                                    HttpSession sesion = pageContext.getSession();
                                    String Permission = "";
                                    try {
                                        Permission = sesion.getAttribute("Permisos").toString();
                                    } catch (Exception e) {
                                        Permission = "";
                                    }
                                    String loteSeleccionado = request.getParameter("lote");
                                    String appPath = application.getRealPath("/");
                                    String uploadPath = appPath + "uploads";
                                    // Si no hay lote seleccionado, usar diseño con flexbox
                                    String claseCardBody = (loteSeleccionado == null)
                                            ? "card-body d-flex justify-content-between"
                                            : "card-body";
                                %>
                                <div class="<%= claseCardBody%>">
                                    <!-- Formulario de subida -->
                                    <Alert:Alert/>
                                    <!-- Listado de carpetas o archivos -->
                                    <%
                                        if (loteSeleccionado == null) {
                                            // === VISTA 1: carpetas ===
                                            File baseDir = new File(uploadPath);
                                            if (baseDir.exists()) {
                                                File[] carpetas = baseDir.listFiles(new FileFilter() {
                                                    @Override
                                                    public boolean accept(File file) {
                                                        return file.isDirectory();
                                                    }
                                                });
                                    %>
                                    <div style="width: 66%">
                                        <h5 class="mt-3 mb-3">Lotes disponibles</h5>

                                        <div class="row g-3">
                                            <%        if (carpetas != null && carpetas.length > 0) {
                                                    for (File carpeta : carpetas) {
                                            %>
                                            <div class="col-6 col-md-4 mb-3 col-lg-3">
                                                <div class="card shadow-sm border-1 h-100 text-center p-3 hover-card border border-warning rounded">
                                                    <a href="FileManager.jsp?lote=<%= carpeta.getName()%>" class="text-decoration-none text-dark">
                                                        <i class="fas fa-folder" style="color:#f5e047ad; font-size:48px;"></i>
                                                        <h6 class="mt-2 mb-0"><%= carpeta.getName()%></h6>
                                                    </a>
                                                    <!-- Botón editar -->
                                                    <div class="EditFolderBatch">
                                                        <button type="button" class="btn btn-green btn-sm mt-2"
                                                                onclick="openRenameModal('<%= carpeta.getName()%>')">
                                                            <i class="fas fa-edit"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>

                                            <%
                                                }
                                            } else {
                                            %>
                                            <div class="col-12 text-center text-muted">
                                                <p>No existen carpetas de lotes todavía.</p>
                                            </div>
                                            <%
                                                }
                                            %>
                                        </div>
                                        <%
                                        } else {
                                        %>
                                        <p class="alert alert-warning mt-3">No se ha creado la carpeta principal de uploads.</p>
                                        <%
                                            }
                                        } else {
                                            // === VISTA 2: archivos dentro del lote ===
                                            File carpeta = new File(uploadPath + File.separator + loteSeleccionado);
                                            if (carpeta.exists()) {
                                                File[] archivos = carpeta.listFiles();
                                        %>
                                        <div class="d-flex justify-content-between align-items-center mt-4 mb-3">
                                            <h5><i class="fas fa-folder-open" style="color: #d9ad0d; font-size: 21px"></i> Archivos del lote: <%= loteSeleccionado%></h5>
                                            <a href="FileManager.jsp" class="btn btn-outline-secondary btn-sm"><i class="fas fa-arrow-left"></i> Volver</a>
                                        </div>

                                        <table class="table table-bordered table-hover align-middle">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>Nombre del archivo</th>
                                                    <th class="text-center" style="width: 150px;">Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    if (archivos != null && archivos.length > 0) {
                                                        for (File archivo : archivos) {
                                                            String nombre = archivo.getName();
                                                %>
                                                <tr>
                                                    <td><%= nombre%></td>
                                                    <td class="text-center ">
                                                        <div class="d-flex ">
                                                            <a href="<%= "uploads/" + loteSeleccionado + "/" + nombre%>"
                                                               class="btn btn-success btn-sm" download>Descargar</a>
                                                            <a href="DeleteFileServlet?lote=<%= loteSeleccionado%>&file=<%= nombre%>&msg=delete_success"
                                                               class="btn btn-danger btn-sm ml-2"
                                                               onclick="return confirm('¿Seguro que deseas eliminar este archivo?');">Eliminar</a>

                                                        </div>
                                                    </td>
                                                </tr>
                                                <%
                                                    }
                                                } else {
                                                %>
                                                <tr>
                                                    <td colspan="2" class="text-center text-muted">No hay archivos en esta carpeta.</td>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </tbody>
                                        </table>
                                        <%
                                        } else {
                                        %>
                                        <div class="alert alert-warning mt-3">La carpeta del lote no existe.</div>
                                        <a href="FileManager.jsp" class="btn btn-outline-secondary btn-sm">⬅️ Volver</a>
                                        <%
                                                }
                                            }
                                        %>

                                    </div>
                                    <%
                                        // Mostrar el formulario solo si no se ha seleccionado ningún lote
                                        if (loteSeleccionado == null) {
                                            if (Permission.contains("(2)")) {

                                    %>
                                    <div class="DivBoxS">
                                        <form action="FileManagerServlet" method="post" class="m-2" enctype="multipart/form-data">
                                            <div class="form-group">
                                                <label>Nombre del Lote:</label>
                                                <input list="listaLotes" name="lote" id="lote" class="form-control" placeholder="Escriba o seleccione un lote" required>
                                                <datalist id="listaLotes">
                                                    <%                                                        // Obtener las carpetas ya registradas
                                                        File baseDir = new File(uploadPath);
                                                        if (baseDir.exists()) {
                                                            File[] carpetas = baseDir.listFiles(new FileFilter() {
                                                                @Override
                                                                public boolean accept(File file) {
                                                                    return file.isDirectory();
                                                                }
                                                            });
                                                            if (carpetas != null && carpetas.length > 0) {
                                                                for (File carpeta : carpetas) {
                                                    %>
                                                    <option value="<%= carpeta.getName()%>"></option>
                                                    <%
                                                                }
                                                            }
                                                        }
                                                    %>
                                                </datalist>
                                            </div>


                                            <div class="form-group">
                                                <label>Subir archivos:</label>
                                                <input type="file" name="files" multiple class="form-control" 
                                                       accept=".pdf,.doc,.docx,.xls,.xlsx,.png,.jpg,.jpeg">
                                            </div>

                                            <div class="text-center mb-2">
                                                <button type="submit" class="btn btn-green">Subir Archivos</button>
                                            </div>
                                        </form>
                                    </div>
                                    <%
                                            } // fin del if (loteSeleccionado == null)
                                        } // fin del if (loteSeleccionado == null)
                                    %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <!-- Modal para renombrar carpeta -->
        <div class="modal fade" id="renameModal" tabindex="-1" aria-labelledby="renameModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content rounded-3 shadow">
                    <!-- CABECERA -->
                    <div class="modal-header" style="background-color: #f8ea82;">
                        <h5 class="modal-title" id="renameModalLabel">Renombrar carpeta</h5>
                        <!-- BOTÓN DE CIERRE CORRECTO -->
                    </div>

                    <!-- FORMULARIO -->
                    <form action="RenameFolderServlet" method="post">
                        <div class="modal-body">
                            <input type="hidden" id="oldName" name="oldName">
                            <div class="form-group mb-3">
                                <label for="newName">Nuevo nombre del lote:</label>
                                <input type="text" id="newName" name="newName" class="form-control" required>
                            </div>
                        </div>

                        <!-- PIE DEL MODAL -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-green">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script>
            function openRenameModal(folderName) {
                document.getElementById('oldName').value = folderName;
                document.getElementById('newName').value = folderName;
                var modalElement = document.getElementById('renameModal');
                var renameModal = new bootstrap.Modal(modalElement);
                renameModal.show();
            }
            function CloseRenameModal() {
                var modalElement = document.getElementById('renameModal');
                var renameModal = new bootstrap.Modal(modalElement);
                renameModal.close();
            }
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
            <%
                String msg = request.getParameter("msg");
                if (msg != null) {
            %>
                switch ("<%= msg%>") {
                    case "upload_success":
                        iziToast.success({
                            title: '¡Éxito!',
                            message: 'Los archivos se subieron correctamente.',
                            position: 'bottomRight'
                        });
                        break;
                    case "delete_success":
                        iziToast.info({
                            title: 'Eliminado',
                            message: 'El archivo fue eliminado correctamente.',
                            position: 'bottomRight'
                        });
                        break;
                    case "folder_created":
                        iziToast.success({
                            title: '¡Lote creado!',
                            message: 'La carpeta del lote fue creada con éxito.',
                            position: 'bottomRight'
                        });
                        break;
                    case "error_upload":
                        iziToast.error({
                            title: 'Error',
                            message: 'Ocurrió un problema al subir los archivos.',
                            position: 'bottomRight'
                        });
                        break;
                    case "download":
                        iziToast.success({
                            title: 'Descarga iniciada',
                            message: 'El archivo se está descargando.',
                            position: 'bottomRight'
                        });
                        break;
                    case "rename_success":
                        iziToast.success({
                            title: '¡Renombrado!',
                            message: 'El nombre del lote se cambió correctamente.',
                            position: 'bottomRight'
                        });
                        break;
                    case "rename_exists":
                        iziToast.warning({
                            title: 'Ya existe',
                            message: 'Ya existe una carpeta con ese nombre.',
                            position: 'bottomRight'
                        });
                        break;
                    case "error_rename":
                        iziToast.error({
                            title: 'Error',
                            message: 'No se pudo cambiar el nombre de la carpeta.',
                            position: 'bottomRight'
                        });
                        break;
                    default:
                        iziToast.warning({
                            title: 'Aviso',
                            message: 'Acción desconocida.',
                            position: 'bottomRight'
                        });
                        break;
                }
            <% }%>
            });

        </script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/BoostratModel.js"></script>

    </body>
</html>
