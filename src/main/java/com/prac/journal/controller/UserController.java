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
    @Autowired
    private  UserRepository userRepository ;

    @GetMapping
    public ResponseEntity<?> getall(){
        List<User> all = userService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(userService.getAll() , HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return journalEntryService.getAll() ;
    }



    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user  ){
        Authentication authentication =   SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findbyUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByuserName(){
        Authentication authentication =   SecurityContextHolder.getContext().getAuthentication();
       String user = authentication.getName();
       userService.deleteByUserName(user);
       return new ResponseEntity<>("User ${user} deleted " , HttpStatus.NO_CONTENT);

    }

}
