package br.gov.ibama.srv_produtos.application.usecases.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ListarProdutosUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ListarProdutosUseCase listarProdutosUseCase;

    private List<Produto> produtos;

    @BeforeEach
    void setUp() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Smartphone");

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Notebook");

        produtos = Arrays.asList(produto1, produto2);
    }

    @Test
    void testExecute() {
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = listarProdutosUseCase.execute();

        assertEquals(2, result.size());
        assertEquals(produtos, result);
    }
}