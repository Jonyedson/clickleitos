package br.com.clickleitos.security.jwt;

import br.com.clickleitos.security.service.UsuarioDetails;
import br.com.clickleitos.security.service.UsuarioDetailsService;
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


    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtProvider jwtProvide, UsuarioDetailsService uuarioDetailsService) {
        this.jwtProvider = jwtProvide;
        this.userDetailsService = uuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        String authorizationHeader = request.getHeader("Authorization");

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
                String token = authorizationHeader.replace("Bearer ", "");
       */
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);





        /*

        try{

            String key = "sfdghfgdsregfsdgfsdgsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfdfgf";
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String email = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");



            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream().map(m -> new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toSet());

            Authentication authentication  = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    simpleGrantedAuthoritySet
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException e){
            throw new IllegalStateException(String.format("Token invalido %s", token));
        }
        filterChain.doFilter(request, response);

         */
    }
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtProvider.validToken(token)) {
            String username = jwtProvider.getUsername(token);
            UsuarioDetails user = (UsuarioDetails) userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
}
