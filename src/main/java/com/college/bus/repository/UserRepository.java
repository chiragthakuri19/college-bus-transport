package com.college.bus.repository;

import com.college.bus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = ?1")
    List<User> findByRole(User.UserRole role);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = ?1")
    Boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = ?1")
    Boolean existsByEmail(String email);
} 