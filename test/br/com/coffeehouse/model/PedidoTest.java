package br.com.coffeehouse.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class PedidoTest {

    @Test
    public void testCriacaoPedido() {
        Pedido pedido = new Pedido(1L);
        assertNotNull(pedido);
        assertTrue(pedido.getItens().isEmpty());
    }

    @Test
    public void testAdicionarProduto() {
        Pedido pedido = new Pedido(1L);
        Produto p = new Produto(1L, "Cafe", 5.00, "Bebidas");
        pedido.adicionarProduto(p);
        assertEquals(1, pedido.getItens().size());
        assertEquals(5.00, pedido.getValorTotal(), 0.001);
    }

    @Test
    public void testCalcularTotalMultiplosItens() {
        Pedido pedido = new Pedido(1L);
        pedido.adicionarProduto(new Produto(1L, "Cafe", 5.00, "Bebidas"));
        pedido.adicionarProduto(new Produto(2L, "Bolo", 12.00, "Confeitaria"));
        assertEquals(17.00, pedido.getValorTotal(), 0.001);
    }
}
