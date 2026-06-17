package br.com.coffeehouse.ctrl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ClienteCtrlTest {

    private ClienteCtrl clienteCtrl;

    @Before
    public void setUp() {
        clienteCtrl = new ClienteCtrl();
    }

    @Test
    public void testRegistrarNovoClienteValido() {
        String resultado = clienteCtrl.registrarNovoCliente("Joao", "11122233341", "joao@email.com");
        assertEquals("Cliente cadastrado com sucesso!", resultado);
    }

    @Test
    public void testRegistrarClienteNomeVazio() {
        String resultado = clienteCtrl.registrarNovoCliente("", "11122233344", "joao@email.com");
        assertEquals("Erro: Nome obrigatório.", resultado);
    }

    @Test
    public void testRegistrarClienteNomeNull() {
        String resultado = clienteCtrl.registrarNovoCliente(null, "11122233344", "joao@email.com");
        assertEquals("Erro: Nome obrigatório.", resultado);
    }

    @Test
    public void testRegistrarClienteCpfInvalidoCurto() {
        String resultado = clienteCtrl.registrarNovoCliente("Joao", "123", "joao@email.com");
        assertEquals("Erro: CPF deve conter 11 dígitos.", resultado);
    }

    @Test
    public void testRegistrarClienteCpfInvalidoLongo() {
        String resultado = clienteCtrl.registrarNovoCliente("Joao", "123456789012", "joao@email.com");
        assertEquals("Erro: CPF deve conter 11 dígitos.", resultado);
    }

    @Test
    public void testRegistrarClienteCpfNull() {
        String resultado = clienteCtrl.registrarNovoCliente("Joao", null, "joao@email.com");
        assertEquals("Erro: CPF deve conter 11 dígitos.", resultado);
    }

    @Test
    public void testRegistrarClienteCpfDuplicado() {
        clienteCtrl.registrarNovoCliente("Joao", "11122233342", "joao@email.com");
        String resultado = clienteCtrl.registrarNovoCliente("Joao Silva", "11122233342", "joao.silva@email.com");
        assertEquals("Erro: Cliente com este CPF já cadastrado.", resultado);
    }

    @Test
    public void testConsultarPorCpf() {
        clienteCtrl.registrarNovoCliente("Joao", "11122233343", "joao@email.com");
        assertNotNull(clienteCtrl.consultarPorCpf("11122233343"));
    }

    @Test
    public void testConsultarPorCpfInexistente() {
        assertNull(clienteCtrl.consultarPorCpf("00000000000"));
    }

    @Test
    public void testListarTodosClientes() {
        clienteCtrl.registrarNovoCliente("Joao", "11122233344", "joao@email.com");
        clienteCtrl.registrarNovoCliente("Maria", "55566677788", "maria@email.com");

        assertEquals(2, clienteCtrl.listarTodosClientes().size());
    }
}
