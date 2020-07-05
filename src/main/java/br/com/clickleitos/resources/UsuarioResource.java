package br.com.clickleitos.resources;

import java.net.URI;
import java.util.List;

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

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.services.UsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService serviceUsuario;


	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> list = serviceUsuario.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {
		Usuario obj = serviceUsuario.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	/*
	@PostMapping
	public ResponseEntity<Void> insert( @RequestBody @Valid Usuario usuario) {
		usuario = serviceUsuario.insert(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	 */

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		serviceUsuario.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid Usuario obj) {
		serviceUsuario.update(id, obj);
		return ResponseEntity.noContent().build();
	}
}