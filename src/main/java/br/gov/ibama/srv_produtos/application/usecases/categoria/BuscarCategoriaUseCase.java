package br.gov.ibama.srv_produtos.application.usecases.categoria;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;

    public Categoria execute(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }
}