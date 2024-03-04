package com.example.mapdmeo.config;

import com.example.mapdmeo.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    private final CustomUserDetailService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Throwable{

        http.formLogin(c ->  c.loginPage("/login")
                .successForwardUrl("/")
                .failureUrl("/security/login-error")
                .permitAll());
        http.logout( c -> c.logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll());
        http.authorizeHttpRequests(
                        c -> c.requestMatchers("/","/home","/js/**","/bootstrap/**","/guest/**","/signup","/security/**","/images/**","/bootstrap/**","/states/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated());
        http.csrf(r->r.disable());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
