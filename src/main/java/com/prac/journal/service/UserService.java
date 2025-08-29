package com.prac.journal.service;

import com.prac.journal.entity.JournalEntry;
import com.prac.journal.entity.User;
import com.prac.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private  static  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    public void saveEntry(User user ){
//        userRepository.save(user);
//    }

    public void saveNewUser(User user ){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public  List<User> getAll(){
        return userRepository.findAll() ;
    }

    public User findbyUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }

    public void updateUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

}
