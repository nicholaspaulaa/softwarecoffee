package br.com.coffeehouse.view;

import br.com.coffeehouse.ctrl.ClienteCtrl;
import br.com.coffeehouse.ctrl.PedidoCtrl;
import br.com.coffeehouse.ctrl.ProdutoCtrl;
import br.com.coffeehouse.model.Cliente;
import br.com.coffeehouse.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TelaVendaBalcao extends JFrame {

    private static final String CATEGORIA_BEBIDAS = "Bebidas";
    private static final String CATEGORIA_CONFEITARIA = "Confeitaria";

    private final PedidoCtrl pedidoCtrl = new PedidoCtrl();
    private final ProdutoCtrl produtoCtrl = new ProdutoCtrl();
    private final ClienteCtrl clienteCtrl = new ClienteCtrl();
    private final JTextArea areaItens = new JTextArea("Itens do Pedido:\n");
    private final Map<Long, Integer> quantidades = new LinkedHashMap<>();
    private final Map<Long, JLabel> labelsQuantidade = new HashMap<>();

    private JPanel painelProdutos;
    private String categoriaSelecionada = CATEGORIA_BEBIDAS;

    public TelaVendaBalcao() {
        setTitle("Frente de Vendas - Balcao");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(520, 560));
        setSize(520, 560);
        pedidoCtrl.iniciarNovoPedido(1L);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        TemaApp.aplicarFundo(painel);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        painel.add(criarPainelCategorias(), BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));
        TemaApp.aplicarFundo(painelCentro);

        painelProdutos = new JPanel();
        painelProdutos.setLayout(new BoxLayout(painelProdutos, BoxLayout.Y_AXIS));
        TemaApp.aplicarFundo(painelProdutos);
        JScrollPane scrollProdutos = new JScrollPane(painelProdutos);
        scrollProdutos.setBorder(null);
        scrollProdutos.getViewport().setBackground(TemaApp.FUNDO);
        scrollProdutos.setPreferredSize(new Dimension(0, 220));
        painelCentro.add(scrollProdutos, BorderLayout.NORTH);

        areaItens.setEditable(false);
        TemaApp.estilizarAreaTexto(areaItens);
        areaItens.setBackground(TemaApp.CAMPO);
        JScrollPane scrollItens = new JScrollPane(areaItens);
        scrollItens.getViewport().setBackground(TemaApp.CAMPO);
        painelCentro.add(scrollItens, BorderLayout.CENTER);

        painel.add(painelCentro, BorderLayout.CENTER);

        JPanel painelFinalizar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TemaApp.aplicarFundo(painelFinalizar);
        JButton btnPagar = new JButton("Finalizar Venda");
        TemaApp.estilizarBotao(btnPagar);
        btnPagar.addActionListener(e -> finalizarVenda());
        painelFinalizar.add(btnPagar);
        painel.add(painelFinalizar, BorderLayout.SOUTH);

        getContentPane().setBackground(TemaApp.FUNDO);
        add(painel);

        exibirProdutosDaCategoria(categoriaSelecionada);
    }

    private JPanel criarPainelCategorias() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 8, 0));
        TemaApp.aplicarFundo(painel);

        JButton btnBebidas = criarBotaoCategoria(CATEGORIA_BEBIDAS);
        JButton btnConfeitaria = criarBotaoCategoria(CATEGORIA_CONFEITARIA);

        btnBebidas.addActionListener(e -> exibirProdutosDaCategoria(CATEGORIA_BEBIDAS));
        btnConfeitaria.addActionListener(e -> exibirProdutosDaCategoria(CATEGORIA_CONFEITARIA));

        painel.add(btnBebidas);
        painel.add(btnConfeitaria);
        return painel;
    }

    private JButton criarBotaoCategoria(String categoria) {
        JButton btn = new JButton(categoria);
        TemaApp.estilizarBotao(btn);
        btn.setPreferredSize(new Dimension(150, 44));
        return btn;
    }

    private void exibirProdutosDaCategoria(String categoria) {
        categoriaSelecionada = categoria;
        painelProdutos.removeAll();
        labelsQuantidade.clear();

        List<Produto> produtos = filtrarPorCategoria(categoria);
        if (produtos.isEmpty()) {
            JLabel vazio = new JLabel("Nenhum produto nesta categoria.");
            TemaApp.estilizarRotulo(vazio);
            vazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelProdutos.add(vazio);
        } else {
            for (Produto produto : produtos) {
                painelProdutos.add(criarLinhaProduto(produto));
                painelProdutos.add(Box.createVerticalStrut(8));
            }
        }

        painelProdutos.revalidate();
        painelProdutos.repaint();
    }

    private List<Produto> filtrarPorCategoria(String categoria) {
        return produtoCtrl.listarCardapio().stream()
                .filter(produto -> categoria.equalsIgnoreCase(produto.getCategoria()))
                .toList();
    }

    private JPanel criarLinhaProduto(Produto produto) {
        JPanel linha = new JPanel(new BorderLayout(8, 0));
        TemaApp.aplicarFundo(linha);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        JLabel info = new JLabel(String.format("%s - R$ %.2f", produto.getNome(), produto.obterPreco()));
        TemaApp.estilizarRotulo(info);
        linha.add(info, BorderLayout.CENTER);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        TemaApp.aplicarFundo(controles);

        JButton btnMenos = criarBotaoQuantidade("-");
        JLabel lblQuantidade = new JLabel(String.valueOf(quantidades.getOrDefault(produto.getId(), 0)));
        TemaApp.estilizarRotulo(lblQuantidade);
        JButton btnMais = criarBotaoQuantidade("+");

        labelsQuantidade.put(produto.getId(), lblQuantidade);
        btnMenos.addActionListener(e -> alterarQuantidade(produto, -1));
        btnMais.addActionListener(e -> alterarQuantidade(produto, 1));

        controles.add(btnMenos);
        controles.add(lblQuantidade);
        controles.add(btnMais);
        linha.add(controles, BorderLayout.EAST);

        return linha;
    }

    private JButton criarBotaoQuantidade(String texto) {
        JButton btn = new JButton(texto);
        TemaApp.estilizarBotao(btn);
        Dimension tamanho = new Dimension(36, 36);
        btn.setPreferredSize(tamanho);
        btn.setMinimumSize(tamanho);
        btn.setMaximumSize(tamanho);
        return btn;
    }

    private void alterarQuantidade(Produto produto, int delta) {
        long id = produto.getId();
        int quantidade = quantidades.getOrDefault(id, 0) + delta;
        if (quantidade <= 0) {
            quantidades.remove(id);
            quantidade = 0;
        } else {
            quantidades.put(id, quantidade);
        }

        JLabel lblQuantidade = labelsQuantidade.get(id);
        if (lblQuantidade != null) {
            lblQuantidade.setText(String.valueOf(quantidade));
        }

        reconstruirPedido();
        atualizarResumoPedido();
    }

    private void reconstruirPedido() {
        pedidoCtrl.iniciarNovoPedido(1L);
        for (Produto produto : produtoCtrl.listarCardapio()) {
            int quantidade = quantidades.getOrDefault(produto.getId(), 0);
            for (int i = 0; i < quantidade; i++) {
                pedidoCtrl.getPedidoAtual().adicionarProduto(produto);
            }
        }
    }

    private void atualizarResumoPedido() {
        StringBuilder resumo = new StringBuilder("Itens do Pedido:\n");
        if (quantidades.isEmpty()) {
            areaItens.setText(resumo.toString());
            return;
        }

        for (Produto produto : produtoCtrl.listarCardapio()) {
            int quantidade = quantidades.getOrDefault(produto.getId(), 0);
            if (quantidade > 0) {
                resumo.append(String.format("- %s x%d: R$ %.2f%n",
                        produto.getNome(),
                        quantidade,
                        produto.obterPreco() * quantidade));
            }
        }

        resumo.append(String.format("%nTotal parcial: R$ %.2f%n",
                pedidoCtrl.getPedidoAtual().getValorTotal()));
        areaItens.setText(resumo.toString());
    }

    private void finalizarVenda() {
        if (pedidoCtrl.getPedidoAtual() == null || pedidoCtrl.getPedidoAtual().getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: Nenhum item no pedido.");
            return;
        }

        Cliente clienteVinculado = null;
        int adicionarCpf = JOptionPane.showConfirmDialog(this,
                "Adicionar CPF do programa de pontos?",
                "Programa de pontos",
                JOptionPane.YES_NO_OPTION);

        if (adicionarCpf == JOptionPane.YES_OPTION) {
            clienteVinculado = solicitarClientePorCpf();
            if (clienteVinculado == null) {
                return;
            }
            pedidoCtrl.getPedidoAtual().vincularCliente(clienteVinculado);
        }

        double totalVenda = pedidoCtrl.getPedidoAtual().getValorTotal();
        String resultado = pedidoCtrl.finalizarPedido();
        if (resultado.startsWith("Pedido finalizado") && clienteVinculado != null) {
            resultado += "\nPontos creditados para " + clienteVinculado.getNome()
                    + ": " + (int) totalVenda;
        }

        JOptionPane.showMessageDialog(this, resultado);

        if (resultado.startsWith("Pedido finalizado")) {
            quantidades.clear();
            areaItens.setText("Itens do Pedido:\n");
            pedidoCtrl.iniciarNovoPedido(1L);
            exibirProdutosDaCategoria(categoriaSelecionada);
        }
    }

    private Cliente solicitarClientePorCpf() {
        String entrada = JOptionPane.showInputDialog(this,
                "Informe o CPF do cliente:",
                "Programa de pontos",
                JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) {
            return null;
        }

        String cpf = sanitizarCpf(entrada);
        if (cpf.isEmpty()) {
            return null;
        }
        if (cpf.length() != 11) {
            JOptionPane.showMessageDialog(this, "Erro: CPF deve conter 11 digitos.");
            return null;
        }

        Cliente cliente = clienteCtrl.consultarPorCpf(cpf);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente nao encontrado.");
            return null;
        }
        return cliente;
    }

    private String sanitizarCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }
}
