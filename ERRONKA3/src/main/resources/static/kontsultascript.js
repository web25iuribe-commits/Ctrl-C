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
        window.location.href = "/logout";
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('[data-action="irten"]').forEach(button => {
        button.style.backgroundColor = '#dc3545';
        button.style.color = '#ffffff';
        button.style.border = 'none';
        button.style.padding = '0.5rem 1rem';
        button.style.borderRadius = '0.25rem';
        button.style.fontWeight = '600';
        button.style.textDecoration = 'none';
        button.style.cursor = 'pointer';
        button.style.display = 'inline-block';
        button.onmouseenter = () => button.style.backgroundColor = '#c82333';
        button.onmouseleave = () => button.style.backgroundColor = '#dc3545';
    });

    document.querySelectorAll('[data-action="atzera"], [data-action="back"]').forEach(button => {
        button.style.backgroundColor = '#0052cc';
        button.style.color = '#ffffff';
        button.style.border = 'none';
        button.style.padding = '0.5rem 1rem';
        button.style.borderRadius = '0.25rem';
        button.style.fontWeight = '600';
        button.style.textDecoration = 'none';
        button.style.cursor = 'pointer';
        button.style.display = 'inline-block';
        button.onmouseenter = () => button.style.backgroundColor = '#0043a3';
        button.onmouseleave = () => button.style.backgroundColor = '#0052cc';
    });
});
