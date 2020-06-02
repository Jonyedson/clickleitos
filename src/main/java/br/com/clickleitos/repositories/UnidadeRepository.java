package br.com.clickleitos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clickleitos.domain.Unidade;
import org.springframework.stereotype.Repository;

@Repository
public interface  UnidadeRepository extends JpaRepository<Unidade, Long>{

}
