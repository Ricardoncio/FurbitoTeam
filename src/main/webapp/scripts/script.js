function redirigir() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    if (user != null) {
        bienvenida();
    } else {
        window.location.href = "http://localhost:8080/FurbitoTeam/plantillas/login.html";
    }
}
redirigir();

function bienvenida() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const mensajeBienvenida = document.getElementById("mensajeBienvenida");
    mensajeBienvenida.innerText = "Bienvenido " + user.nombreUsuario;
}

async function mostrarCarta(){
    const user = JSON.parse(sessionStorage.getItem("user"));
    const response= await fetch("http://localhost:8080/FurbitoTeam/pedirCarta?idUser="+user.id,{method: "GET"});
    const carta = await response.json();
    const divContenedor = document.getElementById("divContenedor");
    const imagenesAnteriores = document.querySelectorAll(".cartaAnimacion");
    console.log(imagenesAnteriores);
    imagenesAnteriores.forEach(img => {img.classList.remove("cartaAnimacion")});
    const imagenCarta = document.createElement("img");
    imagenCarta.src = carta.imageLink;
    imagenCarta.alt = carta.alt;
    imagenCarta.classList.add("cartaAnimacion");
    document.body.appendChild(divContenedor);
    divContenedor.appendChild(imagenCarta);
}

function desconectarse(){
    window.location.href = "http://localhost:8080/FurbitoTeam/plantillas/login.html";
    sessionStorage.removeItem("user");
}