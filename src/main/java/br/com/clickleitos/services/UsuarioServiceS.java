package br.com.clickleitos.services;

import br.com.clickleitos.security.UsuarioDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioServiceS {
    public static UsuarioDetails authenticated(){
        try{
            return (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception  e){
            return null;
        }
    }
}
