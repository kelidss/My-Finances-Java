package com.financecontrol;

import java.util.Date;

public class Transacao {
    private int id;
    private String descricao;
    private double valor;
    private Categoria categoria;
    private Date data;

    public Transacao(String descricao, double valor, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = new Date(); 
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Date getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Transacao{id=" + id + ", descricao='" + descricao + "', valor=" + valor + ", categoria=" + categoria + ", data=" + data + "}";
    }
}
