package br.com.clickleitos.domain.audit;

import br.com.clickleitos.security.service.UsuarioDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return Optional.ofNullable("System");
        }

        UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();
        return Optional.ofNullable(usuarioDetails.getUsername());
    }

}