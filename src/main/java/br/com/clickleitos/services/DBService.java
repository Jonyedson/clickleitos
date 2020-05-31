package br.com.clickleitos.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Leito;
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
		
		Unidade unidade01 = new Unidade(null, "000000000", "UPA - Rendeiras ","-8.719084", "-35.970908", usuario01);
		Unidade unidade02 = new Unidade(null, "000000000", "UPA - Salgado ","-8.619084", "-35.750808", usuario02);
		
		repositoryUsuario.saveAll(Arrays.asList(usuario01, usuario02));
		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));
		
		Leito leito01 = new Leito(null, 100, 20, unidade01);
		unidade01.setLeito(leito01);
		Leito leito02 = new Leito(null, 100, 20, unidade02);
		unidade02.setLeito(leito02);

		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));
		
		

	}

}
