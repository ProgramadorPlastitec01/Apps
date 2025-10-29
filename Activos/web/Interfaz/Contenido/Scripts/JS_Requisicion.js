$(document).ready(function(){
  $(".Td_observacion").change(function(){
      var id_requisicion = $(this).attr("id");
      var td_val = $(this).val();
      var opc = 35;
//      alert(id_fecha);
      $.ajax({
         data: {
             'idRequisicion':id_requisicion,
             'txt_observacion':td_val,
             'opc':opc
         },
         type: 'POST',
         url: 'Requisicion'
      }).done(function(data, textStatus, jqXHR){
//         console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})

