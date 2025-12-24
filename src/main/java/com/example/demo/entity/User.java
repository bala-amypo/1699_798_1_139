// package com.example.demo.entity;

// import jakarta.persistence.*;
// import lombok.Data;
// import java.util.Set;

// @Entity
// @Data
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String username;
//     private String password;
//     private String email;

//     @ElementCollection(fetch = FetchType.EAGER)
//     private Set<String> roles;

//     public User() {
//     }

// public User(String username, String password, Set<String> roles) {
//     this.username = username;
//     this.password = password;
//     this.roles = roles;
// }

// }

package com.example.demo.entity;

import lombok.Data;
import java.util.Set;

@Data
public class User {

    private String username;
    private String password;
    private String email;
    private Set<String> roles;

    public User() {} // default constructor

    public User(String username, String password, String email, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
