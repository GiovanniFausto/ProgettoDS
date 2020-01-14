package com.dslab.videomanagementservice;

import com.dslab.videomanagementservice.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VideoServerDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Utente non trovato");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                getEncoder().encode(user.getPassword()),
                true,
                true,
                true,
                true,
                getAuth(user.getRoles())
        );

    }

    @Bean
    private BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    private List<GrantedAuthority> getAuth(List<String> roles){
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (final String role : roles)
            authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }
}
