package org.prd.securityexample.model.dto.auth;

public record AuthenticationResponse(
    String jwt,
    String refresh_token
) {
}