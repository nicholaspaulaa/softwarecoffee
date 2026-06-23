package br.com.coffeehouse.ctrl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.coffeehouse.TestDataReset;

public class ProdutoCtrlTest {

    private ProdutoCtrl produtoCtrl;

    @Before
    public void setUp() {
        TestDataReset.produtos();
        produtoCtrl = new ProdutoCtrl();
    }

    @Test
    public void testCadastrarProdutoValido() {
        String resultado = produtoCtrl.cadastrarProduto(100L, "Cha Gelado", 7.00, "Bebidas");
        assertEquals("Produto salvo com sucesso!", resultado);
    }

    @Test
    public void testCadastrarProdutoIdNull() {
        String resultado = produtoCtrl.cadastrarProduto(null, "Cha Gelado", 7.00, "Bebidas");
        assertEquals("Erro: ID inválido.", resultado);
    }

    @Test
    public void testCadastrarProdutoIdZero() {
        String resultado = produtoCtrl.cadastrarProduto(0L, "Cha Gelado", 7.00, "Bebidas");
        assertEquals("Erro: ID inválido.", resultado);
    }

    @Test
    public void testCadastrarProdutoIdNegativo() {
        String resultado = produtoCtrl.cadastrarProduto(-1L, "Cha Gelado", 7.00, "Bebidas");
        assertEquals("Erro: ID inválido.", resultado);
    }

    @Test
    public void testCadastrarProdutoNomeVazio() {
        String resultado = produtoCtrl.cadastrarProduto(100L, "", 7.00, "Bebidas");
        assertEquals("Erro: Nome do produto inválido.", resultado);
    }

    @Test
    public void testCadastrarProdutoNomeNull() {
        String resultado = produtoCtrl.cadastrarProduto(100L, null, 7.00, "Bebidas");
        assertEquals("Erro: Nome do produto inválido.", resultado);
    }

    @Test
    public void testCadastrarProdutoPrecoNegativo() {
        String resultado = produtoCtrl.cadastrarProduto(100L, "Cha Gelado", -1.00, "Bebidas");
        assertEquals("Erro: O preço não pode ser negativo.", resultado);
    }

    @Test
    public void testCadastrarProdutoPrecoNegativoMinimo() {
        String resultado = produtoCtrl.cadastrarProduto(100L, "Cha Gelado", -0.01, "Bebidas");
        assertEquals("Erro: O preço não pode ser negativo.", resultado);
    }

    @Test
    public void testCadastrarProdutoPrecoZero() {
        String resultado = produtoCtrl.cadastrarProduto(100L, "Cha Gelado", 0.00, "Bebidas");
        assertEquals("Produto salvo com sucesso!", resultado);
    }

    @Test
    public void testListarCardapio() {
        assertNotNull(produtoCtrl.listarCardapio());
        assertTrue(produtoCtrl.listarCardapio().size() >= 3);
    }

    @Test
    public void testRemoverDoCardapioExistente() {
        boolean removido = produtoCtrl.removerDoCardapio(3L);
        assertTrue(removido);
    }

    @Test
    public void testRemoverDoCardapioInexistente() {
        boolean removido = produtoCtrl.removerDoCardapio(999L);
        assertFalse(removido);
    }
}
