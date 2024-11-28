package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.Authenticated.handle.HandleAfterSuccessfulLogin;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HandleAfterSuccessfulLogin handleAfterSuccessfulLogin;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/h2-console/**", "/signup", "/css/**", "/js/**", "/login/logout","/login/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin().loginPage("/login").permitAll();

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true).clearAuthentication(true);

        http.formLogin()
                .defaultSuccessUrl("/home", true)
                .successHandler(handleAfterSuccessfulLogin)
                .failureUrl("/login?error=true");

        http.csrf().disable()
                .headers().frameOptions().sameOrigin(); // Allow H2 Console in iframe
    }
}