package com.example.ApiMessenger.Rest;

import com.example.ApiMessenger.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CheckAccount {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CheckAccount(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/check_account")
    public ResponseEntity<String> login(@RequestBody Users user){
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(), user.getPassword());
        try {
            Authentication authenticationRespone = authenticationManager.authenticate(authenticationRequest);
            if(authenticationRespone.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authenticationRespone);
            }
            return new ResponseEntity<>("Đăng nhập thành công", HttpStatus.OK);
        }catch (AuthenticationException ex){
            throw new RuntimeException(ex);
        }
    }
}
