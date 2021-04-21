package com.diplomska.backend.security;

import com.diplomska.backend.service.implementation.UserDetailsServiceImpl;
import com.diplomska.backend.service.interfaces.FacebookClientService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;

import static com.diplomska.backend.security.SecurityConstants.*;

@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailsServiceImpl userDetailsService;
    private UserService userService;
    private FacebookClientService facebookClientService;

    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailsService, UserService userService, FacebookClientService facebookClientService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.facebookClientService = facebookClientService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers( ADMIN_CREATE_USER, LOGIN_URL,USER_CREATE_USER, FACEBOOK_LOGIN, CONFIRM_ACCOUNT, FORGOT_PASSWORD, RESET_PASSWORD,
                        GET_ALL_POSTS, GET_POST, GET_LAST_THREE_POSTS, GET_ALL_POSTS_FROM_AGENCY, GET_ALL_POSTS_FROM_USERS, GET_ALL_COMMENTS_FOR_POST, CREATE_CONTACT)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFacebookFilter(authenticationManager(), userService, facebookClientService))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        config.addExposedHeader("WWW-Authenticate");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Headers");

        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return source;

    }

}
