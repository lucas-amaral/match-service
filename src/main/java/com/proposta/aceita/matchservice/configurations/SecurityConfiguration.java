package com.proposta.aceita.matchservice.configurations;

import com.proposta.aceita.matchservice.entities.enums.Authority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Value("${services.internal.username}")
    private String internalUser;

    @Value("${services.internal.password}")
    private String internalPassword;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .passwordEncoder(PASSWORD_ENCODER)
                .withUser(internalUser)
                .password(PASSWORD_ENCODER.encode(internalPassword))
                .authorities(Authority.SYSTEM.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest()
                .hasAuthority(Authority.SYSTEM.name())

                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }

}