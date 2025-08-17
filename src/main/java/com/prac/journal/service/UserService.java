package com.prac.journal.service;

import com.prac.journal.entity.JournalEntry;
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

    public void saveEntry(User user ){
        userRepository.save(user);
    }

    public  List<User> getAll(){
        return userRepository.findAll() ;
    }

    public User findbyUserName(String userName){
        return userRepository.findByUserName(userName);
    }

//    public void deleteByUserName(String userName){
//        userRepository.deleteByUserName(userName);
//    }
}
