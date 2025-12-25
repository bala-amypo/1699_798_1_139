// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.util.Set;

// @Entity
// @Table(name = "users")
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;



//     @Column(nullable = false, unique = true)
//     private String email;

//     @Column(nullable = false)
//     private String password;

//     private String name;

//     @ElementCollection(fetch = FetchType.EAGER)
//     @CollectionTable(
//             name = "user_roles",
//             joinColumns = @JoinColumn(name = "user_id")
//     )
//     @Column(name = "role")
//     private Set<String> roles;

//     public User() {
//     }

//     // ✅ REQUIRED BY TESTS
//     public User(String email, String password, Set<String> roles) {
//         this.email = email;
//         this.password = password;
//         this.roles = roles;
//     }

//     public User(String email, String password, String name, Set<String> roles) {
//         this.email = email;
//         this.password = password;
//         this.name = name;
//         this.roles = roles;
//     }

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

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

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public Set<String> getRoles() {
//         return roles;
//     }

//     public void setRoles(Set<String> roles) {
//         this.roles = roles;
//     }
// }


package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    // ✅ FIX: created_at column
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // ✅ REQUIRED: No-arg constructor (JPA)
    public User() {
    }

    // ✅ REQUIRED: Constructor used by TESTS
    public User(String email, String password, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    // ✅ OPTIONAL: Full constructor
    public User(Long id, String email, String password, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    // ✅ AUTO-SET created_at before insert
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // getters & setters
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
 
    public Set<String> getRoles() {
        return roles;
    }
 
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

