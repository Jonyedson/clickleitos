package br.com.clickleitos.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clickleitos.domain.Usuario;


public interface  UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	
}
