package org.prd.securityexample.service;

import org.prd.securityexample.model.dto.SaveUser;
import org.prd.securityexample.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    User createOne(SaveUser user);
    User disableOneById(Long id);
    User updateOneById(Long id, SaveUser user);
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneById(Long id);
    Page<User> findAll(Pageable pageable);
}