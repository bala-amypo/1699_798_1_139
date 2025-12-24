// package com.example.demo.repository;

// import com.example.demo.entity.User;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.Optional;

// public interface UserRepository extends JpaRepository<User, Long> {

//     Optional<User> findByEmail(String email);
// }

package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Check if a username already exists
    boolean existsByUsername(String username);

    // Find a user by username
    Optional<User> findByUsername(String username);
}
