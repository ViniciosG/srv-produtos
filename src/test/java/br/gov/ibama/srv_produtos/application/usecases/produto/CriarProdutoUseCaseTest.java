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
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class CriarProdutoUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CriarProdutoUseCase criarProdutoUseCase;

    private Produto produto;
    private Categoria categoria;
    private Long categoriaId = 1L;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Eletrônicos");

        produto = new Produto();
        produto.setNome("Smartphone");
        produto.setDescricao("Smartphone de última geração");
        produto.setPreco(new BigDecimal("1999.99"));
        produto.setQuantidadeEstoque(10);
        produto.onCreate();
    }

    @Test
    void testExecuteSuccess() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = criarProdutoUseCase.execute(produto, categoriaId);

        assertEquals(produto.getNome(), result.getNome());
        assertEquals(produto.getDescricao(), result.getDescricao());
        assertEquals(produto.getPreco(), result.getPreco());
        assertEquals(produto.getQuantidadeEstoque(), result.getQuantidadeEstoque());
        assertEquals(categoria, result.getCategoria());
        assertEquals(StatusProduto.ATIVO, result.getStatus());
    }

    @Test
    void testExecuteCategoriaNotFound() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> {
            criarProdutoUseCase.execute(produto, categoriaId);
        });
    }
}