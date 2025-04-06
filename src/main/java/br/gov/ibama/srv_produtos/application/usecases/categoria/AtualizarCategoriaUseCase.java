package br.gov.ibama.srv_produtos.application.usecases.categoria;

import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaDuplicadaException;
import br.gov.ibama.srv_produtos.domain.exceptions.CategoriaNotFoundException;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;

    public Categoria execute(Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(categoria.getId())
                .orElseThrow(() -> new CategoriaNotFoundException(categoria.getId()));

        if (!categoria.getNome().equals(categoriaExistente.getNome())
                && categoriaRepository.existsByNome(categoria.getNome())) {
            throw new CategoriaDuplicadaException(categoria.getNome());
        }

        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setDescricao(categoria.getDescricao());

        return categoriaRepository.save(categoriaExistente);
    }
}