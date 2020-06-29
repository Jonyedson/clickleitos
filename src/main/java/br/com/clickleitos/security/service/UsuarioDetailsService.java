package br.com.clickleitos.security.service;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if(usuario == null){
            throw new UsernameNotFoundException(String.format("Email não encontrado: %s ", email));
        }
        return new UsuarioDetails(usuario.getId(), usuario.getEmail(),usuario.getSenha(), usuario.getProfiles(),true,true,true,true);
    }
}
