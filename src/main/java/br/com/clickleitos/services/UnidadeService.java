package br.com.clickleitos.services;

import java.util.List;
import java.util.Optional;

import br.com.clickleitos.domain.Leito;
import br.com.clickleitos.repositories.LeitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Unidade;
import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UnidadeRepository;
import br.com.clickleitos.services.exceptions.DatabaseException;
import br.com.clickleitos.services.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository repository;

	@Autowired
	private LeitoRepository leitoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public List<Unidade> findAll() {
		List<Unidade> list = repository.findAll();
		return list;
	}

	public Unidade findById(Long id) {
		Optional<Unidade> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void insert(Unidade unidade, Leito leito, Long id) {
		Usuario  usuario = usuarioService.findById(id);
		unidade.setId(null);
		unidade.setUsuario(usuario);
		leito.setId(null);
		unidade.setLeito(leito);
		repository.save(unidade);
	}
	public Unidade insert(Unidade unidade) {
		unidade.setId(null);
		unidade = repository.save(unidade);
		return unidade;
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

	public void update(Long id, Unidade obj) {
		Unidade entity = findById(id);
		updateData(entity, obj);
	}

	private void updateData(Unidade entity, Unidade obj) {
		if (obj.getNome() != null) {
			entity.setNome(obj.getNome());

		}
		if (obj.getCnpj() != null) {
			entity.setCnpj(obj.getCnpj());

		}
		if (obj.getLatitude() != null) {
			entity.setLatitude(obj.getLatitude());

		}
		if (obj.getLongitude() != null) {
			entity.setLongitude(obj.getLongitude());

		}if (obj.getUsuario() != null) {
			entity.setUsuario(obj.getUsuario());

		}



	}

}
