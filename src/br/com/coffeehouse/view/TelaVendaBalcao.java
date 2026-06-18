package br.com.coffeehouse.view;

import br.com.coffeehouse.ctrl.PedidoCtrl;
import br.com.coffeehouse.ctrl.ProdutoCtrl;
import br.com.coffeehouse.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaVendaBalcao extends JFrame {

    private static final Dimension TAMANHO_BOTAO_PRODUTO = new Dimension(320, 56);

    private final PedidoCtrl pedidoCtrl = new PedidoCtrl();
    private final ProdutoCtrl produtoCtrl = new ProdutoCtrl();
    private final JTextArea areaItens = new JTextArea("Itens do Pedido:\n");

    public TelaVendaBalcao() {
        setTitle("Frente de Vendas - Balcao");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(520, 520));
        setSize(520, 520);
        pedidoCtrl.iniciarNovoPedido(1L);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        TemaApp.aplicarFundo(painel);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        painel.add(criarPainelCardapio(), BorderLayout.NORTH);

        areaItens.setEditable(false);
        TemaApp.estilizarAreaTexto(areaItens);
        areaItens.setBackground(TemaApp.CAMPO);
        JScrollPane scrollItens = new JScrollPane(areaItens);
        scrollItens.getViewport().setBackground(TemaApp.CAMPO);
        painel.add(scrollItens, BorderLayout.CENTER);

        JPanel painelFinalizar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TemaApp.aplicarFundo(painelFinalizar);
        JButton btnPagar = new JButton("Finalizar Venda");
        TemaApp.estilizarBotao(btnPagar);
        btnPagar.addActionListener(e -> finalizarVenda());
        painelFinalizar.add(btnPagar);
        painel.add(painelFinalizar, BorderLayout.SOUTH);

        getContentPane().setBackground(TemaApp.FUNDO);
        add(painel);
    }

    private JPanel criarPainelCardapio() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        TemaApp.aplicarFundo(painel);

        JLabel titulo = new JLabel("Cardapio:");
        TemaApp.estilizarRotulo(titulo);
        painel.add(titulo, BorderLayout.NORTH);

        JPanel listaBotoes = new JPanel();
        listaBotoes.setLayout(new BoxLayout(listaBotoes, BoxLayout.Y_AXIS));
        TemaApp.aplicarFundo(listaBotoes);

        List<Produto> cardapio = produtoCtrl.listarCardapio();
        if (cardapio.isEmpty()) {
            JLabel vazio = new JLabel("Nenhum produto cadastrado.");
            TemaApp.estilizarRotulo(vazio);
            listaBotoes.add(vazio);
        } else {
            for (Produto produto : cardapio) {
                listaBotoes.add(criarBotaoProduto(produto));
                listaBotoes.add(Box.createVerticalStrut(8));
            }
        }

        JScrollPane scrollCardapio = new JScrollPane(listaBotoes);
        scrollCardapio.setBorder(null);
        scrollCardapio.getViewport().setBackground(TemaApp.FUNDO);
        scrollCardapio.setPreferredSize(new Dimension(0, 200));
        painel.add(scrollCardapio, BorderLayout.CENTER);

        return painel;
    }

    private JButton criarBotaoProduto(Produto produto) {
        JButton btn = new JButton(formatarBotaoProduto(produto));
        TemaApp.estilizarBotao(btn);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(TAMANHO_BOTAO_PRODUTO);
        btn.setMinimumSize(TAMANHO_BOTAO_PRODUTO);
        btn.setMaximumSize(TAMANHO_BOTAO_PRODUTO);
        btn.addActionListener(e -> adicionarProduto(produto));
        return btn;
    }

    private String formatarBotaoProduto(Produto produto) {
        return String.format(
                "<html><center>%s<br>R$ %.2f</center></html>",
                produto.getNome(),
                produto.obterPreco()
        );
    }

    private void adicionarProduto(Produto produto) {
        pedidoCtrl.getPedidoAtual().adicionarProduto(produto);
        areaItens.append(String.format("- %s: R$ %.2f%n", produto.getNome(), produto.obterPreco()));
        areaItens.append(String.format("Total parcial: R$ %.2f%n%n",
                pedidoCtrl.getPedidoAtual().getValorTotal()));
    }

    private void finalizarVenda() {
        String resultado = pedidoCtrl.finalizarPedido();
        JOptionPane.showMessageDialog(this, resultado);

        if (resultado.startsWith("Pedido finalizado")) {
            areaItens.setText("Itens do Pedido:\n");
            pedidoCtrl.iniciarNovoPedido(1L);
        }
    }
}
