package br.gov.ibama.srv_produtos.application.usecases.produto;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarProdutosUseCase {

    private final ProdutoRepository produtoRepository;

    public List<Produto> execute() {
        return produtoRepository.findAll();
    }
}