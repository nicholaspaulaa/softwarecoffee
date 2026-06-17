package br.com.coffeehouse.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProdutoTest {

    @Test
    public void testCriacaoProduto() {
        Produto p = new Produto(1L, "Cafe Expresso", 6.50, "Bebidas");
        assertNotNull(p);
        assertEquals(Long.valueOf(1L), p.getId());
        assertEquals("Cafe Expresso", p.getNome());
        assertEquals(6.50, p.getPrecoBase(), 0.001);
    }

    @Test
    public void testConstrutorVazio() {
        Produto p = new Produto();
        assertNotNull(p);
        assertNull(p.getId());
        assertNull(p.getNome());
        assertEquals(0.0, p.getPrecoBase(), 0.001);
    }

    @Test
    public void testSetters() {
        Produto p = new Produto();
        p.setId(2L);
        p.setNome("Cappuccino");
        p.setPrecoBase(9.00);

        assertEquals(Long.valueOf(2L), p.getId());
        assertEquals("Cappuccino", p.getNome());
        assertEquals(9.00, p.getPrecoBase(), 0.001);
    }

    @Test
    public void testSetPrecoBaseNegativo() {
        Produto p = new Produto();
        p.setPrecoBase(-5.00);
        assertEquals(-5.00, p.getPrecoBase(), 0.001);
    }

    @Test
    public void testObterPreco() {
        Produto p = new Produto(1L, "Cappuccino", 9.00, "Bebidas");
        assertEquals(9.00, p.obterPreco(), 0.001);
    }

    @Test
    public void testObterPrecoAposSet() {
        Produto p = new Produto();
        p.setPrecoBase(12.50);
        assertEquals(12.50, p.obterPreco(), 0.001);
    }
}
