package org.prd.securityexample.model.dto.auth;

public record AuthenticationRequest(
    String username,
    String password
) {
}