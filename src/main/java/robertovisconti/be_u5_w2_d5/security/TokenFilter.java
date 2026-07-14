package robertovisconti.be_u5_w2_d5.security;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import robertovisconti.be_u5_w2_d5.exceptions.UnauthorizedException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;

    public TokenFilter(JWTTools jwtTools) {
        this.jwtTools = jwtTools;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        // 1. Verifico se c'è un authoritazion all'interno dell'header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato Bearer ");

        // 2. EStraggo il token
        String accessToken = authHeader.replace("Bearer ", "");
        System.out.println(accessToken);

        // 3. Verifico il token
        this.jwtTools.verifyToken(accessToken);

        // 4. Token ok
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
