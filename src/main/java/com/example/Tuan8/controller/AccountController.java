package com.example.Tuan8.controller;

import com.example.Tuan8.dto.LoginDTO;
import com.example.Tuan8.exception.ErrorResponse;
import com.example.Tuan8.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse rs) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            String token = jwtService.generateToken(loginDTO.getUsername());

            return ResponseEntity.ok(
                    "login:" + token
            );
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErrorResponse(HttpStatus.FORBIDDEN.value(), "username or password is incorrect")
            );
        }
        catch (Exception e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }
}
