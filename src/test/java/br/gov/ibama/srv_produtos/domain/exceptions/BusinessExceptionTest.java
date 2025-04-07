package br.gov.ibama.srv_produtos.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    void testConstructor() {
        String mensagem = "Erro de negócio";
        BusinessException exception = new BusinessException(mensagem);
        assertEquals(mensagem, exception.getMessage());
    }
}