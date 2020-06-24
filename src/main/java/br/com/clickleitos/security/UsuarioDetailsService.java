package br.com.clickleitos.security;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService{


    @Autowired
    private UsuarioRepository usuarioRepository;


    //Buscando informações no banco de dados Email e senha para validar

    @Override
    public UsuarioDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null){
            throw new UsernameNotFoundException(email);
        }

        return new UsuarioDetails(usuario.getId(),usuario.getEmail(),usuario.getSenha(), usuario.getProfiles());

    }


}
