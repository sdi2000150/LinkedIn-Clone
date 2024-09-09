package com.Backend_v10.Security;

import com.Backend_v10.Security.JwtService;
import com.Backend_v10.Security.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// A custom filter responsible for handling JWT token validation and authentication
@Component  // Marks JwtAuthFilter as a Spring bean, allowing dependency injection
public class JwtAuthFilter extends OncePerRequestFilter { // It will be executed only once for each incoming request (OncePerRequestFilter)

    @Autowired  // Inject JwtService
    private JwtService jwtService;

    @Autowired  // Inject UserInfoService
    private UserInfoService userDetailsService;

    @Override   // Override the doFilterInternal method (This is the core method of the filter, executed for each request)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Check if the header starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract token
            email = jwtService.extractEmail(token); // Extract email from token
        }

        // If the token is valid and no authentication is set in the context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Validate token and set authentication
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
