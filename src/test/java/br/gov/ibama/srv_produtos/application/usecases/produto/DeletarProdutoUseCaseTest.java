package br.gov.ibama.srv_produtos.application.usecases.produto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.exceptions.ProdutoNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class DeletarProdutoUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private DeletarProdutoUseCase deletarProdutoUseCase;

    private Produto produto;
    private Long produtoId = 1L;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Smartphone");
    }

    @Test
    void testExecuteSuccess() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(produto);

        deletarProdutoUseCase.execute(produtoId);

        verify(produtoRepository).delete(produto);
    }

    @Test
    void testExecuteProdutoNotFound() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFoundException.class, () -> {
            deletarProdutoUseCase.execute(produtoId);
        });
    }
}