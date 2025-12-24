package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    public User() {
    }

public User(String username, String password, Set<String> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
}

}
