package org.prd.securityexample.controller;

import org.prd.securityexample.model.entity.User;
import org.prd.securityexample.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    public UserRepository userRepository;

    @RequestMapping("all")
    public List<User> all() {
        return userRepository.findAll();
    }
}