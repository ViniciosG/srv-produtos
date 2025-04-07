package br.gov.ibama.srv_produtos.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.gov.ibama.srv_produtos.api.dtos.ProdutoDTO;
import br.gov.ibama.srv_produtos.application.usecases.produto.AtivarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.AtualizarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.BuscarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.CriarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.DeletarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.InativarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.ListarProdutosUseCase;
import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.entities.StatusProduto;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private CriarProdutoUseCase criarProdutoUseCase;

    @Mock
    private BuscarProdutoUseCase buscarProdutoUseCase;

    @Mock
    private ListarProdutosUseCase listarProdutosUseCase;

    @Mock
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    @Mock
    private InativarProdutoUseCase inativarProdutoUseCase;

    @Mock
    private AtivarProdutoUseCase ativarProdutoUseCase;

    @Mock
    private DeletarProdutoUseCase deletarProdutoUseCase;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;
    private ProdutoDTO produtoDTO;
    private Long produtoId = 1L;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral");
        categoria.onCreate();

        produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Smartphone");
        produto.setDescricao("Smartphone de última geração");
        produto.setPreco(new BigDecimal("1999.99"));
        produto.setCategoria(categoria);
        produto.onCreate();

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Smartphone");
        produtoDTO.setDescricao("Smartphone de última geração");
        produtoDTO.setPreco(new BigDecimal("1999.99"));
        produtoDTO.setCategoriaId(categoria.getId());
        produtoDTO.setStatus(StatusProduto.ATIVO);
    }

    @Test
    void testCriarProduto() {
        when(criarProdutoUseCase.execute(any(Produto.class), any(Long.class))).thenReturn(produto);

        ResponseEntity<ProdutoDTO> response = produtoController.criarProduto(produtoDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produtoDTO.getNome(), response.getBody().getNome());
        assertEquals(produtoDTO.getDescricao(), response.getBody().getDescricao());
        assertEquals(produtoDTO.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testBuscarProduto() {
        when(buscarProdutoUseCase.execute(produtoId)).thenReturn(produto);

        ResponseEntity<ProdutoDTO> response = produtoController.buscarProduto(produtoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produto.getNome(), response.getBody().getNome());
        assertEquals(produto.getDescricao(), response.getBody().getDescricao());
        assertEquals(produto.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testListarProdutos() {
        List<Produto> produtos = Arrays.asList(produto);
        when(listarProdutosUseCase.execute()).thenReturn(produtos);

        ResponseEntity<List<ProdutoDTO>> response = produtoController.listarProdutos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(produto.getNome(), response.getBody().get(0).getNome());
        assertEquals(produto.getDescricao(), response.getBody().get(0).getDescricao());
        assertEquals(produto.getPreco(), response.getBody().get(0).getPreco());
    }

    @Test
    void testAtualizarProduto() {
        when(atualizarProdutoUseCase.execute(any(Produto.class), any(Long.class))).thenReturn(produto);

        ResponseEntity<ProdutoDTO> response = produtoController.atualizarProduto(produtoId, produtoDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produtoDTO.getNome(), response.getBody().getNome());
        assertEquals(produtoDTO.getDescricao(), response.getBody().getDescricao());
        assertEquals(produtoDTO.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testInativarProduto() {
        when(inativarProdutoUseCase.execute(produtoId)).thenReturn(produto);

        ResponseEntity<ProdutoDTO> response = produtoController.inativarProduto(produtoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produto.getNome(), response.getBody().getNome());
        assertEquals(produto.getDescricao(), response.getBody().getDescricao());
        assertEquals(produto.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testAtivarProduto() {
        when(ativarProdutoUseCase.execute(produtoId)).thenReturn(produto);

        ResponseEntity<ProdutoDTO> response = produtoController.ativarProduto(produtoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produto.getNome(), response.getBody().getNome());
        assertEquals(produto.getDescricao(), response.getBody().getDescricao());
        assertEquals(produto.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testDeletarProduto() {
        ResponseEntity<Void> response = produtoController.deletarProduto(produtoId);

        assertEquals(204, response.getStatusCodeValue());
    }
}