package com.prac.journal.service;

import com.prac.journal.entity.User;
import com.prac.journal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private  static  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user ){
        userRepository.save(user);
    }

    public void saveNewUser(User user ){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            logger.info("This info might from error to info the  same user is not allowed to create account");
            throw new RuntimeException(e);
        }
      }

    public void saveAdmin(User user ){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Arrays.asList("USER","ADMIN"));
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
