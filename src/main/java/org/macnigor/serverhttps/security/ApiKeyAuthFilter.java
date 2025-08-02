package org.macnigor.serverhttps.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.macnigor.serverhttps.model.User;
import org.macnigor.serverhttps.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "Authorization";

    private final UserRepository userRepository;

    public ApiKeyAuthFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Пропускаем авторегистрацию и другие публичные пути
        if (path.equals("/api/auth/register") ) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HEADER_NAME);

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Bearer");
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = header.substring(7);
        Optional<User> userOpt = userRepository.findByApiKey(token);

        if (userOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Bearer");
            response.getWriter().write("Unauthorized");
            return;
        }

        User user = userOpt.get();

        var auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getApiKey(),
                List.of(() -> "ROLE_API_USER")
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }


}
