package br.com.clickleitos.services;

import java.util.List;
import java.util.Optional;

import br.com.clickleitos.domain.Unidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UsuarioRepository;
import br.com.clickleitos.services.exceptions.DatabaseException;
import br.com.clickleitos.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UnidadeService unidadeService;

	public UsuarioService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	public List<Usuario> findAll() {
		List<Usuario> list = repository.findAll();
		return list;
	}

	public Usuario findById(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Usuario insert(Usuario obj) {
		Unidade unidade = unidadeService.findById(obj.getUnidade().getId());
		Usuario usuario = new Usuario(null, obj.getNome(), obj.getCpf(), obj.getEmail(),passwordEncoder.encode(obj.getSenha()), unidade);

		obj = repository.save(usuario);
		return obj;
	}

	public Usuario insertGerente(Usuario obj) {
		Usuario usuario = new Usuario(null, obj.getNome(), obj.getCpf(), obj.getEmail(),passwordEncoder.encode(obj.getSenha()));

		obj = repository.save(usuario);
		return obj;
	}
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Usuario update(Long id, Usuario obj) {
		Usuario entity = findById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Usuario entity, Usuario obj) {
		if (obj.getNome() != null) {
			entity.setNome(obj.getNome());
		}
		if (obj.getEmail() != null) {
			entity.setEmail(obj.getEmail());
		}
		if (obj.getCpf() != null) {
			entity.setCpf(obj.getCpf());
		}
		if (obj.getStatus() != null) {
			entity.setStatus(obj.getStatus());
		}
		if (obj.getUnidade() != null) {
			entity.setUnidade(obj.getUnidade());
		}
	}
	//Long id, String nome,String cpf, String email, String senha, Unidade unidade
	public Usuario encoPass(Usuario obj){
		Unidade unidade = unidadeService.findById(obj.getUnidade().getId());
		Usuario usuario = new Usuario(null, obj.getNome(), obj.getCpf(), obj.getEmail(),passwordEncoder.encode(obj.getSenha()), unidade);

		return usuario;
	}

}
