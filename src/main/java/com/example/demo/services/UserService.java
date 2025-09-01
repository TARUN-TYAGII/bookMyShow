package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.createUser(user);
    } 

    public User getUserById(Long id){
        return userRepository.getUserById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public User deleteUserById(Long id) {
        return userRepository.deleteUserById(id);
    }

    public User updateUserById(Long id, User req) {
       return userRepository.updateUserById(id, req);
    }
    
}
