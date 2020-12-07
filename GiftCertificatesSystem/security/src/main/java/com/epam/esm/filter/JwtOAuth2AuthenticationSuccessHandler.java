package com.epam.esm.filter;

import com.epam.esm.jwt.JwtTokenSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Jwt authentication filter based on authentication and
 * preparing jwt token for authenticated oauth users.
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
        response.addHeader("token", token);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
