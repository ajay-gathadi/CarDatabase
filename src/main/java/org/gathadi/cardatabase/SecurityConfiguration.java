package org.gathadi.cardatabase;

import org.gathadi.cardatabase.service.UserDetailsServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserDetailsServiceImplementation userDetailsService;

    public SecurityConfiguration(UserDetailsServiceImplementation userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method defines the password hashing algorithm, BCryptPasswordEncoder,
     * which encodes a hashed password during the authentication process.
     * @param authenticationManagerBuilder the builder used to create an `AuthenticationManager` with custom user details and password encoding
     * @throws Exception if an error occurs while configuring the `AuthenticationManagerBuilder`
     */
    public void configureGloabl(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
