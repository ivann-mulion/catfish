package org.cat.fish.tripservice.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.tripservice.security.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;


    public Authentication getAuthentication(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();



        Long userId = claims.get("userId", Long.class);
        String firstName = claims.get("firstName", String.class);
        String lastName = claims.get("lastName", String.class);

        List<GrantedAuthority> authorities = extractAuthorities(claims);

        UserPrincipal principal = new UserPrincipal(userId, firstName, lastName);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    private List<GrantedAuthority> extractAuthorities(Claims claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        List<String> roles = (List<String>) claims.get("authorities");

        if (roles != null) {
            roles.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
        }

        return authorities;
    }

    public Boolean validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid format Token -> Message: ", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token -> Message: ", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token -> Message: ", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: ", e);
        }
        return false;
    }

}