package br.gov.ibama.srv_produtos.domain.exceptions;

public class CategoriaDuplicadaException extends BusinessException {

    public CategoriaDuplicadaException(String nome) {
        super(String.format("Já existe uma categoria com o nome '%s'", nome));
    }
}