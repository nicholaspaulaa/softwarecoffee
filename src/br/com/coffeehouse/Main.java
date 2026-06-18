package br.com.coffeehouse;

import br.com.coffeehouse.view.JanelaPrincipal;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
