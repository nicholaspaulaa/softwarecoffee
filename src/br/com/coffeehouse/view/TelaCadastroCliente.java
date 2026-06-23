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
        setMinimumSize(new Dimension(520, 320));
        setSize(520, 320);

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        TemaApp.aplicarFundo(painel);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel formulario = new JPanel(new GridLayout(3, 2, 8, 8));
        TemaApp.aplicarFundo(formulario);
        formulario.add(criarRotulo("Nome:"));
        formulario.add(tNome);
        TemaApp.estilizarCampo(tNome);
        formulario.add(criarRotulo("CPF (11 digitos):"));
        formulario.add(tCpf);
        TemaApp.estilizarCampo(tCpf);
        formulario.add(criarRotulo("Email:"));
        formulario.add(tEmail);
        TemaApp.estilizarCampo(tEmail);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        TemaApp.aplicarFundo(botoes);
        JButton btnSalvar = new JButton("Salvar");
        JButton btnConsultar = new JButton("Consultar CPF");
        JButton btnListar = new JButton("Listar Todos");
        JButton btnExcluir = new JButton("Excluir");
        TemaApp.estilizarBotao(btnSalvar);
        TemaApp.estilizarBotao(btnConsultar);
        TemaApp.estilizarBotao(btnListar);
        TemaApp.estilizarBotao(btnExcluir);
        btnExcluir.setBackground(new Color(0xB0, 0x00, 0x20));

        btnSalvar.addActionListener(e -> salvar());
        btnConsultar.addActionListener(e -> consultar());
        btnListar.addActionListener(e -> listar());
        btnExcluir.addActionListener(e -> excluir());

        botoes.add(btnSalvar);
        botoes.add(btnConsultar);
        botoes.add(btnListar);
        botoes.add(btnExcluir);

        painel.add(formulario, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        getContentPane().setBackground(TemaApp.FUNDO);
        add(painel);
    }

    private JLabel criarRotulo(String texto) {
        JLabel rotulo = new JLabel(texto);
        TemaApp.estilizarRotulo(rotulo);
        return rotulo;
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

    private void excluir() {
        String cpf = sanitizarCpf(tCpf.getText());
        if (cpf.length() != 11) {
            JOptionPane.showMessageDialog(this, "Informe um CPF valido para excluir.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Excluir cliente de CPF " + cpf + "?",
                "Confirmar exclusao",
                JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        boolean removido = ctrl.excluirCliente(cpf);
        JOptionPane.showMessageDialog(this,
                removido ? "Cliente removido com sucesso!" : "Cliente nao encontrado.");
        if (removido) {
            limparCampos();
        }
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
