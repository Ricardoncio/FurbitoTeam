let cardId=null;

async function mostrarInventario(){
    const user = JSON.parse(sessionStorage.getItem("user"));
    const response= await fetch("http://localhost:8080/FurbitoTeam/coleccion?idUser="+user.id,{method: "GET"});
    const inventario = await response.json();
    const equipo = obtenerPlantilla();
    console.log(equipo)
    crearCartas(inventario);
}

function crearCartas(inventory){
    const inventario = document.getElementById('inventario');
    inventory.forEach(card => {
        const carta = document.createElement('img');
        carta.id = 'card-'+card.id;
        carta.src = card.imageLink;
        carta.alt = card.alt;
        carta.draggable = true;
        carta.addEventListener('dragstart', drag);
        inventario.appendChild(carta);
    });
}

function allowDrop(ev) {
    ev.preventDefault();
}
  
function drag(ev) {
    let data = ev.target.id;
    ev.dataTransfer.setData("text", data);
    cardId=data.split('-')[1];
}
  
function drop(ev) {
    event.preventDefault();
    ev.target.remove();
    const draggedElementId = event.dataTransfer.getData('text/plain');
    const dropZone = event.currentTarget;
    const draggedElement = document.getElementById(draggedElementId);
    dropZone.appendChild(draggedElement);
    actualizarPlantilla(cardId);
}

async function actualizarPlantilla(cardId){
    const userId = JSON.parse(sessionStorage.getItem("user")).id;
    console.log(userId)
    console.log(cardId)
    const response= await fetch("http://localhost:8080/FurbitoTeam/cambiarEquipo?idUser="+userId+"&idCarta="+cardId,{method: "PUT"});
    const plantilla = await response.json();
}

async function obtenerPlantilla(){
    const userId = JSON.parse(sessionStorage.getItem("user")).id;
    const response= await fetch("http://localhost:8080/FurbitoTeam/equipo?idUser="+userId,{method: "PUT"});
    const equipo = await response.json();
    return equipo;
}

mostrarInventario() 