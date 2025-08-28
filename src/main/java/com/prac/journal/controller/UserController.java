package com.prac.journal.controller;

import com.prac.journal.entity.JournalEntry;
import com.prac.journal.entity.User;
import com.prac.journal.repository.UserRepository;
import com.prac.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getall(){
        List<User> all = userService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(userService.getAll() , HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return journalEntryService.getAll() ;
    }

    // Will use public for  creating users
//    @PostMapping
//    public ResponseEntity<?> createEntry(@RequestBody User user){
//        try {
////            userService.saveEntry(user);
//            userService.saveNewUser(user);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("User with this username already exists!", HttpStatus.BAD_REQUEST);
//        }
//    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user  ){
        Authentication authentication =   SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findbyUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
