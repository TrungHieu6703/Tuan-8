package com.example.Tuan8.service;

import com.example.Tuan8.dto.EmailDTO;
import com.example.Tuan8.dto.LoginDTO;
import com.example.Tuan8.dto.PasswordDTO;
import com.example.Tuan8.exception.ErrorResponse;
import com.example.Tuan8.model.User;
import com.example.Tuan8.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InMemoryTokenBlacklist inMemoryTokenBlacklist;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${sharedSecret}")
    private String sharedSecretKey;

    @Value("${REFRESH_TOKEN_KEY}")
    private String refreshTokenKey;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    public ResponseEntity<?> login(LoginDTO loginDTO, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            String accessToken = jwtService.generateAccessTokenOrRefreshToken(loginDTO.getUsername(), sharedSecretKey, jwtExpirationMs);
            String refreshToken = jwtService.generateAccessTokenOrRefreshToken(loginDTO.getUsername(), refreshTokenKey, jwtRefreshExpirationMs);

            jwtService.SetCookie(response, refreshToken);

            return ResponseEntity.ok("login:" + accessToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErrorResponse(HttpStatus.FORBIDDEN.value(), "username or password is incorrect")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        jwtService.removeRefreshTokenCookie(request, response);
        String accessToken = jwtService.getJwtFromRequest(request);
        inMemoryTokenBlacklist.addToBlacklist(accessToken);
        return ResponseEntity.ok("Logged out successfully");
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtService.getRefreshToken(request);
        if (refreshToken != null) {
            boolean tokenValidate = jwtService.validateToken(refreshToken, refreshTokenKey);
            if (tokenValidate) {
                String username = jwtService.getUsernameFromToken(refreshToken);
                if (username != null) {
                    return ResponseEntity.ok(jwtService.generateAccessTokenOrRefreshToken(username, sharedSecretKey, jwtExpirationMs));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error getting username from token");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh token");
        }
    }

    public ResponseEntity<?> sendMail(EmailDTO emailDTO) {
        try {
            Boolean hasEmail = userRepo.existsByEmail(emailDTO.getEmail());
            if (!hasEmail) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("email not found");
            }
            User user = userRepo.findByEmail(emailDTO.getEmail()).orElseThrow(
                    () -> new EntityNotFoundException("Can't find email")
            );
            String token = jwtService.generateAccessTokenOrRefreshToken(user.getUsername(), sharedSecretKey, jwtExpirationMs);
            mailService.sendEmail(emailDTO.getEmail(), token);
        } catch (MailException mailException) {
            System.out.println(mailException);
        }

        return ResponseEntity.ok("Congratulations! Your mail has been sent to the user.");
    }

    public ResponseEntity<?> changePassword(String accesstoken, PasswordDTO passwordDTO) {
        String username = jwtService.getUsernameFromToken(accesstoken);
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepo.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }

    public ResponseEntity<?> changePassword(HttpServletRequest request, PasswordDTO passwordDTO) {
        String token = jwtService.getJwtFromRequest(request);
        String username = jwtService.getUsernameFromToken(token);
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepo.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }

    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello Spring");
    }
}
