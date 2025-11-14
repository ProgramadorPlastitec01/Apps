$(document).ready(function(){
  $(".label_sc").change(function(){
      var id_persona = $(this).attr("name");
      var td_val = $(this).val();
      var opc = 11;
      $.ajax({
         data: {
             'dcm':id_persona,
             'sdcl':td_val,
             'opc':opc
         },
         type: 'POST',
         url: 'Personal'
      }).done(function(data, textStatus, jqXHR){
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})