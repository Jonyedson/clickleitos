package br.com.clickleitos.services;

import br.com.clickleitos.security.UsuarioS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioServiceS {
    public static UsuarioS authenticated(){
        try{
            return (UsuarioS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception  e){
            return null;
        }
    }
}