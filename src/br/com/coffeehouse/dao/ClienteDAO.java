package br.com.coffeehouse.dao;

import br.com.coffeehouse.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static List<Cliente> bancoClientes = new ArrayList<>();

    public void salvar(Cliente c) {
        Cliente existente = buscarPorCpf(c.getCpf());
        if (existente != null) {
            // Se já existe, atualiza os dados (Update do CRUD)
            existente.setNome(c.getNome());
            existente.setEmail(c.getEmail());
        } else {
            // Se não existe, adiciona (Create do CRUD)
            bancoClientes.add(c);
        }
    }

    public Cliente buscarPorCpf(String cpf) {
        for (Cliente c : bancoClientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(bancoClientes);
    }

    public boolean excluir(String cpf) {
        Cliente c = buscarPorCpf(cpf);
        if (c != null) {
            return bancoClientes.remove(c);
        }
        return false;
    }
}