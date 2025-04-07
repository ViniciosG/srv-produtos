package br.gov.ibama.srv_produtos.application.usecases.categoria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.entities.StatusCategoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaDuplicadaException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CriarCategoriaUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CriarCategoriaUseCase criarCategoriaUseCase;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral");
    }

    @Test
    void testExecuteSuccess() {
        when(categoriaRepository.existsByNome(categoria.getNome())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria result = criarCategoriaUseCase.execute(categoria);

        assertEquals(categoria.getNome(), result.getNome());
        assertEquals(categoria.getDescricao(), result.getDescricao());
        assertEquals(StatusCategoria.ATIVA, result.getStatus());
    }

    @Test
    void testExecuteCategoriaDuplicada() {
        when(categoriaRepository.existsByNome(categoria.getNome())).thenReturn(true);

        assertThrows(CategoriaDuplicadaException.class, () -> {
            criarCategoriaUseCase.execute(categoria);
        });
    }
}