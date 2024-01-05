package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);



    @Query("SELECT COUNT(u) FROM User u WHERE u.showstatus = 'true'")
    Long countByShowstatusIsTrue();
}
