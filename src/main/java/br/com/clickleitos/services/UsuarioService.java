package br.com.clickleitos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	
	public List<Usuario> findAll(){
		List<Usuario> list = repository.findAll();
		return list;
	}
	
	public Usuario findById(Long id) {
		Optional<Usuario> optional = repository.findById(id);
		Usuario obj = optional.get();
		return obj;
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
}
