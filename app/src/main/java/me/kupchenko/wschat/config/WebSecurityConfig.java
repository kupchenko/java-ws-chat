package me.kupchenko.wschat.config;

import me.kupchenko.wschat.security.SAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .loginPage("/signin.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/chat.html")
                .failureUrl("/signin.html?status=error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/signin.html")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/js/**", "/lib/**", "/images/**", "/css/**", "/signin.html","/registration.html","/").permitAll()
                .antMatchers("/websocket").hasRole("ADMIN")
                .anyRequest().authenticated();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Bean
    public SAuthenticationProvider getAuthenticationProvider() {
        return new SAuthenticationProvider();
    }
}
