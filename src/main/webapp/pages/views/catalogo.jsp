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
	if (prodotti != null && prodotti.size() != 0) {
	
	   	for (Object o : prodotti) {
	      	ProdottoBean prodotto = (ProdottoBean) o;
	      	String prezzo = prodotto.getPrezzo().toPlainString();
	
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
						<p style="margin: 0px; color: #888; text-transform: capitalize;">Tipo: <%= prodotto.getTipo() %></p>
						<br>
						<%= prodotto.getNome() %>
						<br>
						&euro;&nbsp;<%= prezzo %> + <%= prodotto.getIVA() %> &percnt; IVA
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
						<div class="aggiungi-prodotto">
							<form action="AggiungiProdotto" method="POST">
								<input type="hidden" name="ID" value="<%= prodotto.getID() %>">
								<button class="btn-shine button" type="submit">
									<span>Aggiungi al carrello</span>
									<lord-icon src="https://cdn.lordicon.com/dnoiydox.json" trigger="hover" colors="primary:#1663c7,secondary:#f24c00" stroke="85"></lord-icon>
								</button>
							</form>
						</div>
			 		</td>
				</tr>
			</table>
		</div>
		<%
			} } else {
		%>
		<p class="niente">Nessun prodotto disponibile</p>
		<lord-icon src="https://cdn.lordicon.com/imamsnbq.json" trigger="loop" delay="1500" colors="primary:#000000,secondary:#000000" stroke="85" style="width:25%;height:25%"></lord-icon>
		<% } %>
	</div>
	<%@ include file="../footer.jsp" %>
</body>
</html>
