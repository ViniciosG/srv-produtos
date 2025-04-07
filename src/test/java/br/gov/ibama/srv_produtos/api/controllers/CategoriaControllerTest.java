package br.gov.ibama.srv_produtos.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.gov.ibama.srv_produtos.api.dtos.CategoriaDTO;
import br.gov.ibama.srv_produtos.application.usecases.categoria.AtivarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.AtualizarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.BuscarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.CriarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.DeletarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.InativarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.ListarCategoriasUseCase;
import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.entities.StatusCategoria;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    @Mock
    private CriarCategoriaUseCase criarCategoriaUseCase;

    @Mock
    private BuscarCategoriaUseCase buscarCategoriaUseCase;

    @Mock
    private ListarCategoriasUseCase listarCategoriasUseCase;

    @Mock
    private AtualizarCategoriaUseCase atualizarCategoriaUseCase;

    @Mock
    private InativarCategoriaUseCase inativarCategoriaUseCase;

    @Mock
    private AtivarCategoriaUseCase ativarCategoriaUseCase;

    @Mock
    private DeletarCategoriaUseCase deletarCategoriaUseCase;

    @InjectMocks
    private CategoriaController categoriaController;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;
    private Long categoriaId = 1L;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral");
        categoria.onCreate();

        categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNome("Eletrônicos");
        categoriaDTO.setDescricao("Produtos eletrônicos em geral");
        categoriaDTO.setStatus(StatusCategoria.ATIVA);
    }

    @Test
    void testCriarCategoria() {
        when(criarCategoriaUseCase.execute(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<CategoriaDTO> response = categoriaController.criarCategoria(categoriaDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoriaDTO.getNome(), response.getBody().getNome());
        assertEquals(categoriaDTO.getDescricao(), response.getBody().getDescricao());
    }

    @Test
    void testBuscarCategoria() {
        when(buscarCategoriaUseCase.execute(categoriaId)).thenReturn(categoria);

        ResponseEntity<CategoriaDTO> response = categoriaController.buscarCategoria(categoriaId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoria.getNome(), response.getBody().getNome());
        assertEquals(categoria.getDescricao(), response.getBody().getDescricao());
    }

    @Test
    void testListarCategorias() {
        List<Categoria> categorias = Arrays.asList(categoria);
        when(listarCategoriasUseCase.execute()).thenReturn(categorias);

        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarCategorias();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(categoria.getNome(), response.getBody().get(0).getNome());
        assertEquals(categoria.getDescricao(), response.getBody().get(0).getDescricao());
    }

    @Test
    void testAtualizarCategoria() {
        when(atualizarCategoriaUseCase.execute(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<CategoriaDTO> response = categoriaController.atualizarCategoria(categoriaId, categoriaDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoriaDTO.getNome(), response.getBody().getNome());
        assertEquals(categoriaDTO.getDescricao(), response.getBody().getDescricao());
    }

    @Test
    void testInativarCategoria() {
        when(inativarCategoriaUseCase.execute(categoriaId)).thenReturn(categoria);

        ResponseEntity<CategoriaDTO> response = categoriaController.inativarCategoria(categoriaId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoria.getNome(), response.getBody().getNome());
        assertEquals(categoria.getDescricao(), response.getBody().getDescricao());
    }

    @Test
    void testAtivarCategoria() {
        when(ativarCategoriaUseCase.execute(categoriaId)).thenReturn(categoria);

        ResponseEntity<CategoriaDTO> response = categoriaController.ativarCategoria(categoriaId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoria.getNome(), response.getBody().getNome());
        assertEquals(categoria.getDescricao(), response.getBody().getDescricao());
    }

    @Test
    void testDeletarCategoria() {
        ResponseEntity<Void> response = categoriaController.deletarCategoria(categoriaId);

        assertEquals(204, response.getStatusCodeValue());
    }
}