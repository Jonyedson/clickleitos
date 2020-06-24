package br.com.clickleitos.security;

import br.com.clickleitos.dto.CredentialDto;
import br.com.clickleitos.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter /*extends UsernamePasswordAuthenticationFilter */{
/*
    private AuthenticationManager authenticationManager;

    private JWTProvider jwtUtil;

    private UsuarioService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTProvider jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    //Validação da senhas enviadas pelo front
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            CredentialDto credential = new ObjectMapper()
                    .readValue(req.getInputStream(), CredentialDto.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credential.getEmail(), credential.getSenha(), new ArrayList<>());
            Authentication authenticate = authenticationManager.authenticate(authToken);
            return authenticate;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Enviar token caso a validação seja ok
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {


        String username = ((UsuarioS) auth.getPrincipal()).getUsername();
        Long id = ((UsuarioS) auth.getPrincipal()).getId();
        String token = jwtUtil.generateToken(username,id);
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("access-control-expose-headers", "Authorization");

    }
    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
    */
}
