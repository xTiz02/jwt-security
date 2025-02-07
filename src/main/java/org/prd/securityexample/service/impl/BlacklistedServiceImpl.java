package org.prd.securityexample.service.impl;

import org.prd.securityexample.model.entity.BlacklistedToken;
import org.prd.securityexample.model.entity.RefreshToken;
import org.prd.securityexample.model.repo.BlacklistRepository;
import org.prd.securityexample.service.BlacklistedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlacklistedServiceImpl implements BlacklistedService {

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Override
    public BlacklistedToken save(RefreshToken token) {
        BlacklistedToken bl = BlacklistedToken.builder()
                .jti(token.getJti())
                .refreshToken(token)
                .blacklistedAt(LocalDateTime.now())
                .build();

        return blacklistRepository.save(bl);
    }
}