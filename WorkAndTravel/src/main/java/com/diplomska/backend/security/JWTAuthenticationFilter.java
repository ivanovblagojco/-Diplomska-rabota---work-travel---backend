package com.diplomska.backend.security;

import com.auth0.jwt.JWT;
import com.diplomska.backend.exceptions.UserNotEnabledException;
import com.diplomska.backend.model.Role;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.diplomska.backend.security.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            com.diplomska.backend.model.User creds = new com.diplomska.backend.model.User();
            creds.setEmail(req.getHeader("email"));
            creds.setPassword(req.getHeader("password"));

            com.diplomska.backend.model.User user = userService.findByEmail(req.getHeader("email"));

            if(user.getIs_enabled()){
                Role role = user.getRole();
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(role.getName()));
                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                creds.getEmail(),
                                creds.getPassword(),
                                authorities)
                );
            }else{
                throw new UserNotEnabledException();
            }

        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        com.diplomska.backend.model.User user = userService.findByEmail(req.getHeader("email"));
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("authority", user.getRole().getName())
                .sign(HMAC512(SECRET.getBytes()));
        res.getWriter().print(TOKEN_PREFIX + token);
    }
}
