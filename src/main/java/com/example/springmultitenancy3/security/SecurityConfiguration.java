package com.example.springmultitenancy3.security;

import com.example.springmultitenancy3.security.filter.TestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final TestFilter testFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
          .csrf().disable().authorizeHttpRequests()
          .requestMatchers("/test").permitAll()
          .anyRequest().authenticated()
    ;

    http.addFilterBefore(testFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
