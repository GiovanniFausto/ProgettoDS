package com.dslab.videomanagementservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class VideoServiceConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    VideoServerDetailsService videoDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(videoDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/vms/register/").permitAll()//chiunque si può registrare
                .antMatchers(HttpMethod.POST, "/vms/videos/").hasAuthority("AUTHOR")//per fare la prima post deve essere loggato
                .antMatchers(HttpMethod.POST, "/vms/videos/{id}").hasAuthority("AUTHOR")//per caricare un video deve essere loggato
                .anyRequest().permitAll()//per le varie get non deve essere loggato
                .and()
                .csrf().disable();

    }
}