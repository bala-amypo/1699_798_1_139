// package com.example.demo.security;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;
// import java.util.Set;

// @Component
// public class JwtTokenProvider {

//     private static final String SECRET =
//             "mysecretkeymysecretkeymysecretkey123456"; // 32+ chars

//     private static final long EXPIRATION = 86400000; // 1 day

//     private Key getSigningKey() {
//         return Keys.hmacShaKeyFor(SECRET.getBytes());
//     }

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
//         } catch (JwtException | IllegalArgumentException ex) {
//             return false;
//         }
//     }

//     public Long getUserId(String token) {
//         Claims claims = Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();

//         return claims.get("userId", Long.class);
//     }

//     @SuppressWarnings("unchecked")
//     public Set<String> getRoles(String token) {
//         Claims claims = Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();

//         return Set.copyOf((java.util.List<String>) claims.get("roles"));
//     }

//     public String getEmail(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }
// }

package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "mySecretKey123456";
    private final long jwtExpirationMs = 3600000; // 1 hour

    // Create token
   public String createToken(long userId, String email, Set<String> roles) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationMs);

    return Jwts.builder()
            .setSubject(Long.toString(userId))
            .claim("email", email)
            .claim("roles", roles)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
}
  public String getEmail(String token) {
    return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .get("email", String.class);
}


    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Get userId from token
    public Long getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    // ✅ Extract roles from token
    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (Set<String>) claims.get("roles");
    }
}
