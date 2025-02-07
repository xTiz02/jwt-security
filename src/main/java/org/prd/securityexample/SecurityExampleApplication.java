package org.prd.securityexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityExampleApplication implements CommandLineRunner {

    @Autowired
    public PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SecurityExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String password = "12345";
        String encodedPassword = passwordEncoder.encode(password);

        System.out.println("Password: " + password);
        System.out.println("Encoded Password: " + encodedPassword);
    }
}