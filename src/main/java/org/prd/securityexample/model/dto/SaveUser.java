package org.prd.securityexample.model.dto;

public record SaveUser(
    String username,
    String password,
    String repeatedPassword,
    String email,
    Integer max_role
) {
}