package br.gov.ibama.srv_produtos.application.usecases.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.entities.StatusProduto;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.exceptions.ProdutoNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class AtualizarProdutoUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    private Produto produto;
    private Produto produtoExistente;
    private Categoria categoria;
    private Long categoriaId = 1L;
    private Long produtoId = 1L;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Eletrônicos");

        produtoExistente = new Produto();
        produtoExistente.setId(produtoId);
        produtoExistente.setNome("Smartphone Antigo");
        produtoExistente.setDescricao("Smartphone antigo");
        produtoExistente.setPreco(new BigDecimal("999.99"));
        produtoExistente.setQuantidadeEstoque(5);
        produtoExistente.setCategoria(categoria);
        produtoExistente.setStatus(StatusProduto.ATIVO);

        produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Smartphone Novo");
        produto.setDescricao("Smartphone de última geração");
        produto.setPreco(new BigDecimal("1999.99"));
        produto.setQuantidadeEstoque(10);
        produto.setStatus(StatusProduto.INATIVO);
    }

    @Test
    void testExecuteSuccess() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente));
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoExistente);

        Produto result = atualizarProdutoUseCase.execute(produto, categoriaId);

        assertEquals(produto.getNome(), result.getNome());
        assertEquals(produto.getDescricao(), result.getDescricao());
        assertEquals(produto.getPreco(), result.getPreco());
        assertEquals(produto.getQuantidadeEstoque(), result.getQuantidadeEstoque());
        assertEquals(categoria, result.getCategoria());
        assertEquals(produto.getStatus(), result.getStatus());
    }

    @Test
    void testExecuteProdutoNotFound() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFoundException.class, () -> {
            atualizarProdutoUseCase.execute(produto, categoriaId);
        });
    }

    @Test
    void testExecuteCategoriaNotFound() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente));
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> {
            atualizarProdutoUseCase.execute(produto, categoriaId);
        });
    }
}