package com.example.Tuan8.controller;

import com.example.Tuan8.dto.EmailDTO;
import com.example.Tuan8.dto.LoginDTO;
import com.example.Tuan8.dto.PasswordDTO;
import com.example.Tuan8.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        return authService.login(loginDTO, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return authService.logout(request, response);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/sendmail")
    public ResponseEntity<?> sendMail(@RequestBody EmailDTO emailDTO) {
        return authService.sendMail(emailDTO);
    }

    @PostMapping("/change-password/{accesstoken}")
    public ResponseEntity<?> changePassword(@PathVariable String accesstoken, @RequestBody PasswordDTO passwordDTO) {
        return authService.changePassword(accesstoken, passwordDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody PasswordDTO passwordDTO) {
        return authService.changePassword(request, passwordDTO);
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return authService.hello();
    }
}
