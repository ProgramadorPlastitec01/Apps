$(document).ready(function(){

	// obtenemos el valor actual de la burbuja
	var valor = parseInt($('.burbuja').html());
	var $burbuja = $('.burbuja');

	// al presionar algún botón del div "botones"
	$('#contenedor').on('click',function(event){

		// almacenamos el valor que tenía la burbuja antes del click
		var valorPrevio = valor;

		// obtenemos el nombre del botón presionado
		var boton = $(event.target).attr('id');

		if (boton == 'incrementar') {
			valor++;	
		} else{

			// no permitimos decrementar si ya está el valor en 0
			if (valor > 0) {
				if (boton == 'decrementar') {
					valor--;
				} else{
					valor = 0;
				};
			}
		};
		
		// si hubo un cambio en el valor
		if (valor != valorPrevio) {
			agrandar($burbuja);			
		} 
	});

	// función que pasado un tiempo, quita la clase "agrandar" del elemento
	function removeAnimation(){
		setTimeout(function() {
			$burbuja.removeClass('agrandar')
		}, 1000);
	}

	// función que modifica el valor de la burbuja y la agranda
	function agrandar ($elemento) {
		$elemento.html(valor).addClass('agrandar');
		removeAnimation();
	}
});