package ru.otus.books.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebAppConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests().mvcMatchers("/static/**").permitAll().and()
                .authorizeRequests().mvcMatchers("/h2-console/**").permitAll().and()
                .authorizeRequests().mvcMatchers("/**").authenticated().and()
                .formLogin()
                .and().logout().logoutUrl("/logout")
                .and().exceptionHandling();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
