package com.sparta.icticket.common.config;

import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.security.UserDetailsServiceImpl;
import com.sparta.icticket.common.security.jwt.*;
import com.sparta.icticket.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter(){
        return new JwtExceptionFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());

        // jwt 사용 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/admin").permitAll() //테스트용 입니다. 추후에 삭제해야 합니다.
                        .requestMatchers(HttpMethod.GET, "/performances/**").permitAll() //테스트용 입니다. 추후에 변경해야 합니다.
                        .requestMatchers(HttpMethod.GET, "/banners/**").permitAll()
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/performances/{performanceId}/likes-count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/performances/{performanceId}/comments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/performances/{performanceId}/sessions").permitAll()
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/performances/{performanceId}/sessions/{sessionId}/seat-count").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
        );

        http.exceptionHandling(exception ->
                exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
        );

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }
}
