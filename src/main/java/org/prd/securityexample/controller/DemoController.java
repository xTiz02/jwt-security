package org.prd.securityexample.controller;

import org.prd.securityexample.model.dto.auth.AuthenticationResponse;
import org.prd.securityexample.model.entity.User;
import org.prd.securityexample.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping("all")
    public List<User> all() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/admin")
    public String admin(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken){
        return "Admin";
    }

    @PreAuthorize("hasAuthority('ASSISTANT_ADMINISTRATOR')")
    @GetMapping("/assistant")
    public String assistant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken){
        return "Assistant";
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/customer")
    public String customer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken){
        return "Customer";
    }
}