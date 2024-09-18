document.addEventListener('DOMContentLoaded', function() {
    const formCategoria = document.getElementById('form-categoria');
    const formTransacao = document.getElementById('form-transacao');
    const listaTransacoes = document.getElementById('lista-transacoes');
    const saldoGeral = document.getElementById('saldo-geral');
    const receitas = document.getElementById('receitas');
    const despesas = document.getElementById('despesas');
    const selectCategoria = document.getElementById('transacao-categoria');

    function carregarCategorias() {
        fetch('/api/categorias')
            .then(response => response.json())
            .then(categorias => {
                selectCategoria.innerHTML = ''; 
                categorias.forEach(categoria => {
                    const option = document.createElement('option');
                    option.value = categoria.id;
                    option.textContent = categoria.nome;
                    selectCategoria.appendChild(option);
                });
            })
            .catch(error => console.error('Erro ao carregar categorias:', error));
    }

    function carregarTransacoes() {
        fetch('/api/transacoes')
            .then(response => response.json())
            .then(transacoes => {
                listaTransacoes.innerHTML = ''; 
                let total = 0;
                let receitaTotal = 0;
                let despesaTotal = 0;
                transacoes.forEach(transacao => {
                    const li = document.createElement('li');
                    li.textContent = `${transacao.descricao} - R$ ${transacao.valor.toFixed(2)} - Categoria: ${transacao.categoria.nome}`;
                    listaTransacoes.appendChild(li);
                    total += transacao.valor;
                    if (transacao.valor > 0) {
                        receitaTotal += transacao.valor;
                    } else {
                        despesaTotal += Math.abs(transacao.valor);
                    }
                });
                saldoGeral.textContent = `R$ ${total.toFixed(2)}`;
                receitas.textContent = `R$ ${receitaTotal.toFixed(2)}`;
                despesas.textContent = `R$ ${despesaTotal.toFixed(2)}`;
            })
            .catch(error => console.error('Erro ao carregar transações:', error));
    }

    formCategoria.addEventListener('submit', function(event) {
        event.preventDefault();
        const nome = document.getElementById('categoria-nome').value;
        fetch('/api/categorias', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome })
        })
        .then(response => response.json())
        .then(() => {
            carregarCategorias(); 
            formCategoria.reset();
        })
        .catch(error => console.error('Erro ao adicionar categoria:', error));
    });

    formTransacao.addEventListener('submit', function(event) {
        event.preventDefault();
        const descricao = document.getElementById('transacao-descricao').value;
        const valor = parseFloat(document.getElementById('transacao-valor').value);
        const categoriaId = parseInt(document.getElementById('transacao-categoria').value);
        fetch('/api/transacoes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ descricao, valor, categoria_id: categoriaId })
        })
        .then(response => response.json())
        .then(() => {
            carregarTransacoes();
            formTransacao.reset(); 
        })
        .catch(error => console.error('Erro ao adicionar transação:', error));
    });

    carregarCategorias();
    carregarTransacoes();
});
