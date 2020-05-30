package br.com.clickleitos.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public void instantiateTestDatabase() {
		Usuario user01 = new Usuario(null, "123", "test01@gmail.com");
		Usuario user02 = new Usuario(null, "456", "test02@gmail.com");
		
		repository.saveAll(Arrays.asList(user01, user02));
	}
	
	

}
