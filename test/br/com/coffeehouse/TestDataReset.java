package br.com.coffeehouse;

import br.com.coffeehouse.dao.ClienteDAO;
import br.com.coffeehouse.dao.ProdutoDAO;
import br.com.coffeehouse.model.Produto;

import java.lang.reflect.Field;
import java.util.List;

public final class TestDataReset {

    private TestDataReset() {
    }

    @SuppressWarnings("unchecked")
    public static void clientes() {
        try {
            Field banco = ClienteDAO.class.getDeclaredField("bancoClientes");
            banco.setAccessible(true);
            ((List<?>) banco.get(null)).clear();

            Field proximoId = ClienteDAO.class.getDeclaredField("proximoId");
            proximoId.setAccessible(true);
            proximoId.setLong(null, 1L);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Nao foi possivel limpar clientes para teste.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void produtos() {
        try {
            Field banco = ProdutoDAO.class.getDeclaredField("bancoProdutos");
            banco.setAccessible(true);
            List<Produto> produtos = (List<Produto>) banco.get(null);
            produtos.clear();
            produtos.add(new Produto(1L, "Cafe Expresso", 6.50, "Bebidas"));
            produtos.add(new Produto(2L, "Cappuccino Italiano", 9.00, "Bebidas"));
            produtos.add(new Produto(3L, "Fatia de Bolo de Cenoura", 12.00, "Confeitaria"));

            Field proximoId = ProdutoDAO.class.getDeclaredField("proximoId");
            proximoId.setAccessible(true);
            proximoId.setLong(null, 4L);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Nao foi possivel limpar produtos para teste.", e);
        }
    }
}
