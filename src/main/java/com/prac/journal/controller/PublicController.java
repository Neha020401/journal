package com.prac.journal.controller;

import com.prac.journal.entity.User;
import com.prac.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Okayy ! ";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createEntry(@RequestBody User user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User with this username already exists!", HttpStatus.BAD_REQUEST);
        }
    }

}
