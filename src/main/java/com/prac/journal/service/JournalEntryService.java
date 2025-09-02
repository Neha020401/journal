package com.prac.journal.service;


import com.prac.journal.entity.JournalEntry;
import com.prac.journal.entity.User;
import com.prac.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findbyUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalentries().add(saved);
            userService.saveUser(user);
            return  saved ;
        } catch (Exception e) {
            throw new RuntimeException("An Error has occured  while saving the entry .",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll() ;
    }

    public Optional<JournalEntry> findbyId(ObjectId id){
        return  journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false ;
        try{
            User user = userService.findbyUserName(userName);
             removed = user.getJournalentries().removeIf(x -> x.getId().equals(id));
            if(!removed){
                userService.saveNewUser(user);
                journalEntryRepository.deleteById(id);
            }

        } catch (Exception e){
              System.out.println(e);
              throw  new RuntimeException("An error occured  while delete the entry ",e);
        }
        return  removed ;
        }
}

