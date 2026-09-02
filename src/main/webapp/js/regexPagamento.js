document.getElementById("modifica-pagamento-form").addEventListener('submit', checkPagamento);

function checkPagamento(event) {
    let valido = document.getElementsByClassName("pagamento-non-valido")[0];
    valido.innerHTML = "";

    let nome = document.getElementById("nomeCartaNuova");
    let nomeVal = nome.value;
    const regNome = /^[A-Za-z\s]{2,50}$/;

    if (!regNome.test(nomeVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Nome non valido. Il nome deve contenere da 2 a 50 caratteri o spazi.");        					
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let cognome = document.getElementById("cognomeCartaNuova");
    let cognomeVal = cognome.value;
    const regCognome = /^[A-Za-z\s]{2,50}$/;

    if (!regCognome.test(cognomeVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Cognome non valido. Il cognome deve contenere da 2 a 50 caratteri o spazi.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let CVV = document.getElementById("CVVNuovo");
    let CVVVal = CVV.value;
    const regCVV = /^\d{3,4}$/;

    if (!regCVV.test(CVVVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("CVV non valido. Il CVV deve contenere 3 o 4 cifre.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let carta = document.getElementById("numCartaNuova");
    let cartaVal = carta.value;
    const regCarta = /^\d{8,19}$/;

    if (!regCarta.test(cartaVal)) {
        event.preventDefault();
        let textNode = document.createTextNode("Numero carta non valido. Il numero carta deve contenere da 8 a 19 cifre.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }
}
