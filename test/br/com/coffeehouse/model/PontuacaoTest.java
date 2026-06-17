package br.com.coffeehouse.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class PontuacaoTest {

    @Test
    public void testAcumularValorExato() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(10.0);
        assertEquals(10, c.getSaldoPontos());
    }

    @Test
    public void testAcumularTruncaDecimais() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(9.99);
        assertEquals(9, c.getSaldoPontos());
    }

    @Test
    public void testAcumularTruncaCincoenta() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(5.50);
        assertEquals(5, c.getSaldoPontos());
    }

    @Test
    public void testAcumularValorPequenoTruncaZero() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(0.99);
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testAcumularValorAbaixoDeUm() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(0.01);
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testAcumularCemReais() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(100.0);
        assertEquals(100, c.getSaldoPontos());
    }

    @Test
    public void testAcumularMultiplosPedidos() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(15.0);
        c.acumularPontos(25.0);
        c.acumularPontos(30.0);
        assertEquals(70, c.getSaldoPontos());
    }

    @Test
    public void testAcumularMultiplosPedidosComTruncamento() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(10.50);
        c.acumularPontos(10.50);
        assertEquals(20, c.getSaldoPontos());
    }

    @Test
    public void testAcumularValorNegativo() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(-5.0);
        assertEquals(-5, c.getSaldoPontos());
    }

    @Test
    public void testAcumularValorNegativoComDecimais() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(-5.99);
        assertEquals(-5, c.getSaldoPontos());
    }

    @Test
    public void testSaldoInicialZero() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testAcumularSemEstouroLimite() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(Integer.MAX_VALUE - 1);
        c.acumularPontos(1.0);
        assertEquals(Integer.MAX_VALUE, c.getSaldoPontos());
    }
}
