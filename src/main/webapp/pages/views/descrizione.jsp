<%-- Controller Servlet: [control.prodotto.]DescrizioneProdotto.java (/DescrizioneProdotto) --%>

<%@ page import="model.prodotto.ProdottoBean" %>

<% ProdottoBean prodottoBean = (ProdottoBean) request.getAttribute("prodottoBean"); %>

<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/descrizione.css">
	<script src="${pageContext.request.contextPath}/js/imageZoom.js"></script>
	<script src="${pageContext.request.contextPath}/js/modal.js"></script>
	<title>Descrizione Prodotto</title>
	<script src="https://cdn.lordicon.com/lordicon.js" integrity="sha384-PK1lIBVhBrtMXugDWQiwet5p1Zyhig1NEpFX+3vW1L4EkzlrMdwZS3I8WcSMHdUm" crossorigin="anonymous"></script>
</head>

<body>
	<%@ include file="../header.jsp" %>
	
	<%
		String prezzo = prodottoBean.getPrezzo().toPlainString();
	%>
	
	<table class="mostraProdotto">
		<tr>
			<td class="Prodotto">
				<div class="img-zoom-container" onclick="openModal();">
					<img id="myimage" src="${pageContext.request.contextPath}/api/GetImage?nome=<%= prodottoBean.getGrafica() %>" alt="<%= prodottoBean.getNome() %>" class="mgt">
				</div>
			</td>
			<td class="Descrizione">
				<br>
				<div class="titolo"><h1><%= prodottoBean.getNome() %></h1></div>
				<br>
				<div class="prezzo">&euro;&nbsp;<%= prezzo %> </div>
				<div class="iva"><p>+ <%= prodottoBean.getIVA() %> &percnt; IVA</p></div>
				<br>
				<div class="descrizione-titolo">
					<p>Descrizione:</p>
				</div>
				<p>
					<%= prodottoBean.getDescrizione() %>
					<br>
				</p>
				<form action="${pageContext.request.contextPath}/AggiungiProdotto" method="post">
					<br>
					<br>
					<label>
						<input type="hidden" name="ID" value="<%= prodottoBean.getID() %>">
					    <button id="addToCartButton" class="btn-shine button" type="submit">
    						<span>Aggiungi al carrello</span>
							<lord-icon src="https://cdn.lordicon.com/dnoiydox.json" trigger="hover" colors="primary:#1663c7,secondary:#f24c00" stroke="85"></lord-icon>
						</button>
			  		</label>
				</form>
				<div id="myresult" class="img-zoom-result"></div>
			</td>
		</tr>
	</table>
	
  	<div id="myModal" class="modal">
		<div class="modal-content">
			<span class="close" onclick="closeModal();">&times;</span>
			<img src="${pageContext.request.contextPath}/api/GetImage?nome=<%= prodottoBean.getGrafica() %>" alt="<%= prodottoBean.getNome() %>" class="show">
		</div>
	</div>
	
	<%@ include file="../footer.jsp" %>
	
	<script>
        window.addEventListener('load', function() {
            imageZoom('myimage', 'myresult');
        });
    </script>
</body>
</html>