package org.egs.sampleStore.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egs.sampleStore.dto.UserDto;
import org.egs.sampleStore.entity.User;
import org.egs.sampleStore.enums.BlockStatus;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;


    private UserService userService;


    private AutherizationHeader autherizationHeader;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider , UserService userService , AutherizationHeader autherizationHeader) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.autherizationHeader = autherizationHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        if(token != null) {
            String username = autherizationHeader.getUsernameFromToken(token);
            User userDto = userService.loadUserByUsername(username);
            if (userDto.getBlockStatus() == BlockStatus.BLOCKED) {
                throw new CustomException("you are blocked", HttpStatus.FORBIDDEN);
            }
        }

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getStatus().value(), ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}