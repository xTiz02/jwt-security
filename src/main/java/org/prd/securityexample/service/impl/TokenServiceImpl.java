package org.prd.securityexample.service.impl;

import org.prd.securityexample.model.entity.RefreshToken;
import org.prd.securityexample.model.repo.BlacklistRepository;
import org.prd.securityexample.model.repo.TokenRepository;
import org.prd.securityexample.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public Optional<RefreshToken> findByJti(String jti) {
        return tokenRepository.findByJti(jti);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return tokenRepository.save(refreshToken);
    }

}