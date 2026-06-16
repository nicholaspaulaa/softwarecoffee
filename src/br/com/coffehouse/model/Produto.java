package br.com.coffeehouse.model;

public class Produto {
    private Long id;
    private String nome;
    private double precoBase;
    private String category;

    public Produto() {}

    public Produto(Long id, String nome, double precoBase, String category) {
        this.id = id;
        this.nome = nome;
        this.precoBase = precoBase;
        this.category = category;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPrecoBase() { return precoBase; }
    public void setPrecoBase(double precoBase) { this.precoBase = precoBase; }

    public double obterPreco() {
        return this.precoBase;
    }
}