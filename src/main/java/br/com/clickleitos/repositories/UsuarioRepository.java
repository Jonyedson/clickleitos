package br.com.clickleitos.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.clickleitos.domain.Usuario;

public interface  UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Transactional(readOnly = true)
	Usuario findByEmail(String email);
}
