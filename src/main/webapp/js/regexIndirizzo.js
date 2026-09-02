let aggiungiOrdineForm = document.getElementById("aggiungi-ordine");
let modificaIndirizzoForm = document.getElementById("modifica-indirizzo-form");

if (aggiungiOrdineForm) {
    aggiungiOrdineForm.addEventListener('submit', checkIndirizzo);
}
if (modificaIndirizzoForm) {
    modificaIndirizzoForm.addEventListener('submit', checkIndirizzo);
}

function checkIndirizzo(event) {
    let valido = document.getElementsByClassName("indirizzo-non-valido")[0];
    valido.innerHTML = "";

    let nome = document.getElementById("nome-spedizione");
    if(nome) {
	    let nomeVal = nome.value;
	    const regNome = /^[A-Za-z\s]{2,50}$/;
	    if (!regNome.test(nomeVal)) {
	        event.preventDefault();
	        let textNode = document.createTextNode("Nome non valido. Il nome deve contenere da 2 a 50 caratteri o spazi.");
	        valido.appendChild(textNode);
	        valido.appendChild(document.createElement("br"));
	    }
    }
    
    let cognome = document.getElementById("cognome-spedizione");
    if(cognome) {
	    let cognomeVal = cognome.value;
	    const regCognome = /^[A-Za-z\s]{2,50}$/;
	    if (!regCognome.test(cognomeVal)) {
	        event.preventDefault();
	        let textNode = document.createTextNode("Cognome non valido. Il cognome deve contenere da 2 a 50 caratteri o spazi.");
	        valido.appendChild(textNode);
	        valido.appendChild(document.createElement("br"));
	    }
    }

    let cap = document.getElementById("cap-spedizione");
    let capVal = cap.value;
    const regCap = /^\d{5}$/;
    if (!regCap.test(capVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("CAP non valido. Il CAP deve contenere 5 cifre.");
        valido.appendChild(textNode);
        valido.appendChild(document.createElement("br"));
    }

    let citta = document.getElementById("citta-spedizione");
    let cittaVal = citta.value;
    const regCitta = /^[A-Za-z\s]{2,50}$/;
    if (!regCitta.test(cittaVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Città non valida. Il nome della città deve contenere da 2 a 50 caratteri o spazi");
        valido.appendChild(textNode);
        valido.appendChild(document.createElement("br"));
    }

    let via = document.getElementById("via-spedizione");
    let viaVal = via.value;
    const regVia = /^[A-Za-z0-9\s.,]{2,100}$/;
    if (!regVia.test(viaVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Via non valida. Il nome della via deve contenere da 2 a 100 caratteri, numeri, spazi, virgole o punti.");
        valido.appendChild(textNode);
        valido.appendChild(document.createElement("br"));
    }
}