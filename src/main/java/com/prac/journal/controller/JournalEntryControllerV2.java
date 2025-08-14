package com.prac.journal.controller;


import com.prac.journal.entity.JournalEntry;
import com.prac.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getall(){
        List<JournalEntry> all = journalEntryService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return journalEntryService.getAll() ;
    }

    @PostMapping
    public ResponseEntity<JournalEntry> CreatedEntry(@RequestBody JournalEntry myentries){
        try{
            myentries.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myentries);
            return  new ResponseEntity<>(myentries, HttpStatus.CREATED);
//            return myentries ;
        }catch (Exception e){
            return  new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntries(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myId);
        if( journalEntry.isPresent()){
            return  new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return journalEntryService.findbyId(myId).orElse(null) ;
    }


    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntries(@PathVariable ObjectId myId){
         journalEntryService.deleteById(myId);
         return  new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> putEntries(@PathVariable ObjectId id , @RequestBody JournalEntry updatedEntry){
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

