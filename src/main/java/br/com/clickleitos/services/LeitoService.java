package br.com.clickleitos.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.clickleitos.domain.Leito;
import br.com.clickleitos.domain.Unidade;
import br.com.clickleitos.repositories.LeitoRepository;
import br.com.clickleitos.services.exceptions.DatabaseException;
import br.com.clickleitos.services.exceptions.ResourceNotFoundException;

@Service
public class LeitoService {

	@Autowired
	private LeitoRepository repository;

	@Autowired
	private UnidadeService service;
	
	public List<Leito> findAll() {
		List<Leito> list = repository.findAll();
		return list;
	}

	public Leito findById(Long id) {
		Optional<Leito> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional
	public Leito insert(Leito obj, Long id) {
		Unidade unidade = service.findById(id);
		obj.setId(null);
		obj.setUnidade(unidade);
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
	
	public Leito update(Long id, Leito obj) {
		Leito entity = findById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Leito entity, Leito obj) {
		if (obj.getDisponiveis() != null) {
			entity.setDisponiveis(obj.getDisponiveis());

		}
		if (obj.getTotal() != null) {
			entity.setTotal(obj.getTotal());

		}
	}

}
