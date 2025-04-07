package br.gov.ibama.srv_produtos.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdutoTest {

    private Produto produto;
    private Categoria categoria;

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
        produto.setDescricao("Smartphone de última geração");
        produto.setPreco(new BigDecimal("1999.99"));
        produto.setQuantidadeEstoque(10);
        produto.setCategoria(categoria);
        produto.onCreate();
    }

    @Test
    void testOnCreate() {
        produto.onCreate();
        assertEquals(StatusProduto.ATIVO, produto.getStatus());
    }

    @Test
    void testAdicionarEstoque() {
        produto.adicionarEstoque(5);
        assertEquals(15, produto.getQuantidadeEstoque());
    }

    @Test
    void testRemoverEstoque() {
        produto.removerEstoque(5);
        assertEquals(5, produto.getQuantidadeEstoque());
    }

    @Test
    void testInativar() {
        produto.inativar();
        assertEquals(StatusProduto.INATIVO, produto.getStatus());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, produto.getId());
        assertEquals("Smartphone", produto.getNome());
        assertEquals("Smartphone de última geração", produto.getDescricao());
        assertEquals(new BigDecimal("1999.99"), produto.getPreco());
        assertEquals(10, produto.getQuantidadeEstoque());
        assertEquals(StatusProduto.ATIVO, produto.getStatus());
        assertEquals(categoria, produto.getCategoria());
    }
}