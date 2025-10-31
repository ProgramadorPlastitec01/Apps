$(document).ready(function(){
  $(".prueba").change(function(){
      var id_user = $(this).attr("id");
      var txt_img = $(this).val();
      var opc = 2;
//      alert(id_fecha);
      $.ajax({
         data: {
             'id_user':id_user,
             'txt_img':txt_img,
             'opc':opc
         },
         type: 'POST',
         url: 'Perfil'
      }).done(function(data, textStatus, jqXHR){
//         console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})