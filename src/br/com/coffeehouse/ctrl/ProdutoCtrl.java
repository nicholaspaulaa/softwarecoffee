package br.com.coffeehouse.ctrl;

import br.com.coffeehouse.dao.ProdutoDAO;
import br.com.coffeehouse.model.Produto;
import java.util.List;

public class ProdutoCtrl {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public String cadastrarProduto(Long id, String nome, double preco, String categoria) {
        if (id == null || id <= 0) return "Erro: ID inválido.";
        if (nome == null || nome.trim().isEmpty()) return "Erro: Nome do produto inválido.";
        if (preco < 0) return "Erro: O preço não pode ser negativo.";

        Produto novo = new Produto(id, nome, preco, categoria);
        produtoDAO.salvar(novo);
        return "Produto salvo com sucesso!";
    }

    public List<Produto> listarCardapio() {
        return produtoDAO.listarTodos();
    }

    public boolean removerDoCardapio(Long id) {
        return produtoDAO.excluir(id);
    }
}