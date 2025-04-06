package br.gov.ibama.srv_produtos.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ibama.srv_produtos.domain.entities.Produto;
import br.gov.ibama.srv_produtos.domain.repositories.ProdutoRepository;

@Repository
public interface JpaProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepository {
}