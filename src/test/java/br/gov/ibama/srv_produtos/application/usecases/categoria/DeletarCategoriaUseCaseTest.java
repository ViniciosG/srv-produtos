package br.gov.ibama.srv_produtos.application.usecases.categoria;

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

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class DeletarCategoriaUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private DeletarCategoriaUseCase deletarCategoriaUseCase;

    private Categoria categoria;
    private Long categoriaId = 1L;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Eletrônicos");
    }

    @Test
    void testExecuteSuccess() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(categoria);

        deletarCategoriaUseCase.execute(categoriaId);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void testExecuteCategoriaNotFound() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> {
            deletarCategoriaUseCase.execute(categoriaId);
        });
    }
}