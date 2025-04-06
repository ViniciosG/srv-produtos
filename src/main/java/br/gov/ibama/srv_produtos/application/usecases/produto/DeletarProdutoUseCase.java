package br.gov.ibama.srv_produtos.application.usecases.produto;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.exceptions.ProdutoNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletarProdutoUseCase {

    private final ProdutoRepository produtoRepository;

    public void execute(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        produtoRepository.delete(produto);
    }
}