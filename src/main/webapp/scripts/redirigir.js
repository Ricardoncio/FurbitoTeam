function redirigir() {
    const idUser = sessionStorage.getItem("idUser");
    const user = JSON.parse(sessionStorage.getItem("user"));
    if (user == null && idUser == null) {
        window.location.href = "http://localhost:8080/FurbitoTeam/plantillas/login.html";
    }
}
redirigir();

function volverInicio() {
    const inicioBtn = document.getElementById("volverInicio");
    if (inicioBtn) {
        inicioBtn.addEventListener("click", () => {
            window.location.href = "http://localhost:8080/FurbitoTeam/index.html"
        })
    }
}
volverInicio();