package com.svj.service;

import com.svj.entity.UserCredential;
import com.svj.repository.UserCredentialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserCredentialRepository repository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    AuthService(UserCredentialRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.repository= repository;
        this.passwordEncoder= passwordEncoder;
        this.jwtService= jwtService;
    }

    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "USer added to the system!";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
