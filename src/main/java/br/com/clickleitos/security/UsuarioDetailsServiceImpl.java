package br.com.clickleitos.security;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario ==null){
            throw new UsernameNotFoundException(email);
        }
        return new UsuarioS(usuario.getId(),usuario.getEmail(),usuario.getSenha(),usuario.getProfiles());
    }
}
