package com.application.rest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) //Cross site Request Forgery se encarga de interceptar la comunicacion entre el navegador y el servidor lo cual es peligroso
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/product/**").permitAll()
                        .requestMatchers("/api/maker/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .successHandler(successHandler())
                        .permitAll())
                .logout(Customizer.withDefaults())     //Login y logout accesible para todos y default
                .httpBasic(Customizer.withDefaults())  //<----------- NO ES LO IDEAL SOLO LO ESTOY PROBANDO
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)// Creo una sesion, si existe la reutilizo
                        .invalidSessionUrl("/login")
                                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession) //Cuando detecta un ataque se va a generar otro id de sesion, asi al atacante no lo deja introducirse
                        .maximumSessions(1)
                        .expiredUrl("/login")//-----> si su sesion expira, lo llevo al login
                        .sessionRegistry(sessionRegistry())

                        )
                .build();
    }
    @Bean
    public SessionRegistry sessionRegistry()
    {
        return new SessionRegistryImpl();
    }
    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication)-> response.sendRedirect("/api/maker/1")); // <-------------- Esto se usa si mi API va a tener vistas HTML, si es solo Json (como es el caso) no
    }
}
