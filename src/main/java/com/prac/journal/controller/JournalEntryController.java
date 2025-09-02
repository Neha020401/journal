package com.prac.journal.controller;


import com.prac.journal.entity.JournalEntry;
import com.prac.journal.entity.User;
import com.prac.journal.service.JournalEntryService;
import com.prac.journal.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getallJournalEntryforUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
        String userName = authentication.getName();
        User user = userService.findbyUserName(userName);
        List<JournalEntry> all = user.getJournalentries();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(journalEntryService.getAll() ,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return journalEntryService.getAll() ;
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@Valid @RequestBody JournalEntry myentries) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
            String userName = authentication.getName();
            User user = userService.findbyUserName(userName);
            myentries.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryService.saveEntry(myentries, userName );
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Can't create entry for user  ${userName}",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntries(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
        String userName = authentication.getName();
       User user =  userService.findbyUserName(userName);
     List<JournalEntry> collect =  user.getJournalentries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

     if(!collect.isEmpty()){
         Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myId);
         if( journalEntry.isPresent()){
             return  new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
         }
     }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntries(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
        String userName = authentication.getName();
        boolean removed =  journalEntryService.deleteById(myId, userName);
        if(removed){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
        }else{
            return   new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
         }

    @PutMapping("id/{id}")
    public ResponseEntity<?> putEntries(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
        String userName = authentication.getName();
        User user =  userService.findbyUserName(userName);
        List<JournalEntry> collect =  user.getJournalentries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        JournalEntry old = journalEntryService.findbyId(id).orElse(null);

        if (old != null) {
            old.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("")? updatedEntry.getTitle() : old.getTitle());
            old.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new  ResponseEntity<>(HttpStatus.OK) ;
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}

