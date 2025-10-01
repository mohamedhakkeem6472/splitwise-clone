package com.example.splitwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.splitwise.model.UserBalance;
import com.example.splitwise.model.Group;
import com.example.splitwise.model.User;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    Optional<UserBalance> findByGroupAndUserAndCounterUser(Group group, User user, User counterUser);
    Optional<UserBalance> findByUserAndCounterUser(User user, User counterUser);
}
