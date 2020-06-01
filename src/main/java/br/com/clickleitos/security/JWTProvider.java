package br.com.clickleitos.security;

import br.com.clickleitos.services.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JWTProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private UsuarioService usuarioService;

    public String generateToken(String username,Long id) {
        return Jwts.builder()
                .setSubject(username).claim("id", id)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public Integer getIdDoUsuario(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return Integer.parseInt(claims.get("id").toString());
        }
        return null;
    }

    public boolean validToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }
        catch (Exception e) {
            return null;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }
}
