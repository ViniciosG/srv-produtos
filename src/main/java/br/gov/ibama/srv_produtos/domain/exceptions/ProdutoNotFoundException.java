package br.gov.ibama.srv_produtos.domain.exceptions;

public class ProdutoNotFoundException extends ResourceNotFoundException {

    public ProdutoNotFoundException(Long id) {
        super(String.format("Produto com ID %d não encontrado", id));
    }
}