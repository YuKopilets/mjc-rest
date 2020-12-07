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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private static final String REDIRECT_URL = "login";
    private static final List<String> UNCHECKED_URIS;
    private static final String STATIC_RESOURCES_URL = "/gift_certificates_system/static/";

    private final JwtTokenSupport jwtTokenSupport;

    static {
        UNCHECKED_URIS = new ArrayList<>();
        UNCHECKED_URIS.add("/gift_certificates_system/login");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (isUncheckedUri(httpRequest)) {
            chain.doFilter(request, response);
        }

        String token = getTokenFromRequest(httpRequest);
        if (tokenIsValid(token)) {
            Authentication authentication = jwtTokenSupport.parseTokenToAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.sendRedirect(REDIRECT_URL);
        }
    }

    private boolean isUncheckedUri(HttpServletRequest request) {
        String currentUrl = request.getRequestURI();
        return UNCHECKED_URIS.contains(currentUrl) || currentUrl.startsWith(STATIC_RESOURCES_URL);
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
