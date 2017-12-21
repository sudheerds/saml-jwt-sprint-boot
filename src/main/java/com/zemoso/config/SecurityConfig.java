package com.zemoso.config;

import com.zemoso.config.jwt.JwtAuthenticationFilter;
import com.zemoso.config.jwt.JwtAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author sudheerds
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Rest security configuration for /api/
     */
    @Configuration
    @Order(1)
    public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

        private static final String apiMatcher = "/api/**";

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.addFilterBefore(new JwtAuthenticationFilter(apiMatcher, super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);

            http.antMatcher(apiMatcher).authorizeRequests()
                    .anyRequest()
                    .authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(new JwtAuthenticationProvider());
        }
    }

    /**
     * Saml security config
     */
    @Configuration
    @Import(SamlSecurityConfig.class)
    public static class SamlConfig {

    }

}
