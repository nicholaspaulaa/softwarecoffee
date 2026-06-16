package br.com.coffeehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Long id;
    private List<Produto> itens = new ArrayList<>();
    private Cliente clienteVinculado;
    private double valorTotal;

    public Pedido(Long id) {
        this.id = id;
    }

    public void adicionarProduto(Produto p) {
        itens.add(p);
        calcularTotal();
    }

    public void vincularCliente(Cliente c) {
        this.clienteVinculado = c;
    }

    public void calcularTotal() {
        this.valorTotal = 0;
        for (Produto p : itens) {
            this.valorTotal += p.obterPreco();
        }
    }

    public double getValorTotal() { return valorTotal; }
    public List<Produto> getItens() { return itens; }
    public Cliente getClienteVinculado() { return clienteVinculado; }
}