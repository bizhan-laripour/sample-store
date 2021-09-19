package org.egs.sampleStore.security;


import org.egs.sampleStore.service.UserService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private AutherizationHeader autherizationHeader;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider , UserService userService, AutherizationHeader autherizationHeader) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.autherizationHeader = autherizationHeader;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, userService , autherizationHeader);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}