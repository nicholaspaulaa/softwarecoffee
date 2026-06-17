package br.com.coffeehouse.ctrl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.coffeehouse.model.Cliente;
import br.com.coffeehouse.model.Produto;

public class PedidoCtrlTest {

    private PedidoCtrl pedidoCtrl;

    @Before
    public void setUp() {
        pedidoCtrl = new PedidoCtrl();
    }

    @Test
    public void testIniciarNovoPedido() {
        pedidoCtrl.iniciarNovoPedido(1L);
        assertNotNull(pedidoCtrl.getPedidoAtual());
    }

    @Test
    public void testFinalizarPedidoVazio() {
        pedidoCtrl.iniciarNovoPedido(1L);
        String resultado = pedidoCtrl.finalizarPedido();
        assertEquals("Erro: Nenhum item no pedido.", resultado);
    }

    @Test
    public void testFinalizarPedidoSemIniciar() {
        String resultado = pedidoCtrl.finalizarPedido();
        assertEquals("Erro: Nenhum item no pedido.", resultado);
    }

    @Test
    public void testFinalizarPedidoComItens() {
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 5.00, "Bebidas")
        );
        String resultado = pedidoCtrl.finalizarPedido();
        assertTrue(resultado.contains("Pedido finalizado"));
    }

    @Test
    public void testFinalizarPedidoSemClienteNaoAcumulaPontos() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 50.0, "Bebidas")
        );
        pedidoCtrl.finalizarPedido();
        assertEquals(0, c.getSaldoPontos());
    }

    @Test
    public void testFinalizarPedidoComClienteAcumulaPontos() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 50.0, "Bebidas")
        );
        pedidoCtrl.getPedidoAtual().vincularCliente(c);
        pedidoCtrl.finalizarPedido();
        assertEquals(50, c.getSaldoPontos());
    }

    @Test
    public void testFinalizarPedidoComClienteTruncaPontos() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 9.99, "Bebidas")
        );
        pedidoCtrl.getPedidoAtual().vincularCliente(c);
        pedidoCtrl.finalizarPedido();
        assertEquals(9, c.getSaldoPontos());
    }

    @Test
    public void testFinalizarPedidoMultiplosItensAcumulaPontos() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 15.0, "Bebidas")
        );
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(2L, "Bolo", 10.0, "Confeitaria")
        );
        pedidoCtrl.getPedidoAtual().vincularCliente(c);
        pedidoCtrl.finalizarPedido();
        assertEquals(25, c.getSaldoPontos());
    }

    @Test
    public void testFinalizarPedidoAcumulaEmClienteExistente() {
        Cliente c = new Cliente(1L, "Maria", "12345678901", "maria@email.com");
        c.acumularPontos(100.0);
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 30.0, "Bebidas")
        );
        pedidoCtrl.getPedidoAtual().vincularCliente(c);
        pedidoCtrl.finalizarPedido();
        assertEquals(130, c.getSaldoPontos());
    }

    @Test
    public void testFinalizarPedidoRetornaTotal() {
        pedidoCtrl.iniciarNovoPedido(1L);
        pedidoCtrl.getPedidoAtual().adicionarProduto(
            new Produto(1L, "Cafe", 15.50, "Bebidas")
        );
        String resultado = pedidoCtrl.finalizarPedido();
        assertTrue(resultado.contains("R$ 15.5"));
    }
}
