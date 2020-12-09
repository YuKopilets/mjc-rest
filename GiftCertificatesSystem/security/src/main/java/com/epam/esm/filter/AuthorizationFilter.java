package com.epam.esm.filter;

import com.epam.esm.jwt.JwtTokenSupport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The {@code Authorization filter} is checking request to having jwt tokens
 * and validate them.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see GenericFilterBean
 */
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenSupport jwtTokenSupport;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) request);
        if (tokenIsValid(token)) {
            Authentication authentication = jwtTokenSupport.parseTokenToAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.replace("Bearer ", "");
        }
        return StringUtils.EMPTY;
    }

    private boolean tokenIsValid(String token) {
        return StringUtils.isNotEmpty(token) && jwtTokenSupport.validateToken(token);
    }
}
