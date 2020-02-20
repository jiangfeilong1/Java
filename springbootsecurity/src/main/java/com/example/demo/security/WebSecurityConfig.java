package com.example.demo.security;

import com.example.demo.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CustomUserService customUserService() {
        return new CustomUserService();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/ayUser/test")
                .permitAll();
        super.configure(httpSecurity);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
                //.inMemoryAuthentication()
                //.withUser("hhh").password("0000").roles("ADMIN")
                //.and()
                //.withUser("yyyy").password("7777").roles("USER");
    }
}
