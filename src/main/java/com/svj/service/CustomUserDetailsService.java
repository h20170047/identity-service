package com.svj.service;

import com.svj.config.CustomUserDetails;
import com.svj.entity.UserCredential;
import com.svj.repository.UserCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserCredentialRepository repository;

    CustomUserDetailsService(UserCredentialRepository repository){
        this.repository= repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential= repository.findByName(username);
        return credential.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found with name: "+username));
    }
}
