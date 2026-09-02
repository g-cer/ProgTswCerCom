<%-- Controller Servlet: [control.utente.]Login.java (/Login) --%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>
	<%@ include file="../header.jsp" %>
	
	<div class="logSig">
		<img src="${pageContext.request.contextPath}/images/system/logo.jpeg" id="imgLogSig" alt="Logo">
		<div class="form-container">
			<% 
				if (session.getAttribute("utentePresente") != null  && session.getAttribute("utentePresente").equals(false)) { 
			%>
			<p class="userStatus">Utente registrato correttamente!</p>
			<% 
				} else if (session.getAttribute("utentePresente")!= null && session.getAttribute("utentePresente").equals(true)) { 
			%>
			<p class="userStatus">Username o email gi&agrave; in uso</p>
			<% 
				}
			    session.removeAttribute("utentePresente");
			%>
			
			<form action="${pageContext.request.contextPath}/Login" method="post" id="lform">
			    <p id="head">Accedi</p>
			    <!-- Quando il link viene cliccato, viene eseguita la funzione JavaScript changeForm con l'argomento "Sign Up". -->
			    <p>Non ti sei ancora registrato? <a href='javascript:changeForm("Sign Up")'>Registrati ora!</a></p> 
			    <div class="group">      
      				<label for="username">Username</label>
      				<input type="text" name="username" id="username" required>
    			</div>
    			<div class="group">      
      				<label for="password">Password</label>
      				<input type="password" name="password" id="password" required>
    			</div>
			    <br>
			    <button type="submit" class="button2">Accedi</button>
			</form>
			
			<form action="${pageContext.request.contextPath}/Registrazione" method="post" id="sform">
			    <p id="head">Registrati</p>
		        <p>Sei gi&agrave; registrato? <a href='javascript:changeForm("Log In")'>Accedi ora!</a></p> 
			    <div class="group">      
      				<label for="usernameReg">Username</label>
      				<input type="text" name="usernameReg" id="usernameReg" required>
    			</div>
    			<div class="group">      
      				<label for="passwordReg">Password</label>
      				<input type="password" name="passwordReg" id="passwordReg" required>
    			</div>
    			<div class="group">      
      				<label for="nomeReg">Nome</label>
      				<input type="text" name="nomeReg" id="nomeReg" required>
    			</div>
    			<div class="group">      
      				<label for="cognomeReg">Cognome</label>
      				<input type="text" name="cognomeReg" id="cognomeReg" required>
    			</div>
    			<div class="group" id="email">
      				<label for="emailReg">Email</label>
      				<input type="text" name="emailReg" id="emailReg" required>
    			</div>
    			<div class="group">      
      				<label for="dataNascitaReg">Data di Nascita</label>
      				<input type="date" name="dataNascitaReg" id="dataNascitaReg" required>
    			</div>
		        <br> 
		        <button type="submit" id="registerButton" class="button3">Registrati</button>
		        <br>
		        <br>
				<div class="non-valida"></div>
			</form>
		</div>
	</div>
	
	<%@ include file="../footer.jsp" %>
	
	<!-- Mostra solo il form di accesso, nascondi il form di registrazione -->
	<script>
	window.onload = function() {
		document.getElementById("lform").style.display = "block";
		document.getElementById("sform").style.display = "none";
	}
	</script>
	
	<!-- Cambia dinamicamente tra form di registrazione e di accesso -->
	<script src="${pageContext.request.contextPath}/js/changeForm.js"></script>

	<!-- Validazione del form tramite espressioni regolari -->
	<script src="${pageContext.request.contextPath}/js/regexRegistrazione.js"></script>
</body>
</html>
