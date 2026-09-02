<%-- Controller Servlet: [control.]Catalogo.java (/Catalogo) --%>

<%@ page import="java.util.Collection" %>
<%@ page import="model.prodotto.ProdottoBean" %>

<% Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti"); %>

<!DOCTYPE html>
<html lang="it">
<head>
  	<meta charset="UTF-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<link rel="stylesheet" href="css/catalogo.css">
  	<link rel="stylesheet" href="css/modifica.css">
  	<title>Catalogo</title>
  	<script src="https://cdn.lordicon.com/lordicon.js" integrity="sha384-PK1lIBVhBrtMXugDWQiwet5p1Zyhig1NEpFX+3vW1L4EkzlrMdwZS3I8WcSMHdUm" crossorigin="anonymous"></script>
</head>

<body>
	<%@ include file="../header.jsp" %>
  	<h1 class="index-header"> <a href="Catalogo">Catalogo</a> </h1>
  	<div class="dropdown b">
		<button class="dropbtn">Ordina per &dtrif;</button>
	    <div class="dropdown-content">
    		<a href="Catalogo?ordine=nome">Nome</a>
	    	<a href="Catalogo?ordine=prezzo">Prezzo</a>
	    	<a href="Catalogo?ordine=tipo">Tipo</a>
	    </div>
	</div>
  	<br>
  	<div class="prodotti">
		<%
	  		//Formatter per il prezzo
		  	if (prodotti != null && prodotti.size() != 0) {
		
		    	//Stampa catalogo
			    for (Object o : prodotti) {
		      		ProdottoBean prodotto = (ProdottoBean) o;
			      	String prezzo = prodotto.getPrezzo().toPlainString();
			
			      	//if (prezzo.matches("[0-9]+"))
					//	prezzo += ".00";
		%>
    	<div class="elemento">
	    	<table class="dettagli">
    			<tr>
	    			<td colspan="2" class="prodotto">
                        <a href="${pageContext.request.contextPath}/DescrizioneProdotto?id=<%= prodotto.getID() %>"><img src="${pageContext.request.contextPath}/api/GetImage?nome=<%= prodotto.getGrafica() %>" alt="<%= prodotto.getNome() %>"></a>
	    			</td>
	    		</tr>
	    		<tr>
		    		<td colspan="2">
		    			<%= prodotto.getTipo() %>
		    			<br>
		    			<%= prodotto.getNome() %>
		    			<br>
		    			&euro;&nbsp;<%= prezzo %> + <%= prodotto.getIVA() %> &percnt; IVA<br>
		    			<br>
		    		</td>
	    		</tr>
		    	<tr>
	    			<td class="bottoni" colspan="2">
		    			<form action="DescrizioneProdotto" method="GET">
		          			<input type="hidden" name="id" value="<%= prodotto.getID() %>">
	          				<button class="btn-shine button" type="submit">
	    						<span>Mostra Descrizione</span>
								<lord-icon src="https://cdn.lordicon.com/msoeawqm.json" trigger="hover" colors="primary:#1663c7,secondary:#f24c00" stroke="120"></lord-icon>
							</button>
		        		</form>
		    		</td>
	    		</tr>
	    		<tr>
		    		<td class="funzioni">
		        		<p style="font-size:2%">&nbsp;</p>
				        <form action="${pageContext.request.contextPath}/admin/ModificaProdotto" method="GET">
			          		<input type="hidden" name="id" value="<%= prodotto.getID() %>">
				          	<button class="green btn-shine button" type="submit">
   								<span>Modifica</span>
								<lord-icon src="https://cdn.lordicon.com/sbiheqdr.json" trigger="hover" colors="primary:#0c8125,secondary:#f24c00" stroke="120"></lord-icon>
							</button>
				        </form>
				    </td>
				    <td class="funzioni">
				        <p style="font-size:2%">&nbsp;</p>
				        <form action="${pageContext.request.contextPath}/admin/DeleteProdotto" method="POST" onsubmit="return confirm('Vuoi davvero eliminare il prodotto?');">
			          		<input type="hidden" name="ID" value="<%= prodotto.getID() %>">
				          	<input type="hidden" name="tipo" value="<%= prodotto.getTipo()%>">
				          	<button class="red btn-shine button" type="submit">
    							<span>Elimina</span>
								<lord-icon src="https://cdn.lordicon.com/gsqxdxog.json" trigger="hover" colors="primary:#810c0c,secondary:#f24c00" stroke="120"></lord-icon>
							</button>
				        </form>
		    		</td>
	    		</tr>
	    	</table>
    	</div>
    	<%
      		} } else {
    	%>
    	<p class="niente">Nessun prodotto disponibile</p>
		<lord-icon src="https://cdn.lordicon.com/imamsnbq.json" trigger="loop" delay="1500" colors="primary:#000000,secondary:#000000" stroke="85" style="width:25%;height:25%"></lord-icon>
    	<% 
   			} 
   		%>
	</div>
	<br>
	<div class="inserimentoProdotti">
	  	<h1>Inserimento prodotti</h1>
	  	<form action="${pageContext.request.contextPath}/admin/SaveProdotto" method="post" id="aggiungi" enctype="multipart/form-data">
			<fieldset>
			  	<legend>Compilare i seguenti campi</legend>
			  	<label>Nome: <input type="text" name="nome" id="nome" required autocomplete="off"></label> 
			  	<br>
			  	<label>Prezzo: <input type="text" inputmode="decimal" name="prezzo" id="prezzo" required autocomplete="off"></label> 
			  	<br>
			  	<label>IVA: <input type="text" inputmode="numeric" name="IVA" id="iva" required autocomplete="off"></label> 
			  	<br>
			  	<label for="tipo">Tipo:</label>
			  	<select name="tipo" id="tipo">
			  		<option value="keyboard">Keyboard</option>
				  	<option value="switch">Switch</option>
				  	<option value="keycap">Keycap</option>
			  	</select> 
			  	<br>
			  	<label>Quantit&agrave;: <input type="number" min="1" max="100" name="quantita" required autocomplete="off"></label> 
			  	<br>
			  	<label>Descrizione: <br> <textarea name="descrizione" required autocomplete="off"></textarea></label> 
			  	<br> 
			  	<br>
			  	<label>Grafica: <input type="file" name="grafica" accept="image/*" required></label> 
			  	<br>
			  	<div class="non-valido"></div>
			  	<br>
			  	<input type="submit" value="Carica">
		  	</fieldset>
		</form>
	</div>
	<br>
	
  	<script src="${pageContext.request.contextPath}/js/regexUpdateAggiuntaProdotto.js"></script>
  	<%@ include file="../footer.jsp" %>
</body>
</html>