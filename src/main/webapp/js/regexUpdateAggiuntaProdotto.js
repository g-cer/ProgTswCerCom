let aggiungiProdotto = document.getElementById("aggiungi");
let updateProdotto = document.getElementById("update");

if (aggiungiProdotto) {
    aggiungiProdotto.addEventListener('submit', checkProdotto);
}
if (updateProdotto) {
    updateProdotto.addEventListener('submit', checkProdotto);
}
	
function checkProdotto(event) {
    let valido = document.getElementsByClassName("non-valido")[0];
    valido.innerHTML = "";

    let float = document.getElementById("prezzo");
    let floatVal = float.value
    const regFloat = /^[+]?\d+(\.\d{1,2})?$/

    if(!regFloat.test(floatVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Formato del prezzo non valido. Deve essere un valore numerico positivo con massimo due cifre decimali.");
        valido.appendChild(textNode);
        document.createElement("br");
        let br = document.createElement("br");
        valido.appendChild(br);
    }
    
    let iva = document.getElementById("iva");
    let ivaVal  = iva.value
    const regIva = /^(100|[1-9]?\d(\.\d\d?)?)$/;

    if(!regIva.test(ivaVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Formato iva non valido. L'iva deve essere un valore percentuale da 0 a 100, con massimo due cifre decimali.");
        valido.appendChild(textNode);
        document.createElement("br");
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let nome = document.getElementById("nome");
    let nomeVal = nome.value
    const regNome = /^[A-Za-z0-9\s\'\-]{2,50}$/;

    if(!regNome.test(nomeVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Formato nome non valido. Il nome deve contenere da 2 a 50 caratteri compresi numeri, spazi, apostrofi e trattini.");
        valido.appendChild(textNode);
        document.createElement("br");
        let br = document.createElement("br");
        valido.appendChild(br);
    }
}
