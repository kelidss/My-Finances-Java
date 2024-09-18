package com.financecontrol;

public class App {
    public static void main(String[] args) {

        DatabaseConnection dbConnection = new DatabaseConnection();
        CategoriaDAO categoriaDAO = new CategoriaDAO(dbConnection.getConnection());
        TransacaoDAO transacaoDAO = new TransacaoDAO(dbConnection.getConnection());

        // Exemplo: Adicionar uma nova categoria
        Categoria categoria = new Categoria("Despesas");
        categoriaDAO.adicionarCategoria(categoria);

        // Exemplo: Adicionar uma nova transação
        Transacao transacao = new Transacao("Compra supermercado", 150.00, categoria);
        transacaoDAO.adicionarTransacao(transacao);

        // Mostrar todas as transações
        transacaoDAO.listarTransacoes().forEach(System.out::println);

        // Fechar conexão
        dbConnection.close();
    }
}
