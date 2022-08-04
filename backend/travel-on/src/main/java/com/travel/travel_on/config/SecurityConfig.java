package com.travel.travel_on.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PERMIT_ALL_URL_ARRAY = {
            // swagger
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",

            // controller
            "/user/login",
            "/user/regist",
            "/user/idcheck",
            "/user/nickcheck",
            "/user/email",
            "/notice/page",
            "/notice/faq",
            "/notice/detail/**",
    };

    private static final String[] PERMIT_ADMIN_URL_ARRAY = {
            // controller
            "/qna/admin/**",
            "/notice/**",
    };

    private static final String[] PERMIT_ADMIN_AND_USER_URL_ARRAY = {
            // controller
            "/qna/detail/**",
            "/qna/modify",
    };

    private static final String[] PERMIT_USER_URL_ARRAY = {
            // controller
            "/qna/**",
            "/user/**",
            "/alarm/**",
            "/plan/**",
            "/videochat/**",
    };




    @Override
    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers(
//                        "/favicon.ico"
//                        ,"/error"
//                        ,"/swagger-ui/**"
//                        ,"/swagger-resources/**"
//                        ,"/v3/api-docs/**"
//                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // swagger API 호출시 403 에러 발생 방지
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_URL_ARRAY).permitAll()
                .antMatchers(PERMIT_ADMIN_URL_ARRAY).access("hasRole('ADMIN')") // ADMIN만 가능
                .antMatchers(PERMIT_ADMIN_AND_USER_URL_ARRAY).access("hasRole('ADMIN') or hasRole('USER')") //ADMIN or SYS
                .antMatchers(PERMIT_USER_URL_ARRAY).hasRole("USER") // 페이지 유저만 가능
                .anyRequest().authenticated();
    }

}
