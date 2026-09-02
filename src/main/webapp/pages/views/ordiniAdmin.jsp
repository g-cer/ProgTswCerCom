<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.time.format.DateTimeFormatter" %>
<%@ page import="model.ordine.OrdineBean" %>
<%@ page import="model.acquisto.AcquistoProdotto" %>

<%
    Map<?, ?> ordini = (Map<?, ?>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Visualizza Ordini</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ordiniAdmin.css">
</head>

<body>
    <%@ include file="../header.jsp" %>
    <div class="ordiniAdmin">
        <h1><a href="${pageContext.request.contextPath}/admin/OrdiniAdmin">Visualizza Ordini</a></h1>

        <!-- Form per filtri -->
        <form action="${pageContext.request.contextPath}/admin/OrdiniAdmin" method="post">
            <label for="username">Cliente:</label>
            <input type="text" id="username" name="username">

            <label for="dataInizio">Data Inizio:</label>
            <input type="date" id="dataInizio" name="dataInizio">

            <label for="dataFine">Data Fine:</label>
            <input type="date" id="dataFine" name="dataFine">

            <button type="submit">Filtra</button>
        </form>

		<div class="divTabella">
	        <!-- Tabella per visualizzare gli ordini -->
	        <table>
	            <thead>
	                <tr>
	                    <th>ID Ordine</th>
	                    <th>Cliente</th>
	                    <th>Prezzo Totale</th>
	                    <th>Data Ordine</th>
	                    <th>Data Consegna</th>
	                    <th>Indirizzo Consegna</th>
	                    <th>Azioni</th>
	                </tr>
	            </thead>
	            <tbody>
	                <%
	                    if (ordini != null && !ordini.isEmpty()) {
	                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                        for (Map.Entry<?, ?> entry : ordini.entrySet()) {
	                            OrdineBean ordine = (OrdineBean) entry.getKey();
	                            Collection<AcquistoProdotto> acquisti = (Collection<AcquistoProdotto>) entry.getValue();
	                %>
	                            <tr>
	                                <td><%= ordine.getID() %></td>
	                                <td><%= ordine.getUsername() %></td>
	                                <td>€<%= String.format("%.2f", ordine.getPrezzoTotale()) %></td>
	                                <td><%= ordine.getDataOrdine().format(dateFormatter) %></td>
	                                <td><%= ordine.getDataConsegna().format(dateFormatter) %></td>
	                                <td><%= ordine.getVia() %>, <%= ordine.getCitta() %>, <%= ordine.getCap() %></td>
	                                <td><button id="button-<%= ordine.getID() %>" onclick="toggleDetails(<%= ordine.getID() %>)">Mostra Dettagli</button></td>
	                            </tr>
	                            <tbody id="details-<%= ordine.getID() %>" class="details-table" style="display: none;">
	                                <tr>
	                                    <th colspan="2">Prodotto</th>
	                                    <th>Grafica</th>
	                                    <th>Quantità</th>
	                                    <th>Prezzo</th>
	                                    <th>IVA</th>
	                                </tr>
	                                <% for (AcquistoProdotto acquisto : acquisti) { %>
	                                <tr>
	                                    <td colspan="2"><%= acquisto.getProdotto().getNome() %></td>
	                                    <td>
	                                        <img src="${pageContext.request.contextPath}/api/GetImage?nome=<%= acquisto.getProdotto().getGrafica() %>" alt="<%= acquisto.getAcquisto().getIDProdotto() %>">
	                                    </td>
	                                    <td><%= acquisto.getAcquisto().getQuantita() %></td>
	                                    <td>€ <%= acquisto.getAcquisto().getPrezzoAq() %></td>
	                                    <td><%= acquisto.getAcquisto().getIvaAq() %> %</td>
	                                </tr>
	                                <% } %>
	                            </tbody>
	                <%
	                        }
	                    } else {
	                %>
	                    <tr>
	                        <td colspan="7">Nessun ordine trovato.</td>
	                    </tr>
	                <%
	                    }
	                %>
	            </tbody>
	        </table>
		</div>
    </div>
    <%@ include file="../footer.jsp" %>
    <script>
        function toggleDetails(orderId) {
            var details = document.getElementById("details-" + orderId);
            var button = document.getElementById("button-" + orderId);
            if (details.style.display === "none") {
                details.style.display = "table-row-group";
                button.innerText = "Nascondi Dettagli";
            } else {
                details.style.display = "none";
                button.innerText = "Mostra Dettagli";
            }
        }
    </script>
</body>
</html>
