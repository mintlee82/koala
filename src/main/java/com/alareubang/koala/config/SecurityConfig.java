package com.alareubang.koala.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
//	private final PrincipalDetailService principalDetailService;
	
	@Bean
    public PasswordEncoder passwordEncoder(){ //비밀번호 암호화를 위해 사용 시큐리티는 비밀번호가 암호화 되있어야 사용가능하다
        return new BCryptPasswordEncoder();   //회원가입할때 쓰면된다.
    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        	.cors()
        	.and()
            .authorizeHttpRequests().requestMatchers("/auth/**", "/js/**", "/css/**").permitAll()
    	    .anyRequest().authenticated()
            .and()
            	.headers().frameOptions().disable()
            .and()
                .formLogin()
            	.loginPage("/auth/loginForm")
            	.loginProcessingUrl("/auth/loginProc")
            	.defaultSuccessUrl("/")
            	;
        
        http.sessionManagement()
        	.maximumSessions(1) //세션 최대 허용 수
        	.maxSessionsPreventsLogin(false); // false이면 중복 로그인하면 이전 로그인이 풀린다.

//        http.logout()
//        	.logoutUrl("/logout")
//        	.logoutSuccessUrl("/auth/loginForm");
        return http.build();
    }
}
