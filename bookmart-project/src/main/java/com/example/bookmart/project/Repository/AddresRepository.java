package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddresRepository extends JpaRepository<Address,Long> {
    List<Address> findByUserId(Long userId);
}
