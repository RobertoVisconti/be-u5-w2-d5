package robertovisconti.be_u5_w2_d5.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;

import java.util.Date;

@Component
public class JWTTools {

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
                .signWith()
                .compact();
    }

//    public void verifyToken() {
//
//    }


}
