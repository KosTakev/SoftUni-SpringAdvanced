package com.example.security.config;

import com.example.security.model.enums.UserRoleEnum;
import com.example.security.repository.UserRepository;
import com.example.security.service.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    //Here we have to expose 3 things:
    //1. PasswordEncoder
    //2. SecurityFilterChain
    //3. UserDetailsService

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http
        //define which requests are allowed and which not:
                .authorizeRequests()
                //everyone could download this static resources(which are css, js and images)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                //everyone could access this pages:
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                //only users with role MODERATOR can access this page:
                .antMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name())
                //only user with ADMIN role can access this page:
                .antMatchers("/pages/admins").hasRole(UserRoleEnum.ADMIN.name())
                //only logged-in users can access other pages:
                .anyRequest().authenticated()
              //generating the login page:
              .and()
                //configuration of the login form:
                .formLogin()
                //the custom login form:
                .loginPage("/users/login")
                //the name of the username form field (it has to match with the name in the html form!!!):
                .usernameParameter(UsernamePasswordAuthenticationFilter
                        .SPRING_SECURITY_FORM_USERNAME_KEY)
                //the name of the password form field (it has to match with the name in the html form!!!):
                .passwordParameter(UsernamePasswordAuthenticationFilter
                        .SPRING_SECURITY_FORM_PASSWORD_KEY)
                //the page shown if the login is successful (the home page):
                .defaultSuccessUrl("/")
                //if the login isn't successful:
                //todo: controller for "/users/login-error"
                .failureForwardUrl("/users/login-error")
              //generating the logout page:
              .and()
                //same as for login! but using logout():
                .logout()
                .logoutUrl("/users/logout")
                //invalidating the session and deleting the cookies of the logout user:
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");

       //build() calls SecurityFilterChain:
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {

        return new AppUserDetailsService(userRepository);
    }

}
