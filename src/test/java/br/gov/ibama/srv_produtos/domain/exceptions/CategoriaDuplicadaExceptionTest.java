package br.gov.ibama.srv_produtos.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CategoriaDuplicadaExceptionTest {

    @Test
    void testConstructor() {
        String nome = "Eletrônicos";
        CategoriaDuplicadaException exception = new CategoriaDuplicadaException(nome);
        assertEquals(String.format("Já existe uma categoria com o nome '%s'", nome), exception.getMessage());
    }
}