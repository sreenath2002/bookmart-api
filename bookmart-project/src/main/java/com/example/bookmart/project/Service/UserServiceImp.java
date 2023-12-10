package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Repository.UserRepository;
import com.example.bookmart.project.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserDetailsService {

    UserRepository userRepository;
    public  UserServiceImp (UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByEmail(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("user not fount with email" + username);
        }

        List<GrantedAuthority>authorities=new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }


    public String findUserRoleById(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException("Invalid user ID");
        }

        User user = findUserById(userId);

        if (user != null && user.getRole() != null && !user.getRole().isEmpty()) {
            return user.getRole();
        } else {
            // You can throw a more specific exception or return a default role here
            throw new UserException("User role not found or empty for user ID: " + userId);
        }
    }


    public User findUserById(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException("Invalid user ID");
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserException("User not found for ID: " + userId);
        }

        return optionalUser.get();
    }
}

