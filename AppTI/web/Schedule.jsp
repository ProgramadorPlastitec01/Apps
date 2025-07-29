
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld"  prefix="Alert"%>
<%@taglib uri="/WEB-INF/tlds/Tld_schedule.tld" prefix="Schedule"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fullcalendar/fullcalendar.min.css">
        <link rel="shortcut icon" href="Interface/Content/Imagen/Icon1.png" />
        <link rel="" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">

        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
        <title>R-TI-026</title>
        <style>
            h2{
                text-transform: capitalize;
            }
            a{
                text-transform: capitalize;
            }
        </style>
    </head>
    <body>
        <!--<body class="sidebar-mini">-->
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Schedule:Schedule/>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function ChangeType(Type) {
                if (Type === 1) {
                    document.getElementById("Technique").style.display = "block";
                    document.getElementById("Developer").style.display = "none";
                    document.getElementById("Input1").setAttribute("required", "true");
                    document.getElementById("Input2").removeAttribute("required");
                    document.getElementById("Input2").value = "";
                } else {
                    document.getElementById("Developer").style.display = "block";
                    document.getElementById("Technique").style.display = "none";
                    document.getElementById("Input2").setAttribute("required", "true");
                    document.getElementById("Input1").removeAttribute("required");
                    document.getElementById("Input1").value = "";
                }
            }
            function AsigmentType() {
                // Obtener el valor del input
                var Tapp = document.getElementById("Input2").value;
                var typeAppInput = document.getElementById("TypeApp");
                if (Tapp.trim() === "") {
                    typeAppInput.value = "0";
                    return;
                }
                const Part = Tapp.split("-").map(part => part.trim());
                if (Part.length === 2) {
                    const item1 = Part[0];
                    const item2 = Part[1];
                    typeAppInput.value = item1;
                    document.getElementById("Input2").value = "VERIFICACION DE CONTROL APLICATIVO " + item2;
                } else {
                    console.error("Formato inválido: No se encontró '-' en el texto seleccionado.");
                    typeAppInput.value = "0";
                }
            }
            function Masive(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("ChMonth").value;
                if (content.includes(id)) {
                    document.getElementById("ChMonth").value = content.replace(id, "");
                } else {
                    document.getElementById("ChMonth").value += id;
                }
            }
            function SaveContinue(val) {
                document.getElementById("SCN").value = val;
            }
        </script>
        <script>
            $(document).ready(function () {
                $('#colorInput').colorpicker({
                    format: 'hex'
                }).on('colorpickerChange', function (event) {
                    $(this).val(event.color.toString());
                    $('#iconContainer i').css('color', event.color.toString());
                });
            });
            $(document).ready(function () {
                $('#colorInput2').colorpicker({
                    format: 'hex'
                }).on('colorpickerChange', function (event) {
                    $(this).val(event.color.toString());
                    $('#iconContainer i').css('color', event.color.toString());
                });
            });
        </script>
        <script>
            function SignatureR026(i, vld) {
                if (vld > 0) {
                    const Id = document.getElementById("IdSc" + i).value;
                    const Activity = document.getElementById("Activity" + i).value;
                    document.getElementById("IdSg").value = Id;
                    document.getElementById("Act").value = Activity;
                    document.getElementById("Validation").value = vld;
                } else {
                    document.getElementById("IdSg").value = "";
                    document.getElementById("Act").value = "";
                }

            }
            function MasiveActivity(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("IdMasive").value;
                if (content.includes(id)) {
                    document.getElementById("IdMasive").value = content.replace(id, "");
                } else {
                    document.getElementById("IdMasive").value += id;
                }
            }
            function ActivityTextarea(nameAct) {
                const container = document.getElementById("ContAct");

                if (!nameAct) {
                    alert("El campo de actividad está vacío.");
                    return; // Detenemos la ejecución si no hay contenido
                }

                // Buscar si ya existe un elemento con este texto
                const existingItem = Array.from(container.children).find(
                        (child) => child.getAttribute("data-act") === nameAct
                );

                if (existingItem) {
                    // Si el elemento ya existe, lo eliminamos
                    container.removeChild(existingItem);
                } else {
                    // Si no existe, lo creamos y lo añadimos
                    const newItem = document.createElement("div");
                    newItem.setAttribute("data-act", nameAct); // Atributo para identificar
                    newItem.innerHTML = `<i class='fas fa-star'></i>` + nameAct; // Agregar texto y el ícono
                    container.appendChild(newItem);
                }
            }

        </script>
        <script>
            function toggleAllCheckboxes(master) {
                const checkboxes = document.querySelectorAll("table tbody input[type='checkbox']");
                const isChecked = master.checked;

                const container = document.getElementById("ContAct");
                const idMasive = document.getElementById("IdMasive");

                if (!isChecked) {
                    // Limpiar el contenido de ContAct y IdMasive cuando se desmarque el checkbox maestro
                    container.innerHTML = "";  // Eliminar todas las actividades
                    idMasive.value = "";      // Limpiar el campo de IdMasive
                }

                checkboxes.forEach(checkbox => {
                    if (checkbox.disabled) {
                        return; // Ignorar checkboxes deshabilitados
                    }

                    checkbox.checked = isChecked;

                    // Obtener valores para ejecutar las funciones MasiveActivity y ActivityTextarea
                    const id = checkbox.getAttribute("data-id");
                    const act = checkbox.getAttribute("data-act");

                    // Solo ejecutar si el checkbox está seleccionado
                    if (isChecked && id && act) {
                        MasiveActivity(id);
                        ActivityTextarea(act);
                    } else if (!isChecked && id && act) {
                        // Si se desmarca, eliminar la actividad individualmente (si es necesario)
                        const content = idMasive.value;
                        const idWithBrackets = "[" + id + "]";
                        if (content.includes(idWithBrackets)) {
                            idMasive.value = content.replace(idWithBrackets, "");
                        }

                        const transform = "<div><i class='fas fa-star'></i> " + act + "</div>";
                        if (container.innerHTML.includes(transform)) {
                            container.innerHTML = container.innerHTML.replace(transform, "");
                        }
                    }
                });
            }

        </script>
        <script type="text/javascript">
            function EnableDivView(id) {
                var content = document.getElementById("IdMasive").value;
                if (content.length > 0) {
                    const body = document.body;
                    if (document.getElementById("View" + id).style.display === "none") {
                        document.getElementById("View" + id).style.display = "block";
                    } else if (document.getElementById("View" + id).style.display === "block") {
                        document.getElementById("View" + id).style.display = "none";
                    }
                    body.classList.toggle('modal-open');
                } else {
                    iziToast.warning({
                        title: 'Alerta!',
                        message: 'Debe seleccionar minimo un registro',
                        position: 'bottomRight',
                        time: 3000
                    });
                }

            }
        </script>
        <script type="text/javascript">
            const icon = document.getElementById('myIcon');
            const input = document.getElementById('colorInput');

            icon.addEventListener('click', () => {
                input.focus();
            });

            const icon2 = document.getElementById('myIconU');
            const input2 = document.getElementById('colorInput2');

            icon2.addEventListener('click', () => {
                input2.focus();
            });
        </script>
        <Alert:Alert/>
        <script src="Interface/Content/Assets/modules/fullcalendar/fullcalendar.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/fullcalendar/locale-all.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-calendar.js"></script>
        <script src="Interface/Content/Assets/js/fullcalendar/index.global.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>
    </body>
</html>
