package br.gov.ibama.srv_produtos.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ibama.srv_produtos.api.dtos.ProdutoDTO;
import br.gov.ibama.srv_produtos.application.usecases.produto.AtivarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.AtualizarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.BuscarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.CriarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.DeletarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.InativarProdutoUseCase;
import br.gov.ibama.srv_produtos.application.usecases.produto.ListarProdutosUseCase;
import br.gov.ibama.srv_produtos.domain.entities.Produto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final CriarProdutoUseCase criarProdutoUseCase;
    private final BuscarProdutoUseCase buscarProdutoUseCase;
    private final ListarProdutosUseCase listarProdutosUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;
    private final InativarProdutoUseCase inativarProdutoUseCase;
    private final AtivarProdutoUseCase ativarProdutoUseCase;
    private final DeletarProdutoUseCase deletarProdutoUseCase;

    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setStatus(produtoDTO.getStatus());

        Produto produtoCriado = criarProdutoUseCase.execute(produto, produtoDTO.getCategoriaId());
        return ResponseEntity.ok(new ProdutoDTO(produtoCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProduto(@PathVariable Long id) {
        Produto produto = buscarProdutoUseCase.execute(id);
        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<Produto> produtos = listarProdutosUseCase.execute();
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setStatus(produtoDTO.getStatus());

        Produto produtoAtualizado = atualizarProdutoUseCase.execute(produto, produtoDTO.getCategoriaId());
        return ResponseEntity.ok(new ProdutoDTO(produtoAtualizado));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<ProdutoDTO> inativarProduto(@PathVariable Long id) {
        Produto produto = inativarProdutoUseCase.execute(id);
        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<ProdutoDTO> ativarProduto(@PathVariable Long id) {
        Produto produto = ativarProdutoUseCase.execute(id);
        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        deletarProdutoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}