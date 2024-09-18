package com.financecontrol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    private Connection connection;

    public TransacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionarTransacao(Transacao transacao) {
        String sql = "INSERT INTO transacao (descricao, valor, categoria_id, data) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transacao.getDescricao());
            stmt.setDouble(2, transacao.getValor());
            stmt.setInt(3, transacao.getCategoria().getId());
            stmt.setDate(4, new java.sql.Date(transacao.getData().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transacao> listarTransacoes() {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacao";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("categoria_id"), "Despesas"); // Exemplo simplificado
                Transacao transacao = new Transacao(
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        categoria
                );
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transacoes;
    }
}
