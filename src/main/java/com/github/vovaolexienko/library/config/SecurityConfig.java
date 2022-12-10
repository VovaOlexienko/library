package com.github.vovaolexienko.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource, PasswordEncoder passwordEncoder) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, 'true' as enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, role from users where username = ?")
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/saveBook").hasRole("ADMIN")
                .antMatchers("/deleteBook").hasRole("ADMIN")
                .antMatchers("/author").hasRole("ADMIN")
                .antMatchers("/saveAuthor").hasRole("ADMIN")
                .antMatchers("/deleteAuthor").hasRole("ADMIN")
                .antMatchers("/genre").hasRole("ADMIN")
                .antMatchers("/saveGenre").hasRole("ADMIN")
                .antMatchers("/deleteGenre").hasRole("ADMIN")
                .antMatchers("/voting").authenticated()
                .antMatchers("/logout").authenticated()
                .antMatchers("/registration").not().authenticated()
                .antMatchers("/login").not().authenticated()
                .antMatchers("/signUp").not().authenticated()
                .antMatchers("/signIn").not().authenticated()
                .antMatchers("/**").permitAll()

                .and()
                .exceptionHandling().accessDeniedPage("/error")

                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/signIn")
                .defaultSuccessUrl("/book")
                .passwordParameter("password")
                .usernameParameter("username")

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/book")
                .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
                .invalidateHttpSession(true)

                .and()
                .build();
    }
}