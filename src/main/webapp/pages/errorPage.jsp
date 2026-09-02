<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorPage.css">
    <title>Errore</title>
</head>
<body>
	<%@ include file="header.jsp"%>
    <div class="error-content">
        <h1>Ops! Qualcosa è andato storto.</h1>
        <h2><%= request.getAttribute("javax.servlet.error.message")%></h2>
        <p>Siamo spiacenti, ma sembra che ci sia stato un errore. Stiamo lavorando per risolverlo. Torna indietro o visita la nostra homepage.</p>
        <a href="${pageContext.request.contextPath}/">Torna alla Home Page</a>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
