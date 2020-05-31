package br.com.clickleitos.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Unidade;
import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UnidadeRepository;
import br.com.clickleitos.repositories.UsuarioRepository;

@Service
public class DBService {

	@Autowired
	private UsuarioRepository repositoryUsuario;
	
	@Autowired
	private UnidadeRepository repositoryUnidade;

	public void instantiateTestDatabase() throws ParseException{

		Usuario usuario01 = new Usuario(null, "admin01", "123 ", "test01@gmail.com");
		Usuario usuario02 = new Usuario(null, "admin02", "456", "test02@gmail.com");
		
		Unidade unidade01 = new Unidade(null, "000000000", "UPA - Rendeira ","-85433554", "-1453465");
		usuario01.setUnidade(unidade01);
		Unidade unidade02 = new Unidade(null, "000000000", "UPA - Salgado ","-84345788", "-123245345");
		unidade02.setUsuario(usuario02);
		
		unidade01.setUsuario(usuario01);
		unidade02.setUsuario(usuario02);
		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));
		repositoryUsuario.saveAll(Arrays.asList(usuario01, usuario02));
		
	}

}
