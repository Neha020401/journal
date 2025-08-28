package com.prac.journal.config;

import com.prac.journal.service.UserServiceDetailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserServiceDetailImp userServiceDetailImp ;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**","/user/**").authenticated()
                        .anyRequest().permitAll()
                ).httpBasic();

        http.csrf().disable();
        return http.build();
    }

    public void  configure(AuthenticationManagerBuilder auth )throws  Exception {
        auth.userDetailsService(userServiceDetailImp).passwordEncoder(passwordEncoder())  ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
