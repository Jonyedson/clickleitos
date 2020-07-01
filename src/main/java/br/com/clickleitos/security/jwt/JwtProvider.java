package br.com.clickleitos.security.jwt;

import br.com.clickleitos.security.service.UsuarioDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {


    @Value("${jwt.secret}")
    private String jwtSecret;


    @Value("${jwt.expiration}")
    private Long jwtExpiration;


    public String generateJwtToken(Authentication authentication) {
        UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();
        //verificar token
        String compact = Jwts.builder()
                .setSubject((usuarioDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
        return compact;
    }


    public boolean validToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String email = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (email != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).parseClaimsJws(token).getBody();
        }
        catch (Exception e) {
            return null;
        }
    }

    public String getEmail(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
