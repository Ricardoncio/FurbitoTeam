async function recuperarUsuario() {
    let idUser;
    let userGuardado;
    if (sessionStorage.getItem("idUser")) {
        idUser = sessionStorage.getItem("idUser");
    } else {
        userGuardado = JSON.parse(sessionStorage.getItem("user"));
        idUser = userGuardado.id;
    }
    const response = await fetch("http://localhost:8080/FurbitoTeam/usuario?idUser=" + idUser, {method: "GET"});
    const user = await response.json();
    sessionStorage.setItem("user",JSON.stringify(user));
    sessionStorage.removeItem("idUser");
}
function bienvenida() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const mensajeBienvenida = document.getElementById("mensajeBienvenida");
    if (mensajeBienvenida) {
        mensajeBienvenida.innerText = "¡Bienvenido " + user.nombreUsuario + "!";
    }
    const contadorTiradas = document.getElementById("contadorTiradas");
    if (contadorTiradas) {
        contadorTiradas.innerText = "Tiradas: " + user.tiradas;
    }
}
function mostrarAdmin() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    if (user.id === 1 || user.id === 3) {
        const adminBtn = document.getElementById("adminBtn");
        adminBtn.classList.remove("hide");
        const tiradas = document.getElementById("contadorTiradas");
        tiradas.style.right = "135px";
    }
}

recuperarUsuario().then(() => {
    bienvenida();
    mostrarAdmin();
});

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
        imagenCarta.classList.add("carta");
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