package com.capstone.mylittlelibrarybackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u From User u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);
}
