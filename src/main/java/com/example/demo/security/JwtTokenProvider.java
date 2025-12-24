
// package com.example.demo.security;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.nio.charset.StandardCharsets;
// import java.security.Key;
// import java.util.Date;
// import java.util.List;
// import java.util.Set;

// @Component
// public class JwtTokenProvider {

//     // ✅ 256-bit+ SECRET (MANDATORY)
//     private static final String SECRET =
//             "this_is_a_very_secure_secret_key_for_hs256_algorithm_123456";

//     private static final long EXPIRATION = 86400000; // 1 day

//     private final Key key = Keys.hmacShaKeyFor(
//             SECRET.getBytes(StandardCharsets.UTF_8)
//     );

//     // ================= TOKEN CREATION =================
//     public String createToken(Long userId, String email, Set<String> roles) {

//         return Jwts.builder()
//                 .setSubject(email)
//                 .claim("userId", userId)
//                 .claim("roles", roles)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     // ================= VALIDATION =================
//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder()
//                     .setSigningKey(key)
//                     .build()
//                     .parseClaimsJws(token);
//             return true;
//         } catch (JwtException | IllegalArgumentException e) {
//             return false;
//         }
//     }

//     // ================= REQUIRED =================
//     public String getEmail(String token) {
//         return getClaims(token).getSubject();
//     }

//     public Long getUserId(String token) {
//         Object value = getClaims(token).get("userId");
//         if (value instanceof Integer) {
//             return ((Integer) value).longValue();
//         }
//         return (Long) value;
//     }

//     @SuppressWarnings("unchecked")
//     public Set<String> getRoles(String token) {
//         return Set.copyOf((List<String>) getClaims(token).get("roles"));
//     }

//     // ================= INTERNAL =================
//     private Claims getClaims(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }
package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; // Your JWT utility class
    private final UserDetailsService userDetailsService;

    // Public URLs that don't require authentication
    private static final List<String> PUBLIC_URLS = List.of(
            "/auth",
            "/api/auth",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/v3/api-docs"
    );

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip JWT check for public URLs
        if (PUBLIC_URLS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT from Authorization header
        String token = getJwtFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
