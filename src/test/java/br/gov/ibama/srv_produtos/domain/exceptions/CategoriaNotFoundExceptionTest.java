package br.gov.ibama.srv_produtos.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CategoriaNotFoundExceptionTest {

    @Test
    void testConstructor() {
        Long id = 1L;
        CategoriaNotFoundException exception = new CategoriaNotFoundException(id);
        assertEquals(String.format("Categoria com ID %d não encontrada", id), exception.getMessage());
    }
}