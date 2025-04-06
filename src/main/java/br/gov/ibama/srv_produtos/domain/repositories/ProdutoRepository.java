package br.gov.ibama.srv_produtos.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ibama.srv_produtos.domain.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto save(Produto produto);

    Optional<Produto> findById(Long id);

    List<Produto> findAll();

    void delete(Produto produto);
}