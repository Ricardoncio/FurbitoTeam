async function mostrarCarta(){
    const user = JSON.parse(sessionStorage.getItem("user"));
    const contadorTiradas = document.getElementById("contadorTiradas");
    if (user.tiradas > 0) {
        const response= await fetch("http://localhost:8080/FurbitoTeam/pedirCarta?idUser="+user.id,{method: "GET"});
        const carta = await response.json();
        const divContenedor = document.getElementById("divContenedor");
        const imagenesAnteriores = document.querySelectorAll(".cartaAnimacion");
        imagenesAnteriores.forEach(img => {img.classList.remove("cartaAnimacion")});
        const imagenCarta = document.createElement("img");
        imagenCarta.src = carta.imageLink;
        imagenCarta.alt = carta.alt;
        imagenCarta.classList.add("cartaAnimacion");
        document.body.appendChild(divContenedor);
        divContenedor.appendChild(imagenCarta);
        user.tiradas = user.tiradas - 1;
        contadorTiradas.innerText = "Tiradas: " + user.tiradas;
        sessionStorage.setItem("user", JSON.stringify(user));
    } else {
        contadorTiradas.style.color = "red";
    }
}

function desconectarse(){
    window.location.href = "http://localhost:8080/FurbitoTeam/plantillas/login.html";
    sessionStorage.removeItem("user");
}