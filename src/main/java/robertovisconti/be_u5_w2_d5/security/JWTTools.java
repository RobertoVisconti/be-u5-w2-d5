package robertovisconti.be_u5_w2_d5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {

    private final String secret;

    public JWTTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }


    public String generateToken(Dipendente dipendente) {
        // il builder ci permette di costruire il token, metodo della libreria
        return Jwts.builder()
                // Data Emissione
                .issuedAt(new Date(System.currentTimeMillis()))
                // Data Scadenza
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                // Proprietario del token / id dell'utente
                .subject(String.valueOf(dipendente.getId()))
                // Firma token per l integrità
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Ci sono stati problemi con il token! Rieffettuare login!");
        }

    }


}
