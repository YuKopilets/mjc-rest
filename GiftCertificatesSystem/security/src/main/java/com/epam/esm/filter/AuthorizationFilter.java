package com.epam.esm.filter;

import com.epam.esm.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class AuthorizationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserDetailsService userDetailsService;

    public AuthorizationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest)request);
        if (tokenIsValid(token)) {
            String userLogin = JwtUtils.getLoginFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);
            Authentication authentication = generateAuthentication(userDetails);
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
        return StringUtils.isNotEmpty(token) && JwtUtils.validateToken(token);
    }

    private UsernamePasswordAuthenticationToken generateAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
