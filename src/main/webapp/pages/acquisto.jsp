<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/acquisto.css">
    <title>Acquisto effettuato!</title>
</head>

<body>
	<%@ include file="./header.jsp"%>
	<table>
		<tr>
			<td>
				<img src="${pageContext.request.contextPath}/images/system/acquisto.jpeg" alt="Acquisto">
			</td>
			<td>
				<p>Acquisto effettuato!</p>
				<a href="${pageContext.request.contextPath}/">Torna alla Home Page</a>
			</td>
		</tr>
	</table>
	<%@ include file="footer.jsp"%>
</body>
</html>
