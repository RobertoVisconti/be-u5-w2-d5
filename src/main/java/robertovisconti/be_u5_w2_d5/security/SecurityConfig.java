package robertovisconti.be_u5_w2_d5.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Ci permette di configurare le impostazioni di spring security
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {


        // disabilito per non far gestire l'autenticazione tramite i classici form generati dal server, come la pagina di login che mi crea di default spring
        httpSecurity.formLogin(formLogin -> formLogin.disable());


        // Dato che utilizzo JWT che è stateless devo disabilitare le sessioni
        httpSecurity.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Disabilito la protezone perchè non utilizzo i cookie
        httpSecurity.csrf(csrf -> csrf.disable());

        // Vado ad eliminare il controllo di autenticazione su tutti gli end-point che fà Spring Security di default (401)
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());

        return httpSecurity.build();
    }


}
