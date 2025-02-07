package org.prd.securityexample.controller;

import jakarta.validation.Valid;
import org.prd.securityexample.model.dto.auth.AuthenticationRequest;
import org.prd.securityexample.model.dto.auth.AuthenticationResponse;
import org.prd.securityexample.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;


    @PreAuthorize("permitAll")
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam(name = "token") String jwt){
        boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    //    @CrossOrigin
    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest){

        AuthenticationResponse rsp = authService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken){
        return authService.refreshToken(refreshToken);
    }
    @PreAuthorize("hasAuthority('CREATE_ONE_REFRESH_TOKEN')")
    @PostMapping("/new-refresh-token")
    public AuthenticationResponse newRefreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken){
        return authService.createRefreshToken(refreshToken);
    }
}