document.addEventListener('DOMContentLoaded', function() {
    const formCategoria = document.getElementById('form-categoria');
    const formTransacao = document.getElementById('form-transacao');
    const listaTransacoes = document.getElementById('lista-transacoes');
    const saldoGeral = document.getElementById('valor-total');
    const receitas = document.getElementById('receitas');
    const despesas = document.getElementById('despesas');
    const selectCategoria = document.getElementById('transacao-categoria');
    const ctx = document.getElementById('despesas-chart').getContext('2d'); 

    let despesasChart = null; 

    function loadCategorys() {
        fetch('/categorys')
            .then(response => response.json())
            .then(categorys => {
                selectCategoria.innerHTML = '';

                // Adiciona a opção padrão "Categoria"
                const defaultOption = document.createElement('option');
                defaultOption.value = '';
                defaultOption.textContent = 'Categoria';
                selectCategoria.appendChild(defaultOption);

                categorys.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.id;
                    option.textContent = category.name;
                    selectCategoria.appendChild(option);
                });
            })
            .catch(error => console.error('Erro ao carregar categorias:', error));
    }

    function loadTransactions() {
        fetch('/transactions')
            .then(response => response.json())
            .then(transactions => { 
                let total = 0;
                let receitaTotal = 0;
                let despesaTotal = 0;
                const despesasCategoria = {}; 
                listaTransacoes.innerHTML = '';

                transactions.forEach(transaction => {
                    const li = document.createElement('li');
                    li.textContent = `${transaction.description} - R$ ${transaction.value.toFixed(2)} - Categoria: ${transaction.category.name}`;
                    listaTransacoes.appendChild(li);
                    total += transaction.value;
                    if (transaction.value > 0) {
                        receitaTotal += transaction.value;
                    } else {
                        despesaTotal += Math.abs(transaction.value);
                       
                        if (!despesasCategoria[transaction.category.name]) {
                            despesasCategoria[transaction.category.name] = 0;
                        }
                        despesasCategoria[transaction.category.name] += Math.abs(transaction.value);
                    }
                });

                saldoGeral.textContent = `R$ ${total.toFixed(2)}`;
                receitas.textContent = `R$ ${receitaTotal.toFixed(2)}`;
                despesas.textContent = `R$ ${despesaTotal.toFixed(2)}`;

                atualizarGraficoDespesas(despesasCategoria); 
            })
            .catch(error => console.error('Erro ao carregar transações:', error));
    }

    function atualizarGraficoDespesas(despesasCategoria) {
        if (despesasChart) {
            despesasChart.destroy(); 
        }

        despesasChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: Object.keys(despesasCategoria),
                datasets: [{
                    label: 'Despesas por Categoria',
                    data: Object.values(despesasCategoria),
                    backgroundColor: '#3498db',
                    borderColor: '#2980b9',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return `R$ ${value}`;
                            }
                        }
                    }
                }
            }
        });
    }

    formCategoria.addEventListener('submit', function(event) {
        event.preventDefault();
        const name = document.getElementById('categoria-nome').value;
        fetch('categorys', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "name": name
            })
        })
        .then(response => response.json())
        .then(() => {
            loadCategorys(); 
            formCategoria.reset();
        })
        .catch(error => console.error('Erro ao adicionar categoria:', error));
    });

    formTransacao.addEventListener('submit', function(event) {
        event.preventDefault();
        const description = document.getElementById('transacao-descricao').value;
        const value = parseFloat(document.getElementById('transacao-valor').value);
        const category_id = parseInt(document.getElementById('transacao-categoria').value);
        fetch('/transactions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                "description": description,
                "value": value,
                "category": {
                    "id": category_id
                }
            })
        })
        .then(response => response.json())
        .then(() => {
            loadTransactions();
            formTransacao.reset(); 
        })
        .catch(error => console.error('Erro ao adicionar transação:', error));
    });

    loadCategorys();
    loadTransactions();
});
