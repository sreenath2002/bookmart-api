package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.User;
import com.example.bookmart.project.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}
