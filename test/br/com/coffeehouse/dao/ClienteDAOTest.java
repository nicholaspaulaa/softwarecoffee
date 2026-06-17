package br.com.coffeehouse.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.coffeehouse.model.Cliente;

public class ClienteDAOTest {

    private ClienteDAO clienteDAO;

    @Before
    public void setUp() {
        clienteDAO = new ClienteDAO();
    }

    @Test
    public void testSalvarEBuscarPorCpf() {
        Cliente c = new Cliente(1L, "Joao", "11122233341", "joao@email.com");
        clienteDAO.salvar(c);

        Cliente encontrado = clienteDAO.buscarPorCpf("11122233341");
        assertNotNull(encontrado);
        assertEquals("Joao", encontrado.getNome());
    }

    @Test
    public void testSalvarAtualizaClienteExistente() {
        Cliente c = new Cliente(1L, "Joao", "11122233342", "joao@email.com");
        clienteDAO.salvar(c);

        Cliente atualizado = new Cliente(1L, "Joao Silva", "11122233342", "joao.novo@email.com");
        clienteDAO.salvar(atualizado);

        Cliente encontrado = clienteDAO.buscarPorCpf("11122233342");
        assertEquals("Joao Silva", encontrado.getNome());
        assertEquals("joao.novo@email.com", encontrado.getEmail());
    }

    @Test
    public void testListarTodos() {
        clienteDAO.salvar(new Cliente(1L, "Joao", "11122233343", "joao@email.com"));
        clienteDAO.salvar(new Cliente(2L, "Maria", "55566677711", "maria@email.com"));

        assertEquals(2, clienteDAO.listarTodos().size());
    }

    @Test
    public void testExcluirCliente() {
        Cliente c = new Cliente(1L, "Joao", "11122233344", "joao@email.com");
        clienteDAO.salvar(c);

        boolean removido = clienteDAO.excluir("11122233344");
        assertTrue(removido);
        assertNull(clienteDAO.buscarPorCpf("11122233344"));
    }

    @Test
    public void testExcluirClienteInexistente() {
        boolean removido = clienteDAO.excluir("00000000000");
        assertFalse(removido);
    }

    @Test
    public void testBuscarPorCpfInexistente() {
        Cliente encontrado = clienteDAO.buscarPorCpf("00000000000");
        assertNull(encontrado);
    }

    @Test
    public void testSalvarComIdAuto() {
        Cliente c = new Cliente(null, "Joao", "11122233345", "joao@email.com");
        clienteDAO.salvarComIdAuto(c);

        assertNotNull(c.getId());
        Cliente encontrado = clienteDAO.buscarPorCpf("11122233345");
        assertNotNull(encontrado);
        assertEquals(c.getId(), encontrado.getId());
    }

    @Test
    public void testSalvarComIdAutoMantemIdExistente() {
        Cliente c = new Cliente(99L, "Joao", "11122233346", "joao@email.com");
        clienteDAO.salvarComIdAuto(c);

        assertEquals(Long.valueOf(99L), c.getId());
    }
}
