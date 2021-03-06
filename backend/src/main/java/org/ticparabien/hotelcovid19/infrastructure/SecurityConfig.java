package org.ticparabien.hotelcovid19.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.ticparabien.hotelcovid19.controller.Routes;


@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            AuthenticationEntryPoint authenticationEntryPoint = new ApiAuthenticationEntryPoint();
            http.antMatcher("/api/**")
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and().csrf().disable();
        }
    }

    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return new CustomAuthenticationProvider();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage(Routes.login)
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .csrf()
                    .disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/resources/**");
        }
    }
}

