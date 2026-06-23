package br.com.coffeehouse.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.coffeehouse.TestDataReset;
import br.com.coffeehouse.model.Produto;

public class ProdutoDAOTest {

    private ProdutoDAO produtoDAO;

    @Before
    public void setUp() {
        TestDataReset.produtos();
        produtoDAO = new ProdutoDAO();
    }

    @Test
    public void testSalvarNovoProduto() {
        Produto p = new Produto(100L, "Cha Gelado", 7.00, "Bebidas");
        produtoDAO.salvar(p);

        Produto encontrado = produtoDAO.buscarPorId(100L);
        assertNotNull(encontrado);
        assertEquals("Cha Gelado", encontrado.getNome());
    }

    @Test
    public void testBuscarPorIdExistente() {
        Produto encontrado = produtoDAO.buscarPorId(1L);
        assertNotNull(encontrado);
        assertEquals("Cafe Expresso", encontrado.getNome());
    }

    @Test
    public void testBuscarPorIdInexistente() {
        Produto encontrado = produtoDAO.buscarPorId(999L);
        assertNull(encontrado);
    }

    @Test
    public void testListarTodos() {
        assertTrue(produtoDAO.listarTodos().size() >= 3);
    }

    @Test
    public void testExcluirProdutoExistente() {
        boolean removido = produtoDAO.excluir(3L);
        assertTrue(removido);
        assertNull(produtoDAO.buscarPorId(3L));
    }

    @Test
    public void testExcluirProdutoInexistente() {
        boolean removido = produtoDAO.excluir(999L);
        assertFalse(removido);
    }

    @Test
    public void testSalvarAtualizaProdutoExistente() {
        Produto existente = produtoDAO.buscarPorId(1L);
        assertNotNull(existente);

        Produto atualizado = new Produto(1L, "Cafe Expresso Atualizado", 7.50, "Bebidas");
        produtoDAO.salvar(atualizado);

        Produto encontrado = produtoDAO.buscarPorId(1L);
        assertEquals("Cafe Expresso Atualizado", encontrado.getNome());
        assertEquals(7.50, encontrado.getPrecoBase(), 0.001);
    }
}
