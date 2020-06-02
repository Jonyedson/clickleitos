package br.com.clickleitos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clickleitos.domain.Leito;
import org.springframework.stereotype.Repository;

@Repository
public interface  LeitoRepository extends JpaRepository<Leito, Long>{

}
