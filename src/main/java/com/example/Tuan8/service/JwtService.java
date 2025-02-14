package com.example.Tuan8.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    public String generateAccessTokenOrRefreshToken(String username, String key, int expiration) {
        try {
            JWSSigner signer = new MACSigner(key);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(new Date(new Date().getTime() + expiration))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Lỗi JWT", e);
        }
    }

    public void SetCookie(HttpServletResponse rs, String token){
        Cookie cookie = new Cookie("refeshToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(jwtRefreshExpirationMs);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        rs.addCookie(cookie);
    }

    public boolean validateToken(String token, String key) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(key.getBytes());

            return signedJWT.verify(verifier) &&
                    new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
        } catch (Exception e) {
            return false;
        }
    }

    public void removeRefreshTokenCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("refeshToken", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }

    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refeshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
