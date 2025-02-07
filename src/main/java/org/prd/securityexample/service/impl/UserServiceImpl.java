package org.prd.securityexample.service.impl;

import org.prd.securityexample.model.dto.SaveUser;
import org.prd.securityexample.model.entity.User;
import org.prd.securityexample.model.repo.UserRepository;
import org.prd.securityexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private UserRepository userRepo;

    @Override
    public User createOne(SaveUser user) {
        return null;
    }

    @Override
    public User disableOneById(Long id) {
        return null;
    }

    @Override
    public User updateOneById(Long id, SaveUser user) {
        return null;
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<User> findOneById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    private void validatePassword(SaveUser dto) {
        if(dto.password() == null || dto.repeatedPassword() == null){
            throw new RuntimeException("Passwords don't match");
        }
        if(!dto.password().equals(dto.repeatedPassword())){
            throw new RuntimeException("Passwords don't match");
        }
    }
}