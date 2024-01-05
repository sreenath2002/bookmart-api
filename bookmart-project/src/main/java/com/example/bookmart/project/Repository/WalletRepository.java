package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
