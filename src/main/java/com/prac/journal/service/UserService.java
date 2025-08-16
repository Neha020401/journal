package com.prac.journal.service;


import com.prac.journal.entity.User;
import com.prac.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User userEntry ){
        userRepository.save(userEntry);
    }

    public List<User> getAll(){
        return userRepository.findAll() ;
    }

    public Optional<User> findbyId(ObjectId id){
        return  userRepository.findById(id);
    }

    public void deleteById(ObjectId id ){
        userRepository.deleteById(id);
    }
}
