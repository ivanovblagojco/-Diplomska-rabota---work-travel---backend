package com.diplomska.backend.security;

import com.auth0.jwt.JWT;
import com.diplomska.backend.helpers.FacebookUser;
import com.diplomska.backend.model.Role;
import com.diplomska.backend.service.interfaces.FacebookClientService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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


public class JWTAuthenticationFacebookFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private FacebookClientService facebookClientService;
    private FacebookUser facebookUser;
    public JWTAuthenticationFacebookFilter(AuthenticationManager authenticationManager, UserService userService, FacebookClientService facebookClientService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.facebookClientService = facebookClientService;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(FACEBOOK_LOGIN, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            facebookUser = facebookClientService.getUser(req.getHeader("auth_token"));
            com.diplomska.backend.model.User user;
            try {
                user = userService.findByEmail(facebookUser.getEmail());
            }
            catch (Exception e){
                user = new com.diplomska.backend.model.User();

                user.setEmail(facebookUser.getEmail());
                user.setName(facebookUser.getFirstName());
                user.setSurname(facebookUser.getLastName());
                user.setPassword("");

                user = userService.create(user, false);
            }

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            "",
                            authorities)
            );
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        com.diplomska.backend.model.User user = userService.findByEmail(facebookUser.getEmail());
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("authority", user.getRole().getName())
                .sign(HMAC512(SECRET.getBytes()));
        res.getWriter().print(TOKEN_PREFIX + token);
    }
}
