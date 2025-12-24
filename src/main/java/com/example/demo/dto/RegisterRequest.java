// package com.example.demo.dto;

// public class RegisterRequest {

//     private String email;
//     private String password;
//     private String role;

//     public String getEmail() {
//         return email;
//     }
 
//     public void setEmail(String email) {
//         this.email = email;
//     }
 
//     public String getPassword() {
//         return password;
//     }
 
//     public void setPassword(String password) {
//         this.password = password;
//     }
 
//     public String getRole() {
//         return role;
//     }
 
//     public void setRole(String role) {
//         this.role = role;
//     }
// }

package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
