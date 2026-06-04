package com.studentspace.config;

import com.studentspace.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (jwtService.estValide(token)) {
            String subject = jwtService.extraireSubject(token);
            String role    = jwtService.extraireRole(token);

            var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                subject, null,
                List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role))
            );
            org.springframework.security.core.context.SecurityContextHolder
                .getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
