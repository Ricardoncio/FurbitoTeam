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
                sessionStorage.setItem("user", JSON.stringify(data));
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