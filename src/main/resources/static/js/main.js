document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("jwtToken");

    if (!token && window.location.pathname != "/login") {
        alert("Você precisa fazer login.");
        window.location.href = "/login";
        return;
    }
});

document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (response.ok) {
            const data = await response.json();
            
            localStorage.setItem('jwtToken', data.token);
            logar(data.token)
        } else {
            alert('Login falhou. Verifique suas credenciais.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro ao realizar login. Tente novamente mais tarde.');
    }
});

function logar(token){
    // Requisição para carregar o index.html
    fetch("/", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Acesso não autorizado.");
        }
        return response.text(); // Recebe o HTML como texto
    })
    .then(html => {
        document.open();
        document.write(html); // Renderiza o HTML na página
        document.close();
    })
    .catch(err => {
        console.error(err);
        alert("Sessão inválida ou expirada. Faça login novamente.");
        localStorage.removeItem("jwtToken");
        window.location.href = "/login";
    });
}