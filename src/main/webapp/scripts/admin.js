function myFunction() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
}
document.getElementById("formulario").addEventListener("submit", async (event) => {
    event.preventDefault();
    const formulario = document.getElementById("formulario");
    const formData = new FormData(formulario);
    const formBody = new URLSearchParams();
    formData.forEach((value, key) => {
        formBody.append(key,value)
    })
    console.log('Form Data:', [...formData.entries()]); // Log form data entries
    console.log('Form Body:', formBody.toString()); // Log form body string
    const response = await fetch(formulario.action,{
        method: formulario.method,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formBody
    });
    if (response.ok) {
        const mensaje = document.getElementById("snackbar");
        mensaje.innerText = "¡Cambios guardados con éxito!";
        myFunction();
    } else {
        const mensaje = document.getElementById("snackbar");
        mensaje.innerText = "Lo sentimos, no se han podido guardar los cambios";
        myFunction();
    }

})