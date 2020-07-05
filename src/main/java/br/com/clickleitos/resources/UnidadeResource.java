package br.com.clickleitos.resources;

import java.net.URI;
import java.util.List;

import br.com.clickleitos.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.clickleitos.domain.Leito;
import br.com.clickleitos.domain.Unidade;
import br.com.clickleitos.services.LeitoService;
import br.com.clickleitos.services.UnidadeService;

@RestController
@RequestMapping("/unidade")
public class UnidadeResource {

	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private LeitoService serviceLeito;

	@GetMapping
	public ResponseEntity<List<Unidade>> findAll() {

		List<Unidade> list = serviceUnidade.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Unidade> findByIdUnidade(@PathVariable Long id) {
		Unidade obj = serviceUnidade.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Unidade obj) {
		Leito leito = obj.getLeito();
		obj = serviceUnidade.insert(obj);
		serviceLeito.insert(leito, obj.getId());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		serviceUnidade.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateUnidade(@PathVariable Long id, @RequestBody Unidade obj) {
		serviceUnidade.update(id, obj);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}/leito")
	public ResponseEntity<Leito> findById(@PathVariable Long id) {
		Leito obj = serviceLeito.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PutMapping(value = "/{id}/leito")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Leito obj) {
		serviceLeito.update(id, obj);
		return ResponseEntity.noContent().build();
	}
}