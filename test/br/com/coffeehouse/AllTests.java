package br.com.coffeehouse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.coffeehouse.model.ProdutoTest;
import br.com.coffeehouse.model.PedidoTest;
import br.com.coffeehouse.model.FuncionarioTest;
import br.com.coffeehouse.model.ClienteTest;
import br.com.coffeehouse.model.PontuacaoTest;
import br.com.coffeehouse.dao.ClienteDAOTest;
import br.com.coffeehouse.dao.ProdutoDAOTest;
import br.com.coffeehouse.ctrl.ProdutoCtrlTest;
import br.com.coffeehouse.ctrl.PedidoCtrlTest;
import br.com.coffeehouse.ctrl.ClienteCtrlTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ProdutoTest.class,
    PedidoTest.class,
    FuncionarioTest.class,
    ClienteTest.class,
    PontuacaoTest.class,
    ClienteDAOTest.class,
    ProdutoDAOTest.class,
    ProdutoCtrlTest.class,
    PedidoCtrlTest.class,
    ClienteCtrlTest.class
})
public class AllTests {
}
