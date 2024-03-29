package br.com.clickleitos.security.jwt;

import br.com.clickleitos.security.service.UsuarioDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtProvider jwtProvide, UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvide;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            try {
                UsernamePasswordAuthenticationToken auth = getAuthentication(getJwt(request));
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch (Exception e ){
                logger.error("Can NOT set user authentication -> Message: {}", e);
            }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtProvider.validToken(token)) {
            String username = jwtProvider.getEmail(token);
            UsuarioDetails usuarioDetails = (UsuarioDetails) userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(usuarioDetails, null, usuarioDetails.getAuthorities());
        }
        return null;
    }

    private String getJwt(HttpServletRequest request) {
        String Header = request.getHeader("Authorization");

        if (Header != null && Header.startsWith("Bearer ")) {
            String token = Header.replace("Bearer ", "");
            return token;
        }
        return null;
    }
}
