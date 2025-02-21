package org.prd.securityexample.service.auth;

import org.prd.securityexample.model.dto.auth.AuthenticationRequest;
import org.prd.securityexample.model.dto.auth.AuthenticationResponse;
import org.prd.securityexample.model.entity.BlacklistedToken;
import org.prd.securityexample.model.entity.RefreshToken;
import org.prd.securityexample.model.entity.User;
import org.prd.securityexample.service.BlacklistedService;
import org.prd.securityexample.service.TokenService;
import org.prd.securityexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BlacklistedService blacklistedService;

    public AuthenticationResponse login(AuthenticationRequest autRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                autRequest.username(), autRequest.password()
        );

        authenticationManager.authenticate(authentication);

        User user = userService.findOneByUsername(autRequest.username()).orElseThrow(() -> new RuntimeException("User not found"));
        String jwt = jwtService.generateToken(user, generateJwtAccessExtraClaims(user));
        String rfJwt = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(jwt,rfJwt);
    }

    public boolean validateToken(String jwt) {

        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        String jti = jwtService.extractJti(refreshToken);
        verificateAndReturnRefreshToken(jti);
        String username = jwtService.extractUsername(refreshToken);
        User user = userService.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        String jwt = jwtService.generateToken(user, generateJwtAccessExtraClaims(user));
        return new AuthenticationResponse(jwt,refreshToken);
    }

    public AuthenticationResponse createRefreshToken(String rf) {
        String username = jwtService.extractUsername(rf);
        String jti = jwtService.extractJti(rf);
        RefreshToken oldToken = verificateAndReturnRefreshToken(jti);

        User user = userService.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        String newRfJwt = jwtService.generateRefreshToken(user);
        String newAccessJwt = jwtService.generateToken(user, generateJwtAccessExtraClaims(user));

        blacklistedService.save(oldToken); // Blacklist old token
        tokenService.save(jwtService.rfJwtToRefreshToken(newRfJwt)); // Save new Refresh token
        return new AuthenticationResponse(newAccessJwt,newRfJwt);
    }

    private RefreshToken verificateAndReturnRefreshToken(String jti) {
        RefreshToken token = tokenService.findByJti(jti).orElseThrow(() -> new RuntimeException("Refresh Token not found"));
        if(token.getBlacklistedToken()!=null){
            throw new RuntimeException("Refresh Token is blacklisted");
        }
        return token;
    }


    private Map<String, Object> generateJwtAccessExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",user.getUsername());
        extraClaims.put("roles",user.getAllRolesArray());
        extraClaims.put("authorities",user.getAllPermissionsArray());
        return extraClaims;
    }
}