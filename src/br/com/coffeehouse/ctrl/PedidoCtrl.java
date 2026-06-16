package br.com.coffeehouse.ctrl;

import br.com.coffeehouse.model.Pedido;
import br.com.coffeehouse.model.Cliente;

public class PedidoCtrl {
    private Pedido pedidoAtual;

    public void iniciarNovoPedido(Long idPedido) {
        this.pedidoAtual = new Pedido(idPedido);
    }

    public Pedido getPedidoAtual() {
        return this.pedidoAtual;
    }

    public String finalizarPedido() {
        if (pedidoAtual == null || pedidoAtual.getItens().isEmpty()) {
            return "Erro: Nenhum item no pedido.";
        }
        
        // Regra de negócio [RF017]: Se houver cliente associado, acumula pontos
        if (pedidoAtual.getClienteVinculado() != null) {
            Cliente c = pedidoAtual.getClienteVinculado();
            c.acumularPontos(pedidoAtual.getValorTotal());
        }
        
        return "Pedido finalizado! Total: R$ " + pedidoAtual.getValorTotal();
    }
}