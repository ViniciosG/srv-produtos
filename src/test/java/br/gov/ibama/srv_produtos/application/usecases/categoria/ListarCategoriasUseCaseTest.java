package br.gov.ibama.srv_produtos.application.usecases.categoria;

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

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class ListarCategoriasUseCaseTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ListarCategoriasUseCase listarCategoriasUseCase;

    private List<Categoria> categorias;

    @BeforeEach
    void setUp() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Eletrônicos");
        categoria1.setDescricao("Produtos eletrônicos em geral");
        categoria1.onCreate();

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Roupas");
        categoria2.setDescricao("Roupas em geral");
        categoria2.onCreate();

        categorias = Arrays.asList(categoria1, categoria2);
    }

    @Test
    void testExecute() {
        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<Categoria> result = listarCategoriasUseCase.execute();

        assertEquals(2, result.size());
        assertEquals(categorias.get(0).getId(), result.get(0).getId());
        assertEquals(categorias.get(0).getNome(), result.get(0).getNome());
        assertEquals(categorias.get(0).getDescricao(), result.get(0).getDescricao());
        assertEquals(categorias.get(1).getId(), result.get(1).getId());
        assertEquals(categorias.get(1).getNome(), result.get(1).getNome());
        assertEquals(categorias.get(1).getDescricao(), result.get(1).getDescricao());
    }
}