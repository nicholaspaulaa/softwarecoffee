package br.com.coffeehouse.ctrl;

import br.com.coffeehouse.dao.ClienteDAO;
import br.com.coffeehouse.model.Cliente;
import java.util.List;

public class ClienteCtrl {
    private ClienteDAO clienteDAO = new ClienteDAO();

    public String registrarNovoCliente(String nome, String cpf, String email) {
        if (nome == null || nome.trim().isEmpty()) return "Erro: Nome obrigatório.";
        if (cpf == null || cpf.length() != 11) return "Erro: CPF deve conter 11 dígitos.";
        
        Cliente existente = clienteDAO.buscarPorCpf(cpf);
        if (existente != null) return "Erro: Cliente com este CPF já cadastrado.";

        Cliente novo = new Cliente(null, nome, cpf, email);
        clienteDAO.salvarComIdAuto(novo);
        return "Cliente cadastrado com sucesso!";
    }

    public Cliente consultarPorCpf(String cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }

    public boolean excluirCliente(String cpf) {
        return clienteDAO.excluir(cpf);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }
}