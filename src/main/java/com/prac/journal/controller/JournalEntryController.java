package com.prac.journal.controller;

import com.prac.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.*;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries = new HashMap<>();


   @GetMapping
   public List<JournalEntry> getall(){
//     return new ArrayList<>(journalEntries.values());
       return null;
   }

   @PostMapping
   public boolean CreatedEntry(@RequestBody JournalEntry myentries){
//       journalEntries.put(myentries.getId(), myentries);
       return true ;
   }

   @GetMapping("id/{myId}")
   public JournalEntry getJournalEntries(@PathVariable Long myId){
//      return  journalEntries.get(myId);
   return null ;
   }


    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntries(@PathVariable Long myId){
//        return  journalEntries.remove(myId);
        return null ;
    }

    @PutMapping("id/{id}")
    public JournalEntry putEntries(@PathVariable Long id , @RequestBody JournalEntry myEntries){
//     return  journalEntries.put(id,myEntries);
   return null ;
   }

}
