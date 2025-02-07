package org.prd.securityexample.service;

import org.prd.securityexample.model.entity.RefreshToken;

import java.util.Optional;

public interface TokenService {
    Optional<RefreshToken> findByJti(String jti);
    RefreshToken save(RefreshToken refreshToken);
}