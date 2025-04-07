package br.gov.ibama.srv_produtos.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProdutoNotFoundExceptionTest {

    @Test
    void testConstructor() {
        Long id = 1L;
        ProdutoNotFoundException exception = new ProdutoNotFoundException(id);
        assertEquals(String.format("Produto com ID %d não encontrado", id), exception.getMessage());
    }
}