package com.epam.esm.config;

import com.epam.esm.security.filter.AuthorizationFilter;
import com.epam.esm.security.filter.JwtLocalAuthenticationFilter;
import com.epam.esm.security.filter.JwtOAuth2AuthenticationSuccessHandler;
import com.epam.esm.security.jwt.JwtTokenSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String[] ADMIN_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/actuator",
            "/actuator/*"
    };

    private final UserDetailsService userDetailsService;
    private final OidcUserService oidcUserService;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    private final AuthorizationFilter authorizationFilter;
    private final JwtOAuth2AuthenticationSuccessHandler authenticationSuccessHandler;
    private final JwtTokenSupport jwtTokenSupport;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                    .antMatchers("/", "/registration", "/static/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/certificates","/certificates/*").permitAll()
                    .antMatchers(ADMIN_WHITELIST).hasRole(ADMIN_ROLE)
                    .antMatchers(HttpMethod.POST,
                            "/certificates", "/certificates/", "/tags", "/tags/").hasRole(ADMIN_ROLE)
                    .antMatchers(HttpMethod.PATCH).hasRole(ADMIN_ROLE)
                    .antMatchers(HttpMethod.DELETE).hasRole(ADMIN_ROLE)
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                    .permitAll()
                .and()
                    .oauth2Login()
                        .loginPage("/oauth")
                        .authorizationEndpoint()
                        .baseUri("/oauth2/authorize-client")
                .and()
                    .successHandler(authenticationSuccessHandler)
                    .userInfoEndpoint()
                        .oidcUserService(oidcUserService)
                        .userService(oAuth2UserService)
                .and()
                    .permitAll(false)
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .addFilter(new JwtLocalAuthenticationFilter(authenticationManager(), jwtTokenSupport))
                    .addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
       builder.userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder());
    }
}
