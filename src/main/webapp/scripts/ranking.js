let parametro;
async function recuperarRanking() {
    const response = await fetch("http://localhost:8080/FurbitoTeam/ranking",{method: "GET"});
    parametro = await response.json();
}
function mostrarRanking(responseJson) {
    for (let i = 1; i < 7; i++) {
        let jugador = "jugador" + i;
        let puntos = "puntos" + i;
        const jugadorTxt = document.getElementById(jugador);
        const puntosTxt = document.getElementById(puntos);
        switch (i) {
            case 1:
                jugadorTxt.innerText = responseJson.primero[0];
                puntosTxt.innerText = responseJson.primero[1] + " pts";
                break;
            case 2:
                jugadorTxt.innerText = responseJson.segundo[0];
                puntosTxt.innerText = responseJson.segundo[1] + " pts";
                break;
            case 3:
                jugadorTxt.innerText = responseJson.tercero[0];
                puntosTxt.innerText = responseJson.tercero[1] + " pts";
                break;
            case 4:
                jugadorTxt.innerText = responseJson.cuarto[0];
                puntosTxt.innerText = responseJson.cuarto[1] + " pts";
                break;
            case 5:
                jugadorTxt.innerText = responseJson.quinto[0];
                puntosTxt.innerText = responseJson.quinto[1] + " pts";
                break;
            case 6:
                jugadorTxt.innerText = responseJson.sexto[0];
                puntosTxt.innerText = responseJson.sexto[1] + " pts";
                break;
        }
    }
}
recuperarRanking().then(() => {
    mostrarRanking(parametro);
});