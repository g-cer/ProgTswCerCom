<%@ page import="control.utente.Login" %>
<%@ page import="model.utente.UtenteBean" %>

<% Integer tipoUtente = (Integer) request.getSession().getAttribute("tipoUtente"); %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

<nav>
	<a href="${pageContext.request.contextPath}/">Home</a>
	<a href="${pageContext.request.contextPath}/Catalogo">Prodotti</a>
	<%-- <a href="${pageContext.request.contextPath}/pages/carrello.jsp" style="float: right;"> <i class="fa-solid fa-cart-shopping"></i> </a> --%>
	
	<% 
		if (tipoUtente == null) { 
	%>
	<a href="${pageContext.request.contextPath}/pages/carrello.jsp" style="float: right;"> <i class="fa-solid fa-cart-shopping"></i> </a>
	<a href="${pageContext.request.contextPath}/Login" style="float: right;"> <i class="fa-solid fa-user"></i> Accedi/Registrati </a>
 	<% 
 		} else if (tipoUtente != null && tipoUtente.equals(Login.REGISTRATO)) { 
 	%>
  	<a href="${pageContext.request.contextPath}/pages/carrello.jsp" style="float: right;"> <i class="fa-solid fa-cart-shopping"></i> </a>
  	<a href="${pageContext.request.contextPath}/user/Profilo" style="float: right;"> <i class="fa-solid fa-user"></i> <%= ((UtenteBean)request.getSession().getAttribute("utente")).getNome() %></a>
	<%
		} else if (tipoUtente != null && tipoUtente.equals(Login.ADMIN)) {
	%>
	<a href="${pageContext.request.contextPath}/admin/OrdiniAdmin" style="float: right;"> <i class="fa-solid fa-inbox"></i> Lista Ordini </a>
	<a href="${pageContext.request.contextPath}/Logout" style="float: right;"> <i class="fa-solid fa-arrow-right-from-bracket"></i>Logout </a>
	<%
		} 
	%>
	<br>
 	<div class="search-container">
	    <form autocomplete="off" action="Catalogo">
			<input type="text" id="search" name="search" placeholder="Trova..." onkeyup="searchPrediction();" onblur="hidePrediction();" onfocus="showPrediction();">
		    <button type="submit"><i class="fa fa-search"></i></button>
   		</form>
   		<div id="results" onmousedown="event.preventDefault();"></div>
 	</div>
</nav>

<script>
	function hidePrediction() {
		document.getElementById("results").style.display = "none";
	}
	function showPrediction() {
		document.getElementById("results").style.display = "block";
	}
	function searchPrediction() {
		let query = document.getElementById("search").value;
		
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (this.readyState == 4) {
				if (this.status == 200) {
					let div = document.getElementById("results");
					div.innerHTML = "";
					
					let res = JSON.parse(this.responseText);
					
					for (let el of res) {
						div.innerHTML += "<a href='${pageContext.request.contextPath}/DescrizioneProdotto?id=" + el.ID + "'>" + el.nome + "</a>"
						console.log(el);
					}
				}
			}
		}
		xhr.open("GET", "${pageContext.request.contextPath}/ajax/search?query=" +document.getElementById("search").value, true);
		xhr.send("query=" + query);
	}
</script>