package com.genieLogiciele.sprint.service;


import com.genieLogiciele.sprint.entities.UserApp;
import com.genieLogiciele.sprint.repo.UserAppRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey;
    private final UserAppRepo userAppRepo;

    public JwtService(UserAppRepo userAppRepo) {
        try {
            KeyGenerator kGen = KeyGenerator.getInstance("HmacSHA256");
            kGen.init(256); // Ensure key size is 256 bits
            SecretKey skey = kGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(skey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
        this.userAppRepo = userAppRepo;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        UserApp user = userAppRepo.findByEmail(userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());

        claims.put("id",user.getId());
        claims.put("nom",user.getFirstname()+" "+user.getLastname());
        return Jwts.builder()
                .claims(claims) // Add claims to the token
                .subject(userDetails.getUsername()) // Set the subject (e.g., username)
                .issuedAt(new Date(System.currentTimeMillis())) // Set issue date
                .expiration(new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000))) // Set expiration date
                .signWith(getKey()) // Sign the token with the secret key
                .compact(); // Build the token
    }

    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey()) // Set the signing key
                .build()
                .parseClaimsJws(token) // Parse the JWT token
                .getBody(); // Extract the claims
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
