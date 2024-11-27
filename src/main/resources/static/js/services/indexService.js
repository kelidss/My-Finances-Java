document.addEventListener('DOMContentLoaded', function() {
    const jwtToken = localStorage.getItem('jwtToken');

    const formCategory = document.getElementById('form-categoria');
    const formTransaction = document.getElementById('form-transacao');
    const listTransactions = document.getElementById('lista-transacoes');
    const saldoTotal = document.getElementById('valor-total');
    const saldoReceitas = document.getElementById('total-receitas');
    const saldoDespesas = document.getElementById('total-despesas');
    const selectCategory = document.getElementById('transacao-categoria');
    const filterDateInput = document.getElementById('filter-date');
    const totalFiltrado = document.getElementById('total-filtrado');
    let categories_to_map = "";

    async function loadCategories() {
        const response = await fetch('/categorys');
        const categories = await response.json();

        categories_to_map = categories;

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

    async function loadTransactions(filterDate = null) {
    
        totalFiltrado.textContent = `R$ 0,00`;

        const response = await fetch('/transactions', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json',
            },
        });
        const transactions = await response.json();
    
        let total = 0;
        let receitaTotal = 0;
        let despesaTotal = 0;
        const despesasCategoria = {};
        let totalFiltradoValor = 0;
    
        listTransactions.innerHTML = '';
    
        transactions.forEach(transaction => {
            const transactionDate = new Date(transaction.createdAt);
            const formattedTransactionDate = transactionDate.toISOString().split('T')[0]; 
    
            if (filterDate && filterDate !== formattedTransactionDate) {
                return;  
            }
    
            const formattedDate = `${transactionDate.getDate().toString().padStart(2, '0')}/${(transactionDate.getMonth() + 1).toString().padStart(2, '0')}/${transactionDate.getFullYear()}`;
    
            const li = document.createElement('li');
            li.textContent = `${transaction.description} - R$ ${transaction.value.toFixed(2)} - Categoria: ${transaction.category.name} - Data: ${formattedDate}`;
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
    
            totalFiltradoValor += transaction.value;
        });
    
        saldoTotal.textContent = `R$ ${total.toFixed(2)}`;
        saldoReceitas.textContent = `R$ ${receitaTotal.toFixed(2)}`;
        saldoDespesas.textContent = `R$ ${despesaTotal.toFixed(2)}`;
    

        if (filterDate) {
            totalFiltrado.textContent = `R$ ${totalFiltradoValor.toFixed(2)}`;
        }
    
        atualizarGraficoDespesas(despesasCategoria);
    }
    
    document.getElementById('apply-filter').addEventListener('click', function() {
        const filterDate = filterDateInput.value ? filterDateInput.value : null;
        loadTransactions(filterDate);
    });

    document.getElementById('clear-filter').addEventListener('click', function() {
        filterDateInput.value = ''; 
        loadTransactions(); 
    });

    formCategory.addEventListener('submit', async function(event) {
        event.preventDefault();
        const name = document.getElementById('categoria-nome').value;

        const response = await fetch('categorys', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ "name": name })
        });

        const jsonResponse = await response.json();
        alert(jsonResponse.message || "Categoria criada com sucesso");
        loadCategories();
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
                "category": { "id": category_id }
            })
        });

        const jsonResponse = await response.json();
        alert(jsonResponse.message || "Transação criada com sucesso");
        loadTransactions();
        formTransaction.reset();
    });

    function atualizarGraficoDespesas(despesasCategoria) {
        const ctx = document.getElementById('despesas-chart').getContext('2d');

        const despesasChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: Object.keys(despesasCategoria),
                datasets: [{
                    label: 'Despesas por Categoria',
                    data: Object.values(despesasCategoria),
                    backgroundColor: ['#FF4D4D', '#FFCC00', '#66FF66', '#4CAF50'],
                    borderColor: '#1E1E1E',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        labels: { color: '#E0E0E0' }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            color: '#D1D1D1',
                            callback: function(value) {
                                return `R$ ${value}`;
                            }
                        },
                        grid: { color: '#444' }
                    },
                    x: {
                        ticks: { color: '#D1D1D1' },
                        grid: { color: '#444' }
                    }
                }
            }
        });
    }

    loadCategories();
    loadTransactions();
});
