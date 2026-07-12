package com.dicedicebaby.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Argon2PasswordEncoder(16, 32, 1, 19456, 2);
  }

  // Need to be modified after development (disable and permitAll !)
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
      throws Exception {
    MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

    http.cors(withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth
                    // Keep explicitly builder MVC for Swagger
                    .requestMatchers(mvc.pattern("/v3/api-docs"), mvc.pattern("/v3/api-docs/**"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/swagger-ui/**"), mvc.pattern("/swagger-ui.html"))
                    .permitAll()
                    // Keep the rest open for dev
                    .anyRequest()
                    .permitAll())
        .httpBasic(withDefaults());

    return http.build();
  }
}
