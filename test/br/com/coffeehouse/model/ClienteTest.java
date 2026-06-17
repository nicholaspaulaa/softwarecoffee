package br.com.coffeehouse.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ClienteTest {

    @Test
    public void testCriacaoCliente() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        assertNotNull(c);
        assertEquals(Long.valueOf(1L), c.getId());
        assertEquals("Maria", c.getNome());
        assertEquals("12345678901", c.getCpf());
        assertEquals("maria@email.com", c.getEmail());
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testConstrutorVazio() {
        Cliente c = new Cliente();
        assertNotNull(c);
        assertNull(c.getId());
        assertNull(c.getNome());
        assertNull(c.getCpf());
        assertNull(c.getEmail());
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testSetters() {
        Cliente c = new Cliente();
        c.setId(2L);
        c.setNome("Joao");
        c.setCpf("99988877766");
        c.setEmail("joao@email.com");

        assertEquals(Long.valueOf(2L), c.getId());
        assertEquals("Joao", c.getNome());
        assertEquals("99988877766", c.getCpf());
        assertEquals("joao@email.com", c.getEmail());
    }

    @Test
    public void testAcumularPontos() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(50.0);
        assertEquals(50, c.getSaldoPontos());
    }

    @Test
    public void testAcumularPontosMultiplasVezes() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(30.0);
        c.acumularPontos(20.0);
        assertEquals(50, c.getSaldoPontos());
    }

    @Test
    public void testAcumularPontosValorZero() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(0.0);
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testAcumularPontosTruncaParaInteiro() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(9.99);
        assertEquals(9, c.getSaldoPontos());
    }
}
