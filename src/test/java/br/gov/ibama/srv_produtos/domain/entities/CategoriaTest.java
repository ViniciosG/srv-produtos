package br.gov.ibama.srv_produtos.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaTest {

    private Categoria categoria;
    private Produto produto;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral");
        categoria.onCreate();

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Smartphone");
    }

    @Test
    void testOnCreate() {
        categoria.onCreate();
        assertEquals(StatusCategoria.ATIVA, categoria.getStatus());
    }

    @Test
    void testAdicionarProduto() {
        categoria.adicionarProduto(produto);
        assertTrue(categoria.getProdutos().contains(produto));
        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    void testRemoverProduto() {
        categoria.adicionarProduto(produto);
        categoria.removerProduto(produto);
        assertTrue(categoria.getProdutos().isEmpty());
        assertEquals(null, produto.getCategoria());
    }

    @Test
    void testInativar() {
        categoria.inativar();
        assertEquals(StatusCategoria.INATIVA, categoria.getStatus());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, categoria.getId());
        assertEquals("Eletrônicos", categoria.getNome());
        assertEquals("Produtos eletrônicos em geral", categoria.getDescricao());
        assertEquals(StatusCategoria.ATIVA, categoria.getStatus());
        assertNotNull(categoria.getProdutos());
        assertTrue(categoria.getProdutos().isEmpty());
    }
}