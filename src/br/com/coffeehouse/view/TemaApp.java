package br.com.coffeehouse.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TemaApp {

    public static final Color FUNDO = new Color(0xF4, 0xEF, 0xE6);
    public static final Color BOTAO = new Color(0x02, 0x57, 0x26);
    public static final Color BOTAO_TEXTO = Color.WHITE;
    public static final Color TEXTO = new Color(0x2C, 0x2C, 0x2C);
    public static final Color CAMPO = Color.WHITE;

    private static final String ARQUIVO_FONTE = "HelveticaNeueBold.otf";
    private static final int RAIO_BOTAO = 14;
    private static Font fonteBase;

    private TemaApp() {
    }

    public static void inicializar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        aplicarFonteGlobal();
    }

    public static Font obterFonte(int tamanho) {
        return carregarFonteBase().deriveFont((float) tamanho);
    }

    public static void aplicarFundo(JComponent componente) {
        componente.setBackground(FUNDO);
        componente.setOpaque(true);
    }

    public static void estilizarBotao(JButton botao) {
        botao.setForeground(BOTAO_TEXTO);
        botao.setBackground(BOTAO);
        botao.setFont(obterFonte(14));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        botao.setUI(new BotaoArredondadoUI());
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(obterFonte(13));
    }

    public static void estilizarRotulo(JLabel rotulo) {
        rotulo.setFont(obterFonte(13));
        rotulo.setForeground(TEXTO);
    }

    public static void estilizarAreaTexto(JTextArea area) {
        area.setFont(obterFonte(13));
    }

    private static void aplicarFonteGlobal() {
        Font padrao = obterFonte(14);
        Font menor = obterFonte(12);

        UIManager.put("Button.font", padrao);
        UIManager.put("Label.font", padrao);
        UIManager.put("TextField.font", padrao);
        UIManager.put("TextArea.font", menor);
        UIManager.put("Panel.font", padrao);
        UIManager.put("OptionPane.messageFont", padrao);
        UIManager.put("OptionPane.buttonFont", padrao);
    }

    private static Font carregarFonteBase() {
        if (fonteBase != null) {
            return fonteBase;
        }

        fonteBase = carregarArquivoFonte();
        if (fonteBase != null) {
            return fonteBase;
        }

        fonteBase = buscarFonteInstalada();
        if (fonteBase != null) {
            return fonteBase;
        }

        fonteBase = new Font("SansSerif", Font.BOLD, 14);
        return fonteBase;
    }

    private static Font carregarArquivoFonte() {
        String[] caminhos = {
                "src/resources/fonts/" + ARQUIVO_FONTE,
                "resources/fonts/" + ARQUIVO_FONTE
        };

        for (String caminho : caminhos) {
            Font fonte = lerFonte(() -> Files.newInputStream(Path.of(caminho)));
            if (fonte != null) {
                return fonte;
            }
        }

        return lerFonte(() -> TemaApp.class.getResourceAsStream("/resources/fonts/" + ARQUIVO_FONTE));
    }

    private static Font buscarFonteInstalada() {
        String[] nomes = {"HelveticaNeue-Bold", "Helvetica Neue Bold", "Helvetica Neue"};
        GraphicsEnvironment ambiente = GraphicsEnvironment.getLocalGraphicsEnvironment();

        for (String nome : nomes) {
            for (String familia : ambiente.getAvailableFontFamilyNames()) {
                if (familia.equalsIgnoreCase(nome)) {
                    return new Font(familia, Font.BOLD, 14);
                }
            }
        }
        return null;
    }

    private static Font lerFonte(InputStreamSupplier fornecedor) {
        try (InputStream entrada = fornecedor.open()) {
            if (entrada == null) {
                return null;
            }
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, entrada);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
            return fonte;
        } catch (Exception e) {
            return null;
        }
    }

    @FunctionalInterface
    private interface InputStreamSupplier {
        InputStream open() throws Exception;
    }

    private static final class BotaoArredondadoUI extends BasicButtonUI {

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton botao = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color cor = botao.getBackground();
            ButtonModel model = botao.getModel();
            if (!model.isEnabled()) {
                cor = cor.brighter();
            } else if (model.isPressed()) {
                cor = cor.darker();
            } else if (model.isRollover()) {
                cor = cor.brighter();
            }

            g2.setColor(cor);
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), RAIO_BOTAO, RAIO_BOTAO);
            g2.dispose();

            super.paint(g, c);
        }
    }
}
