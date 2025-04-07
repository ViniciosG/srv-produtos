package br.gov.ibama.srv_produtos.api.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.gov.ibama.srv_produtos.domain.exceptions.BusinessException;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaDuplicadaException;
import br.gov.ibama.srv_produtos.domain.exceptions.ResourceNotFoundException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleResourceNotFoundException() {
        String message = "Recurso não encontrado";
        ResourceNotFoundException ex = new ResourceNotFoundException(message);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleResourceNotFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    void testHandleBusinessException() {
        String message = "Erro de negócio";
        BusinessException ex = new BusinessException(message);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleBusinessException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    void testHandleCategoriaDuplicadaException() {
        String nome = "Categoria duplicada";
        CategoriaDuplicadaException ex = new CategoriaDuplicadaException(nome);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleCategoriaDuplicadaException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(String.format("Já existe uma categoria com o nome '%s'", nome), response.getBody().getMessage());
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Erro genérico");

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleGenericException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals("Ocorreu um erro interno no servidor", response.getBody().getMessage());
    }
}