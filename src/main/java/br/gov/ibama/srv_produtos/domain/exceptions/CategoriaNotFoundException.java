package br.gov.ibama.srv_produtos.domain.exceptions;

public class CategoriaNotFoundException extends ResourceNotFoundException {

    public CategoriaNotFoundException(Long id) {
        super(String.format("Categoria com ID %d não encontrada", id));
    }
}