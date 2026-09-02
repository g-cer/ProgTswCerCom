let n_img = document.querySelectorAll('.img-per-slider img').length;
let corrente = 1;
let intervalID;

const slideContainer = document.querySelector('.slider-categories');
const cards = document.querySelectorAll('.slide-category');

let currentIndex = 0;

let prodottoContainer = document.querySelector('.prodotti-inline');

function next() {
	clearInterval(intervalID); // Cancella l'intervallo corrente
	let currentImg = document.getElementById('img-' + corrente);
	let currentCaption = document.getElementById('a' + corrente);
	currentImg.classList.remove('active');
	currentCaption.style.visibility = "hidden";
	currentCaption.style.opacity = 0;
	corrente = (corrente % n_img) + 1;
	let nextImg = document.getElementById('img-' + corrente);
	let nextCaption = document.getElementById('a' + corrente);
	nextImg.classList.add('active');
	nextCaption.style.visibility = "visible";
	nextCaption.style.opacity = 1;
	
	intervalID = setInterval(next, 3500); // Imposta un nuovo intervallo
}

function prec() {
	clearInterval(intervalID); // Cancella l'intervallo corrente
	let currentImg = document.getElementById('img-' + corrente);
	currentImg.classList.remove('active');
	corrente = ((corrente - 2 + n_img) % n_img) + 1;
	let prevImg = document.getElementById('img-' + corrente);
	prevImg.classList.add('active');
	intervalID = setInterval(next, 3500); // Imposta un nuovo intervallo
}



function showNextProdotto() {
	prodottoContainer.scrollBy({left: 300, behavior: "smooth"});
}

function showPreviousProdotto() {
	prodottoContainer.scrollBy({left: -300, behavior: "smooth"});  
}

intervalID = setInterval(next, 3500); // Imposta l'intervallo iniziale