package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public User createUser(User user){
        // Set ID and timestamps
        user.setId(idGenerator.getAndIncrement());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        
        users.add(user);
        return user;
    }

    public User getUserById(Long id){
        User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        System.out.println("User from repo: " + user);
        return user;
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(users);
    }

    public User deleteUserById(Long id) {
        User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        users.remove(user);
        return user;
    }
}
