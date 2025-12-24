
// package com.example.demo.security;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;
// import java.util.List;
// import java.util.Set;

// @Component
// public class JwtTokenProvider {

//     private static final String SECRET =
//             "mysecretkeymysecretkeymysecretkey123456"; // 32+ chars

//     private static final long EXPIRATION = 86400000; // 1 day

//     private Key getSigningKey() {
//         return Keys.hmacShaKeyFor(SECRET.getBytes());
//     }

//     // ✅ USED BY AuthServiceImpl & TESTS
//     public String createToken(Long userId, String email, Set<String> roles) {
//         Claims claims = Jwts.claims().setSubject(email);
//         claims.put("userId", userId);
//         claims.put("roles", roles);

//         Date now = new Date();
//         Date expiry = new Date(now.getTime() + EXPIRATION);

//         return Jwts.builder()
//                 .setClaims(claims)
//                 .setIssuedAt(now)
//                 .setExpiration(expiry)
//                 .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder()
//                     .setSigningKey(getSigningKey())
//                     .build()
//                     .parseClaimsJws(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }

//     public String getEmail(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     public Long getUserId(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .get("userId", Long.class);
//     }

//     @SuppressWarnings("unchecked")
//     public Set<String> getRoles(String token) {
//         List<String> roles =
//                 (List<String>) Jwts.parserBuilder()
//                         .setSigningKey(getSigningKey())
//                         .build()
//                         .parseClaimsJws(token)
//                         .getBody()
//                         .get("roles");

//         return Set.copyOf(roles);
//     }
// }

// package com.example.demo.security;

// import io.jsonwebtoken.*;
// import org.springframework.stereotype.Component;

// import java.util.Date;
// import java.util.List;
// import java.util.Set;

// @Component
// public class JwtTokenProvider {

//     private static final String SECRET_KEY = "secret123";
//     private static final long EXPIRATION = 86400000; // 1 day

//     // ================= TOKEN CREATION =================
//     public String createToken(Long userId, String email, Set<String> roles) {

//         return Jwts.builder()
//                 .setSubject(email)
//                 .claim("userId", userId)
//                 .claim("roles", roles)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
//                 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                 .compact();
//     }

//     // ================= VALIDATION =================
//     public boolean validateToken(String token) {
//         try {
//             Jwts.parser()
//                     .setSigningKey(SECRET_KEY)
//                     .parseClaimsJws(token);
//             return true;
//         } catch (JwtException | IllegalArgumentException e) {
//             return false;
//         }
//     }

//     // ================= REQUIRED BY FILTER =================
//     public String getEmail(String token) {
//         return getClaims(token).getSubject();
//     }

//     // ================= REQUIRED BY TEST =================
//     public Long getUserId(String token) {
//         Object value = getClaims(token).get("userId");
//         if (value instanceof Integer) {
//             return ((Integer) value).longValue();
//         }
//         return (Long) value;
//     }

//     // ================= REQUIRED BY TEST =================
//     @SuppressWarnings("unchecked")
//     public Set<String> getRoles(String token) {
//         return Set.copyOf(
//                 (List<String>) getClaims(token).get("roles")
//         );
//     }

//     // ================= INTERNAL =================
//     private Claims getClaims(String token) {
//         return Jwts.parser()
//                 .setSigningKey(SECRET_KEY)
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }


package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

public class JwtTokenProvider {

    // Secure 256-bit key for HS256
    private final SecretKey secretKey;

    // Token validity in milliseconds (e.g., 1 hour)
    private final long validityInMs = 3600000;

    public JwtTokenProvider() {
        // Generate secure 256-bit key
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * Create JWT token with email, userId, and roles
     */
    public String createToken(String email, Long userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Get email (subject) from token
     */
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Get userId from token
     */
    public Long getUserId(String token) {
        Object userId = getClaims(token).get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return (Long) userId;
    }

    /**
     * Get roles from token
     */
    public List<String> getRoles(String token) {
        Object rolesObj = getClaims(token).get("roles");
        if (rolesObj instanceof List<?>) {
            return ((List<?>) rolesObj).stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Helper method to parse claims
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
