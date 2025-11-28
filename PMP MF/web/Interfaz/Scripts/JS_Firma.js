$(document).ready(function(){
  $(".Td_firma_prf").change(function(){
      var id_orden = $(this).attr("id");
      var td_val = $(this).val();
      var opc = 35;
//      alert(id_fecha);
      $.ajax({
         data: {
             'idRequisicion':id_orden,
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
$(document).ready(function(){
  $(".Td_firma_mtf").change(function(){
      var id_orden = $(this).attr("id");
      var td_val = $(this).val();
      var opc = 35;
//      alert(id_fecha);
      $.ajax({
         data: {
             'idRequisicion':id_orden,
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

