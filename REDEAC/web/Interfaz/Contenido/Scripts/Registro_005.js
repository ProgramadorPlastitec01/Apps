$(document).ready(function(){
  $(".td_fecha").change(function(){
      var id_fecha = $(this).attr("id");
      var td_val = $(this).val();
      var opc = 10;
//      alert(id_fecha);
      $.ajax({
         data: {
             'id_fecha':id_fecha,
             'td_val':td_val,
             'opc':opc
         },
         type: 'POST',
         url: 'Registro'
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
  $(".td_fechaVe").change(function(){
      var idC = $(this).attr("id");
      var td_valF = $(this).val();
      var opc = 15;
//      alert(idC);
//      alert(td_valF);
      $.ajax({
         data: {
             'idC':idC,
             'txt_fechaC':td_valF,
             'opc':opc
         },
         type: 'POST',
         url: 'Registro'
      }).done(function(data, textStatus, jqXHR){
//          console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})
$(document).ready(function(){
  $(".td_fechaVa").change(function(){
      var idC = $(this).attr("id");
      var td_valF = $(this).val();
      var opc = 16;
//      alert(idC);
//      alert(td_valF);
      $.ajax({
         data: {
             'idC':idC,
             'txt_fechaC':td_valF,
             'opc':opc
         },
         type: 'POST',
         url: 'Registro'
      }).done(function(data, textStatus, jqXHR){
//          console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})
$(document).ready(function(){
  $(".td_responsable").change(function(){
      var idR = $(this).attr("id");
      var td_valR = $(this).val();
      var opc = 17;
//      alert(idC);
//      alert(td_valF);
      $.ajax({
         data: {
             'idR':idR,
             'txt_responsable':td_valR,
             'opc':opc
         },
         type: 'POST',
         url: 'Registro'
      }).done(function(data, textStatus, jqXHR){
//          console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})
$(document).ready(function(){
  $(".td_responsableV").change(function(){
      var idR2 = $(this).attr("id");
      var td_valR2 = $(this).val();
      var opc = 19;
//      alert(idC);
//      alert(td_valF);
      $.ajax({
         data: {
             'idR2':idR2,
             'txt_responsableV':td_valR2,
             'opc':opc
         },
         type: 'POST',
         url: 'Registro'
      }).done(function(data, textStatus, jqXHR){
//          console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})
$(document).ready(function(){
  $(".td_fechaV").change(function(){
      var idF = $(this).attr("id");
      var td_valF = $(this).val();
      var opc = 20;
//      alert(idC);
//      alert(td_valF);
      $.ajax({
         data: {
             'idF':idF,
             'txt_fechaV':td_valF,
             'opc':opc
         },
         type: 'POST',
         url: 'Registro'
      }).done(function(data, textStatus, jqXHR){
//          console.log(data); 
//         if(data != 1){
//             window.location.replace()
//         }
      }).fail(function(jqXHR, textStatus, errorThrow){
          
      });
  });
})


