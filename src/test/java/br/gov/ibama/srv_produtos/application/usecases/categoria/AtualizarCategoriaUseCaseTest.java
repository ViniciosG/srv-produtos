package br.gov.ibama.srv_produtos.application.usecases.categoria;

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

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaDuplicadaException;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class AtualizarCategoriaUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private AtualizarCategoriaUseCase atualizarCategoriaUseCase;

    private Categoria categoriaExistente;
    private Categoria categoriaAtualizada;
    private Long categoriaId = 1L;

    @BeforeEach
    void setUp() {
        categoriaExistente = new Categoria();
        categoriaExistente.setId(categoriaId);
        categoriaExistente.setNome("Eletrônicos");
        categoriaExistente.setDescricao("Produtos eletrônicos em geral");
        categoriaExistente.onCreate();

        categoriaAtualizada = new Categoria();
        categoriaAtualizada.setId(categoriaId);
        categoriaAtualizada.setNome("Eletrônicos Atualizado");
        categoriaAtualizada.setDescricao("Produtos eletrônicos atualizados");
    }

    @Test
    void testExecuteSuccess() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.existsByNome(categoriaAtualizada.getNome())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaAtualizada);

        Categoria result = atualizarCategoriaUseCase.execute(categoriaAtualizada);

        assertEquals(categoriaAtualizada.getId(), result.getId());
        assertEquals(categoriaAtualizada.getNome(), result.getNome());
        assertEquals(categoriaAtualizada.getDescricao(), result.getDescricao());
    }

    @Test
    void testExecuteCategoriaNotFound() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> {
            atualizarCategoriaUseCase.execute(categoriaAtualizada);
        });
    }

    @Test
    void testExecuteCategoriaDuplicada() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.existsByNome(categoriaAtualizada.getNome())).thenReturn(true);

        assertThrows(CategoriaDuplicadaException.class, () -> {
            atualizarCategoriaUseCase.execute(categoriaAtualizada);
        });
    }

    @Test
    void testExecuteMesmoNome() {
        categoriaAtualizada.setNome("Eletrônicos"); // Mesmo nome da categoria existente
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaAtualizada);

        Categoria result = atualizarCategoriaUseCase.execute(categoriaAtualizada);

        assertEquals(categoriaAtualizada.getId(), result.getId());
        assertEquals(categoriaAtualizada.getNome(), result.getNome());
        assertEquals(categoriaAtualizada.getDescricao(), result.getDescricao());
    }
}