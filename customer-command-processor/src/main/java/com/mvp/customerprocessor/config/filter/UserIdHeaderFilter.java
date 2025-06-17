package com.mvp.customerprocessor.config.filter;

import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class UserIdHeaderFilter extends OncePerRequestFilter {

    private static final String USER_ID_HEADER = "x-user-id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String userId = request.getHeader(USER_ID_HEADER);
            if (userId != null && !userId.isEmpty()) {
                UserContextHolder.setUserId(UUID.fromString(userId));
            } else {
                throw new CustomerProcessorRuntimeException("Не передан userId");
            }
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/booking");
    }
}
