package br.com.coffeehouse.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class FuncionarioTest {

    @Test
    public void testCriacaoFuncionario() {
        Funcionario f = new Funcionario(1L, "Joao", "Atendente");
        assertNotNull(f);
        assertEquals("Atendente", f.getCargo());
    }
}
