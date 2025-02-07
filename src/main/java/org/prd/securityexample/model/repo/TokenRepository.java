package org.prd.securityexample.model.repo;

import org.prd.securityexample.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByJti(String jti);
}