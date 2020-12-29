package com.epam.esm.security.filter;

import com.epam.esm.security.jwt.JwtTokenSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Jwt authentication filter based on authentication and
 * preparing jwt token for authenticated OAuth 2.0 users.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see SimpleUrlAuthenticationSuccessHandler
 */
@Component
@RequiredArgsConstructor
public class JwtOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenSupport jwtTokenSupport;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String token = jwtTokenSupport.generateToken(authentication);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
