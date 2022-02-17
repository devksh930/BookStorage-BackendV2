package com.bookstoragev2.bookstorage.common.config;

import com.bookstoragev2.bookstorage.authentication.CustomUserDetailService;
import com.bookstoragev2.bookstorage.authentication.JwtAccessDeniedHandler;
import com.bookstoragev2.bookstorage.authentication.JwtAuthenticationEntryPointHandler;
import com.bookstoragev2.bookstorage.authentication.JwtFilter;
import com.bookstoragev2.bookstorage.common.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter filter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPointHandler authenticationEntryPointHandler;
    private final CustomUserDetailService userDetailService;
    private final CorsProperties corsProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .headers().frameOptions().sameOrigin()
                .and()
                    .cors()
                .and()
                    .csrf().disable()
                    .httpBasic().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .antMatchers("/users").permitAll()
                        .antMatchers("/auth").permitAll()
                        .antMatchers("/users/me").authenticated()
                .and()
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPointHandler)
                    .accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
