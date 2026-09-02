<%@ page import="model.prodotto.ProdottoOrdine" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.CarrelloModel" %>

<!-- Reperisci il carrello già presente nella sessione, altrimenti creane uno nuovo -->
<%
	CarrelloModel carrello;
	//Accesso alla sessione thread-safe,solo un thread alla volta può eseguire il codice all'interno del blocco
	synchronized (session) {
		carrello = (CarrelloModel) session.getAttribute("carrello");
		if (carrello == null) {
			carrello = new CarrelloModel();
			session.setAttribute("carrello", carrello);
		}
	}
	Collection<?> oggettiCarrello = carrello.getCarrello();
%>

<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/carrello.css">
  	<title>Carrello</title>
</head>

<body>
  	<%@ include file="header.jsp" %>
  	<div class="carrello">
		<h1>Carrello</h1>
	  	<table id="prodotti">
		    <tr>
	        	<td>Nome</td>
				<td>Prezzo</td>
				<td>Iva</td>
				<td>Tipo</td>
				<td>Grafica</td>
				<td>Quantit&agrave;</td>
				<td></td>
		    </tr>
		    <%
				int si = 0;
				if (oggettiCarrello != null && oggettiCarrello.size() != 0) {
					si = 1;
				
				  	for (Object o : oggettiCarrello) {
				    	ProdottoOrdine prodottoOrdine = (ProdottoOrdine) o;
				    	String prezzo = prodottoOrdine.getProdottoBean().getPrezzo().toPlainString();
				
		    %>
			<tr class="productRow" id="<%= prodottoOrdine.getProdottoBean().getID() %>">
				<td><%= prodottoOrdine.getProdottoBean().getNome() %> </td>
				<td>&euro;&nbsp;<%= prezzo %> </td>
				<td><%= prodottoOrdine.getProdottoBean().getIVA() %> % </td>
				<td><%= prodottoOrdine.getProdottoBean().getTipo() %> </td>
				<td><img src="${pageContext.request.contextPath}/api/GetImage?nome=<%= prodottoOrdine.getProdottoBean().getGrafica() %>" alt="<%= prodottoOrdine.getProdottoBean().getNome() %>" class="img-cart"></td>
				<td>
	        		<form class="addForm">
						<div class="quantity-container">
                           <button class="decrement">-</button>
                           <input type="number" name="quantita" min="0" max="100" value="<%= prodottoOrdine.getQuantita() %>" class="inputQuan">
                           <button class="increment">+</button>
                        </div>
                        <input type="hidden" name="ID" value="<%= prodottoOrdine.getProdottoBean().getID() %>">
		        	</form>
		      	</td>
		      	<td>
				<form class="rmvForm" >
					<input type="hidden" name="ID" value="<%= prodottoOrdine.getProdottoBean().getID() %>">
					<button type="submit" class="button22">Elimina</button>
				</form>
				</td>
			</tr>
		    <%
				} } else {
		    %>
		    <tr>
	   			<td colspan="7">Nessun prodotto nel carrello</td>
		    </tr>
		    <% 
		    	} 
		    %>
		</table>
		<% 
			if (si > 0) 
		%>
		<div id="checkout">
			<button type="submit" class="button222" onclick="document.location.href='${pageContext.request.contextPath}/user/Checkout'" >Checkout</button>
 		</div>
	</div>
	<%@ include file="footer.jsp" %>
	
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
  	<script>
	  	//Esegui questo codice solo quando il documento è stato caricato e pronto
	    $(document).ready(function() {
   	 		$(".quantity-container .increment").click(function(event) {
	                event.preventDefault();
	                var input = $(this).siblings("input[type='number']");
	                var currentValue = parseInt(input.val());
	                if (currentValue < parseInt(input.attr('max'))) {
	                    input.val(currentValue + 1).trigger('change');
	                }
	            });

            $(".quantity-container .decrement").click(function(event) {
                event.preventDefault();
                var input = $(this).siblings("input[type='number']");
                var currentValue = parseInt(input.val());
                if (currentValue > parseInt(input.attr('min'))) {
                    input.val(currentValue - 1).trigger('change');
                }
            });
	    	
	    	$(".inputQuan").on("change", function(event) {
	        	var form = $(this).closest("form");
	    	
	        	$.ajax({
	                type: "POST",
	                url: "${pageContext.request.contextPath}/AggiungiProdotto",
	                data: form.serialize(),
	                success: function(response) {
	                    console.log("Quantità aggiornata con successo.");
	                },
	                error: function(xhr, status, error) {
	                    console.error("Errore nell'aggiornamento della quantità.");
	                }
            	});
	        });
	      	
	    	$(".rmvForm").on("submit", function(event) {
				event.preventDefault();
			  	let $form = $(this);
			  	
			  	$.ajax({
				    type: "POST",
				    url: "${pageContext.request.contextPath}/RimuoviProdotto",
				    data: $form.serialize(),
				    //Funzione di successo, viene eseguita se la richiesta AJAX ha avuto esito positivo
				    success: function() {
						//Cancella il prodotto con uno specifico ID dal carrello
						let ID = $form.find("input[name='ID']").val();
						console.log("Found ID: ", ID);
						
				      	if (ID) {
							document.getElementById(ID.toString()).remove();
				        	//Se il carrello è diventato vuoto, ricarica la pagina
				        	if (document.getElementsByClassName("productRow").length === 0) {
				          		location.reload();
			        		}
			      		} else {
			        		console.error("ID not found in form.");
				      	}
	    			},
			    	error: function(jqXHR, textStatus, errorThrown) {
			      	console.error("AJAX request failed: " + textStatus + ", " + errorThrown);
		    		}
  				});
			});
	  	});
	</script>
</body>
</html>
