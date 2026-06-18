package br.com.coffeehouse.view;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private TelaCadastroCliente telaClientes;
    private TelaVendaBalcao telaCaixa;

    public JanelaPrincipal() {
        setTitle("BrewMaster POS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(420, 380));
        setSize(420, 380);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 15));
        TemaApp.aplicarFundo(painel);
        painel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        painel.add(criarLogo(), BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        TemaApp.aplicarFundo(painelBotoes);
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        JButton btnCaixa = criarBotaoMenu("Abrir Frente de Caixa / Vendas");
        JButton btnClientes = criarBotaoMenu("Gerenciar Clientes (CRUD)");

        btnCaixa.addActionListener(e -> abrirTelaCaixa());
        btnClientes.addActionListener(e -> abrirTelaClientes());

        painelBotoes.add(btnCaixa);
        painelBotoes.add(Box.createVerticalStrut(12));
        painelBotoes.add(btnClientes);

        painel.add(painelBotoes, BorderLayout.CENTER);
        getContentPane().setBackground(TemaApp.FUNDO);
        add(painel);
    }

    private JLabel criarLogo() {
        ImageIcon original = carregarLogo();
        int larguraMax = 280;
        int alturaMax = 200;
        int largura = original.getIconWidth();
        int altura = original.getIconHeight();

        if (largura <= 0 || altura <= 0) {
            return new JLabel();
        }

        double escala = Math.min((double) larguraMax / largura, (double) alturaMax / altura);
        int novaLargura = (int) (largura * escala);
        int novaAltura = (int) (altura * escala);
        Image imagem = original.getImage().getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(imagem), SwingConstants.CENTER);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lblLogo;
    }

    private ImageIcon carregarLogo() {
        String caminho = "src/resources/images/logo.png";
        ImageIcon icon = new ImageIcon(caminho);
        if (icon.getIconWidth() > 0) {
            return icon;
        }

        java.net.URL url = getClass().getResource("/resources/images/logo.png");
        if (url != null) {
            return new ImageIcon(url);
        }

        return new ImageIcon();
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        TemaApp.estilizarBotao(btn);
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
