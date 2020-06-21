package br.com.clickleitos.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void instantiateTestDatabase() throws ParseException{

		Unidade unidade01 = new Unidade(null,"UPA - Rendeiras " , "000000000",-8.719084, -35.970908);
		Unidade unidade02 = new Unidade(null, "UPA - Salgado ", "000000000",-8.619084, -35.750808);

		Usuario usuario01 = new Usuario(null, "admin01","435565565",  "test01@gmail.com", passwordEncoder.encode("123"), unidade01);
		Usuario usuario02 = new Usuario(null, "admin02", "2343546", "test02@gmail.com",passwordEncoder.encode("456") , unidade02);

		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));
		repositoryUsuario.saveAll(Arrays.asList(usuario01, usuario02));

		Leito leito01 = new Leito(null, 100, 20, unidade01);
		unidade01.setLeito(leito01);
		Leito leito02 = new Leito(null, 100, 20, unidade02);
		unidade02.setLeito(leito02);

		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));

		

	}

}
