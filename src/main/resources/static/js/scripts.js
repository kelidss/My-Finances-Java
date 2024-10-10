document.addEventListener('DOMContentLoaded', function() {
    const formCategory = document.getElementById('form-categoria');
    const formTransaction = document.getElementById('form-transacao');
    const listTransactions = document.getElementById('lista-transacoes');
    const saldoGeral = document.getElementById('valor-total');
    const receitas = document.getElementById('receitas');
    const despesas = document.getElementById('despesas');
    const selectCategory = document.getElementById('transacao-categoria');
    const ctx = document.getElementById('despesas-chart').getContext('2d'); 

    let despesasChart = null; 

    async function loadCategories() {
        const response = await fetch('/categorys');
        const categories = await response.json();

        selectCategory.innerHTML = '';

        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.textContent = 'Categoria';
        selectCategory.appendChild(defaultOption);

        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            selectCategory.appendChild(option);
        });
    }

    async function loadTransactions() {
        const response = await fetch('/transactions');
        const transactions = await response.json();
                
        let total = 0;
        let receitaTotal = 0;
        let despesaTotal = 0;
        const despesasCategoria = {}; 
        listTransactions.innerHTML = '';

        transactions.forEach(transaction => {
            const li = document.createElement('li');
            li.textContent = `${transaction.description} - R$ ${transaction.value.toFixed(2)} - Categoria: ${transaction.category.name}`;
            listTransactions.appendChild(li);
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

    formCategory.addEventListener('submit', async function(event) {
        event.preventDefault();

        const name = document.getElementById('categoria-nome').value;

        const response = await fetch('categorys', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "name": name
            })
        });

        const jsonResponse = await response.json();
        alert(jsonResponse.message || "Categoria criada com sucesso");
        loadCategories()
        formCategory.reset();
    });

    formTransaction.addEventListener('submit', async function(event) {
        event.preventDefault();

        const description = document.getElementById('transacao-descricao').value;
        const value = parseFloat(document.getElementById('transacao-valor').value);
        const category_id = parseInt(document.getElementById('transacao-categoria').value);

        const response = await fetch('/transactions', {
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
        
        const jsonResponse = await response.json();
        alert(jsonResponse.message || "Transação criada com sucesso");
        loadTransactions();
        formTransaction.reset();
    });

    loadCategories();
    loadTransactions();
});
