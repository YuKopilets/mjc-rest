package com.epam.esm.config;

import com.epam.esm.filter.AuthorizationFilter;
import com.epam.esm.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String[] AUTHORIZE_ADMIN_LIST = {
            // -- swagger
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
            // -- actuator
            "/actuator",
            "/actuator/*"
    };

    private final UserDetailsService userDetailsService;
    private final OidcUserService oidcUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                    .antMatchers("/", "/registration", "/static/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/certificates","/certificates/*").permitAll()
                    .antMatchers(AUTHORIZE_ADMIN_LIST).hasRole(ADMIN_ROLE)
                    .antMatchers(HttpMethod.POST, "/certificates", "/certificates/", "/tags", "/tags/")
                        .hasRole(ADMIN_ROLE)
                    .antMatchers(HttpMethod.PATCH).hasRole(ADMIN_ROLE)
                    .antMatchers(HttpMethod.DELETE).hasRole(ADMIN_ROLE)
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
                    .oauth2Login()
                        .loginPage("/oauth")
                        .defaultSuccessUrl("/", true)
                        .authorizationEndpoint()
                        .baseUri("/oauth2/authorize-client")
                        .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                    .tokenEndpoint()
                        .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                    .userInfoEndpoint()
                        .oidcUserService(oidcUserService)
                .and()
                    .permitAll(false)
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))

                    .addFilterBefore(new AuthorizationFilter(userDetailsService),
                            UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        return new DefaultAuthorizationCodeTokenResponseClient();
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
