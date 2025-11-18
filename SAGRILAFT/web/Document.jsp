<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_Document.tld" prefix="Document" %>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="tld_alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentos | SGLT</title>

        <script type = "text/javascript" >
            history.pushState(null, null, 'Document.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Document.jsp');
            });
        </script>

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png"/>
        <link rel="stylesheet" href="Interfaz/Contenido/Fonts/FontStyle.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.4/xlsx.full.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js"></script>
        <style>
            .nav-link .active {
                background: #5ecbeb;
            }
            .signature-pad {
                margin-bottom: 20px;
            }

            .canvas-container {
                margin-bottom: 20px;
            }

            canvas {
                border: 1px solid #000;
            }

            button {
                margin-top: 10px;
            }
        </style>

    </head>
    <!--<body class="sidebar-mini">-->
    <body>
        <!--<button onclick="cargarDatos()">Cargar datos</button>-->


        <!-- Lottie Loader -->
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Base.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Document:Documents/>
                </div>
            </div>
        </div>
        <tld_alert:AlertModule/>

        <script>
            function moving(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("ListAttach").value;
                if (content.includes(id)) {
                    document.getElementById("ListAttach").value = content.replace(id, "");
                } else {
                    document.getElementById("ListAttach").value += id;
                }
            }

        </script>

        <script>
            document.getElementById('queryType').addEventListener('change', function () {
                var queryTypeText = document.getElementById('queryTypeText');
                queryTypeText.textContent = this.checked ? 'Internacional' : 'Nacional';
            });
        </script>

        <script>
            function timer() {
                $("#swal-5").ready(function () {
                    swal({
                        title: 'Favor espere, se esta enviando correo!',
                        text: '<i class="fas fa-spinner fa-spin" style="font-size: 50px;color: #00281b;"></i>',
                        icon: 'warning',
                        buttons: false,
                        showConfirmButton: false,
                        allowEscapeKey: false,
                        dangerMode: true,
                        html: true,
                    });
                });
            }
        </script>

        <script>
            const textCanvas = document.getElementById('signature-canvas');
            const contextText = textCanvas.getContext('2d');
            const nameInput = document.getElementById('name-input');
            const fontStyleInput = document.getElementById('font-style-select');

            nameInput.addEventListener('input', () => {
                const name = nameInput.value;
                const fontStyle = fontStyleInput.value;
                contextText.clearRect(0, 0, textCanvas.width, textCanvas.height);
                contextText.font = `bold 60px ${fontStyle}`;
                contextText.fillText(name, 10, 100);
            });

            fontStyleInput.addEventListener('change', () => {
                const name = nameInput.value;
                const fontStyle = fontStyleInput.value;
                contextText.clearRect(0, 0, textCanvas.width, textCanvas.height);
                contextText.font = `bold 60px ${fontStyle}`;
                contextText.fillText(name, 0, 100);
            });


            window.onload = function () {
                var nameInput = document.getElementById('name-input');
                nameInput.dispatchEvent(new Event('input'));
            };
        </script>
        <script>
            const textCanvasx = document.getElementById('signature-canvasBoss');
            const contextTextx = textCanvasx.getContext('2d');
            const nameInputx = document.getElementById('name-inputx');
            const fontStyleInputx = document.getElementById('font-style-selectx');

            nameInputx.addEventListener('input', () => {
                const name = nameInputx.value;
                const fontStyle = fontStyleInputx.value;
                contextTextx.clearRect(0, 0, textCanvasx.width, textCanvasx.height);
                contextTextx.font = `bold 60px ${fontStyle}`;
                contextTextx.fillText(name, 10, 100);
            });

            fontStyleInputx.addEventListener('change', () => {
                const name = nameInputx.value;
                const fontStyle = fontStyleInputx.value;
                contextTextx.clearRect(0, 0, textCanvasx.width, textCanvasx.height);
                contextTextx.font = `bold 60px ${fontStyle}`;
                contextTextx.fillText(name, 0, 100);
            });


            window.onload = function () {
                var nameInputx = document.getElementById('name-inputx');
                nameInputx.dispatchEvent(new Event('input'));
                var nameInput = document.getElementById('name-input');
                nameInput.dispatchEvent(new Event('input'));
//                var nameInput = document.getElementById('name-input-v');
//                nameInput.dispatchEvent(new Event('input'));
            };
        </script>
        <script>
            const textCanvasv = document.getElementById('sigAgree');
            const contextTextV = textCanvasv.getContext('2d');
            const nameInputV = document.getElementById('nameInputV');
            const fontStyleInputV = document.getElementById('fontSelectV');

            nameInputV.addEventListener('input', () => {
                const name = nameInputV.value;
                const fontStyle = fontStyleInputV.value;
                contextTextV.clearRect(0, 0, textCanvasv.width, textCanvasv.height);
                contextTextV.font = `bold 60px ${fontStyle}`;
                contextTextV.fillText(name, 0, 100);
            });

            fontStyleInputV.addEventListener('change', () => {
                const name = nameInputV.value;
                const fontStyle = fontStyleInputV.value;
                contextTextV.clearRect(0, 0, textCanvasv.width, textCanvasv.height);
                contextTextV.font = `bold 60px ${fontStyle}`;
                contextTextV.fillText(name, 0, 100);
            });


            window.onload = function () {
//                var nameInputx = document.getElementById('name-inputx');
//                nameInputx.dispatchEvent(new Event('input'));
//                var nameInput = document.getElementById('name-input');
//                nameInput.dispatchEvent(new Event('input'));
                var nameInputV = document.getElementById('nameInputV');
                nameInputV.dispatchEvent(new Event('input'));
            };
        </script>
        <script>
            function Imprimir(id) {
                var contenedor = document.getElementById("Imprimir" + id).innerHTML;

                var frame = document.createElement("iframe");
                frame.name = "frame1";
                frame.style.position = "absolute";
                frame.style.top = "-1000000px";
                document.body.appendChild(frame);

                frame.onload = function () {
                    frame.contentWindow.focus();
                    frame.contentWindow.print();
                    setTimeout(function () {
                        document.body.removeChild(frame);
                    }, 1000);
                };


                var doc = frame.contentDocument || frame.contentWindow.document;
                doc.open();
                doc.write('<!DOCTYPE html><html><head><title>Imprimir</title>');
                doc.write('<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css">');
                doc.write('<link rel="stylesheet" type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css">');
                doc.write('</head><body>');
                doc.write(contenedor);
                doc.write('<script src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js"><\/script>');
                doc.write('<script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"><\/script>');
                doc.write('<script src="Interfaz/Firma/assets/bezier.js"><\/script>');
                doc.write('<script src="Interfaz/Firma/jquery.signaturepad.js"><\/script>');
                doc.write('<script src="Interfaz/Firma/assets/json2.min.js"><\/script>');
                doc.write('</body></html>');
                doc.close();
                return false;
            }
            ;
        </script>

        <script>
            function toggleMenu() {
                document.getElementById("menu").classList.toggle("open");
            }
        </script>


        <script>
            function swapTypeRegister(valider) {
                var select = document.getElementById("slectorType").value;
                select = select.split("/")[1];
                if (select != valider) {
                    iziToast.info({
                        title: 'Atención!',
                        message: 'Si cambia el tipo del documento, todo el proceso que lleva el usuario se perderá.',
                        position: 'bottomRight'
                    });
                    document.getElementById("idValidSelector").value = 1;
                } else {
                    document.getElementById("idValidSelector").value = 0;
                }

            }
        </script>

        <script>
            document.getElementById('btnImprimir').addEventListener('click', () => {
                // Obtener el contenido de la sección a imprimir
                const contenido = document.getElementById('dataDocument').outerHTML;
                // Crear una nueva ventana o iframe para imprimir
                const ventanaImpresion = window.open('', '_blank', 'width=800, height=600');
                // Escribir el contenido HTML en la ventana
                let cont = `<html>
                            <head>
                                <title>Imprimir</title>
                                <style>
                                    body { 
                                        font-family: Arial, sans-serif;
                                        margin-top: 20mm;  /* Margen superior */
                                        margin-bottom: 20mm; /* Margen inferior */
                                    }
                                    #content {
                                        margin-top: 10mm;
                                        margin-bottom: 10mm;
                                    }
                                    table {
                                        width: 100%;
                                        border-collapse: collapse; /* Evitar bordes duplicados */
                                    }
                                    table, th, td {
                                        border: 1px solid black; /* Borde negro */
                                    }
                                    th, td {
                                        padding: 8px; /* Espaciado interno */
                                        text-align: left; /* Alineación del texto */
                                    }
                                </style>
                            </head>
                            <body>
                                <div id="content">XXXDATAXXX</div>
                            </body>
                        </html>`;
                cont = cont.replace('XXXDATAXXX', contenido);
                ventanaImpresion.document.write(cont);
                // Esperar a que la nueva ventana cargue el contenido y luego enviar a imprimir
                ventanaImpresion.document.close(); // Cerrar el documento
                ventanaImpresion.print(); // Imprimir
                ventanaImpresion.close(); // Cerrar la ventana después de imprimir
            });
        </script>



        <script>
            document.getElementById('formConcluir').addEventListener('submit', function (e) {
                e.preventDefault(); // Detiene el envío automático

                swal({
                    title: "¿Desea concluir este documento?",
                    text: "Una vez concluido no podrá modificarlo.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Sí, concluir",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function (isConfirm) {
                            if (isConfirm) {
                                document.getElementById('formConcluir').submit();
                            }
                        });
            });
        </script>



        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/ExportExcel.js"></script>
        <script src="Interfaz/Contenido/assets/js/SignatureSecond.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
