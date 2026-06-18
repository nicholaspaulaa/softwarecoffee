package br.com.coffeehouse;

import br.com.coffeehouse.view.JanelaPrincipal;
import br.com.coffeehouse.view.TemaApp;

public class Main {
    public static void main(String[] args) {
        TemaApp.inicializar();
        java.awt.EventQueue.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
