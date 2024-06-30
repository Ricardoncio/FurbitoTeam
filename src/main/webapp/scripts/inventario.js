async function mostrarInventario(){
    const user = JSON.parse(sessionStorage.getItem("user"));
    const response= await fetch("http://localhost:8080/FurbitoTeam/coleccion?idUser="+user.id,{method: "GET"});
    const inventario = await response.json();
    crearCartas(inventario);
}

function crearCartas(inventory){
    const inventario = document.getElementById('inventario');
    inventory.forEach(card => {
        const carta = document.createElement('img');
        carta.classList.add('card-'+card.id);
        carta.src = card.imageLink;
        carta.alt = card.alt;
        inventario.appendChild(carta);
    });
}

mostrarInventario()