package com.prac.journal.controller;

import com.prac.journal.entity.JournalEntry;
import com.prac.journal.entity.User;
import com.prac.journal.repository.UserRepository;
import com.prac.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody User user){
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User with this username already exists!", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb = userService.findbyUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
