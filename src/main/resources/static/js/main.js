/**
 * 
 */

	$(document).ready(function() {
			$('#enviar').click(function() {
				checked = $("input[type=checkbox]:checked").length;

				if (!checked) {
					alert("Debes elegir al menos una categoria.");
					return false;
				}

			});
			
			$('#modificar').click(function() {
				checked = $("input[type=checkbox]:checked").length;

				if (!checked) {
					alert("Debes elegir al menos un usuario.");
					return false;
				}

			});
			
		    $("#radioUsuario").hide();   
		    $("#perfilUsuario").hide();   
		    
		    
		    
		});
		