package br.gov.ibama.srv_produtos.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

    @Test
    void testConstructor() {
        String mensagem = "Recurso não encontrado";
        ResourceNotFoundException exception = new ResourceNotFoundException(mensagem);
        assertEquals(mensagem, exception.getMessage());
    }
}