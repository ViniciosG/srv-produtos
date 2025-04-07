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
import br.gov.ibama.srv_produtos.domain.entities.StatusCategoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class InativarCategoriaUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private InativarCategoriaUseCase inativarCategoriaUseCase;

    private Categoria categoria;
    private Long categoriaId = 1L;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Eletrônicos");
        categoria.setStatus(StatusCategoria.ATIVA);
    }

    @Test
    void testExecuteSuccess() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria result = inativarCategoriaUseCase.execute(categoriaId);

        assertEquals(StatusCategoria.INATIVA, result.getStatus());
    }

    @Test
    void testExecuteCategoriaNotFound() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> {
            inativarCategoriaUseCase.execute(categoriaId);
        });
    }
}