package br.com.coffeehouse.model;

public class Funcionario {
    private Long id;
    private String nome;
    private String cargo;

    public Funcionario(Long id, String nome, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }

    public String getCargo() { return cargo; }
}