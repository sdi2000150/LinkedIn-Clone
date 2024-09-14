package com.Backend_v10.Security;

import com.Backend_v10.Security.JwtAuthFilter;
import com.Backend_v10.Security.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Defines configuration for the application. It tells Spring that this class contains beans that should be managed and injected into other parts of the application.
@EnableWebSecurity // Enables Spring Security
@EnableMethodSecurity // Allows method-level security (to use annotations like  @PreAuthorize and @PostAuthorize to define authorization rules for specific methods)
public class SecurityConfig {

    @Autowired  // Enables automatic dependency injection
    private JwtAuthFilter authFilter; // Automatically inject an instance of the JwtAuthFilter class

    @Bean   // Defines a bean that can be injected into other parts of the application (same for rest beans)
    public UserDetailsService userDetailsService() { // UserDetailsService is an interface provided by Spring Security that is responsible for loading user details from a data source
        return new UserInfoService(); // Ensure UserInfoService implements UserDetailsService (Spring will create a new UserInfoService object and make it available for injection into other classes that require a UserDetailsService)
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // SecurityFilterChain is a chain of filters that are applied to secure the application (incoming requests are passed through these filters)
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()    // Allow all requests to /auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests
                .requestMatchers("/user/**").permitAll()  // For now allow all requests to /user
                .requestMatchers("/article/**").permitAll()  // For now allow all requests to /article
                .requestMatchers("/job/**").permitAll()  // For now allow all requests to /job
                .requestMatchers("/request/**").permitAll()  // For now allow all requests to /job


                // .requestMatchers("/auth/user/**").hasAuthority("ROLE_USER")
                // .requestMatchers("/auth/admin/**").hasAuthority("ROLE_ADMIN")
                // .anyRequest().authenticated() // Protect all other endpoints
            )
            .sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
            )
            .authenticationProvider(authenticationProvider()) // Custom authentication provider
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {  // Securely hash user passwords before storing them in the database
        return new BCryptPasswordEncoder(); // Password encoding
    }

    @Bean
    public AuthenticationProvider authenticationProvider() { // Responsible for authenticating users based on their credentials
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { // Responsible for coordinating the authentication process
        return config.getAuthenticationManager();
    }
}
