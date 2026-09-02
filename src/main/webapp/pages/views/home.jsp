<%-- Controller Servlet: [control.]Home.java (/) --%>

<%@ page import="java.util.Collection" %>
<%@ page import="model.prodotto.ProdottoBean" %>

<% Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti"); %>

<!DOCTYPE html>
<html lang="it">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0">
	  <link rel="stylesheet" href="css/home.css">
	  <link rel="stylesheet" href="css/catalogo.css">
	  <title>Home</title>
	  <script src="https://cdn.lordicon.com/lordicon.js" integrity="sha384-PK1lIBVhBrtMXugDWQiwet5p1Zyhig1NEpFX+3vW1L4EkzlrMdwZS3I8WcSMHdUm" crossorigin="anonymous"></script>
	</head>

	<body>
		<%@ include file="../header.jsp"%>
		<div class="container">
	
			<div class="slider">
				<div class="img-per-slider">
					<img id="img-1" class="active" src="${pageContext.request.contextPath}/images/system/banner1.webp" data-value="d1" alt="alt1">
					<a style="visibility: visible;" href="${pageContext.request.contextPath}/DescrizioneProdotto?id=5" id="a1">Tastiera Meccanica Silenziosa</a>
					<img id="img-2" src="${pageContext.request.contextPath}/images/system/banner2.jpg" data-value="d2" alt="alt2">
					<a href="${pageContext.request.contextPath}/DescrizioneProdotto?id=2" id="a2">Tastiera Compatta Wireless</a>
					<img id="img-3" src="${pageContext.request.contextPath}/images/system/banner3.webp" data-value="d3" alt="alt3">
					<a href="${pageContext.request.contextPath}/DescrizioneProdotto?id=8" id="a3">Tastiera Ergonomica</a>
					<img id="img-4" src="${pageContext.request.contextPath}/images/system/banner4.webp" data-value="d4" alt="alt4">
					<a href="${pageContext.request.contextPath}/DescrizioneProdotto?id=3" id="a4">Tastiera Professionale</a>
				</div>
			</div>

		</div>

		<h1 class="cat-Title"><a href="${pageContext.request.contextPath}/Catalogo?ordine=tipo">Le nostre categorie</a></h1>
		
		<div class="slider-categories">
		 	<div class="slide-category" id="cat-1">
				<a href="Tipo?tipo=keyboard">
			 	<div class="card">
	    			<img src="${pageContext.request.contextPath}/images/system/keyboard.png" alt="Tastiere" class="card-image">
	    			<div class="card-text">
	      				Tastiere
	    			</div>
	  			</div>
				</a>
			</div>
			<div class="slide-category" id="cat-2">
				<a href="Tipo?tipo=keycap">
			 	<div class="card">
	    			<img src="${pageContext.request.contextPath}/images/system/keycap.png" alt="Copritasti" class="card-image">
					<div class="card-text">
	      				Copritasti
	    			</div>
	  			</div>
				</a>
			</div>
			<div class="slide-category" id="cat-3">
				<a href="Tipo?tipo=switch">
			 	<div class="card">
	    			<img src="${pageContext.request.contextPath}/images/system/switch.png" alt="Switch" class="card-image">
	    			<div class="card-text">
	      				Switch
	    			</div>
	  			</div>
				</a>
			</div>
			
		  </div>
		  <h1 class="cat-Title"><a href="${pageContext.request.contextPath}/Catalogo">I nostri prodotti</a></h1>
		  <div class="prodotti-inline">
		    <%
		      int si = 0;
		      if (prodotti != null && prodotti.size() != 0) {
		    	si = 1;
				
		        // Stampa primi 10 del catalogo
		        int i = 0;
		        for (Object o : prodotti) {
		          i++;
		          if (i <= 10) {
			          ProdottoBean prodotto = (ProdottoBean) o;
			          String prezzo = prodotto.getPrezzo().toPlainString();
			
			          //if (prezzo.matches("[0-9]+"))
			          //  prezzo += ".00";
		    %>
		    	<div class="elemento-inline" id="<%=i %>">
			    	<table class="dettagli">
						<caption hidden>Catalogo</caption>
						<tr hidden>
							<th>Grafica</th>
							<th>Descrizione</th>
							<th>Bottoni</th>
						</tr>
			    		<tr>
			    			<td colspan="2" class="prodotto">
								<a href="${pageContext.request.contextPath}/DescrizioneProdotto?id=<%= prodotto.getID() %>">
								  <img src="${pageContext.request.contextPath}/api/GetImage?nome=<%= prodotto.getGrafica() %>" alt="<%= prodotto.getNome() %>"></a>
			    			</td>
			    		</tr>
			    		<tr>
				    		<td colspan="2">
				    			<p style="margin: 0px; color: #888; text-transform: capitalize;">Tipo: <%= prodotto.getTipo() %></p><br>
				    			<%= prodotto.getNome() %><br>
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
				        		<p style="font-size:2%">&nbsp;</p>
				        		<form action="AggiungiProdotto" method="POST">
				          			<input type="hidden" name="ID" value="<%= prodotto.getID() %>">
				         			<button class="btn-shine button" type="submit">
			    						<span>Aggiungi al carrello</span>
										<lord-icon src="https://cdn.lordicon.com/dnoiydox.json" trigger="hover" colors="primary:#1663c7,secondary:#f24c00" stroke="85"></lord-icon>
									</button>
				        		</form>
				    		</td>
			    		</tr>
			    	</table>
		    	</div>
		    	
		    <%
		      // Parentesi del for e dell'if
		          } } } else {
		    %>
		    <p class="niente">Nessun prodotto disponibile</p>
			<lord-icon
			    src="https://cdn.lordicon.com/imamsnbq.json"
			    trigger="loop"
			    delay="1500"
			    colors="primary:#000000,secondary:#000000"
			    stroke="85"
			    style="width:25%;height:25%">
			</lord-icon>
		    <% } %>
		 </div>		 
		 <% if (si > 0) { %>		
		 <div class="cento">
		 	<a href="${pageContext.request.contextPath}/Catalogo" class="vis">Visualizza tutti...</a>
		 </div>
		 <div class="button-container">
			<button class="button-cat" onclick="showPreviousProdotto();"><i class="fa-solid fa-arrow-left"></i></button>
			<button class="button-cat" onclick="showNextProdotto();"><i class="fa-solid fa-arrow-right"></i></button>
		 </div>
		<% } %>
		<script src="${pageContext.request.contextPath}/js/home.js"></script>
		
		<%@ include file="../footer.jsp"%>
	</body>

</html>
