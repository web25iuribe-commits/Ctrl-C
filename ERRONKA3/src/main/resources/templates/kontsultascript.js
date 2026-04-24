const bsButton = new bootstrap.Button('#myButton')

document.querySelectorAll('.btn').forEach(buttonElement => {
    const button = bootstrap.Button.getOrCreateInstance(buttonElement)
    button.toggle()
})

function toggleButton() {
    const button = document.querySelector('#myButton')
    button.classList.toggle('active')
}


function irten() {
    if (confirm("Ziur zaude irten nahi duzula?")) {
        window.location.href = "./index.html";
    }
}


function bilatu() {
    localStorage.setItem("searchQuery", document.querySelector('.form-control').value);
    window.location.href = "./bilatu.html";
    let searchQuery = localStorage.getItem("searchQuery");
    console.log("Bilaketa kontsulta_admi.js-tik: " + searchQuery);
    window.location.href = "http://localhost:8080/bilatu?query=" + searchQuery;
}

