package br.gov.ibama.srv_produtos.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ibama.srv_produtos.domain.entities.Categoria;
import br.gov.ibama.srv_produtos.domain.repositories.CategoriaRepository;

@Repository
public interface JpaCategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepository {
}