// Mostra o nascondi la finestra Modale

function openModal() {
    const modal = document.getElementById("myModal");
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById("myModal");
    modal.style.display = "none";
    
}

// Chiude il modale se l'utente clicca fuori dalla finestra modale
window.onclick = function(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}