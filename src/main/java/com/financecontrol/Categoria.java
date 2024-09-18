package com.financecontrol;

public class Categoria {
    private int id;
    private String nome;

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nome='" + nome + "'}";
    }
}
