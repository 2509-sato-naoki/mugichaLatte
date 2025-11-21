package com.example.mugichaLatte.repository;

import com.example.mugichaLatte.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u ORDER BY u.id ASC")
    List<User> findAllUserOrderById();
    User findByAccountAndPassword(String account, String password);
    User findByAccount(String account);
}
