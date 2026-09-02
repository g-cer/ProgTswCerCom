//Rendi visibile il form di registrazione ("sform") o di login ("lform")

function changeForm(str) {
	
	  let lForm = document.getElementById("lform");
	  let sForm = document.getElementById("sform");
	
	  if (str === "Sign Up") {
		    lForm.style.display = "none";
		    sForm.style.display = "block";
	  } else {
		    sForm.style.display = "none";
		    lForm.style.display = "block";
  }
}