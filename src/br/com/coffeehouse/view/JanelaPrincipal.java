package br.com.coffeehouse.view;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private TelaCadastroCliente telaClientes;
    private TelaVendaBalcao telaCaixa;

    public JanelaPrincipal() {
        setTitle("BrewMaster POS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(420, 320));
        setSize(420, 320);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel lblTitulo = new JLabel("Coffee House POS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painel.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        JButton btnCaixa = criarBotaoMenu("Abrir Frente de Caixa / Vendas");
        JButton btnClientes = criarBotaoMenu("Gerenciar Clientes (CRUD)");

        btnCaixa.addActionListener(e -> abrirTelaCaixa());
        btnClientes.addActionListener(e -> abrirTelaClientes());

        painelBotoes.add(btnCaixa);
        painelBotoes.add(Box.createVerticalStrut(12));
        painelBotoes.add(btnClientes);

        painel.add(painelBotoes, BorderLayout.CENTER);
        add(painel);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setPreferredSize(new Dimension(320, 44));
        return btn;
    }

    private void abrirTelaClientes() {
        if (telaClientes == null || !telaClientes.isDisplayable()) {
            telaClientes = new TelaCadastroCliente();
        }
        telaClientes.setLocationRelativeTo(this);
        telaClientes.setVisible(true);
        telaClientes.toFront();
    }

    private void abrirTelaCaixa() {
        if (telaCaixa == null || !telaCaixa.isDisplayable()) {
            telaCaixa = new TelaVendaBalcao();
        }
        telaCaixa.setLocationRelativeTo(this);
        telaCaixa.setVisible(true);
        telaCaixa.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
