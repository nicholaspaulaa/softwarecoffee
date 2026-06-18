package br.com.coffeehouse.view;

import br.com.coffeehouse.ctrl.PedidoCtrl;
import br.com.coffeehouse.model.Produto;

import javax.swing.*;
import java.awt.*;

public class TelaVendaBalcao extends JFrame {

    private final PedidoCtrl pedidoCtrl = new PedidoCtrl();
    private final JTextArea areaItens = new JTextArea("Itens do Pedido:\n");

    public TelaVendaBalcao() {
        setTitle("Frente de Vendas - Balcao");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(420, 320));
        setSize(420, 320);
        pedidoCtrl.iniciarNovoPedido(1L);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        areaItens.setEditable(false);
        areaItens.setFont(new Font("Monospaced", Font.PLAIN, 12));
        painel.add(new JScrollPane(areaItens), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnAdd = new JButton("Adicionar Expresso");
        JButton btnPagar = new JButton("Finalizar Venda");

        btnAdd.addActionListener(e -> adicionarExpresso());
        btnPagar.addActionListener(e -> finalizarVenda());

        painelBotoes.add(btnAdd);
        painelBotoes.add(btnPagar);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);
    }

    private void adicionarExpresso() {
        Produto expresso = new Produto(1L, "Cafe Expresso", 6.50, "Bebidas");
        pedidoCtrl.getPedidoAtual().adicionarProduto(expresso);
        areaItens.append(String.format("- %s: R$ %.2f%n", expresso.getNome(), expresso.obterPreco()));
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
