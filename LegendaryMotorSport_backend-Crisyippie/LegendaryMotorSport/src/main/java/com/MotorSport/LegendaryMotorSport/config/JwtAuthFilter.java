package com.MotorSport.LegendaryMotorSport.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.MotorSport.LegendaryMotorSport.service.CustomUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Interceptando petición: {}", request.getRequestURI());

        try {
            final String authHeader = request.getHeader("Authorization");
            String email = null;
            String token = null;

            logger.info("Authorization header: {}", authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                logger.info("Token extraído: {}", token);

                email = jwtUtil.extractUsername(token);
                logger.info("Email extraído del token: {}", email);
            } else {
                logger.warn("Authorization header ausente o mal formado");
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("Buscando usuario por email: {}", email);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                logger.info("Usuario encontrado: {}", userDetails.getUsername());

                if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {
                    logger.info("Token válido. Autenticando usuario: {}", email);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("Autenticación establecida en el contexto de seguridad");
                } else {
                    logger.warn("Token inválido para el usuario: {}", email);
                }
            }
        } catch (Exception e) {
            logger.warn("JWT inválido o error de autenticación: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
