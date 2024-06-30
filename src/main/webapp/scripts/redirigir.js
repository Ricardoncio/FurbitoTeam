function bienvenida() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const mensajeBienvenida = document.getElementById("mensajeBienvenida");
    if(mensajeBienvenida){
        mensajeBienvenida.innerText = "Bienvenido " + user.nombreUsuario;
    }
}

function redirigir() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    if (user != null) {
        bienvenida();
    } else {
        window.location.href = "http://localhost:8080/FurbitoTeam/plantillas/login.html";
    }
}

redirigir();