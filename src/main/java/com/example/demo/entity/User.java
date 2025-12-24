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

import java.util.Set;

public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Set<String> roles;

    public User() {
    }

    // ✅ REQUIRED BY TESTS
    public User(String email, String password, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    // ✅ ALREADY USED SOMEWHERE ELSE
    public User(String email, String password, String name, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}


  