package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;


public interface UserService {

    public User findUserById(Long userid)throws UserException;
    public User findUserProfileByJwt(String jwt)throws UserException;

    String findUserRoleById(Long userId) throws UserException;


}
