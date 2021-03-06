package com.epam.esm.security.filter;

import com.epam.esm.security.jwt.JwtTokenSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Jwt authentication filter based on authentication and
 * preparing jwt token for authenticated local users.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see UsernamePasswordAuthenticationFilter
 */
@RequiredArgsConstructor
public class JwtLocalAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenSupport jwtTokenSupport;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        setAuthenticationManager(authenticationManager);
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication)
            throws IOException, ServletException {
        String token = jwtTokenSupport.generateToken(authentication);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        super.successfulAuthentication(request, response, chain, authentication);
    }
}
