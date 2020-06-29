package br.com.clickleitos.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        String token = authorizationHeader.replace("Bearer ", "");
        try{
            String key = "sfdghfgdsregfsdgfsdgsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfsfdghfgdsregfsdgfsdgdfgfdfgf";
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String email = body.getSubject();

            List<Object> authorities = (List<Object>) body.get("authorities");
            System.out.println(authorities);


            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream().map(m -> new SimpleGrantedAuthority(m.toString())).collect(Collectors.toSet());

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
    }
}
