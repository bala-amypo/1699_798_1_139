
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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "mySecretKey"; // Replace with your secure key
    private final long JWT_EXPIRATION = 24 * 60 * 60 * 1000; // 1 day

    // Generate JWT token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Must-have method for your filter
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

