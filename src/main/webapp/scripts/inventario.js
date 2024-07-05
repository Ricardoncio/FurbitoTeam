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
            carta.style.cursor = 'grab';
            carta.addEventListener('dragstart', drag);
            inventario.appendChild(carta);
        }else{
            const crdInTeam = document.getElementById(pos);
            crdInTeam.children[0].remove()
            const carta = document.createElement('img');
            carta.id = 'card-'+card.id;
            carta.src = card.imageLink;
            carta.alt = card.alt;
            carta.style.cursor = 'grab';
            carta.addEventListener('dragstart', drag);
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
    const draggedElementId = event.dataTransfer.getData('text/plain');
    if(!draggedElementId.includes('recursos/blank.png') && draggedElementId!==ev.target.id){
        ev.target.remove();
        const dropZone = event.currentTarget;
        const draggedElement = document.getElementById(draggedElementId);
        if(draggedElement.parentElement.id==='inventario'){
            if(ev.target.id){
                const inventario = document.getElementById('inventario');
                inventario.appendChild(ev.target);
            }
        }else{
            const otherCard = document.getElementById(draggedElement.parentElement.id);
            otherCard.appendChild(ev.target);
            await actualizarPlantilla(ev.target.id.split('-')[1]?ev.target.id.split('-')[1]:null,ev.target.parentElement.id);
        }
        dropZone.appendChild(draggedElement);
        await actualizarPlantilla(cardId,dropZone.id);
    }
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

async function removeCard(pos){
    const div = document.getElementById(pos);
    const inventario = document.getElementById('inventario');
    const card = div.children[1];
    if(card.tagName!=='svg' && card.id){
        div.children[1].remove();
        inventario.appendChild(card);
        const carta = document.createElement('img');
        carta.src = '../recursos/blank.png';
        div.appendChild(carta);
        await actualizarPlantilla(null,pos);
    }
}

async function clearInventory(){
    const userId = JSON.parse(sessionStorage.getItem("user")).id;
    await fetch("http://localhost:8080/FurbitoTeam/borrarEquipo?idUser="+userId,{method: "PUT"});

    const team = document.querySelector('.campo');
    for (const card of team.children) {
        if(card.children[1] && card.children[1].tagName!=='svg' && card.children[1].id){
            const inventario = document.getElementById('inventario');
            const card2 = card.children[1];
            card.children[1].remove();
            inventario.appendChild(card2);
            const carta = document.createElement('img');
            carta.src = '../recursos/blank.png';
            card.appendChild(carta)
        }
      }

}

mostrarInventario()