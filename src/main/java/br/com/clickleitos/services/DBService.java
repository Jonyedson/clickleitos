package br.com.clickleitos.services;

import java.text.ParseException;
import java.util.Arrays;

import br.com.clickleitos.domain.enums.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Leito;
import br.com.clickleitos.domain.Unidade;
import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UnidadeRepository;
import br.com.clickleitos.repositories.UsuarioRepository;

@Service
public class DBService {

	private final PasswordEncoder passwordEncoder;


	@Autowired
	private UsuarioRepository repositoryUsuario;
	
	@Autowired
	private UnidadeRepository repositoryUnidade;

	public DBService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	public void instantiateTestDatabase() throws ParseException{


		Unidade unidade01 = new Unidade(null,"UPA - Rendeiras " , "26422414000100",-8.719084, -35.970908);
		Unidade unidade02 = new Unidade(null, "UPA - Salgado ", "21279146000198",-8.619084, -35.750808);

		Usuario usuario01 = new Usuario(null, "admin01","25060537005",  "jhon@gmail.com",passwordEncoder.encode("password"), unidade01);
		Usuario usuario02 = new Usuario(null, "admin02", "25060537005", "oi@gmail.com",passwordEncoder.encode("password") , unidade02);
		Usuario usuario03 = new Usuario(null, "admin02", "25060537005", "jose@gmail.com",passwordEncoder.encode("password") , unidade02);

		usuario01.addProfile(Profile.ADMIN);
		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));
		repositoryUsuario.saveAll(Arrays.asList(usuario01, usuario02, usuario03));

		Leito leito01 = new Leito(null, 100, 20, unidade01);
		unidade01.setLeito(leito01);
		Leito leito02 = new Leito(null, 100, 20, unidade02);
		unidade02.setLeito(leito02);

		repositoryUnidade.saveAll(Arrays.asList(unidade01, unidade02));
	}

}
