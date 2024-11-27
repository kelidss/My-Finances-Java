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

            window.location.href = `/`;
        } else {
            alert('Login falhou. Verifique suas credenciais.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro ao realizar login. Tente novamente mais tarde.');
    }
});