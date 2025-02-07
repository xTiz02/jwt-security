package org.prd.securityexample.service;

import org.prd.securityexample.model.entity.BlacklistedToken;
import org.prd.securityexample.model.entity.RefreshToken;

public interface BlacklistedService {
    BlacklistedToken save(RefreshToken token);
}