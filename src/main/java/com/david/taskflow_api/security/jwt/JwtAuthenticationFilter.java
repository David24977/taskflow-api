package com.david.taskflow_api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1️ Rutas públicas (si quieres saltarte /auth/** aquí, lo vemos luego)

        // 2️ Leer header Authorization
        final String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza por "Bearer ", seguimos la cadena y salimos del filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️ Extraer el token (quita el "Bearer ")
        try{
        final String jwt = authHeader.substring(7);

        // 4️ Sacar el username del token (sub)
        final String username = jwtService.extractUsername(jwt);

        // 5️ Si tenemos username y aún no hay Authentication en el contexto...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Cargamos el usuario desde BD usando UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 6️ Validamos el token contra el usuario
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 7️ Creamos un Authentication para Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 8 Añadimos detalles de la request (IP, header, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9️ Metemos el Authentication en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

    } catch (Exception ex) {
        //  token inválido → NO autenticamos y seguimos
        SecurityContextHolder.clearContext();
    }

        // 10 Siempre continuar la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
