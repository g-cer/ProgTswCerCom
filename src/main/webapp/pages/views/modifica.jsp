<%-- Controller Servlet: [control.prodotto.]ModificaProdotto.java (/admin/ModificaProdotto) --%>

<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="control.utente.Login" %>

<% 
	ProdottoBean prodottoBean = (ProdottoBean) request.getAttribute("prodotto");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifica Prodotto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modifica.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
    <script src="${pageContext.request.contextPath}/js/selezione.js"></script>
</head>

<body onload="setRadio()">
	<%@ include file="../header.jsp" %>
	<div class="inserimentoProdotti">
		<h1>Modifica prodotto</h1>
		<form id="update" action="${pageContext.request.contextPath}/admin/ModificaProdotto" method="POST" enctype="multipart/form-data"></form>
		<fieldset>
		    <legend>Modifica dei campi</legend>
		    <label>Nome: <input form="update" type="text" name="nome" id="nome" value="<%=prodottoBean.getNome()%>" required autocomplete="off"></label> 
		    <br>
		    <label>Prezzo: <input form="update" type="text" name="prezzo" id="prezzo" value="<%=prodottoBean.getPrezzo()%>" required autocomplete="off"></label> 
		    <br>
		    <label>IVA: <input form="update" type="text" name="IVA" id="iva" value="<%=prodottoBean.getIVA()%>" required autocomplete="off"></label> 
		    <br>
		    <input form="update" type="hidden" name="tipoVecchio" value="<%= prodottoBean.getTipo() %>">
		    <label for="tipo">Tipo:</label>
		    <select form="update" name="tipo" id="tipo">
		        <option value="" selected disabled hidden><%= prodottoBean.getTipo() %></option>
		        <option value="keyboard">Keyboard</option>
		        <option value="switch">Switch</option>
		        <option value="keycap">Keycap</option>
		    </select> 
		    <br>
		    <label>Quantit&agrave;: <input form="update" type="number" min="1" max="1000000000" name="quantita" value="<%=prodottoBean.getQuantitaInStock()%>" required autocomplete="off"></label>
		    <br>
		    <label>Descrizione: 
		    <br>
		    <textarea form="update" name="descrizione" required autocomplete="off"><%=prodottoBean.getDescrizione()%></textarea></label>
		    <p>Aggiornare la grafica?</p>
		    <label>S&igrave;<input type="radio" name="selezione" value="si" onclick="selezione()"></label>
		    <label>No<input type="radio" id="radioNo" name="selezione" value="no" checked onclick="selezione()"></label>
		    <br id="newLine" hidden>
		    <label id="labelGrafica" hidden>Grafica: <input form="update" id="fileInput" type="file" hidden name="grafica" accept="image/*"></label> 
		    <br>
		    <div class="non-valido"></div>
		    <br>
		    <input form="update" type="hidden" name="path" value="<%= prodottoBean.getGrafica() %>">
		    <input form="update" type="hidden" name="id" value="<%= prodottoBean.getID() %>">
		    <input form="update" type="submit" value="Modifica">
		</fieldset>
	</div>
	<%@ include file="../footer.jsp" %>
	<script src="${pageContext.request.contextPath}/js/regexUpdateAggiuntaProdotto.js"></script>
</body>
</html>