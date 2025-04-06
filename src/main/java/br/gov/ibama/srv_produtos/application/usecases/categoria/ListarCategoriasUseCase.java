package br.gov.ibama.srv_produtos.application.usecases.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarCategoriasUseCase {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> execute() {
        return categoriaRepository.findAll();
    }
}