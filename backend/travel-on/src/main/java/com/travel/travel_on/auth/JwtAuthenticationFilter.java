package com.travel.travel_on.auth;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.model.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(JwtTokenProvider.HEADER_STRING);

        if (header == null || !header.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            filterChain.doFilter(request, response);
            return;
        }

       filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenProvider.HEADER_STRING);
        if (token != null) {
            JWTVerifier verifier = JwtTokenProvider.getVerifier();
            JwtTokenProvider.handleError(token);
            DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenProvider.TOKEN_PREFIX, ""));
            String userId = decodedJWT.getSubject();
            if (userId != null) {
                UserDto userDto = userService.select(userId);

                    if(userDto != null) {
                        JwtUserDetails userDetails = new JwtUserDetails(userDto.toEntity());

                        UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(userId,
                                null, userDetails.getAuthorities());
                        jwtAuthentication.setDetails(userDetails);
                        return jwtAuthentication;
                    }
            }
            return null;
        }
        return null;
    }
}