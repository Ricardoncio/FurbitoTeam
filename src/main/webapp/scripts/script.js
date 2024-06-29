async function mostrarCarta(){
    const response= await fetch("http://localhost:8080/FurbitoTeam/pedirCarta",{method: "GET"});
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