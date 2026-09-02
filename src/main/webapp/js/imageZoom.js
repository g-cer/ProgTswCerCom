function imageZoom(imgID, resultID) {
    const img = document.getElementById(imgID);
    const result = document.getElementById(resultID);
    const lens = document.createElement("DIV");

    lens.setAttribute("class", "img-zoom-lens");
    img.parentElement.insertBefore(lens, img);

	result.style.display = "block";
    const cx = result.offsetWidth / lens.offsetWidth;
    const cy = result.offsetHeight / lens.offsetHeight;
	result.style.display = null;

    result.style.backgroundImage = `url(${img.src})`;
    result.style.backgroundSize = `${img.width * cx}px ${img.height * cy}px`;

    function showResult() {
        result.style.visibility = "visible";
        lens.style.visibility = "visible";
    }

    function hideResult() {
        result.style.visibility = "hidden";
        lens.style.visibility = "hidden";
    }

    function moveLens(e) {
        e.preventDefault();
        const pos = getCursorPos(e);
        let x = pos.x - lens.offsetWidth / 2;
        let y = pos.y - lens.offsetHeight / 2;

        if (x > img.width - lens.offsetWidth) x = img.width - lens.offsetWidth;
        if (x < 0) x = 0;
        if (y > img.height - lens.offsetHeight) y = img.height - lens.offsetHeight;
        if (y < 0) y = 0;

        lens.style.left = `${x}px`;
        lens.style.top = `${y}px`;
        result.style.backgroundPosition = `-${x * cx}px -${y * cy}px`;
    }

    function getCursorPos(e) {
        let a, x = 0, y = 0;
	    a = img.getBoundingClientRect();

	    x = e.pageX - a.left;
	    y = e.pageY - a.top;

	    x = x - window.pageXOffset;
	    y = y - window.pageYOffset;
	    
	    return { x, y };
    }

    lens.addEventListener("mousemove", moveLens);
    img.addEventListener("mousemove", moveLens);
    lens.addEventListener("touchmove", moveLens);
    img.addEventListener("touchmove", moveLens);

    lens.addEventListener("mouseover", showResult);
    lens.addEventListener("mouseout", hideResult);
    img.addEventListener("mouseover", showResult);
    img.addEventListener("mouseout", hideResult);
    lens.addEventListener("touchstart", showResult);
    lens.addEventListener("touchend", hideResult);
    img.addEventListener("touchstart", showResult);
    img.addEventListener("touchend", hideResult);
    
    addEventListener("resize", (e) => result.style.backgroundSize = `${img.width * cx}px ${img.height * cy}px` );
    
    hideResult();
}
