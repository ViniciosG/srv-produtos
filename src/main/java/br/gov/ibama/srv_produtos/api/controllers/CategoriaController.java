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

import br.gov.ibama.srv_produtos.api.dtos.CategoriaDTO;
import br.gov.ibama.srv_produtos.application.usecases.categoria.AtivarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.AtualizarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.BuscarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.CriarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.DeletarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.InativarCategoriaUseCase;
import br.gov.ibama.srv_produtos.application.usecases.categoria.ListarCategoriasUseCase;
import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CriarCategoriaUseCase criarCategoriaUseCase;
    private final BuscarCategoriaUseCase buscarCategoriaUseCase;
    private final ListarCategoriasUseCase listarCategoriasUseCase;
    private final AtualizarCategoriaUseCase atualizarCategoriaUseCase;
    private final InativarCategoriaUseCase inativarCategoriaUseCase;
    private final AtivarCategoriaUseCase ativarCategoriaUseCase;
    private final DeletarCategoriaUseCase deletarCategoriaUseCase;

    @PostMapping
    public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());

        Categoria categoriaCriada = criarCategoriaUseCase.execute(categoria);
        return ResponseEntity.ok(new CategoriaDTO(categoriaCriada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarCategoria(@PathVariable Long id) {
        Categoria categoria = buscarCategoriaUseCase.execute(id);
        return ResponseEntity.ok(new CategoriaDTO(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<Categoria> categorias = listarCategoriasUseCase.execute();
        List<CategoriaDTO> categoriasDTO = categorias.stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriasDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());

        Categoria categoriaAtualizada = atualizarCategoriaUseCase.execute(categoria);
        return ResponseEntity.ok(new CategoriaDTO(categoriaAtualizada));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<CategoriaDTO> inativarCategoria(@PathVariable Long id) {
        Categoria categoria = inativarCategoriaUseCase.execute(id);
        return ResponseEntity.ok(new CategoriaDTO(categoria));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<CategoriaDTO> ativarCategoria(@PathVariable Long id) {
        Categoria categoria = ativarCategoriaUseCase.execute(id);
        return ResponseEntity.ok(new CategoriaDTO(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        deletarCategoriaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}