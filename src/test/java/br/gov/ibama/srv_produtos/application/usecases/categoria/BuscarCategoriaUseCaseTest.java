package br.gov.ibama.srv_produtos.application.usecases.categoria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
class BuscarCategoriaUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private BuscarCategoriaUseCase buscarCategoriaUseCase;

    private Categoria categoria;
    private Long categoriaId = 1L;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral");
        categoria.onCreate();
    }

    @Test
    void testExecuteSuccess() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));

        Categoria result = buscarCategoriaUseCase.execute(categoriaId);

        assertEquals(categoria.getId(), result.getId());
        assertEquals(categoria.getNome(), result.getNome());
        assertEquals(categoria.getDescricao(), result.getDescricao());
    }

    @Test
    void testExecuteCategoriaNotFound() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> {
            buscarCategoriaUseCase.execute(categoriaId);
        });
    }
}