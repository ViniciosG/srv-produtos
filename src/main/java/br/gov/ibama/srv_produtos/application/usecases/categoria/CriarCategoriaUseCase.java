package br.gov.ibama.srv_produtos.application.usecases.categoria;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaDuplicadaException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;

    public Categoria execute(Categoria categoria) {
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new CategoriaDuplicadaException(categoria.getNome());
        }

        categoria.onCreate();
        return categoriaRepository.save(categoria);
    }
}