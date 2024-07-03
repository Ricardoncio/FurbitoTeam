const audio = document.querySelector("audio");
const button = document.querySelector("#button");

button.addEventListener("click", () => {
    const volumeUp = document.querySelector("#volumeUp");
    const volumeOff = document.querySelector("#volumeOff");
    if (audio.paused) {
        audio.volume = 0.08;
        audio.play();
        volumeUp.classList.toggle("hide");
        volumeOff.classList.toggle("hide");
    } else {
        audio.pause();
        volumeUp.classList.toggle("hide");
        volumeOff.classList.toggle("hide");
    }
});

function empezarMusica() {
    audio.volume = 0.08;
    button.click();
}

document.body.addEventListener("click", () => {
    if (!button.classList.contains("empezado")) {
        empezarMusica();
        button.classList.add("empezado");
    }
})

function login(){
    document.getElementById('loginForm').addEventListener('submit', async function(event) {
        event.preventDefault();
        const formData = new FormData(this);
        const formBody = new URLSearchParams();
        formData.forEach((value, key) => {
            formBody.append(key, value);
        });
        try {
            const response = await fetch(this.action, {
                method: this.method,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formBody.toString()
            });

            if (response.ok) {
                const data = await response.json();
                sessionStorage.setItem("idUser", data.id);
                window.location.href = "http://localhost:8080/FurbitoTeam/index.html";
            } else {
                throw new Error('Network response was not ok ' + response.statusText);
            }

        } catch (error) {
            console.error('Error:', error);
        }
        });
}
login()