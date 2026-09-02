//Ricava il context path dall'URL con cui questo script e' stato caricato,
//cosi' le chiamate AJAX restano corrette qualunque sia il nome del deploy
const SCRIPT_URL = document.currentScript.src;

function contextPath() {
	return SCRIPT_URL.substring(0, SCRIPT_URL.indexOf("/js/"));
}

document.getElementById("sform").addEventListener('submit', checkRegistrazione);
document.getElementById("emailReg").addEventListener('blur', CheckEmail);

function checkRegistrazione(event) {
	event.preventDefault();
	let valido = document.getElementsByClassName("non-valida")[0];
    valido.innerHTML = "";
    
    let username = document.getElementById("usernameReg");
    let valUsername = username.value
    const regUsername = /^[a-zA-Z0-9._-]{3,}$/;
    const usernameCheck = regUsername.test(valUsername); 
    if(!usernameCheck) {
    	let textNode = document.createTextNode("Username non valido. L'username deve contenere almeno 3 caratteri, compresi numeri, punti, underscore e trattini.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let email = document.getElementById("emailReg");
    let valEmail = email.value
    const regEmail = /^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    const emailCheck = regEmail.test(valEmail); 
    if(!emailCheck) {
    	let textNode = document.createTextNode("Email non valida. Il formato corretto deve essere \"esempio@dominio.com\".");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let password = document.getElementById("passwordReg");
    let passwordVal = password.value
    const regPass = /^[A-Za-z0-9.]{3,16}$/;
    const passCheck = regPass.test(passwordVal);
    if(!passCheck) {
    	let textNode = document.createTextNode("Password non valida. La password deve contenere da 3 a 16 caratteri alfanumerici.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let nome = document.getElementById("nomeReg");
    let nomeVal = nome.value
    const regNome = /^[A-Za-z]{2,30}$/;
    const nomeCheck = regNome.test(nomeVal); 
    if(!nomeCheck) {
    	let textNode = document.createTextNode("Nome non valido. Il nome deve contenere da 2 a 30 caratteri letterali.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }

    let cognome = document.getElementById("cognomeReg");
    let cognomeVal = cognome.value
    const regCognome = /^[A-Za-z]{2,30}$/;
    const cognomeCheck = regCognome.test(cognomeVal); 
    if(!cognomeCheck) {
    	let textNode = document.createTextNode("Cognome non valido. Il cognome deve contenere da 2 a 30 caratteri letterali.");
        valido.appendChild(textNode);
        let br = document.createElement("br");
        valido.appendChild(br);
    }
    
	if (!emailCheck || !passCheck || !nomeCheck || !cognomeCheck) return;
	
	document.getElementById("registerButton").disabled = true;
	let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
			valido.innerHTML = "";
			if (xhr.status === 200) {				
	            let response = JSON.parse(xhr.responseText);
	            if (!response.emailExists) {
	                document.getElementById("sform").submit();
	            } else {
					let textNode = document.createTextNode("Email gia' in uso");
	                valido.appendChild(textNode);
	                let br = document.createElement("br");
	                valido.appendChild(br);
				}
			} else {
                valido.appendChild(document.createTextNode("Errore di comunicazione con il server"));
                valido.appendChild(document.createElement("br"));
			}
			document.getElementById("registerButton").disabled = false;
        }
    };
    
    xhr.open('POST', contextPath() + '/CheckEmail', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send('emailReg=' + encodeURIComponent(email.value));
}

function CheckEmail(eventi) {
	let email = this.value;
    let valido = document.getElementsByClassName("non-valida")[0];

    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
			valido.innerHTML = "";
            let response = JSON.parse(xhr.responseText);
            if (response.emailExists) {
                let textNode = document.createTextNode("Email gia' in uso");
                valido.appendChild(textNode);
                let br = document.createElement("br");
                valido.appendChild(br);
            }
        }
    };
    
    xhr.open('POST', contextPath() + '/CheckEmail', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send('emailReg=' + encodeURIComponent(email));
}