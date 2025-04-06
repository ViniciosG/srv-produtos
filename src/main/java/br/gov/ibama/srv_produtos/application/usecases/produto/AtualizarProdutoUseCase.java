package br.gov.ibama.srv_produtos.application.usecases.produto;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.exceptions.ProdutoNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarProdutoUseCase {

        private final ProdutoRepository produtoRepository;
        private final CategoriaRepository categoriaRepository;

        public Produto execute(Produto produto, Long categoriaId) {
                Produto produtoExistente = produtoRepository.findById(produto.getId())
                                .orElseThrow(() -> new ProdutoNotFoundException(produto.getId()));

                Categoria categoria = categoriaRepository.findById(categoriaId)
                                .orElseThrow(() -> new CategoriaNotFoundException(categoriaId));

                produtoExistente.setNome(produto.getNome());
                produtoExistente.setDescricao(produto.getDescricao());
                produtoExistente.setPreco(produto.getPreco());
                produtoExistente.setQuantidadeEstoque(produto.getQuantidadeEstoque());
                produtoExistente.setCategoria(categoria);
                produtoExistente.setStatus(produto.getStatus());

                return produtoRepository.save(produtoExistente);
        }
}