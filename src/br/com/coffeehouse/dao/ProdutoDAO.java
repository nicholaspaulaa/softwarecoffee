package br.com.coffeehouse.dao;

import br.com.coffeehouse.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static List<Produto> bancoProdutos = new ArrayList<>();

    public void salvar(Produto p) {
        Produto existente = buscarPorId(p.getId());
        if (existente != null) {
            existente.setNome(p.getNome());
            existente.setPrecoBase(p.getPrecoBase());
            existente.setCategoria(p.getCategoria());
        } else {
            bancoProdutos.add(p);
        }
    }

    public Produto buscarPorId(Long id) {
        for (Produto p : bancoProdutos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(bancoProdutos);
    }

    public boolean excluir(Long id) {
        Produto p = buscarPorId(id);
        if (p != null) {
            return bancoProdutos.remove(p);
        }
        return false;
    }

    private static long proximoId = 1;

    public void salvarComIdAuto(Produto p) {
        if (p.getId() == null) {
            p.setId(proximoId++);
        }
        salvar(p);
    }

    static {
        bancoProdutos.add(new Produto(1L, "Café Expresso", 6.50, "Bebidas"));
        bancoProdutos.add(new Produto(2L, "Cappuccino Italiano", 9.00, "Bebidas"));
        bancoProdutos.add(new Produto(3L, "Fatia de Bolo de Cenoura", 12.00, "Confeitaria"));
        proximoId = 4;
    }
}