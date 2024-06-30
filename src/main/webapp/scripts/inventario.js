let cardId=null;

async function mostrarInventario(){
    const user = JSON.parse(sessionStorage.getItem("user"));
    const response= await fetch("http://localhost:8080/FurbitoTeam/coleccion?idUser="+user.id,{method: "GET"});
    const inventario = await response.json();
    const equipo = await obtenerPlantilla();
    crearCartas(inventario,equipo);
}

function crearCartas(inventory,equipo){
    const inventario = document.getElementById('inventario');
    inventory.forEach(card => {
        const pos=Object.keys(equipo).find(pos=>equipo[pos].id===card.id)
        if(equipo && !pos){
            const carta = document.createElement('img');
            carta.id = 'card-'+card.id;
            carta.src = card.imageLink;
            carta.alt = card.alt;
            carta.draggable = true;
            carta.addEventListener('dragstart', drag);
            inventario.appendChild(carta);
        }else{
            const crdInTeam = document.getElementById(pos);
            crdInTeam.children[0].remove()
            const carta = document.createElement('img');
            carta.id = 'card-'+card.id;
            carta.src = card.imageLink;
            carta.alt = card.alt;
            crdInTeam.appendChild(carta)
        }
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
  
async function drop(ev) {
    event.preventDefault();
    ev.target.remove();
    const draggedElementId = event.dataTransfer.getData('text/plain');
    const dropZone = event.currentTarget;
    const draggedElement = document.getElementById(draggedElementId);
    dropZone.appendChild(draggedElement);
    await actualizarPlantilla(cardId,dropZone.id);
}

async function actualizarPlantilla(cardId,pos){
    const userId = JSON.parse(sessionStorage.getItem("user")).id;
    await fetch("http://localhost:8080/FurbitoTeam/cambiarEquipo?idUser="+userId+"&idCarta="+cardId+"&pos="+pos,{method: "PUT"});
}

async function obtenerPlantilla(){
    const userId = JSON.parse(sessionStorage.getItem("user")).id;
    const response= await fetch("http://localhost:8080/FurbitoTeam/equipo?idUser="+userId,{method: "GET"});
    const equipo = await response.json();
    return equipo;
}

mostrarInventario() 