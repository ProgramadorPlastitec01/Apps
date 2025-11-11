//
//let contador = 1; // Contador para identificar cada grupo de campos de persona
//
//function agregarPersona() {
//  contador++; // Incrementa el contador
//  
//  const formulario = document.getElementById('formulario');
//  const nuevaPersona = document.createElement('div');
//  nuevaPersona.id = 'persona' + contador;
//  nuevaPersona.className = 'd-flex'; // Agregando la clase 'd-flex'
//  
//  nuevaPersona.innerHTML = `
//    <div class="col-lg-4">
//      <div class="mt-4">
//        <h6>Nombre completo <span class="text-danger">*</span></h6>
//      </div>
//      <div class="mt-2">
//        <input type="text" class="form-control" name="nombre${contador}" placeholder="Nombre completo" data-toggle="tooltip" data-placement="top" title="Denominación Social o Nombre completo">
//      </div>
//    </div>
//    <div class="col-lg-4">
//      <div class="mt-4">
//        <h6>Tipo documento <span class="text-danger">*</span></h6>
//      </div>
//      <div class="d-flex">
//        <div class="col-lg-4" style="margin-left: -15px;" data-toggle="tooltip" data-placement="top" title="">
//          <select class="form-control" name="tipo_documento${contador}">
//            <option value="">Tipo</option>
//            <option value="PP">PP</option>
//            <option value="CC">CC</option>
//            <option value="CE">CE</option>
//            <option value="TI">TI</option>
//            <option value="NIT">NIT</option>
//          </select>
//        </div>
//        <input type="text" class="form-control col-lg-9" name="numero_documento${contador}" placeholder="Número de documento" data-toggle="tooltip" data-placement="top" title="">
//      </div>
//    </div>
//    <div class="col-lg-2">
//      <div class="text-center mt-4">
//        <h6>¿Es PEP? <span class="text-danger">*</span></h6>
//      </div>
//      <div class="d-flex mt-2 justify-content-center">
//        <input type="radio" name="es_pep${contador}" value="si"> &nbsp; Si &nbsp;&nbsp;
//        <input type="radio" name="es_pep${contador}" value="no"> &nbsp; No &nbsp;&nbsp;
//      </div>
//    </div>
//    <div class="col-lg-2">
//      <div class="mt-4">
//        <h6>% Participación <span class="text-danger">*</span></h6>
//      </div>
//      <div class="mt-2">
//        <input type="text" class="form-control" name="porcentaje_participacion${contador}" placeholder="% Participación" data-toggle="tooltip" data-placement="top" title="Porcentaje de participación">
//      </div>
//    </div>
//  `;
//  
//  // Agregar botón de eliminar si no es la primera persona
//  if (contador > 1) {
//    const botonEliminar = document.createElement('button');
//    botonEliminar.textContent = 'Eliminar';
//    botonEliminar.onclick = function() {
//      formulario.removeChild(nuevaPersona);
//    };
//    nuevaPersona.appendChild(botonEliminar);
//  }
//  
//  formulario.appendChild(nuevaPersona);
//}
