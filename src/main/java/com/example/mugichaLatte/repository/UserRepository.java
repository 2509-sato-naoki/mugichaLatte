package com.example.mugichaLatte.repository;

import com.example.mugichaLatte.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);
    Optional<User> findByAccount(String account);
}
