package br.com.coffeehouse.view;

import br.com.coffeehouse.ctrl.ClienteCtrl;
import br.com.coffeehouse.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroCliente extends JFrame {

    private final ClienteCtrl ctrl = new ClienteCtrl();
    private final JTextField tNome = new JTextField();
    private final JTextField tCpf = new JTextField();
    private final JTextField tEmail = new JTextField();

    public TelaCadastroCliente() {
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(420, 280));
        setSize(420, 280);

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel formulario = new JPanel(new GridLayout(3, 2, 8, 8));
        formulario.add(new JLabel("Nome:"));
        formulario.add(tNome);
        formulario.add(new JLabel("CPF (11 digitos):"));
        formulario.add(tCpf);
        formulario.add(new JLabel("Email:"));
        formulario.add(tEmail);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnConsultar = new JButton("Consultar CPF");
        JButton btnListar = new JButton("Listar Todos");

        btnSalvar.addActionListener(e -> salvar());
        btnConsultar.addActionListener(e -> consultar());
        btnListar.addActionListener(e -> listar());

        botoes.add(btnSalvar);
        botoes.add(btnConsultar);
        botoes.add(btnListar);

        painel.add(formulario, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        add(painel);
    }

    private void salvar() {
        String resultado = ctrl.registrarNovoCliente(
                tNome.getText(),
                sanitizarCpf(tCpf.getText()),
                tEmail.getText()
        );
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.startsWith("Cliente cadastrado")) {
            limparCampos();
        }
    }

    private void consultar() {
        Cliente cliente = ctrl.consultarPorCpf(sanitizarCpf(tCpf.getText()));
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente nao encontrado.");
            return;
        }

        tNome.setText(cliente.getNome());
        tCpf.setText(cliente.getCpf());
        tEmail.setText(cliente.getEmail() != null ? cliente.getEmail() : "");
        JOptionPane.showMessageDialog(this,
                "Cliente encontrado!\nPontos de fidelidade: " + cliente.getSaldoPontos());
    }

    private void listar() {
        List<Cliente> clientes = ctrl.listarTodosClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.");
            return;
        }

        StringBuilder lista = new StringBuilder();
        for (Cliente cliente : clientes) {
            lista.append(cliente.getNome())
                    .append(" | CPF: ").append(cliente.getCpf())
                    .append(" | Pontos: ").append(cliente.getSaldoPontos())
                    .append('\n');
        }

        JOptionPane.showMessageDialog(this, lista.toString(), "Clientes cadastrados",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String sanitizarCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private void limparCampos() {
        tNome.setText("");
        tCpf.setText("");
        tEmail.setText("");
    }
}
