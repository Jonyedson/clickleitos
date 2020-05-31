package br.com.clickleitos.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Unidade;
import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UnidadeRepository;
import br.com.clickleitos.services.exceptions.DatabaseException;
import br.com.clickleitos.services.exceptions.ResourceNotFoundException;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository repository;
	
	@Autowired UsuarioService service;

	public List<Unidade> findAll() {
		List<Unidade> list = repository.findAll();
		return list;
	}

	public Unidade findById(Long id) {
		Optional<Unidade> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional
	public Unidade insert(Unidade obj, Long id) {
		Usuario usuario = service.findById(id);
		obj.setId(null);
		//obj.setUsuario(usuario);
		obj = repository.save(obj);
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

	public Unidade update(Long id, Unidade obj) {
		Unidade entity = findById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Unidade entity, Unidade obj) {
		if (obj.getNome() != null) {
			entity.setNome(obj.getNome());

		}
		if (obj.getLatitude() != null) {
			entity.setLatitude(obj.getLatitude());

		}
		if (obj.getLongitude() != null) {
			entity.setLongitude(obj.getLongitude());

		}
		if (obj.getCnpj() != null) {
			entity.setCnpj(obj.getCnpj());
		}
	}

}
