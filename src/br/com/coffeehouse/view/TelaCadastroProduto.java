package br.com.coffeehouse.view;

import br.com.coffeehouse.ctrl.ProdutoCtrl;
import br.com.coffeehouse.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroProduto extends JFrame {

    private final ProdutoCtrl ctrl = new ProdutoCtrl();
    private final JTextField tId = new JTextField("Auto");
    private final JTextField tNome = new JTextField();
    private final JTextField tPreco = new JTextField();
    private final JTextField tCategoria = new JTextField();

    public TelaCadastroProduto() {
        setTitle("Cadastro de Produtos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(560, 360));
        setSize(560, 360);

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        TemaApp.aplicarFundo(painel);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        TemaApp.aplicarFundo(formulario);
        formulario.add(criarRotulo("ID:"));
        formulario.add(tId);
        tId.setEditable(false);
        TemaApp.estilizarCampo(tId);
        formulario.add(criarRotulo("Nome:"));
        formulario.add(tNome);
        TemaApp.estilizarCampo(tNome);
        formulario.add(criarRotulo("Preco (R$):"));
        formulario.add(tPreco);
        TemaApp.estilizarCampo(tPreco);
        formulario.add(criarRotulo("Categoria:"));
        formulario.add(tCategoria);
        TemaApp.estilizarCampo(tCategoria);

        JPanel botoes = new JPanel(new GridLayout(2, 3, 8, 8));
        TemaApp.aplicarFundo(botoes);
        JButton btnSalvar = new JButton("Salvar");
        JButton btnNovo = new JButton("Novo");
        JButton btnConsultar = new JButton("Consultar");
        JButton btnListar = new JButton("Listar Todos");
        JButton btnExcluir = new JButton("Excluir");
        TemaApp.estilizarBotao(btnSalvar);
        TemaApp.estilizarBotao(btnNovo);
        TemaApp.estilizarBotao(btnConsultar);
        TemaApp.estilizarBotao(btnListar);
        TemaApp.estilizarBotao(btnExcluir);
        btnExcluir.setBackground(new Color(0xB0, 0x00, 0x20));

        btnSalvar.addActionListener(e -> salvar());
        btnNovo.addActionListener(e -> limparCampos());
        btnConsultar.addActionListener(e -> consultar());
        btnListar.addActionListener(e -> listar());
        btnExcluir.addActionListener(e -> excluir());

        botoes.add(btnSalvar);
        botoes.add(btnNovo);
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
        Double preco = lerPreco();
        if (preco == null) {
            return;
        }

        String resultado;
        if (isNovoProduto()) {
            resultado = ctrl.registrarNovoProduto(tNome.getText(), preco, tCategoria.getText());
        } else {
            resultado = ctrl.cadastrarProduto(Long.parseLong(tId.getText().trim()),
                    tNome.getText(), preco, tCategoria.getText());
        }

        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.startsWith("Produto cadastrado") || resultado.startsWith("Produto salvo")) {
            limparCampos();
        }
    }

    private void consultar() {
        Long id = solicitarId("Consultar produto");
        if (id == null) {
            return;
        }

        Produto produto = buscarPorId(id);
        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Produto nao encontrado.");
            return;
        }

        tId.setText(String.valueOf(produto.getId()));
        tNome.setText(produto.getNome());
        tPreco.setText(String.valueOf(produto.getPrecoBase()));
        tCategoria.setText(produto.getCategoria() != null ? produto.getCategoria() : "");
    }

    private void listar() {
        List<Produto> produtos = ctrl.listarCardapio();
        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto cadastrado.");
            return;
        }

        StringBuilder lista = new StringBuilder();
        for (Produto produto : produtos) {
            lista.append("ID ").append(produto.getId())
                    .append(" | ").append(produto.getNome())
                    .append(" | R$ ").append(String.format("%.2f", produto.obterPreco()))
                    .append(" | ").append(produto.getCategoria())
                    .append('\n');
        }

        JOptionPane.showMessageDialog(this, lista.toString(), "Produtos do cardapio",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void excluir() {
        Long id = solicitarId("Excluir produto");
        if (id == null) {
            return;
        }

        boolean removido = ctrl.removerDoCardapio(id);
        JOptionPane.showMessageDialog(this,
                removido ? "Produto removido com sucesso!" : "Produto nao encontrado.");
        if (removido) {
            limparCampos();
        }
    }

    private Produto buscarPorId(Long id) {
        for (Produto produto : ctrl.listarCardapio()) {
            if (produto.getId().equals(id)) {
                return produto;
            }
        }
        return null;
    }

    private boolean isNovoProduto() {
        String id = tId.getText().trim();
        return id.isEmpty() || id.equalsIgnoreCase("Auto");
    }

    private Long solicitarId(String titulo) {
        String entrada = JOptionPane.showInputDialog(this, "Informe o ID do produto:", titulo,
                JOptionPane.QUESTION_MESSAGE);
        if (entrada == null || entrada.trim().isEmpty()) {
            return null;
        }

        try {
            long id = Long.parseLong(entrada.trim());
            if (id <= 0) {
                throw new NumberFormatException();
            }
            return id;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: ID invalido.");
            return null;
        }
    }

    private Double lerPreco() {
        try {
            return Double.parseDouble(tPreco.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Preco invalido.");
            return null;
        }
    }

    private void limparCampos() {
        tId.setText("Auto");
        tNome.setText("");
        tPreco.setText("");
        tCategoria.setText("");
    }
}
