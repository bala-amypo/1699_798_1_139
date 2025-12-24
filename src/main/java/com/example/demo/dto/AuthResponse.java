// package com.example.demo.dto;

// public class AuthResponse {

//     private String token;

//     public AuthResponse() {}

//     public AuthResponse(String token) {
//         this.token = token;
//     }

//     public String getToken() {
//         return token;
//     }
 
//     public void setToken(String token) {
//         this.token = token;
//     }
// }

package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String email;
    private String token;
    private Set<String> roles;
}