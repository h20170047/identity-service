package com.svj.controller;

import com.svj.dto.AuthRequest;
import com.svj.entity.UserCredential;
import com.svj.service.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService service;
    private AuthenticationManager authenticationManager;

    AuthController(AuthService service, AuthenticationManager authenticationManager){
        this.service= service;
        this.authenticationManager= authenticationManager;
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        }else{
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Token is valid";
    }
}
