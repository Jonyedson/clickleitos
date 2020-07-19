package br.com.clickleitos.resources;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.services.UsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
    /*
    Tratar as exception nesse path
     */
    @Autowired
    private UsuarioService serviceUsuario;


    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> list = serviceUsuario.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Encontra um Usuario pelo id",notes = "Buncas no banco de dados um Usuario pelo id",
    response = Usuario.class)
    public ResponseEntity<Usuario> findById(@ApiParam(value = "ID é um valor necessario para esta requisão") @PathVariable Long id) {
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