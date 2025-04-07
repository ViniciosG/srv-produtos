package br.gov.ibama.srv_produtos.application.usecases.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.entities.StatusProduto;
import br.gov.ibama.srv_produtos.domain.exceptions.ProdutoNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class InativarProdutoUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private InativarProdutoUseCase inativarProdutoUseCase;

    private Produto produto;
    private Long produtoId = 1L;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Smartphone");
        produto.setStatus(StatusProduto.ATIVO);
    }

    @Test
    void testExecuteSuccess() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = inativarProdutoUseCase.execute(produtoId);

        assertEquals(StatusProduto.INATIVO, result.getStatus());
    }

    @Test
    void testExecuteProdutoNotFound() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFoundException.class, () -> {
            inativarProdutoUseCase.execute(produtoId);
        });
    }
}